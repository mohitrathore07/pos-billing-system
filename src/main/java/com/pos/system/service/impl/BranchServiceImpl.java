package com.pos.system.service.impl;

import com.pos.system.Modal.Branch;
import com.pos.system.Modal.Store;
import com.pos.system.Modal.User;
import com.pos.system.exceptions.UserException;
import com.pos.system.mapper.BranchMapper;
import com.pos.system.payload.dto.BranchDto;
import com.pos.system.repository.BranchRepository;
import com.pos.system.repository.StoreRepository;
import com.pos.system.repository.UserRepository;
import com.pos.system.service.BranchService;
import com.pos.system.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BranchServiceImpl implements BranchService {
    private final BranchRepository branchRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final UserService userService;

    @Override
    public BranchDto createBranch(BranchDto branchDto) {
        User currentUser = userService.getCurrentUser();
        Store store =  storeRepository.findByStoreAdminId(currentUser.getId());

        Branch branch = BranchMapper.toEntity(branchDto,store);
        Branch savedBranch = branchRepository.save(branch);
        return BranchMapper.toDto(savedBranch);
    }

    @Override
    public BranchDto updateBranch(Long id, BranchDto branchDto) throws Exception {
        Branch existing = branchRepository.findById(id).orElseThrow(
                () -> new Exception("Branch not exists")
        );

        existing.setName(branchDto.getName());
        existing.setWorkingDays(branchDto.getWorkingDays());
        existing.setEmail(branchDto.getEmail());
        existing.setPhone(branchDto.getPhone());
        existing.setAddress(branchDto.getAddress());
        existing.setOpenTime(branchDto.getOpenTime());
        existing.setCloseTime(branchDto.getCloseTime());
        existing.setUpdatedAt(LocalDateTime.now());

        Branch updatedBranch = branchRepository.save(existing);

        return BranchMapper.toDto(updatedBranch);
    }

    @Override
    public void deleteBranch(Long id) throws Exception {
        Branch existing = branchRepository.findById(id).orElseThrow(
                () -> new Exception("Branch not exists")
        );
        branchRepository.delete(existing);
    }

    @Override
    public List<BranchDto> getAllBranchesByStoreId(Long storeId) throws UserException {
        List<Branch> branches = branchRepository.findByStoreId(storeId);

        return branches.stream().map(BranchMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public BranchDto getBranchById(Long id) throws Exception {
        Branch existing =  branchRepository.findById(id).orElseThrow(() -> new Exception("branch not exisits"));

        return BranchMapper.toDto(existing);
    }
}
