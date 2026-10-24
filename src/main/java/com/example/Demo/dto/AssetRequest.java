package com.example.Demo.dto;

import com.example.Demo.entity.AssetStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AssetRequest {

    @NotBlank(message = "name must not be blank")
    private String name;

    @NotBlank(message = "serialNumber must not be blank")
    private String serialNumber;

    @NotNull(message = "categoryId is required")
    private Long categoryId;

    // Optional. Defaults to AVAILABLE. Invalid values (e.g. "BROKEN") are rejected with 400.
    private AssetStatus status;

    public AssetRequest() {
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getSerialNumber() { return serialNumber; }
    public void setSerialNumber(String serialNumber) { this.serialNumber = serialNumber; }
    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }
    public AssetStatus getStatus() { return status; }
    public void setStatus(AssetStatus status) { this.status = status; }
}
