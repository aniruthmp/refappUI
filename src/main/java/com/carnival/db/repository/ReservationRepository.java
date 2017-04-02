package com.carnival.db.repository;

import com.carnival.db.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

public interface ReservationRepository extends JpaRepository<Reservation, Long>, QueryByExampleExecutor<Reservation> {

}
