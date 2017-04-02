package com.carnival.ui;

import com.vaadin.annotations.Title;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Title("Reservations UI")
@SpringUI
@Slf4j
public class MainUI extends UI {

    private VerticalLayout layout;

    @Autowired
    ReservationsGrid reservationsGrid;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        setupLayout();
        addHeader();
    }

    private void setupLayout() {
        layout = new VerticalLayout();
        layout.setDefaultComponentAlignment(Alignment.TOP_LEFT);
        setContent(layout);
    }

    private void addHeader() {
        VerticalLayout headerLayout = new VerticalLayout();
        headerLayout.setWidth("80%");

        Button refreshButton = new Button("  Refresh");
        refreshButton.addStyleName(ValoTheme.BUTTON_PRIMARY);
        refreshButton.setIcon(VaadinIcons.REFRESH);
        refreshButton.addClickListener(click -> reservationsForm());

        headerLayout.addComponent(refreshButton);
        layout.addComponent(headerLayout);
    }

    private void reservationsForm() {
        reservationsGrid.setHeight("80%");
        layout.addComponentsAndExpand(reservationsGrid);
    }

}
