package com.example.maintenanceapp.samples.backend;

import org.junit.Before;
import org.junit.Test;

import com.example.maintenanceapp.backend.mock.MockJobService;
import com.example.maintenanceapp.data.Job;
import com.example.maintenanceapp.service.JobService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Simple unit test for the back-end data service.
 */
public class DataServiceTest {

    private JobService service;

    @Before
    public void setUp() throws Exception {
        service = MockJobService.getInstance();
    }

    @Test
    public void testDataServiceCanFetchProducts() throws Exception {
        assertFalse(service.getAllProducts().isEmpty());
    }

    @Test
    public void testDataServiceCanFetchCategories() throws Exception {
        assertFalse(service.getAllCategories().isEmpty());
    }

    @Test
    public void testUpdateProduct_updatesTheProduct() throws Exception {
        Job p = service.getAllProducts().iterator().next();
        p.setItem("My Test Name");
        service.updateProduct(p);
        Job p2 = service.getAllProducts().iterator().next();
        assertEquals("My Test Name", p2.getItem());
    }
}
