package com.cashregister.demo.transaction;

import com.cashregister.demo.item.Item;
import com.cashregister.demo.product.Product;
import com.cashregister.demo.product.ProductRepository;
import com.cashregister.demo.customer.Customer;
import com.cashregister.demo.customer.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

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
    ResponseEntity<Transaction> create(@RequestBody List<Product> products, @RequestParam("customer_id") Long customerId) {
        // Get user
        Optional<Customer> customer = customerRepository.findById(customerId);

        // Get unique skus to get the product objects from the database (source of truth).
        Set<Long> productIds = new HashSet<>();
        for (final Product product : products) {
            productIds.add(product.getId());
        }
        Iterable<Product> productsFromDb = productRepository.findAllById(productIds);
        List<Product> products1 = new ArrayList<>();
        productsFromDb.forEach(products1::add);

        if(products1.size() != productIds.size()) {
            return ResponseEntity.badRequest().body(null);
        }

        // Calculate product quantity
        Map<Long, Long> productQuantity2 = products.stream()
                        .collect(groupingBy(Product::getId, counting()));


        Map<Long, Integer> productQuantity = new HashMap<>();
        for(Product product : products) {
            if(productQuantity.containsKey(product.getId())) {
                productQuantity.put(product.getId(), productQuantity.get(product.getId()) + 1);
            } else {
                productQuantity.put(product.getId(), 1);
            }
        }

        // Calculate total cost
        double total = 0;
        boolean hasLoyaltyNumber = !customer.get().getLoyaltyNumber().isEmpty();
        Map<Long, Double> productTotal = new HashMap<>();
        for(Product product : productsFromDb) {
            if(hasLoyaltyNumber) {
               total += product.getDiscountedPrice() * productQuantity.get(product.getId());
                productTotal.put(product.getId(), productQuantity.get(product.getId()) * product.getDiscountedPrice());
            } else {
                total += product.getDefaultPrice() * productQuantity.get(product.getId());
                productTotal.put(product.getId(), productQuantity.get(product.getId()) * product.getDefaultPrice());
            }
        }

        // Create transaction
        Transaction transaction = new Transaction();
        transaction.setTotal(total);

        List<Item> items = new ArrayList<>();
        for(Product product : productsFromDb) {
            Item item = new Item(productTotal.get(product.getId()), productQuantity.get(product.getId()), product);
            items.add(item);
        }
        transaction.setItems(items);
        transactionRepository.save(transaction);
        return ResponseEntity.ok().body(transaction);
    }
}
