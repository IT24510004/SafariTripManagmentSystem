package com.safaritrip.management.service.impl;

import com.safaritrip.management.dto.BookingDto;
import com.safaritrip.management.model.*;
import com.safaritrip.management.repository.*;
import com.safaritrip.management.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private NationalParkRepository nationalParkRepository;
    @Autowired
    private GuideRepository guideRepository; // Add GuideRepository
    @Autowired
    private VehicleRepository vehicleRepository; // Add VehicleRepository

    // ... createBooking and findBookingsByUsername methods are unchanged ...
    @Override
    public Booking createBooking(BookingDto bookingDto, String username) {
        User tourist = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        NationalPark park = nationalParkRepository.findById(bookingDto.getNationalParkId())
                .orElseThrow(() -> new RuntimeException("National Park not found"));

        BigDecimal pricePerPerson = new BigDecimal("50.00");
        BigDecimal pricePerHour = new BigDecimal("20.00");
        BigDecimal guidePrice = new BigDecimal("100.00");

        BigDecimal total = pricePerPerson.multiply(BigDecimal.valueOf(bookingDto.getNumberOfPeople()))
                .add(pricePerHour.multiply(BigDecimal.valueOf(bookingDto.getRideHours())));

        if (bookingDto.isGuideOption()) {
            total = total.add(guidePrice);
        }

        Booking booking = new Booking();
        booking.setTourist(tourist);
        booking.setNationalPark(park);
        booking.setBookingDate(bookingDto.getBookingDate());
        booking.setTimeSlot(bookingDto.getTimeSlot());
        booking.setNumberOfPeople(bookingDto.getNumberOfPeople());
        booking.setGuideOption(bookingDto.isGuideOption());
        booking.setRideHours(bookingDto.getRideHours());
        booking.setTotalPrice(total);
        booking.setStatus(BookingStatus.PENDING);

        return bookingRepository.save(booking);
    }

    @Override
    public List<Booking> findBookingsByUsername(String username) {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user != null) {
            return bookingRepository.findAllByTouristId(user.getId());
        }
        return Collections.emptyList();
    }

    @Override
    public List<Booking> findAllBookings() {
        return bookingRepository.findAll();
    }

    @Override
    public void updateBookingStatus(Long bookingId, BookingStatus status) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        booking.setStatus(status);
        bookingRepository.save(booking);
    }

    @Override
    public void assignGuideAndVehicle(Long bookingId, Long guideId, Long vehicleId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        // Guide and Vehicle can be optional, so we don't throw an error if not found
        Guide guide = guideRepository.findById(guideId).orElse(null);
        Vehicle vehicle = vehicleRepository.findById(vehicleId).orElse(null);

        booking.setGuide(guide);
        booking.setVehicle(vehicle);

        bookingRepository.save(booking);
    }
}