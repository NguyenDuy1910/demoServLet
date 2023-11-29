package com.example.demo112.dtos;

import jakarta.validation.constraints.NotEmpty;

public class CategoryDTO {
    @NotEmpty(message = "Category's name cannot be empty")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
