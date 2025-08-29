package com.example.application;

import com.example.domain.Reservation;
import com.example.domain.ReservationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateReservationUseCase {

    private final ReservationRepository reservationRepository;

    public CreateReservationUseCase(final ReservationRepository reservationRepository) {this.reservationRepository = reservationRepository;}

    @Transactional
    public void execute(Long amount) {
        reservationRepository.save(new Reservation(amount));
    }

}
