package com.safaritrip.management.repository;

import com.safaritrip.management.model.NationalPark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NationalParkRepository extends JpaRepository<NationalPark, Long> {
}