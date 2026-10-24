package com.example.Demo.service;

import com.example.Demo.dto.AssetRequest;
import com.example.Demo.dto.AssetResponse;
import com.example.Demo.entity.Asset;
import com.example.Demo.entity.AssetStatus;
import com.example.Demo.entity.Category;
import com.example.Demo.entity.User;
import com.example.Demo.exception.BadRequestException;
import com.example.Demo.exception.ResourceNotFoundException;
import com.example.Demo.repository.AssetRepository;
import com.example.Demo.repository.CategoryRepository;
import com.example.Demo.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AssetService {

    private final AssetRepository assetRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public AssetService(AssetRepository assetRepository,
                        CategoryRepository categoryRepository,
                        UserRepository userRepository) {
        this.assetRepository = assetRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<AssetResponse> getAll(AssetStatus status) {
        List<Asset> assets = (status == null)
                ? assetRepository.findAll()
                : assetRepository.findByStatus(status);
        return assets.stream().map(AssetResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public List<AssetResponse> getMyAssets(Long userId) {
        return assetRepository.findByAssignedToId(userId).stream()
                .map(AssetResponse::from)
                .toList();
    }

    @Transactional
    public AssetResponse create(AssetRequest request) {
        if (assetRepository.existsBySerialNumber(request.getSerialNumber())) {
            throw new BadRequestException("Serial number already exists: " + request.getSerialNumber());
        }

        AssetStatus status = request.getStatus() == null ? AssetStatus.AVAILABLE : request.getStatus();
        if (status == AssetStatus.ASSIGNED) {
            throw new BadRequestException(
                    "A new asset cannot be created as ASSIGNED. Create it as AVAILABLE, then use the assign endpoint");
        }

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Category not found with id " + request.getCategoryId()));

        Asset asset = new Asset();
        asset.setName(request.getName().trim());
        asset.setSerialNumber(request.getSerialNumber().trim());
        asset.setStatus(status);
        asset.setCategory(category);
        asset.setAssignedTo(null);

        return AssetResponse.from(assetRepository.save(asset));
    }

    @Transactional
    public AssetResponse assign(Long assetId, Long userId) {
        Asset asset = findAsset(assetId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));

        // Business rule: only AVAILABLE assets can be assigned
        if (asset.getStatus() != AssetStatus.AVAILABLE) {
            throw new BadRequestException(
                    "Asset cannot be assigned because its status is " + asset.getStatus());
        }

        asset.setAssignedTo(user);
        asset.setStatus(AssetStatus.ASSIGNED);
        return AssetResponse.from(assetRepository.save(asset));
    }

    @Transactional
    public AssetResponse returnAsset(Long assetId) {
        Asset asset = findAsset(assetId);

        if (asset.getStatus() != AssetStatus.ASSIGNED) {
            throw new BadRequestException(
                    "Asset cannot be returned because it is not ASSIGNED (current status: " + asset.getStatus() + ")");
        }

        asset.setAssignedTo(null);
        asset.setStatus(AssetStatus.AVAILABLE);
        return AssetResponse.from(assetRepository.save(asset));
    }

    @Transactional
    public void delete(Long assetId) {
        Asset asset = findAsset(assetId);
        assetRepository.delete(asset);
    }

    private Asset findAsset(Long id) {
        return assetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Asset not found with id " + id));
    }
}
