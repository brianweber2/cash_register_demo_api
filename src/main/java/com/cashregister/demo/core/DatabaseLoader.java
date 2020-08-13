package com.cashregister.demo.core;

import com.cashregister.demo.product.Product;
import com.cashregister.demo.product.ProductRepository;
import com.cashregister.demo.transaction.TransactionRepository;
import com.cashregister.demo.customer.Customer;
import com.cashregister.demo.customer.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DatabaseLoader implements ApplicationRunner {
    private final TransactionRepository transactions;
    private final ProductRepository products;
    private final CustomerRepository customers;

    @Autowired
    public DatabaseLoader(TransactionRepository transactions, ProductRepository products, CustomerRepository customers) {
        this.transactions = transactions;
        this.products = products;
        this.customers = customers;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<Product> productsDb = new ArrayList<>();
        Product product = new Product("Scott 1000 Septic Safe Toilet Paper", "6392261230", 13.99, 9.99);
        Product product1 = new Product("Eggs", "9862865625", 4.99, 3.99);
        Product product2 = new Product("Orange Juice", "1711712464", 5.99, 5.49);
        Product product3 = new Product("Bananas", "2494535318", 2.99, 1.99);
        Product product4 = new Product("Swifter", "2817733627", 24.99, 22.99);
        productsDb.add(product);
        productsDb.add(product1);
        productsDb.add(product2);
        productsDb.add(product3);
        productsDb.add(product4);
        products.saveAll(productsDb);

        Customer customer = new Customer("1234567854", "Weber", "3497419812");
        customers.save(customer);

//        Transaction transaction = new Transaction();
//
//        Item item = new Item(product.getDefaultPrice(), 1);
//        item.setProduct(product);
//        Item item1 = new Item(product1.getDefaultPrice(), 1);
//        item1.setProduct(product1);
//
//        double total = (item.getTotal() * item.getQuantity())  + (item1.getTotal() * item1.getQuantity());
//
//        transaction.setTotal(total);
//        transaction.addItem(item);
//        transaction.addItem(item1);
//        transactions.save(transaction);
    }
}
