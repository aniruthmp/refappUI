package com.carnival.ui;

import com.carnival.db.entity.Reservation;

import java.io.Serializable;

public class ReservationModifiedEvent implements Serializable {
    private final Reservation reservation;

    public ReservationModifiedEvent(Reservation r) {
        this.reservation = r;
    }

    public Reservation getReservation(){
        return this.reservation;
    }
}
