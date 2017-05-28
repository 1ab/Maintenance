package com.example.maintenanceapp.view;

import com.example.maintenanceapp.MyUI;
import com.example.maintenanceapp.data.Job;
import com.example.maintenanceapp.data.Workbook;
import com.example.maintenanceapp.service.JobService;
import com.example.maintenanceapp.service.WorkbookService;

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
public class WorkbooksLogic implements Serializable {

	private WorkbooksView view;

	public WorkbooksLogic(WorkbooksView workbooksView) {
		view = workbooksView;
	}

	public void init() {
		editWorkbook(null);
		// Hide and disable if not admin
		if (!MyUI.get().getAccessControl().isUserInRole("admin")) {
			view.setNewProductEnabled(false);
		}
	}

	public void cancelWorkbook() {
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
				newWorkbook();
			} else {
				// Ensure this is selected even if coming directly here from
				// login
				try {
					int pid = Integer.parseInt(productId);
					Workbook job = findWorkbook(pid);
					view.selectRow(job);
				} catch (NumberFormatException e) {
				}
			}
		}
	}

	private Workbook findWorkbook(int productId) {
		return WorkbookService.get().getWorkbookById(productId);
	}

	public void saveWorkbook(Workbook job) {
		view.showSaveNotification(job.getName() + " (" + job.getId() + ") updated");
		view.clearSelection();
		view.updateWorkbook(job);
		setFragmentParameter("");
	}

	public void deleteWorkbook(Workbook job) {
		view.showSaveNotification(job.getName() + " (" + job.getId() + ") removed");
		view.clearSelection();
		view.removeWorkbook(job);
		setFragmentParameter("");
	}

	public void editWorkbook(Workbook job) {
		if (job == null) {
			setFragmentParameter("");
		} else {
			setFragmentParameter(job.getId() + "");
		}
		view.editWorkbook(job);
	}

	public void newWorkbook() {
		view.clearSelection();
		setFragmentParameter("new");
		view.editWorkbook(new Workbook());
	}

	public void rowSelected(Workbook job) {
		if (MyUI.get().getAccessControl().isUserInRole("admin")) {
			view.editWorkbook(job);
		}
	}

}
