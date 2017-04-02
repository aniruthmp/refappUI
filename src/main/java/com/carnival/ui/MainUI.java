package com.carnival.ui;

import com.carnival.db.entity.Reservation;
import com.carnival.service.ReservationService;
import com.vaadin.annotations.Title;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.events.EventScope;
import org.vaadin.spring.events.annotation.EventBusListenerMethod;
import org.vaadin.viritin.button.ConfirmButton;
import org.vaadin.viritin.button.MButton;
import org.vaadin.viritin.fields.MTextField;
import org.vaadin.viritin.grid.MGrid;
import org.vaadin.viritin.layouts.MHorizontalLayout;
import org.vaadin.viritin.layouts.MVerticalLayout;

import java.util.Objects;

@Title("Reservations UI")
@SpringUI
@Slf4j
public class MainUI extends UI {

    private MVerticalLayout layout;
    private MGrid<Reservation> grid = new MGrid<>(Reservation.class)
            .withProperties("id", "firstName", "lastName", "email")
            .withColumnHeaders("Id", "First Name", "Last Name", "Email")
            .withFullWidth();

    private MTextField filterByName = new MTextField()
            .withPlaceholder("Filter by First (or) Last Name");
    private Button addNew = new MButton(VaadinIcons.PLUS, this::add);
    private Button edit = new MButton(VaadinIcons.PENCIL, this::edit);
    private Button delete = new ConfirmButton(VaadinIcons.TRASH,
            "Are you sure you want to delete the entry?", this::remove);

    @Autowired
    ReservationService reservationService;

    @Autowired
    ReservationForm reservationForm;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        setupLayout();
        addHeader();
        addGrid();
        listEntities();

        grid.asSingleSelect().addValueChangeListener(e -> adjustActionButtonState());
        filterByName.addValueChangeListener(e -> {
            listEntities(e.getValue());
        });
    }

    private void setupLayout() {
        layout = new MVerticalLayout();
        layout.setDefaultComponentAlignment(Alignment.TOP_LEFT);
        setContent(layout);
    }

    private void addHeader() {
        VerticalLayout headerLayout = new VerticalLayout();
        headerLayout.setWidth("80%");

        Button refreshButton = new Button("  Refresh");
        refreshButton.addStyleName(ValoTheme.BUTTON_PRIMARY);
        refreshButton.setIcon(VaadinIcons.REFRESH);
//        refreshButton.addClickListener(click -> reservationsForm());

        headerLayout.addComponent(refreshButton);
        layout.addComponent(headerLayout);
    }

//    private void reservationsForm() {
//        reservationsGrid.setHeight("80%");
//        layout.addComponentsAndExpand(reservationsGrid);
//    }

    protected void adjustActionButtonState() {
        boolean hasSelection = !grid.getSelectedItems().isEmpty();
        edit.setEnabled(hasSelection);
        delete.setEnabled(hasSelection);
    }

    private void listEntities() {
        listEntities(Objects.toString(filterByName.getValue(), ""));
    }

    private void addGrid() {
        layout.add(new MHorizontalLayout(filterByName, addNew, edit, delete), grid).expand(grid);
    }

    private void listEntities(String nameFilter) {
        grid.setRows(reservationService.filterByName(nameFilter));
        adjustActionButtonState();
    }

    public void add(ClickEvent clickEvent) {
        edit(new Reservation());
    }

    public void edit(ClickEvent e) {
        edit(grid.asSingleSelect().getValue());
    }

    protected void edit(final Reservation reservation) {
        reservationForm.setEntity(reservation);
        reservationForm.openInModalPopup();
    }

    public void remove() {
        reservationService.delete(grid.asSingleSelect().getValue());
        grid.deselectAll();
        listEntities();
    }

    @EventBusListenerMethod(scope = EventScope.UI)
    public void onReservationModified(ReservationModifiedEvent event) {
        listEntities();
        reservationForm.closePopup();
    }
}
