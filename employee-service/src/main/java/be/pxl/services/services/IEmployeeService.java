package be.pxl.services.services;

import be.pxl.services.domain.dto.EmployeeRequest;
import be.pxl.services.domain.dto.EmployeeResponse;

import java.util.List;

public interface IEmployeeService {
    List<EmployeeResponse> getAllEmployees();

    EmployeeResponse addEmployee(EmployeeRequest employeeRequest);
    EmployeeResponse getEmployeeById(Long id);

    List<EmployeeResponse> findByDepartmentId(Long departmentId);

    List<EmployeeResponse> findByOrganizationId(Long organizationId);
}
