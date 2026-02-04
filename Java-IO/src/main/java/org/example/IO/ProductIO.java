package org.example.IO;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class ProductIO {
    private static final String PRODUCTS_FILE = "src/main/resources/products.xlsx";
    private static final String PURCHASES_FILE = "src/main/resources/purchases.xlsx";

    private List<Product> products = new ArrayList<>();
    private List<Purchase> purchases = new ArrayList<>();

    public List<Product> getProducts() { return products; }

    public void loadProductsFromFile() throws IOException {

        InputStream is = getClass().getClassLoader()
                .getResourceAsStream("products.xlsx");

        if (is == null) {
            System.out.println("products.xlsx not found! Creating new file...");
            initializeProducts();
            return;
        }

        products.clear();

        Workbook workbook = new XSSFWorkbook(is);
        Sheet sheet = workbook.getSheetAt(0);

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {

            Row row = sheet.getRow(i);

            if (row != null) {

                String id = row.getCell(0).getStringCellValue();
                String name = row.getCell(1).getStringCellValue();
                double price = row.getCell(2).getNumericCellValue();
                int quantity = (int) row.getCell(3).getNumericCellValue();

                products.add(new Product(id, name, price, quantity));
            }
        }

        workbook.close();
    }



    private String getCellValue(Cell cell) {
        if (cell == null) return "";
        return cell.getCellType() == CellType.STRING ? 
            cell.getStringCellValue() : String.valueOf((int)cell.getNumericCellValue());
    }

    private double getNumericValue(Cell cell) {
        if (cell == null) return 0;
        return cell.getCellType() == CellType.NUMERIC ? 
            cell.getNumericCellValue() : Double.parseDouble(cell.getStringCellValue());
    }

    public void initializeProducts() throws IOException {
        products.add(new Product("P001", "Laptop", 50000.0, 10));
        products.add(new Product("P002", "Mouse", 500.0, 50));
        products.add(new Product("P003", "Keyboard", 1500.0, 30));
        products.add(new Product("P004", "Monitor", 15000.0, 20));
        products.add(new Product("P005", "Headphones", 2000.0, 40));
        products.add(new Product("P006", "Webcam", 3500.0, 25));
        products.add(new Product("P007", "USB Cable", 200.0, 100));
        products.add(new Product("P008", "Hard Drive", 5000.0, 15));
        products.add(new Product("P009", "SSD", 8000.0, 12));
        products.add(new Product("P010", "RAM", 4000.0, 35));
        products.add(new Product("P011", "Graphics Card", 35000.0, 8));
        products.add(new Product("P012", "Printer", 12000.0, 10));
        products.add(new Product("P013", "Scanner", 8000.0, 15));
        products.add(new Product("P014", "Router", 3000.0, 30));
        products.add(new Product("P015", "Ethernet Cable", 150.0, 80));
        products.add(new Product("P016", "Power Bank", 1800.0, 45));
        products.add(new Product("P017", "Tablet", 25000.0, 18));
        products.add(new Product("P018", "Smartwatch", 15000.0, 22));
        products.add(new Product("P019", "Speaker", 4500.0, 28));
        products.add(new Product("P020", "Microphone", 5500.0, 20));
        saveProductsToFile();
    }

    public void saveProductsToFile() throws IOException {
        Path filePath = Paths.get(PRODUCTS_FILE);
        Files.createDirectories(filePath.getParent());
        
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Products");
        
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Product ID");
        header.createCell(1).setCellValue("Product Name");
        header.createCell(2).setCellValue("Product Price");
        header.createCell(3).setCellValue("Quantity");

        for (int i = 0; i < products.size(); i++) {
            Row row = sheet.createRow(i + 1);
            Product p = products.get(i);
            row.createCell(0).setCellValue(p.getProductId());
            row.createCell(1).setCellValue(p.getProductName());
            row.createCell(2).setCellValue(p.getProductPrice());
            row.createCell(3).setCellValue(p.getQuantity());
        }

        try (OutputStream os = Files.newOutputStream(Paths.get(PRODUCTS_FILE))) {
            workbook.write(os);
        }
        workbook.close();
    }

    public void savePurchasesToFile() throws IOException {
        Path filePath = Paths.get(PURCHASES_FILE);
        Files.createDirectories(filePath.getParent());
        
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Purchases");
        
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Customer Name");
        header.createCell(1).setCellValue("Product ID");
        header.createCell(2).setCellValue("Product Name");
        header.createCell(3).setCellValue("Product Price");
        header.createCell(4).setCellValue("Quantity Bought");

        for (int i = 0; i < purchases.size(); i++) {
            Row row = sheet.createRow(i + 1);
            Purchase p = purchases.get(i);
            row.createCell(0).setCellValue(p.getCustomerName());
            row.createCell(1).setCellValue(p.getProduct().getProductId());
            row.createCell(2).setCellValue(p.getProduct().getProductName());
            row.createCell(3).setCellValue(p.getProduct().getProductPrice());
            row.createCell(4).setCellValue(p.getQuantityBought());
        }

        try (OutputStream os = Files.newOutputStream(Paths.get(PURCHASES_FILE))) {
            workbook.write(os);
        }
        workbook.close();
    }

    public boolean buyProduct(String customerName, String productId, int quantity) throws IOException {
        Product product = products.stream()
                .filter(p -> p.getProductId().equals(productId))
                .findFirst().orElse(null);
        
        if (product == null || product.getQuantity() < quantity) {
            return false;
        }
        
        product.setQuantity(product.getQuantity() - quantity);
        purchases.add(new Purchase(customerName, product, quantity));
        
        saveProductsToFile();
        savePurchasesToFile();
        return true;
    }

    public void displayProducts() {
        System.out.println("Available Products:");
        System.out.println("ID\tName\t\tPrice\tQuantity");
        for (Product p : products) {
            System.out.printf("%s\t%s\t\t%.2f\t%d%n", 
                p.getProductId(), p.getProductName(), p.getProductPrice(), p.getQuantity());
        }
    }
}
