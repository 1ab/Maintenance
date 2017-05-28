package com.example.maintenanceapp.controller;

import java.util.Locale;
import java.util.Objects;
import java.util.stream.Stream;

import com.example.maintenanceapp.data.Job;
import com.example.maintenanceapp.data.Workbook;
import com.example.maintenanceapp.service.JobService;
import com.example.maintenanceapp.service.WorkbookService;
import com.vaadin.data.provider.AbstractDataProvider;
import com.vaadin.data.provider.Query;

public class WorkbooksDataProvider extends AbstractDataProvider<Workbook, String> {

	/** Text filter that can be changed separately. */
	private String filterText = "";

	/**
	 * Store given product to the backing data service.
	 * 
	 * @param workbook
	 *            the updated or new product
	 */
	public void save(Workbook workbook) {
		boolean newWorkbook = workbook.getId() == -1;

		WorkbookService.get().updateWorkbook(workbook);
		if (newWorkbook) {
			refreshAll();
		} else {
			refreshItem(workbook);
		}
	}

	/**
	 * Delete given product from the backing data service.
	 * 
	 * @param job
	 *            the product to be deleted
	 */
	public void delete(Workbook job) {
		WorkbookService.get().deleteWorkbook(job.getId());
		refreshAll();
	}

	/**
	 * Sets the filter to use for the this data provider and refreshes data.
	 * <p>
	 * Filter is compared for product name, availability and category.
	 * 
	 * @param filterText
	 *            the text to filter by, never null
	 */
	public void setFilter(String filterText) {
		Objects.requireNonNull(filterText, "Filter text cannot be null");
		if (Objects.equals(this.filterText, filterText.trim())) {
			return;
		}
		this.filterText = filterText.trim();

		refreshAll();
	}

	public Integer getId(Workbook workbook) {
		Objects.requireNonNull(workbook, "Cannot provide an id for a null product.");

		return workbook.getId();
	}

	@Override
	public boolean isInMemory() {
		return true;
	}

	@Override
	public int size(Query<Workbook, String> t) {
		return (int) fetch(t).count();
	}

	@Override
	public Stream<Workbook> fetch(Query<Workbook, String> query) {
		if (filterText.isEmpty()) {
			return WorkbookService.get().getAllWorkbooks().stream();
		}
		return WorkbookService.get().getAllWorkbooks().stream()
				.filter(product -> passesFilter(product.getName(), filterText)
						|| passesFilter(product.getType(), filterText)
						);
	}

	private boolean passesFilter(Object object, String filterText) {
		return object != null && object.toString().toLowerCase(Locale.ENGLISH).contains(filterText);
	}

}
