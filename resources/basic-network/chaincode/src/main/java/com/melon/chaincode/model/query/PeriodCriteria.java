package com.melon.chaincode.model.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class PeriodCriteria {

    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    @JsonProperty("$gte")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_FORMAT)
    private LocalDateTime start;

    @JsonProperty("$lt")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_FORMAT)
    private LocalDateTime end;
}
