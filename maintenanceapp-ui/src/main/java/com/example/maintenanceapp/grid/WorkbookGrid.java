package com.example.maintenanceapp.grid;

import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.stream.Collectors;

import com.example.maintenanceapp.data.Category;
import com.example.maintenanceapp.data.Job;
import com.example.maintenanceapp.data.Workbook;
import com.example.maintenanceapp.enums.Availability;
import com.example.maintenanceapp.enums.Priority;
import com.example.maintenanceapp.enums.Status;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Grid;
import com.vaadin.ui.renderers.HtmlRenderer;
import com.vaadin.ui.renderers.NumberRenderer;

/**
 * Grid of products, handling the visual presentation and filtering of a set of
 * items. This version uses an in-memory data source that is suitable for small
 * data sets.
 */

/**
 * Grid of products, handling the visual presentation and filtering of a set of
 * items. This version uses an in-memory data source that is suitable for small
 * data sets.
 */
public class WorkbookGrid extends Grid<Workbook> {

	public WorkbookGrid() {
		setSizeFull();

		addColumn(Workbook::getId, new NumberRenderer()).setCaption("Id");
		addColumn(Workbook::getName).setCaption("Name");
		addColumn(Workbook::getType).setCaption("Type");
		// Format and add " €" to price
		final DecimalFormat decimalFormat = new DecimalFormat();
		decimalFormat.setMaximumFractionDigits(2);
		decimalFormat.setMinimumFractionDigits(2);

		// Add an traffic light icon in front of availability

		// Show empty stock as "-"
		// addColumn(product -> {
		// if (product.getStockCount() == 0) {
		// return "-";
		// }
		// return Integer.toString(product.getStockCount());
		// }).setCaption("Stock Count").setComparator((p1, p2) -> {
		// return Integer.compare(p1.getStockCount(), p2.getStockCount());
		// }).setStyleGenerator(product -> "align-right");

		// Show all categories the product is in, separated by commas
	}

	public Workbook getSelectedRow() {
		return asSingleSelect().getValue();
	}

	public void refresh(Workbook job) {
		getDataCommunicator().refresh(job);
	}


}
