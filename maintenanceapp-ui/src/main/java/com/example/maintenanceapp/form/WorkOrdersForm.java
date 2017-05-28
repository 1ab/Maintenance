package com.example.maintenanceapp.form;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Collection;
import java.util.Locale;

import com.example.maintenanceapp.data.Category;
import com.example.maintenanceapp.data.Job;
import com.example.maintenanceapp.data.WorkOrder;
import com.example.maintenanceapp.enums.Availability;
import com.example.maintenanceapp.samples.AttributeExtension;
import com.example.maintenanceapp.view.JobsLogic;
import com.example.maintenanceapp.view.WorkOrdersLogic;
import com.vaadin.data.BeanValidationBinder;
import com.vaadin.data.Binder;
import com.vaadin.data.Result;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.data.ValueContext;
import com.vaadin.server.Page;

/**
 * A form for editing a single product.
 *
 * Using responsive layouts, the form can be displayed either sliding out on the
 * side of the view or filling the whole screen - see the theme for the related
 * CSS rules.
 */
public class WorkOrdersForm extends WorkOrdersFormDesign {

	private WorkOrdersLogic viewLogic;
	private Binder<WorkOrder> binder;
	private WorkOrder currentProduct;

	private static class StockPriceConverter extends StringToIntegerConverter {

		public StockPriceConverter() {
			super("Could not convert value to " + Integer.class.getName());
		}

		@Override
		protected NumberFormat getFormat(Locale locale) {
			// do not use a thousands separator, as HTML5 input type
			// number expects a fixed wire/DOM number format regardless
			// of how the browser presents it to the user (which could
			// depend on the browser locale)
			DecimalFormat format = new DecimalFormat();
			format.setMaximumFractionDigits(0);
			format.setDecimalSeparatorAlwaysShown(false);
			format.setParseIntegerOnly(true);
			format.setGroupingUsed(false);
			return format;
		}

		@Override
		public Result<Integer> convertToModel(String value, ValueContext context) {
			Result<Integer> result = super.convertToModel(value, context);
			return result.map(stock -> stock == null ? 0 : stock);
		}

	}

	public WorkOrdersForm(WorkOrdersLogic sampleCrudLogic) {
		super();
		addStyleName("product-form");
		viewLogic = sampleCrudLogic;

		// Mark the stock count field as numeric.
		// This affects the virtual keyboard shown on mobile devices.


		binder = new BeanValidationBinder<>(WorkOrder.class);
		// binder.forField(price).withConverter(new EuroConverter())
		// .bind("price");
	//	binder.forField(stockCount).withConverter(new StockPriceConverter()).bind("stockCount");

	//	binder.forField(category).bind("category");
		binder.bindInstanceFields(this);

		// enable/disable save button while editing
		binder.addStatusChangeListener(event -> {
			boolean isValid = !event.hasValidationErrors();
			boolean hasChanges = binder.hasChanges();
		});

		save.addClickListener(event -> {
			if (currentProduct != null && binder.writeBeanIfValid(currentProduct)) {
				viewLogic.saveWorkOrder(currentProduct);
			}
		});

		discard.addClickListener(event -> viewLogic.editWorkOrder(currentProduct));

		cancel.addClickListener(event -> viewLogic.cancelWorkOrder());

		delete.addClickListener(event -> {
			if (currentProduct != null) {
				viewLogic.deleteWorkOrder(currentProduct);
			}
		});
	}

	public void editProduct(WorkOrder job) {
		if (job == null) {
			job = new WorkOrder();
		}
		currentProduct = job;
		binder.readBean(job);

		// Scroll to the top
		// As this is not a Panel, using JavaScript
		String scrollScript = "window.document.getElementById('" + getId() + "').scrollTop = 0;";
		Page.getCurrent().getJavaScript().execute(scrollScript);
	}

}
