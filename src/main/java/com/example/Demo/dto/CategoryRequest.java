package com.example.Demo.dto;

import jakarta.validation.constraints.NotBlank;

public class CategoryRequest {

    @NotBlank(message = "name must not be blank")
    private String name;

    public CategoryRequest() {
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
