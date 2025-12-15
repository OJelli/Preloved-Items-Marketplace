package com.example.prelovedmarketplaceapp.model;

public class PickupLocation {
    private String name;
    private String address;

    public PickupLocation(String name, String address) {
        this.name = name;
        this.address = address;
    }

    // --- Getters ---
    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    // Override toString to display only the name in the Spinner/Dropdown
    @Override
    public String toString() {
        return name;
    }
}