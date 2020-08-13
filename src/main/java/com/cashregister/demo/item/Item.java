package com.cashregister.demo.item;

import com.cashregister.demo.core.BaseEntity;
import com.cashregister.demo.product.Product;
import com.cashregister.demo.transaction.Transaction;

import javax.persistence.*;

@Entity
public class Item extends BaseEntity {

    private double total;
    private int quantity;

    @ManyToOne
    private Transaction transaction;

    private Product product;

    protected Item() {
        super();
    }

    public double getTotal() {
        return total;
    }

    public Item(double total, int quantity, Product product) {
        this.total = total;
        this.quantity = quantity;
        this.product = product;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
