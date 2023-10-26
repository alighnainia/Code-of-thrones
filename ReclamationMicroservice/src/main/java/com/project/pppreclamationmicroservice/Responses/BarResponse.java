package com.project.pppreclamationmicroservice.Responses;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BarResponse {
    private List<String> labels;
    private List<Long> bar1;
    private List<Long> line1;
}
