package com.cashregister.demo.transaction;

import com.cashregister.demo.item.Item;
import com.cashregister.demo.product.Product;
import com.cashregister.demo.product.ProductRepository;
import com.cashregister.demo.customer.Customer;
import com.cashregister.demo.customer.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    private final TransactionRepository transactionRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public TransactionController(TransactionRepository transactionRepository, ProductRepository productRepository, CustomerRepository customerRepository) {
        this.transactionRepository = transactionRepository;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
    }

    @RequestMapping(value = "")
    List<Transaction> listTransactions() {
        Iterable<Transaction> iterable = transactionRepository.findAll();
        List<Transaction> transactions = new ArrayList<>();
        iterable.forEach(transactions::add);
        return transactions;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    Transaction create(@RequestBody List<Product> products, @RequestParam("customer_id") Long customerId) {
        // Get user
        Optional<Customer> customer = customerRepository.findById(customerId);

        // Get unique skus to get the product objects from the database (source of truth).
        Set<Long> productIds = new HashSet<>();
        for (final Product product : products) {
            productIds.add(product.getId());
        }
        Iterable<Product> productsFromDb = productRepository.findAllById(productIds);

        // Calculate product quantity
        Map<String, Integer> productQuantity = new HashMap<>();
        for(Product product : products) {
            if(productQuantity.containsKey(product.getSku())) {
                productQuantity.put(product.getSku(), productQuantity.get(product.getSku()) + 1);
            } else {
                productQuantity.put(product.getSku(), 1);
            }
        }

        // Calculate total cost
        double total = 0;
        boolean hasLoyaltyNumber = customer.get().getLoyaltyNumber().isEmpty();
        for(Product product : productsFromDb) {
            if(hasLoyaltyNumber) {
               total += product.getDiscountedPrice() * productQuantity.get(product.getSku());
            } else {
                total += product.getDefaultPrice() * productQuantity.get(product.getSku());
            }
        }

        // Calculate item total.
        Map<String, Double> productTotal = new HashMap<>();
        for(Product product : productsFromDb) {
            if(hasLoyaltyNumber) {
            productTotal.put(product.getSku(), productQuantity.get(product.getSku()) * product.getDiscountedPrice());
            } else {
                productTotal.put(product.getSku(), productQuantity.get(product.getSku()) * product.getDefaultPrice());
            }
        }

        // Create transaction
        Transaction transaction = new Transaction();
        transaction.setTotal(total);

        List<Item> items = new ArrayList<>();
        for(Product product : productsFromDb) {
            Item item = new Item(productTotal.get(product.getSku()), productQuantity.get(product.getSku()), product);
            items.add(item);
        }
        transaction.setItems(items);
        transactionRepository.save(transaction);
        return transaction;
    }
}
