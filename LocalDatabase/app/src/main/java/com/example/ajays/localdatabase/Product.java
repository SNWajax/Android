package com.example.ajays.localdatabase;

public class Product {

    private int _id;
    private String _productname;

    public Product(){

    }

    public Product(String productname) {
        this._productname = productname;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_productname() {
        return _productname;
    }

    public void set_productName(String productname) {
        this._productname = productname;
    }
}
