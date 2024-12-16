package com.itschool.jpa.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CreateReviewDto {
    private Long userId;
    private Long instrumentId;
    private Integer rating;
    private String comment;
}
