package com.example.maintenanceapp.grid;

import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.stream.Collectors;

import com.example.maintenanceapp.data.Category;
import com.example.maintenanceapp.data.Job;
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
public class JobsGrid extends Grid<Job> {

	public JobsGrid() {
		setSizeFull();

		addColumn(Job::getId, new NumberRenderer()).setCaption("Id");
		addColumn(Job::getItem).setCaption("Item");
		addColumn(Job::getWorkProcedure).setCaption("Work procedure");
		addColumn(Job::getTag).setCaption("Tag");
		addColumn(Job::getDepartment).setCaption("Department");
		addColumn(this::htmlPriorityFormatAvailability, new HtmlRenderer()).setCaption("Priority")
		.setComparator((p1, p2) -> {
			return p1.getPriority().toString().compareTo(p2.getPriority().toString());
		});
		addColumn(Job::getFrequency).setCaption("Frequency");
		addColumn(Job::getWhenDue).setCaption("When due(remaining)");
		addColumn(this::htmlStatusFormatAvailability, new HtmlRenderer()).setCaption("Status")
		.setComparator((p1, p2) -> {
			return p1.getStatus().toString().compareTo(p2.getStatus().toString());
		});
		addColumn(Job::getCounter).setCaption("Counter");
		// Format and add " €" to price
		final DecimalFormat decimalFormat = new DecimalFormat();
		decimalFormat.setMaximumFractionDigits(2);
		decimalFormat.setMinimumFractionDigits(2);
	
		// Add an traffic light icon in front of availability

		// Show empty stock as "-"
//		addColumn(product -> {
//			if (product.getStockCount() == 0) {
//				return "-";
//			}
//			return Integer.toString(product.getStockCount());
//		}).setCaption("Stock Count").setComparator((p1, p2) -> {
//			return Integer.compare(p1.getStockCount(), p2.getStockCount());
//		}).setStyleGenerator(product -> "align-right");

		// Show all categories the product is in, separated by commas
	}

	public Job getSelectedRow() {
		return asSingleSelect().getValue();
	}

	public void refresh(Job job) {
		getDataCommunicator().refresh(job);
	}

	private String htmlStatusFormatAvailability(Job job) {
		Status availability = job.getStatus();
		String text = availability.toString();

		String color = "";
		switch (availability) {
		case DUE:
			color = "#2dd085";
			break;
		case NOT_DUE:
			color = "#ffc66e";
			break;
		case NEW_JOB:
			color = "#f54993";
			break;
		default:
			break;
		}

		String iconCode = "<span class=\"v-icon\" style=\"font-family: " + VaadinIcons.CIRCLE.getFontFamily()
				+ ";color:" + color + "\">&#x" + Integer.toHexString(VaadinIcons.CIRCLE.getCodepoint()) + ";</span>";

		return iconCode + " " + text;
	}
	
	private String htmlPriorityFormatAvailability(Job job) {
		Priority availability = job.getPriority();
		String text = availability.toString();

		String color = "";
		switch (availability) {
		case LOW:
			color = "#2dd085";
			break;
		case NORMAL:
			color = "#ffc66e";
			break;
		case HIGH:
			color = "#f54993";
			break;
		default:
			break;
		}

		String iconCode = "<span class=\"v-icon\" style=\"font-family: " + VaadinIcons.CIRCLE.getFontFamily()
				+ ";color:" + color + "\">&#x" + Integer.toHexString(VaadinIcons.CIRCLE.getCodepoint()) + ";</span>";

		return iconCode + " " + text;
	}

}
