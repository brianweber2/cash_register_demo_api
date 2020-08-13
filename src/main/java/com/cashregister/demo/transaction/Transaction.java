package com.cashregister.demo.transaction;

import com.cashregister.demo.core.BaseEntity;

import com.cashregister.demo.item.Item;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.List;

@Entity
public class Transaction extends BaseEntity {
    private double total;

    @OneToMany(mappedBy = "transaction", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Item> items;

    public Transaction() {
        super();
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public List<Item> getItems() {
        return items;
    }

    @JsonProperty
    public void setItems(List<Item> items) {
        this.items = items;
    }
}
