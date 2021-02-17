package org.example.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class PresentationBrowseOutDto {

    @JsonProperty(value = "Title")
    private String title;

    @JsonProperty(value = "StartDateTime")
    private LocalDateTime startDateTime;

    @JsonProperty(value = "EndDateTime")
    private LocalDateTime endDateTime;

    @JsonProperty(value = "Presenter")
    private String presenter;
}
