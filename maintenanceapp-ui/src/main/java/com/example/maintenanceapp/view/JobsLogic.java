package com.example.maintenanceapp.view;

import com.example.maintenanceapp.MyUI;
import com.example.maintenanceapp.data.Job;
import com.example.maintenanceapp.service.JobService;

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
public class JobsLogic implements Serializable {

	private JobsView view;

	public JobsLogic(JobsView simpleCrudView) {
		view = simpleCrudView;
	}

	public void init() {
		editProduct(null);
		// Hide and disable if not admin
		if (!MyUI.get().getAccessControl().isUserInRole("admin")) {
			view.setNewProductEnabled(false);
		}
	}

	public void cancelProduct() {
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
				newProduct();
			} else {
				// Ensure this is selected even if coming directly here from
				// login
				try {
					int pid = Integer.parseInt(productId);
					Job job = findProduct(pid);
					view.selectRow(job);
				} catch (NumberFormatException e) {
				}
			}
		}
	}

	private Job findProduct(int productId) {
		return JobService.get().getProductById(productId);
	}

	public void saveProduct(Job job) {
		view.showSaveNotification(job.getItem() + " (" + job.getId() + ") updated");
		view.clearSelection();
		view.updateProduct(job);
		setFragmentParameter("");
	}

	public void deleteProduct(Job job) {
		view.showSaveNotification(job.getItem() + " (" + job.getId() + ") removed");
		view.clearSelection();
		view.removeProduct(job);
		setFragmentParameter("");
	}

	public void editProduct(Job job) {
		if (job == null) {
			setFragmentParameter("");
		} else {
			setFragmentParameter(job.getId() + "");
		}
		view.editProduct(job);
	}

	public void newProduct() {
		view.clearSelection();
		setFragmentParameter("new");
		view.editProduct(new Job());
	}

	public void rowSelected(Job job) {
		if (MyUI.get().getAccessControl().isUserInRole("admin")) {
			view.editProduct(job);
		}
	}

}
