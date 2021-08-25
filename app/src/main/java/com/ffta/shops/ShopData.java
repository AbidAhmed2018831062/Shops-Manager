package com.ffta.shops;

public class ShopData {
    String name,item,p_b;
    long amount;

    public ShopData(String name, String item, long amount, String p_b) {
        this.name = name;
        this.item = item;
        this.p_b = p_b;
        this.amount = amount;
    }
    public ShopData()
    {}


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getP_b() {
        return p_b;
    }

    public void setP_b(String p_b) {
        this.p_b = p_b;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }
}
