package com.example.Demo.dto;

import com.example.Demo.entity.Asset;
import com.example.Demo.entity.AssetStatus;

import java.time.LocalDateTime;

public record AssetResponse(
        Long id,
        String name,
        String serialNumber,
        AssetStatus status,
        Long categoryId,
        String categoryName,
        Long assignedToId,
        String assignedToUsername,
        LocalDateTime createdAt) {

    // Must be called inside a transaction (lazy associations are touched here).
    public static AssetResponse from(Asset a) {
        return new AssetResponse(
                a.getId(),
                a.getName(),
                a.getSerialNumber(),
                a.getStatus(),
                a.getCategory().getId(),
                a.getCategory().getName(),
                a.getAssignedTo() != null ? a.getAssignedTo().getId() : null,
                a.getAssignedTo() != null ? a.getAssignedTo().getUsername() : null,
                a.getCreatedAt());
    }
}
