package com.pos.system.service;

import com.pos.system.exceptions.UserException;
import com.pos.system.payload.dto.BranchDto;

import java.util.List;

public interface BranchService {

    BranchDto createBranch(BranchDto branchDto);
    BranchDto updateBranch(Long id, BranchDto branchDto) throws Exception;
    void deleteBranch(Long id) throws Exception;
    List<BranchDto> getAllBranchesByStoreId(Long storeId) throws UserException;
    BranchDto getBranchById(Long id) throws Exception;
}
