package org.example.IO;

public class Purchase {
    private String customerName;
    private Product product;
    private int quantityBought;

    public Purchase(String customerName, Product product, int quantityBought) {
        this.customerName = customerName;
        this.product = product;
        this.quantityBought = quantityBought;
    }

    public String getCustomerName() { return customerName; }
    public Product getProduct() { return product; }
    public int getQuantityBought() { return quantityBought; }

    @Override
    public String toString() {
        return customerName + "," + product.getProductId() + "," + product.getProductName() + "," + 
               product.getProductPrice() + "," + quantityBought;
    }
}