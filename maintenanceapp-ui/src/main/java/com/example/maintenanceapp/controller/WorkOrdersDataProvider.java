package com.example.maintenanceapp.controller;

import java.util.Locale;
import java.util.Objects;
import java.util.stream.Stream;

import com.example.maintenanceapp.data.Job;
import com.example.maintenanceapp.data.WorkOrder;
import com.example.maintenanceapp.service.JobService;
import com.example.maintenanceapp.service.WorkOrderService;
import com.vaadin.data.provider.AbstractDataProvider;
import com.vaadin.data.provider.Query;

public class WorkOrdersDataProvider extends AbstractDataProvider<WorkOrder, String> {

	/** Text filter that can be changed separately. */
	private String filterText = "";

	/**
	 * Store given product to the backing data service.
	 * 
	 * @param job
	 *            the updated or new product
	 */
	public void save(WorkOrder job) {
		boolean newProduct = job.getId() == -1;

		WorkOrderService.get().updateWorkOrder(job);
		if (newProduct) {
			refreshAll();
		} else {
			refreshItem(job);
		}
	}

	/**
	 * Delete given product from the backing data service.
	 * 
	 * @param job
	 *            the product to be deleted
	 */
	public void delete(WorkOrder job) {
		WorkOrderService.get().deleteWorkOrder(job.getId());
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

	@Override
	public Integer getId(WorkOrder job) {
		Objects.requireNonNull(job, "Cannot provide an id for a null product.");

		return job.getId();
	}

	@Override
	public boolean isInMemory() {
		return true;
	}

	@Override
	public int size(Query<WorkOrder, String> t) {
		return (int) fetch(t).count();
	}

	@Override
	public Stream<WorkOrder> fetch(Query<WorkOrder, String> query) {
		if (filterText.isEmpty()) {
			return WorkOrderService.get().getAllWorkOrders().stream();
		}
		return WorkOrderService.get().getAllWorkOrders().stream()
				.filter(product -> passesFilter(product.getName(), filterText)
						|| passesFilter(product.getType(), filterText)
						);
	}

	private boolean passesFilter(Object object, String filterText) {
		return object != null && object.toString().toLowerCase(Locale.ENGLISH).contains(filterText);
	}

}
