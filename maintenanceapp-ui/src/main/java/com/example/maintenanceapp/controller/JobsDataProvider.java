package com.example.maintenanceapp.controller;

import java.util.Locale;
import java.util.Objects;
import java.util.stream.Stream;

import com.example.maintenanceapp.data.Job;
import com.example.maintenanceapp.service.JobService;
import com.vaadin.data.provider.AbstractDataProvider;
import com.vaadin.data.provider.Query;

public class JobsDataProvider extends AbstractDataProvider<Job, String> {

	/** Text filter that can be changed separately. */
	private String filterText = "";

	/**
	 * Store given product to the backing data service.
	 * 
	 * @param job
	 *            the updated or new product
	 */
	public void save(Job job) {
		boolean newProduct = job.getId() == -1;

		JobService.get().updateProduct(job);
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
	public void delete(Job job) {
		JobService.get().deleteProduct(job.getId());
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
	public Integer getId(Job job) {
		Objects.requireNonNull(job, "Cannot provide an id for a null product.");

		return job.getId();
	}

	@Override
	public boolean isInMemory() {
		return true;
	}

	@Override
	public int size(Query<Job, String> t) {
		return (int) fetch(t).count();
	}

	@Override
	public Stream<Job> fetch(Query<Job, String> query) {
		if (filterText.isEmpty()) {
			return JobService.get().getAllProducts().stream();
		}
		return JobService.get().getAllProducts().stream()
				.filter(product -> passesFilter(product.getItem(), filterText)
						|| passesFilter(product.getStatus(), filterText)
						|| passesFilter(product.getFrequency(), filterText));
	}

	private boolean passesFilter(Object object, String filterText) {
		return object != null && object.toString().toLowerCase(Locale.ENGLISH).contains(filterText);
	}
}
