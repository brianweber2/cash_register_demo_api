package com.cashregister.demo.product;

import com.cashregister.demo.core.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Product extends BaseEntity {
    private String name;
    @Column(unique = true)
    private String sku;
    private double defaultPrice;
    private double discountedPrice;

    protected Product() {
        super();
    }

    public Product(String name, String sku, double defaultPrice, double discountedPrice) {
        this();
        this.name = name;
        this.sku = sku;
        this.defaultPrice = defaultPrice;
        this.discountedPrice = discountedPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDefaultPrice() {
        return defaultPrice;
    }

    public void setDefaultPrice(double defaultPrice) {
        this.defaultPrice = defaultPrice;
    }

    public double getDiscountedPrice() {
        return discountedPrice;
    }

    public void setDiscountedPrice(double discountedPrice) {
        this.discountedPrice = discountedPrice;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }
}
