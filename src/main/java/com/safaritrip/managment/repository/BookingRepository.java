package com.safaritrip.management.repository;

import com.safaritrip.management.model.Booking;
import com.safaritrip.management.model.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findAllByTouristId(Long touristId);
    List<Booking> findAllByGuideId(Long guideId);
    List<Booking> findAllByVehicleId(Long vehicleId);
    long countByStatus(BookingStatus status);
}