package com.example.cau2de5.model;

public class Book {
    String bookCode;
    String bookName;
    double bookPrice;

    //constructor

    public Book(String bookCode, String bookName, double bookPrice) {
        this.bookCode = bookCode;
        this.bookName = bookName;
        this.bookPrice = bookPrice;
    }

    //getter and setter

    public String getBookCode() {
        return bookCode;
    }

    public void setBookCode(String bookCode) {
        this.bookCode = bookCode;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public double getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(double bookPrice) {
        this.bookPrice = bookPrice;
    }
}
