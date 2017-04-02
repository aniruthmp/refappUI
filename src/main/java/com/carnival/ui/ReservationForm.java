package com.carnival.ui;


import com.carnival.db.entity.Reservation;
import com.carnival.db.repository.ReservationRepository;
import com.vaadin.data.converter.LocalDateToDateConverter;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.InlineDateField;
import com.vaadin.ui.TextField;
import org.vaadin.spring.events.EventBus;
import org.vaadin.viritin.fields.MTextField;
import org.vaadin.viritin.form.AbstractForm;
import org.vaadin.viritin.layouts.MFormLayout;
import org.vaadin.viritin.layouts.MVerticalLayout;

import javax.annotation.PostConstruct;

@UIScope
@SpringComponent
class ReservationForm extends AbstractForm<Reservation> {

    ReservationRepository repository;
    EventBus.UIEventBus eventBus;

    TextField firstName = new MTextField("First Name");
    TextField lastName = new MTextField("Last Name");
    TextField email = new MTextField("Email");
    InlineDateField createdAt = new InlineDateField("Created At");
    ComboBox<String> genderCombo = new ComboBox<>("Gender");

    ReservationForm(ReservationRepository rr, EventBus.UIEventBus eb) {
        super(Reservation.class);
        this.repository = rr;
        this.eventBus = eb;

        // On save & cancel, publish events that other parts of the UI can listen
        setSavedHandler(reservation -> {
            // persist changes
            repository.save(reservation);
            // send the event for other parts of the application
            eventBus.publish(this, new ReservationModifiedEvent(reservation));
        });
        setResetHandler(r -> eventBus.publish(this, new ReservationModifiedEvent(r)));

        setSizeUndefined();
    }

    @PostConstruct
    void init() {
        setWidth("80%");
        genderCombo.setItems("Male", "Female");
    }

    @Override
    protected void bind() {
        getBinder()
                .forMemberField(createdAt)
                .withConverter(new LocalDateToDateConverter());
        super.bind();
    }

    @Override
    protected Component createContent() {
        return new MVerticalLayout(
                new MFormLayout(
                        firstName,
                        lastName,
                        email,
                        createdAt,
                        genderCombo
                ).withWidth(""),
                getToolbar()
        ).withWidth("");
    }
}
