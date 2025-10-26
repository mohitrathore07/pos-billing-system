package com.pos.system.service.impl;

import com.pos.system.Modal.Branch;
import com.pos.system.Modal.Store;
import com.pos.system.Modal.User;
import com.pos.system.domain.UserRole;
import com.pos.system.exceptions.UserException;
import com.pos.system.mapper.UserMapper;
import com.pos.system.payload.dto.UserDto;
import com.pos.system.repository.BranchRepository;
import com.pos.system.repository.StoreRepository;
import com.pos.system.repository.UserRepository;
import com.pos.system.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final StoreRepository storeRepository;
    private final BranchRepository branchRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public UserDto createStoreEmployee(UserDto employee, Long storeId) throws Exception {
        Store store = storeRepository.findById(storeId).orElseThrow(() -> new Exception("Store not found"));

        Branch branch = null;

        if(employee.getRole()==UserRole.ROLE_BRANCH_MANAGER) {
             if(employee.getBranchId() ==null) {
                 throw new Exception("branch id is required to create branch manager");
             }
             branch = branchRepository.findById(employee.getBranchId()).orElseThrow(() -> new Exception("branch not found"));
        }
        User user = UserMapper.toEntity(employee);
        user.setStore(store);
        user.setBranch(branch);
        user.setPassword(passwordEncoder.encode(employee.getPassword()));

        User savedEmployee = userRepository.save(user);
        if(employee.getRole()==UserRole.ROLE_BRANCH_MANAGER && branch!=null) {
            branch.setManager(savedEmployee);
        }
        return UserMapper.toDto(savedEmployee);
    }

    @Override
    public UserDto createBranchEmployee(UserDto user, Long branchId) throws Exception {

        Branch branch = branchRepository.findById(branchId).orElseThrow(() -> new Exception("Branch not found with ID: "+branchId));
        User employee = UserMapper.toEntity(user);
        if(!(employee.getRole().equals(UserRole.ROLE_BRANCH_CASHIER) || employee.getRole().equals(UserRole.ROLE_BRANCH_MANAGER))){
            throw new UserException("Invalid role for branch employee. Must be ROLE_BRANCH_ADMIN or ROLE_BRANCH_MANAGER");
        }

        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        employee.setBranch(branch);

        User isExist = userRepository.findByEmail(employee.getEmail());
        if(isExist!=null) {
            employee.setId(isExist.getId());
        }

        userRepository.save(employee);
        return UserMapper.toDto(employee);
    }

    @Override
    public User updateEmployee(Long employeeId, UserDto employeeDetails) throws Exception {
        User existingEmployee = userRepository.findById(employeeId).orElseThrow(() -> new Exception("employee not exist with id: "+employeeId));

        Branch branch = branchRepository.findById(employeeDetails.getBranchId()).orElseThrow(() -> new Exception("branch not found"));

        existingEmployee.setEmail(employeeDetails.getEmail());
        existingEmployee.setFullName(employeeDetails.getFullName());
        existingEmployee.setPassword(employeeDetails.getPassword());
        existingEmployee.setRole(employeeDetails.getRole());
        existingEmployee.setBranch(branch);

        return userRepository.save(existingEmployee);
    }

    @Override
    public void deleteEmployee(Long employeeId) throws Exception {
        User employee = userRepository.findById(employeeId).orElseThrow(() -> new Exception("employee not found"));
        userRepository.delete(employee);
    }

    @Override
    public List<UserDto> findStoreEmployee(Long storeId, UserRole userRole) throws Exception {

        Store store = storeRepository.findById(storeId).orElseThrow(() -> new Exception(" store not found"));

        return userRepository.findByStore(store).stream().filter(
                user -> userRole == null || user.getRole() == userRole
        )
        .map(UserMapper::toDto)
        .toList();

    }

    @Override
    public List<UserDto> findBranchEmployees(Long branchId, UserRole role) throws Exception {
        Branch branch = branchRepository.findById(branchId).orElseThrow(() -> new Exception("branch not found with id: "+branchId));

        return userRepository.findByBranchId(branchId).stream().filter(
                user -> role == null || user.getRole() == null
        )
        .map(UserMapper::toDto)
        .collect(Collectors.toList()) ;
    }
}
