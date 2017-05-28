package com.example.maintenanceapp.backend.mock;


	import java.util.List;

import com.example.maintenanceapp.data.Category;
import com.example.maintenanceapp.data.Job;
import com.example.maintenanceapp.data.Workbook;
import com.example.maintenanceapp.service.JobService;
import com.example.maintenanceapp.service.WorkbookService;

	/**
	 * Mock data model. This implementation has very simplistic locking and does not
	 * notify users of modifications.
	 */
	public class MockWorkbookService extends WorkbookService {

	    private static MockWorkbookService INSTANCE;

	    private List<Workbook> workbook;
	    private int nextProductId = 0;

	    private MockWorkbookService() {
	    	workbook = MockDataGenerator.createWorkbooks();
	        nextProductId = workbook.size() + 1;
	    }

	    public synchronized static WorkbookService getInstance() {
	        if (INSTANCE == null) {
	            INSTANCE = new MockWorkbookService();
	        }
	        return INSTANCE;
	    }

	    @Override
	    public synchronized List<Workbook> getAllWorkbooks() {
	        return workbook;
	    }

	    @Override
	    public synchronized void updateWorkbook(Workbook p) {
	        if (p.getId() < 0) {
	            // New product
	            p.setId(nextProductId++);
	            workbook.add(p);
	            return;
	        }
	        for (int i = 0; i < workbook.size(); i++) {
	            if (workbook.get(i).getId() == p.getId()) {
	                workbook.set(i, p);
	                return;
	            }
	        }

	        throw new IllegalArgumentException("No product with id " + p.getId()
	                + " found");
	    }

	    @Override
	    public synchronized Workbook getWorkbookById(int productId) {
	        for (int i = 0; i < workbook.size(); i++) {
	            if (workbook.get(i).getId() == productId) {
	                return workbook.get(i);
	            }
	        }
	        return null;
	    }

	    @Override
	    public synchronized void deleteWorkbook(int productId) {
	        Workbook p = getWorkbookById(productId);
	        if (p == null) {
	            throw new IllegalArgumentException("Job with id " + productId
	                    + " not found");
	        }
	        workbook.remove(p);
	    }

}
