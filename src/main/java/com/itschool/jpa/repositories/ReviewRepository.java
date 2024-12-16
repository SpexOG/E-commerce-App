package com.itschool.jpa.repositories;

import com.itschool.jpa.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
