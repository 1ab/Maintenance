package com.example.maintenanceapp.form;

import com.example.maintenanceapp.data.Category;
import com.example.maintenanceapp.enums.Availability;
import com.vaadin.annotations.AutoGenerated;
import com.vaadin.annotations.DesignRoot;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBoxGroup;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.declarative.Design;

/**
 * !! DO NOT EDIT THIS FILE !!
 * 
 * This class is generated by Vaadin Designer and will be overwritten.
 * 
 * Please make a subclass with logic and additional interfaces as needed, e.g
 * class LoginView extends LoginDesign implements View { … }
 */
@DesignRoot
@AutoGenerated
@SuppressWarnings("serial")
public class WorkOrdersFormDesign extends CssLayout {
	protected TextField name;
	protected TextField type;
	protected Button save;
	protected Button discard;
	protected Button cancel;
	protected Button delete;

	public WorkOrdersFormDesign() {
		Design.read(this);
	}

}
