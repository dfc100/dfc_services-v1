package com.dfc.network.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown=true)
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Child {

    private String name;
    private List<Attribute> attributes;
    private String position;
    private Integer userId;
    private String city;
    private String state;
    private String fullName;
    private int numberOfChildren;
    private List<Child> children;

    @Data
    @JsonIgnoreProperties(ignoreUnknown=true)
    @NoArgsConstructor
    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
    public static class Attribute {
        private String position;
    }
}
