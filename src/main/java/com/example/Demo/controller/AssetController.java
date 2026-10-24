package com.example.Demo.controller;

import com.example.Demo.dto.AssetRequest;
import com.example.Demo.dto.AssetResponse;
import com.example.Demo.entity.AssetStatus;
import com.example.Demo.service.AssetService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.util.List;

@RestController
@RequestMapping("/api/assets")
@SecurityRequirement(name = "bearerAuth")
public class AssetController {

    private final AssetService assetService;

    public AssetController(AssetService assetService) {
        this.assetService = assetService;
    }

    // GET /api/assets or /api/assets?status=AVAILABLE
    @GetMapping
    public List<AssetResponse> list(@RequestParam(required = false) AssetStatus status) {
        return assetService.getAll(status);
    }

    @GetMapping("/my-assets")
    public List<AssetResponse> myAssets(Authentication authentication) {
        Long userId = (Long) authentication.getDetails(); // set by JwtAuthenticationFilter
        return assetService.getMyAssets(userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AssetResponse create(@Valid @RequestBody AssetRequest request) {
        return assetService.create(request);
    }

    @PatchMapping("/{id}/assign/{userId}")
    public AssetResponse assign(@PathVariable Long id, @PathVariable Long userId) {
        return assetService.assign(id, userId);
    }

    @PatchMapping("/{id}/return")
    public AssetResponse returnAsset(@PathVariable Long id) {
        return assetService.returnAsset(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        assetService.delete(id);
    }
}
