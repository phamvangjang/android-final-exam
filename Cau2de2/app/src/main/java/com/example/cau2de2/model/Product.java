package com.example.cau2de2.model;

public class Product {
    String ProductCode;
    String ProductName;
    double ProductPrice;

    //constructor

    public Product(String productCode, String productName, double productPrice) {
        ProductCode = productCode;
        ProductName = productName;
        ProductPrice = productPrice;
    }

    //getter and setter

    public String getProductCode() {
        return ProductCode;
    }

    public void setProductCode(String productCode) {
        ProductCode = productCode;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public double getProductPrice() {
        return ProductPrice;
    }

    public void setProductPrice(double productPrice) {
        ProductPrice = productPrice;
    }
}
