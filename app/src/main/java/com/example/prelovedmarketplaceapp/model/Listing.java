package com.example.prelovedmarketplaceapp.model;

import java.io.Serializable;

public class Listing implements Serializable {

    private String id;
    private String title;
    private String description;
    private double price;
    private String category;
    private String imageUrl;
    private String sellerId;
    private long timestamp;
    private String pickupLocation;

    // 🎯 NEW FIELD: Map Thumbnail Reference 🎯
    private String mapThumbnail;

    // 🎯 UPDATED CONSTRUCTOR (10 arguments) 🎯
    public Listing(String id, String title, String description, double price, String category, String sellerId, String imageUrl, long timestamp, String pickupLocation, String mapThumbnail) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.category = category;
        this.sellerId = sellerId;
        this.imageUrl = imageUrl;
        this.timestamp = timestamp;
        this.pickupLocation = pickupLocation;
        this.mapThumbnail = mapThumbnail; // Initialize new field
    }

    // --- Getters ---
    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public double getPrice() { return price; }
    public String getCategory() { return category; }
    public String getImageUrl() { return imageUrl; }
    public String getSellerId() { return sellerId; }
    public long getTimestamp() { return timestamp; }
    public String getPickupLocation() { return pickupLocation; }

    // 🎯 NEW GETTER 🎯
    public String getMapThumbnail() { return mapThumbnail; }
}