package com.example.maintenanceapp.app;

import com.example.maintenanceapp.MyUI;
import com.example.maintenanceapp.view.FormsManualsView;
import com.example.maintenanceapp.view.JobsView;
import com.example.maintenanceapp.view.TipsView;
import com.example.maintenanceapp.view.WorkOrdersView;
import com.example.maintenanceapp.view.WorkbooksView;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;

/**
 * Content of the UI when the user is logged in.
 * 
 * 
 */
public class MainScreen extends HorizontalLayout {
    private Menu menu;

    public MainScreen(MyUI ui) {

        setSpacing(false);
        setStyleName("main-screen");

        CssLayout viewContainer = new CssLayout();
        viewContainer.addStyleName("valo-content");
        viewContainer.setSizeFull();

        final Navigator navigator = new Navigator(ui, viewContainer);
        navigator.setErrorView(ErrorView.class);
        menu = new Menu(navigator);
        menu.addView(new JobsView(), JobsView.VIEW_NAME,
                JobsView.VIEW_NAME, VaadinIcons.EDIT);
        menu.addView(new WorkOrdersView(), WorkOrdersView.VIEW_NAME,
        		WorkOrdersView.VIEW_NAME, VaadinIcons.EDIT);
        menu.addView(new WorkbooksView(), WorkbooksView.VIEW_NAME,
        		WorkbooksView.VIEW_NAME, VaadinIcons.EDIT);
        menu.addView(new FormsManualsView(), "Forms & Manuals", "Forms & Manuals",
                VaadinIcons.INFO_CIRCLE);
        menu.addView(new TipsView(), "Tips & FAQs", "Tips & FAQs",
                VaadinIcons.INFO_CIRCLE);

        navigator.addViewChangeListener(viewChangeListener);

        addComponent(menu);
        addComponent(viewContainer);
        setExpandRatio(viewContainer, 1);
        setSizeFull();
    }

    // notify the view menu about view changes so that it can display which view
    // is currently active
    ViewChangeListener viewChangeListener = new ViewChangeListener() {

        @Override
        public boolean beforeViewChange(ViewChangeEvent event) {
            return true;
        }

        @Override
        public void afterViewChange(ViewChangeEvent event) {
            menu.setActiveView(event.getViewName());
        }

    };
}
