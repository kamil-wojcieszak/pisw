package com.piisw.jpa.repositories;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class FollowerEventInfoDTO {
    private String eventDescription;
    private LocalDateTime eventTime;
    private boolean eventAnalysisRequired;
    private String commentContent;
    private LocalDateTime followerSubscriptionDate;
}
