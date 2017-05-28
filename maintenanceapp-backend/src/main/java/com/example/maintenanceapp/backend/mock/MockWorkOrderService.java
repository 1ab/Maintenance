package com.example.maintenanceapp.backend.mock;

import java.util.List;

import com.example.maintenanceapp.data.WorkOrder;
import com.example.maintenanceapp.service.WorkOrderService;

/**
 * Mock data model. This implementation has very simplistic locking and does not
 * notify users of modifications.
 */
public class MockWorkOrderService extends WorkOrderService {

	private static MockWorkOrderService INSTANCE;

	private List<WorkOrder> workOrder;
	private int nextProductId = 0;

	private MockWorkOrderService() {
		workOrder = MockDataGenerator.createWorkOrders();
		nextProductId = workOrder.size() + 1;
	}

	public synchronized static WorkOrderService getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new MockWorkOrderService();
		}
		return INSTANCE;
	}

	@Override
	public synchronized List<WorkOrder> getAllWorkOrders() {
		return workOrder;
	}

	@Override
	public synchronized void updateWorkOrder(WorkOrder p) {
		if (p.getId() < 0) {
			// New product
			p.setId(nextProductId++);
			workOrder.add(p);
			return;
		}
		for (int i = 0; i < workOrder.size(); i++) {
			if (workOrder.get(i).getId() == p.getId()) {
				workOrder.set(i, p);
				return;
			}
		}

		throw new IllegalArgumentException("No product with id " + p.getId() + " found");
	}

	@Override
	public synchronized WorkOrder getWorkOrderById(int productId) {
		for (int i = 0; i < workOrder.size(); i++) {
			if (workOrder.get(i).getId() == productId) {
				return workOrder.get(i);
			}
		}
		return null;
	}

	@Override
	public synchronized void deleteWorkOrder(int productId) {
		WorkOrder p = getWorkOrderById(productId);
		if (p == null) {
			throw new IllegalArgumentException("Job with id " + productId + " not found");
		}
		workOrder.remove(p);
	}

}
