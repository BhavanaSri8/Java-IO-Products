package org.example.IO;

public class Product {
    private String productId;
    private String productName;
    private double productPrice;
    private int quantity;

    public Product(String productId, String productName, double productPrice, int quantity) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.quantity = quantity;
    }

    public String getProductId() { return productId; }
    public String getProductName() { return productName; }
    public double getProductPrice() { return productPrice; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    @Override
    public String toString() {
        return productId + "," + productName + "," + productPrice + "," + quantity;
    }
}