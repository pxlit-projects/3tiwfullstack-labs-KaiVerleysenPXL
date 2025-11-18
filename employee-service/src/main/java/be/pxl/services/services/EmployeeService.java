package be.pxl.services.services;

import be.pxl.services.client.NotificationClient;
import be.pxl.services.domain.Employee;
import be.pxl.services.domain.NotificationRequest;
import be.pxl.services.domain.dto.EmployeeRequest;
import be.pxl.services.domain.dto.EmployeeResponse;
import be.pxl.services.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService implements IEmployeeService {

    private final EmployeeRepository employeeRepository;
    private final NotificationClient notificationClient;

    @Override
    public List<EmployeeResponse> getAllEmployees() {
        return employeeRepository.findAll().stream().map(this::mapEmployeeToEmployeeResponse).toList();
    }

    @Override
    public EmployeeResponse addEmployee(EmployeeRequest employeeRequest) {
        Employee employee = Employee.builder()
                .age(employeeRequest.getAge())
                .name(employeeRequest.getName())
                .departmentId(employeeRequest.getDepartmentId())
                .organizationId(employeeRequest.getOrganizationId())
                .position(employeeRequest.getPosition())
                .build();
        Employee newEmployee = employeeRepository.save(employee);

        NotificationRequest notificationRequest = NotificationRequest.builder()
                .message("Employee created")
                .sender("Kai")
                .build();
        notificationClient.sendNotification(notificationRequest);

        return mapEmployeeToEmployeeResponse(newEmployee);
    }

    @Override
    public EmployeeResponse getEmployeeById(Long id) {
        return employeeRepository.findById(id).map(this::mapEmployeeToEmployeeResponse).orElse(null);
    }

    @Override
    public List<EmployeeResponse> findByDepartmentId(Long departmentId) {
        return employeeRepository.findByDepartmentId(departmentId)
                .stream().map(this::mapEmployeeToEmployeeResponse).toList();
    }

    @Override
    public List<EmployeeResponse> findByOrganizationId(Long organizationId) {
        return employeeRepository.findByOrganizationId(organizationId)
                .stream().map(this::mapEmployeeToEmployeeResponse).toList();
    }

    public EmployeeResponse mapEmployeeToEmployeeResponse(Employee employee) {
        return EmployeeResponse.builder()
                .id(employee.getId())
                .age(employee.getAge())
                .name(employee.getName())
                .departmentId(employee.getDepartmentId())
                .organizationId(employee.getOrganizationId())
                .position(employee.getPosition())
                .build();
    }
}
