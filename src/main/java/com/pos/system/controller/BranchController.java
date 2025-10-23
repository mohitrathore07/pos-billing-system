package com.pos.system.controller;

import com.pos.system.payload.dto.BranchDto;
import com.pos.system.payload.response.ApiResponse;
import com.pos.system.repository.BranchRepository;
import com.pos.system.service.BranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/branches")
public class BranchController {
    private final BranchRepository branchRepository;
    private final BranchService branchService;

    @PostMapping
    public ResponseEntity<BranchDto> createBranch(@RequestBody BranchDto branchDto) throws Exception {
        BranchDto createdBranch = branchService.createBranch(branchDto);
        return ResponseEntity.ok(createdBranch);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BranchDto> getBranchById(
            @PathVariable Long id
    ) throws Exception {
        BranchDto branch = branchService.getBranchById(id);
        return ResponseEntity.ok(branch);
    }

    @GetMapping("/store/{storeId}")
    public ResponseEntity<List<BranchDto>> getAllBranchByStoreId(
            @PathVariable Long storeId
    ) throws Exception{
        List<BranchDto> branchDtos = branchService.getAllBranchesByStoreId(storeId);
        return ResponseEntity.ok(branchDtos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BranchDto> updatedBranch(
            @PathVariable Long id,
            @RequestBody BranchDto branchDto
    ) throws Exception {
        BranchDto updatedBranch = branchService.updateBranch(id, branchDto);
        return ResponseEntity.ok(updatedBranch);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteBranchById(
            @PathVariable Long id
    ) throws Exception {
        branchService.deleteBranch(id);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("branch deleted successfully....");
        return ResponseEntity.ok(apiResponse);

    }
}
