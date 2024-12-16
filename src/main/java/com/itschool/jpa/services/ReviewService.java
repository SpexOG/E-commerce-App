package com.itschool.jpa.services;

import com.itschool.jpa.dtos.CreateReviewDto;
import com.itschool.jpa.models.Review;

public interface ReviewService {
    Review addReview(CreateReviewDto dto);
}
