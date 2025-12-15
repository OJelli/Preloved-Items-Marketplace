package com.example.prelovedmarketplaceapp.repository;

import com.example.prelovedmarketplaceapp.model.Listing;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ListingRepository {

    private static final List<Listing> LISTINGS = new ArrayList<>();

    static {
        loadDummyData();
    }

    private static void loadDummyData() {
        // --- Sample Location Data (Name, Address, Map Thumbnail Drawable Name) ---
        // NOTE: The Listing constructor must accept 10 arguments now (as defined in Listing.java)
        String midValley = "Mid Valley Megamall, Kuala Lumpur";
        String midValleyMap = "map_thumbnail_midvalley";

        String sunway = "Sunway Pyramid, Petaling Jaya";
        String sunwayMap = "map_thumbnail_sunway";

        String ioiMall = "Puchong IOI Mall, Puchong";
        String ioiMallMap = "map_thumbnail_ioi_mall";
        // ----------------------------------------

        // Sample Listing 1
        LISTINGS.add(new Listing(
                UUID.randomUUID().toString(),
                "Blue Backpack (Used)",
                "A sturdy blue backpack, perfect for university students. Minor wear and tear.",
                35.00,
                "Fashion",
                "Siti",
                "android.resource://com.example.prelovedmarketplaceapp/drawable/dummy_backpack",
                System.currentTimeMillis() - 86400000 * 5,
                midValley,
                midValleyMap // 10th argument
        ));

        // Sample Listing 2
        LISTINGS.add(new Listing(
                UUID.randomUUID().toString(),
                "Vintage Ceramic Mug Set",
                "Set of 4 hand-painted ceramic mugs, excellent condition.",
                20.00,
                "Home & Furniture",
                "Aiman",
                "android.resource://com.example.prelovedmarketplaceapp/drawable/dummy_mugs",
                System.currentTimeMillis() - 86400000 * 2,
                sunway,
                sunwayMap // 10th argument
        ));

        // Sample Listing 3
        LISTINGS.add(new Listing(
                UUID.randomUUID().toString(),
                "Old Guitar for Beginner",
                "Acoustic guitar, perfect for a beginner. Needs new strings.",
                80.00,
                "Toys & Hobbies",
                "Siti",
                "android.resource://com.example.prelovedmarketplaceapp/drawable/dummy_guitar",
                System.currentTimeMillis() - 86400000,
                ioiMall,
                ioiMallMap // 10th argument
        ));
    }

    // --- CRUD Operations ---

    public static void addListing(Listing listing) { // Already static, kept for completeness
        LISTINGS.add(listing);
    }

    public static List<Listing> getAllListings() { // 🎯 FIX: 'static' keyword added 🎯
        return LISTINGS;
    }

    public static Listing getListingById(String id) {
        return LISTINGS.stream()
                .filter(listing -> listing.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public static List<Listing> getListingsBySeller(String sellerId) {
        return LISTINGS.stream()
                .filter(listing -> listing.getSellerId().equals(sellerId))
                .collect(Collectors.toList());
    }
}