package com.example.maintenanceapp.view;

import com.example.maintenanceapp.MyUI;
import com.example.maintenanceapp.data.WorkOrder;
import com.example.maintenanceapp.service.WorkOrderService;

import java.io.Serializable;
import com.vaadin.server.Page;

/**
 * This class provides an interface for the logical operations between the CRUD
 * view, its parts like the product editor form and the data source, including
 * fetching and saving products.
 *
 * Having this separate from the view makes it easier to test various parts of
 * the system separately, and to e.g. provide alternative views for the same
 * data.
 */
public class WorkOrdersLogic implements Serializable {

	private WorkOrdersView view;

	public WorkOrdersLogic(WorkOrdersView simpleCrudView) {
		view = simpleCrudView;
	}

	public void init() {
		editWorkOrder(null);
		// Hide and disable if not admin
		if (!MyUI.get().getAccessControl().isUserInRole("admin")) {
			view.setNewProductEnabled(false);
		}
	}

	public void cancelWorkOrder() {
		setFragmentParameter("");
		view.clearSelection();
	}

	/**
	 * Update the fragment without causing navigator to change view
	 */
	private void setFragmentParameter(String productId) {
		String fragmentParameter;
		if (productId == null || productId.isEmpty()) {
			fragmentParameter = "";
		} else {
			fragmentParameter = productId;
		}

		Page page = MyUI.get().getPage();
		page.setUriFragment("!" + JobsView.VIEW_NAME + "/" + fragmentParameter, false);
	}

	public void enter(String productId) {
		if (productId != null && !productId.isEmpty()) {
			if (productId.equals("new")) {
				newWorkOrder();
			} else {
				// Ensure this is selected even if coming directly here from
				// login
				try {
					int pid = Integer.parseInt(productId);
					WorkOrder job = findWorkOrder(pid);
					view.selectRow(job);
				} catch (NumberFormatException e) {
				}
			}
		}
	}

	private WorkOrder findWorkOrder(int productId) {
		return WorkOrderService.get().getWorkOrderById(productId);
	}

	public void saveWorkOrder(WorkOrder job) {
		view.showSaveNotification(job.getName() + " (" + job.getId() + ") updated");
		view.clearSelection();
		view.updateWorkOrder(job);
		setFragmentParameter("");
	}

	public void deleteWorkOrder(WorkOrder job) {
		view.showSaveNotification(job.getName() + " (" + job.getId() + ") removed");
		view.clearSelection();
		view.removeWorkOrder(job);
		setFragmentParameter("");
	}

	public void editWorkOrder(WorkOrder job) {
		if (job == null) {
			setFragmentParameter("");
		} else {
			setFragmentParameter(job.getId() + "");
		}
		view.editWorkOrder(job);
	}

	public void newWorkOrder() {
		view.clearSelection();
		setFragmentParameter("new");
		view.editWorkOrder(new WorkOrder());
	}

	public void rowSelected(WorkOrder job) {
		if (MyUI.get().getAccessControl().isUserInRole("admin")) {
			view.editWorkOrder(job);
		}
	}

}
