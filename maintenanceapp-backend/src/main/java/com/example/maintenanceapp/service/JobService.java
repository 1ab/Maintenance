package com.example.maintenanceapp.service;

import java.io.Serializable;
import java.util.Collection;

import com.example.maintenanceapp.backend.mock.MockJobService;
import com.example.maintenanceapp.data.Category;
import com.example.maintenanceapp.data.Job;

/**
 * Back-end service interface for retrieving and updating product data.
 */
public abstract class JobService implements Serializable {

    public abstract Collection<Job> getAllProducts();

    public abstract Collection<Category> getAllCategories();

    public abstract void updateProduct(Job p);

    public abstract void deleteProduct(int productId);

    public abstract Job getProductById(int productId);

    public static JobService get() {
        return MockJobService.getInstance();
    }

}
