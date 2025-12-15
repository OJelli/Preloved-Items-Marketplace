package com.example.prelovedmarketplaceapp.model;

public class SustainabilityScore {
    private static final int WEIGHT_LISTING = 10;
    private static final int WEIGHT_SOLD = 20;
    private static final int WEIGHT_PURCHASED = 5;
    private static final int MAX_SCORE_LIMIT = 100;

    public static int calculateScore(int listingsCreated, int itemsSold, int itemsPurchased) {
        int score = 0;
        score += listingsCreated * WEIGHT_LISTING;
        score += itemsSold * WEIGHT_SOLD;
        score += itemsPurchased * WEIGHT_PURCHASED;

        // Cap the score at 100
        return Math.min(score, MAX_SCORE_LIMIT);
    }
}