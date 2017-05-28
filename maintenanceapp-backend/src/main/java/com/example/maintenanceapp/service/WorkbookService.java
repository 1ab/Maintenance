package com.example.maintenanceapp.service;

import java.io.Serializable;
import java.util.Collection;

import com.example.maintenanceapp.backend.mock.MockJobService;
import com.example.maintenanceapp.backend.mock.MockWorkbookService;
import com.example.maintenanceapp.data.Category;
import com.example.maintenanceapp.data.Job;
import com.example.maintenanceapp.data.Workbook;

/**
 * Back-end service interface for retrieving and updating product data.
 */
public abstract class WorkbookService implements Serializable {

	public abstract Collection<Workbook> getAllWorkbooks();

	public abstract void updateWorkbook(Workbook p);

	public abstract void deleteWorkbook(int productId);

	public abstract Workbook getWorkbookById(int productId);

	public static WorkbookService get() {
		return MockWorkbookService.getInstance();
	}

}
