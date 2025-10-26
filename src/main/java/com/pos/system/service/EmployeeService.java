package com.pos.system.service;

import com.pos.system.Modal.User;
import com.pos.system.domain.UserRole;
import com.pos.system.payload.dto.UserDto;

import java.util.List;

public interface EmployeeService {
    UserDto createStoreEmployee(UserDto employee, Long storeId) throws Exception;
    UserDto createBranchEmployee(UserDto employee, Long branchId) throws Exception;
    User updateEmployee(Long employeeId, UserDto employeeDetails) throws Exception;
    void deleteEmployee(Long employeeId) throws Exception;
    List<UserDto> findStoreEmployee(Long storeId, UserRole userRole) throws Exception;
    List<UserDto> findBranchEmployees(Long branchId, UserRole role) throws Exception;
}
