package com.example.maintenanceapp.view;

import com.example.maintenanceapp.controller.WorkbooksDataProvider;
import com.example.maintenanceapp.data.Workbook;
import com.example.maintenanceapp.form.WorkbooksForm;
import com.example.maintenanceapp.grid.WorkbookGrid;
import com.example.maintenanceapp.samples.ResetButtonForTextField;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 * A view for performing create-read-update-delete operations on products.
 *
 * See also {@link SampleCrudLogic} for fetching the data, the actual CRUD
 * operations and controlling the view based on events from outside.
 */
public class WorkbooksView extends CssLayout implements View {

	public static final String VIEW_NAME = "Workbook";
	private WorkbookGrid grid;
	private WorkbooksForm form;
	private TextField filter;

	private WorkbooksLogic viewLogic = new WorkbooksLogic(this);
	private Button newProduct;

	private WorkbooksDataProvider dataProvider = new WorkbooksDataProvider();

	public WorkbooksView() {
		setSizeFull();
		addStyleName("crud-view");
		HorizontalLayout topLayout = createTopBar();

		grid = new WorkbookGrid();
		grid.setDataProvider(dataProvider);
		grid.asSingleSelect().addValueChangeListener(event -> viewLogic.rowSelected(event.getValue()));

		form = new WorkbooksForm(viewLogic);

		VerticalLayout barAndGridLayout = new VerticalLayout();
		barAndGridLayout.addComponent(topLayout);
		barAndGridLayout.addComponent(grid);
		barAndGridLayout.setSizeFull();
		barAndGridLayout.setExpandRatio(grid, 1);
		barAndGridLayout.setStyleName("crud-main-layout");

		addComponent(barAndGridLayout);
		addComponent(form);

		viewLogic.init();
	}

	public HorizontalLayout createTopBar() {
		filter = new TextField();
		filter.setStyleName("filter-textfield");
		filter.setPlaceholder("Filter name, availability or category");
		ResetButtonForTextField.extend(filter);
		// Apply the filter to grid's data provider. TextField value is never
		// null
		filter.addValueChangeListener(event -> dataProvider.setFilter(event.getValue()));

		newProduct = new Button("New Workbook");
		newProduct.addStyleName(ValoTheme.BUTTON_PRIMARY);
		newProduct.setIcon(VaadinIcons.PLUS_CIRCLE);
		newProduct.addClickListener(click -> viewLogic.newWorkbook());

		HorizontalLayout topLayout = new HorizontalLayout();
		topLayout.setWidth("100%");
		topLayout.addComponent(filter);
		topLayout.addComponent(newProduct);
		topLayout.setComponentAlignment(filter, Alignment.MIDDLE_LEFT);
		topLayout.setExpandRatio(filter, 1);
		topLayout.setStyleName("top-bar");
		return topLayout;
	}

	@Override
	public void enter(ViewChangeEvent event) {
		viewLogic.enter(event.getParameters());
	}

	public void showError(String msg) {
		Notification.show(msg, Type.ERROR_MESSAGE);
	}

	public void showSaveNotification(String msg) {
		Notification.show(msg, Type.TRAY_NOTIFICATION);
	}

	public void setNewProductEnabled(boolean enabled) {
		newProduct.setEnabled(enabled);
	}

	public void clearSelection() {
		grid.getSelectionModel().deselectAll();
	}

	public void selectRow(Workbook row) {
		grid.getSelectionModel().select(row);
	}

	public Workbook getSelectedRow() {
		return grid.getSelectedRow();
	}

	public void updateWorkbook(Workbook job) {
		dataProvider.save(job);
		// FIXME: Grid used to scroll to the updated item
	}

	public void removeWorkbook(Workbook job) {
		dataProvider.delete(job);
	}

	public void editWorkbook(Workbook job) {
		if (job != null) {
			form.addStyleName("visible");
			form.setEnabled(true);
		} else {
			form.removeStyleName("visible");
			form.setEnabled(false);
		}
		form.editWorkbook(job);
	}

}
