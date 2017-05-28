package com.example.maintenanceapp.backend.mock;

import java.util.List;

import com.example.maintenanceapp.data.Category;
import com.example.maintenanceapp.data.Job;
import com.example.maintenanceapp.service.JobService;

/**
 * Mock data model. This implementation has very simplistic locking and does not
 * notify users of modifications.
 */
public class MockJobService extends JobService {

    private static MockJobService INSTANCE;

    private List<Job> jobs;
    private List<Category> categories;
    private int nextProductId = 0;

    private MockJobService() {
        categories = MockDataGenerator.createCategories();
        jobs = MockDataGenerator.createProducts(categories);
        nextProductId = jobs.size() + 1;
    }

    public synchronized static JobService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MockJobService();
        }
        return INSTANCE;
    }

    @Override
    public synchronized List<Job> getAllProducts() {
        return jobs;
    }

    @Override
    public synchronized List<Category> getAllCategories() {
        return categories;
    }

    @Override
    public synchronized void updateProduct(Job p) {
        if (p.getId() < 0) {
            // New product
            p.setId(nextProductId++);
            jobs.add(p);
            return;
        }
        for (int i = 0; i < jobs.size(); i++) {
            if (jobs.get(i).getId() == p.getId()) {
                jobs.set(i, p);
                return;
            }
        }

        throw new IllegalArgumentException("No product with id " + p.getId()
                + " found");
    }

    @Override
    public synchronized Job getProductById(int productId) {
        for (int i = 0; i < jobs.size(); i++) {
            if (jobs.get(i).getId() == productId) {
                return jobs.get(i);
            }
        }
        return null;
    }

    @Override
    public synchronized void deleteProduct(int productId) {
        Job p = getProductById(productId);
        if (p == null) {
            throw new IllegalArgumentException("Job with id " + productId
                    + " not found");
        }
        jobs.remove(p);
    }
}
