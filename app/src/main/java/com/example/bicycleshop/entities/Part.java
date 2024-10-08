package com.example.bicycleshop.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "parts" )
public class Part {
    @PrimaryKey(autoGenerate = true)
    private int partID;
    private String partName;
    private double price;
    private int productID;

    public Part(int partID,  String partName, double price, int productID) {
        this.partID = partID;
        this.price = price;
        this.partName = partName;
        this.productID = productID;
    }

    public int getPartID() {
        return partID;
    }

    public void setPartID(int partID) {
        this.partID = partID;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }
}
