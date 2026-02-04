package org.example;

import org.example.IO.ProductIO;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ProductIO productIO = new ProductIO();
        Scanner scanner = new Scanner(System.in);
        
        try {
            productIO.loadProductsFromFile();
            if (productIO.getProducts().isEmpty()) {
                productIO.initializeProducts();
            }
            
            while (true) {
                System.out.println("\n=== Product Management System ===");
                System.out.println("1. View Products");
                System.out.println("2. Buy Product");
                System.out.println("3. Exit");
                System.out.print("Choose option: ");
                
                int choice = scanner.nextInt();
                scanner.nextLine();
                
                switch (choice) {
                    case 1:
                        productIO.displayProducts();
                        break;
                    case 2:
                        System.out.print("Enter customer name: ");
                        String customerName = scanner.nextLine();
                        System.out.print("Enter product ID: ");
                        String productId = scanner.nextLine();
                        System.out.print("Enter quantity: ");
                        int quantity = scanner.nextInt();
                        
                        if (productIO.buyProduct(customerName, productId, quantity)) {
                            System.out.println("Purchase successful! Check purchases.xlsx");
                        } else {
                            System.out.println("Purchase failed! Product not found or insufficient quantity.");
                        }
                        break;
                    case 3:
                        System.out.println("Goodbye!");
                        return;
                    default:
                        System.out.println("Invalid option!");
                }
            }
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}