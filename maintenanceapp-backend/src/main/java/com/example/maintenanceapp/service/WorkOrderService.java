package com.example.maintenanceapp.service;

import java.io.Serializable;
import java.util.Collection;

import com.example.maintenanceapp.backend.mock.MockJobService;
import com.example.maintenanceapp.backend.mock.MockWorkOrderService;
import com.example.maintenanceapp.data.Category;
import com.example.maintenanceapp.data.Job;
import com.example.maintenanceapp.data.WorkOrder;

/**
 * Back-end service interface for retrieving and updating product data.
 */
public abstract class WorkOrderService implements Serializable {

	public abstract Collection<WorkOrder> getAllWorkOrders();

	public abstract void updateWorkOrder(WorkOrder p);

	public abstract void deleteWorkOrder(int productId);

	public abstract WorkOrder getWorkOrderById(int productId);

	public static WorkOrderService get() {
		return MockWorkOrderService.getInstance();
	}

}