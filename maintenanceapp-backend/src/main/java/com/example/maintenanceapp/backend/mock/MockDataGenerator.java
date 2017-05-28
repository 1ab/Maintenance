package com.example.maintenanceapp.backend.mock;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.example.maintenanceapp.data.Category;
import com.example.maintenanceapp.data.Job;
import com.example.maintenanceapp.data.WorkOrder;
import com.example.maintenanceapp.data.Workbook;
import com.example.maintenanceapp.enums.Availability;
import com.example.maintenanceapp.enums.Priority;
import com.example.maintenanceapp.enums.Status;

public class MockDataGenerator {
    private static int nextCategoryId = 1;
    private static int nextProductId = 1;
    private static int nextWorkbookId = 1;
    private static int nextWorkOrderId = 1;
    private static final Random random = new Random(1);
    private static final String categoryNames[] = new String[] {
            "Children's books", "Best sellers", "Romance", "Mystery",
            "Thriller", "Sci-fi", "Non-fiction", "Cookbooks" };

    private static String[] word1 = new String[] { "The art of", "Mastering",
            "The secrets of", "Avoiding", "For fun and profit: ",
            "How to fail at", "10 important facts about",
            "The ultimate guide to", "Book of", "Surviving", "Encyclopedia of",
            "Very much", "Learning the basics of", "The cheap way to",
            "Being awesome at", "The life changer:", "The Vaadin way:",
            "Becoming one with", "Beginners guide to",
            "The complete visual guide to", "The mother of all references:" };

    private static String[] word2 = new String[] { "gardening",
            "living a healthy life", "designing tree houses", "home security",
            "intergalaxy travel", "meditation", "ice hockey",
            "children's education", "computer programming", "Vaadin TreeTable",
            "winter bathing", "playing the cello", "dummies", "rubber bands",
            "feeling down", "debugging", "running barefoot",
            "speaking to a big audience", "creating software", "giant needles",
            "elephants", "keeping your wife happy" };

    static List<Category> createCategories() {
        List<Category> categories = new ArrayList<Category>();
        for (String name : categoryNames) {
            Category c = createCategory(name);
            categories.add(c);
        }
        return categories;

    }

    static List<Job> createProducts(List<Category> categories) {
        List<Job> jobs = new ArrayList<Job>();
        for (int i = 0; i < 100; i++) {
            Job p = createProduct(categories);
            jobs.add(p);
        }

        return jobs;
    }
    
    static List<Workbook> createWorkbooks() {
        List<Workbook> workbook = new ArrayList<Workbook>();
        for (int i = 0; i < 100; i++) {
        	Workbook p = createWorkbook();
            workbook.add(p);
        }

        return workbook;
    }
    
    static List<WorkOrder> createWorkOrders() {
        List<WorkOrder> workbook = new ArrayList<WorkOrder>();
        for (int i = 0; i < 100; i++) {
        	WorkOrder p = createWorkOrder();
            workbook.add(p);
        }

        return workbook;
    }

    private static Category createCategory(String name) {
        Category c = new Category();
        c.setId(nextCategoryId++);
        c.setName(name);
        return c;
    }

    private static Job createProduct(List<Category> categories) {
        Job p = new Job();
        p.setId(nextProductId++);
        p.setItem(generateName());

        p.setFrequency((random.nextInt(250) + 50) / 10);
        p.setStatus(Status.values()[random.nextInt(Status
                .values().length)]);
        if (p.getStatus() == Status.DUE) {
            p.setWhenDue(random.nextInt(523));
        }

        p.setPriority(Priority.NORMAL);
        return p;
    }
    
    private static Workbook createWorkbook() {
        Workbook p = new Workbook();
        p.setId(nextWorkbookId++);
        p.setName(generateName());

        p.setType(generateName());

        return p;
    }
    
    private static WorkOrder createWorkOrder() {
    	WorkOrder p = new WorkOrder();
        p.setId(nextWorkOrderId++);
        p.setName(generateName());

        p.setType(generateName());

        return p;
    }

    private static Set<Category> getCategory(List<Category> categories,
            int min, int max) {
        int nr = random.nextInt(max) + min;
        HashSet<Category> productCategories = new HashSet<Category>();
        for (int i = 0; i < nr; i++) {
            productCategories.add(categories.get(random.nextInt(categories
                    .size())));
        }

        return productCategories;
    }

    private static String generateName() {
        return word1[random.nextInt(word1.length)] + " "
                + word2[random.nextInt(word2.length)];
    }

}
