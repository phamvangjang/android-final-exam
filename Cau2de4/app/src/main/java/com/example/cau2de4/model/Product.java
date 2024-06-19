package com.example.cau2de4.model;

public class Product {
    String productCode;
    String productName;
    double productPrice;

    //constructor

    public Product(String productCode, String productName, double productPrice) {
        this.productCode = productCode;
        this.productName = productName;
        this.productPrice = productPrice;
    }

    //getter and setter

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }
}
