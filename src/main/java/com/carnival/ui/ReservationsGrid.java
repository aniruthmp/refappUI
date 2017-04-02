package com.carnival.ui;

import com.carnival.model.Reservation;
import com.carnival.service.ReservationService;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.grid.HeaderRow;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.List;

@UIScope
@SpringComponent
@Slf4j
public class ReservationsGrid extends VerticalLayout {
    @Autowired
    ReservationService reservationService;

    private List<Reservation> reservationList;

    @PostConstruct
    void init() {
        setDefaultComponentAlignment(Alignment.TOP_LEFT);
        update();
    }

    private void update() {
        log.info("Came inside update()");
        setReservations(reservationService.getReservations());
    }

    private void setReservations(List<Reservation> reservations) {
        this.reservationList = reservations;
        removeAllComponents();

        Grid<Reservation> grid = new Grid<>();
        grid.addColumn(Reservation::getFirstName).setCaption("First Name");
        grid.addColumn(Reservation::getLastName).setCaption("Last Name");
        grid.addColumn(Reservation::getEmail).setCaption("Email");
        grid.addColumn(Reservation::getGender).setCaption("Gender");
        grid.addColumn(Reservation::getCreatedTime).setCaption("Created at");
        grid.addColumn(Reservation::getModifiedTime).setCaption("Modified at");

        grid.setItems(this.reservationList);
        grid.setWidth("80%");
        grid.setHeightMode(HeightMode.ROW);

        addComponent(grid);
    }
}
