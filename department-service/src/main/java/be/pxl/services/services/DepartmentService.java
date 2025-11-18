package be.pxl.services.services;

import be.pxl.services.domain.Department;
import be.pxl.services.domain.Employee;
import be.pxl.services.domain.dto.DepartmentRequest;
import be.pxl.services.domain.dto.DepartmentResponse;
import be.pxl.services.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentService implements IDepartmentService {
    private final DepartmentRepository departmentRepository;


    @Override
    public DepartmentResponse addDepartment(DepartmentRequest departmentRequest) {
        Department department = Department.builder()
                .organizationId(departmentRequest.getOrganizationId())
                .name(departmentRequest.getName())
                .employees(new ArrayList<Employee>())
                .position(departmentRequest.getPosition())
                .build();
        Department newDepartment = departmentRepository.save(department);
        return mapDepartmentToDepartmentResponse(newDepartment);
    }

    @Override
    public List<DepartmentResponse> getAllDepartments() {
        return departmentRepository.findAll().stream().map(this::mapDepartmentToDepartmentResponse).toList();
    }

    @Override
    public DepartmentResponse getDepartmentById(Long id) {
        return departmentRepository.findById(id).map(this::mapDepartmentToDepartmentResponse).orElse(null);
    }

    @Override
    public List<DepartmentResponse> findByOrganizationId(Long organizationId) {
        return departmentRepository.findByOrganizationId(organizationId).stream()
                .map(this::mapDepartmentToDepartmentResponse).toList();
    }

    @Override
    public void addEmployees(DepartmentResponse department) {
        return; // TODO
    }

    private DepartmentResponse mapDepartmentToDepartmentResponse(Department department) {
        return DepartmentResponse.builder()
                .id(department.getId())
                .organizationId(department.getOrganizationId())
                .name(department.getName())
                .position(department.getPosition())
                .build();
    }
}
