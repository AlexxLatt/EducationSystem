package dev.educationsystem.dto.request;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ScheduleFilter {

    private Long id;

    private Long groupId;

    private Long teacherId;
    private Long courseId;

    private LocalDateTime classStartDate;
    private LocalDateTime classEndDate;
}
