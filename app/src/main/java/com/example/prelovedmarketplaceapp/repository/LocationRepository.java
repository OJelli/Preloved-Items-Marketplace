package com.example.prelovedmarketplaceapp.repository;

import com.example.prelovedmarketplaceapp.model.PickupLocation;
import java.util.Arrays;
import java.util.List;

public class LocationRepository {

    // List of predefined locations for the dropdown
    private static final List<PickupLocation> LOCATIONS = Arrays.asList(
            new PickupLocation("Mid Valley Megamall", "Lingkaran Syed Putra, Mid Valley City, 59200 Kuala Lumpur"),
            new PickupLocation("Sunway Pyramid", "3, Jalan PJS 11/15, Bandar Sunway, 47500 Petaling Jaya"),
            new PickupLocation("One Utama Shopping Centre", "Bandar Utama, 47800 Petaling Jaya"),
            new PickupLocation("Setia City Mall", "Persiaran Setia Dagang, Setia Alam, 40170 Shah Alam"),
            new PickupLocation("Puchong IOI Mall", "Laman Puteri 3, Bandar Puteri, 47100 Puchong")
    );

    public static List<PickupLocation> getAllLocations() {
        return LOCATIONS;
    }
}