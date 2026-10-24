package com.example.Demo.repository;

import com.example.Demo.entity.Asset;
import com.example.Demo.entity.AssetStatus;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssetRepository extends JpaRepository<Asset, Long> {

    boolean existsBySerialNumber(String serialNumber);

    // @EntityGraph loads category + assignedTo in the same query (avoids N+1 queries)
    @Override
    @EntityGraph(attributePaths = {"category", "assignedTo"})
    List<Asset> findAll();

    @EntityGraph(attributePaths = {"category", "assignedTo"})
    List<Asset> findByStatus(AssetStatus status);

    @EntityGraph(attributePaths = {"category", "assignedTo"})
    List<Asset> findByAssignedToId(Long userId);
}
