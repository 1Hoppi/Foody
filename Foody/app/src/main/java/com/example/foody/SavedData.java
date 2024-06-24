package com.example.foody;

import java.util.ArrayList;
import java.util.HashMap;

public class SavedData {
    public static final String ip = "5.79.175.224:1010"; // Local server womp womp
    public static ArrayList<HashMap> menu = new ArrayList<>();
    public static ArrayList<HashMap> special_offers = new ArrayList<>();
    public static HashMap<Integer, Integer> cart = new HashMap<>();
    public static ArrayList<HashMap> orders = new ArrayList<>();
    public static int cartSize = 0;
    public static int orderPrice = 0;
}
