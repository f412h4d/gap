package com.company.paw.graphql.services;

import com.company.paw.Repositories.EmployeeRepository;
import com.company.paw.Repositories.OrganizationRepository;
import com.company.paw.Repositories.PositionRepository;
import com.company.paw.graphql.InputTypes.EmployeeInput;
import com.company.paw.models.Employee;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final PositionRepository positionRepository;
    private final OrganizationRepository organizationRepository;
    private final ImageService imageService;

    @GraphQLQuery
    public List<Employee> allEmployees() {
        return employeeRepository.findAll();
    }

    @GraphQLQuery
    public Employee getEmployee(String id) {
        return employeeRepository.findById(id).orElse(null);
    }

    @GraphQLMutation
    public Employee addEmployee(EmployeeInput employeeInput) {
        return employeeRepository.save(addInput(employeeInput));
    }

    @GraphQLMutation
    public Employee updateEmployee(String id, EmployeeInput employeeInput) {
        return employeeRepository.save(updateInput(id, employeeInput));
    }

    @GraphQLMutation
    public Employee deleteEmployee(String id) {
        Employee employee = employeeRepository.findById(id).get();
        employeeRepository.delete(employeeRepository.findById(id).get());
        return employee;
    }

    private Employee addInput(EmployeeInput input) {


        return Employee.builder()
                .fullName(input.getFullName())
                .nationalId(input.getNationalId())
                .employeeId(input.getEmployeeId())
                .address(input.getAddress())
                .phoneNumber(input.getPhoneNumber())
                .position(positionRepository.findById(input.getPositionId()).orElse(null))
                .organization(organizationRepository.findById(input.getOrganizationId()).orElse(null))
                .images(imageService.imagesIdToImages(input.getImages()))
                .build();
    }

    private Employee updateInput(String inputId, EmployeeInput input) {
        Employee employee = employeeRepository.findById(inputId).orElse(new Employee());
        //todo throw error if required fields are Null
        //but also is handled in front end
        if (!input.getFullName().isEmpty())
            employee.setFullName(input.getFullName());
        if (!input.getNationalId().isEmpty())
            employee.setNationalId(input.getNationalId());
        if (!input.getEmployeeId().isEmpty())
            employee.setEmployeeId(input.getEmployeeId());
        if (!input.getAddress().isEmpty())
            employee.setAddress(input.getAddress());
        if (!input.getPhoneNumber().isEmpty())
            employee.setPhoneNumber(input.getPhoneNumber());
        if (!input.getPositionId().isEmpty())
            employee.setPosition(positionRepository.findById(input.getPositionId()).orElse(null));
        if (!input.getOrganizationId().isEmpty())
            employee.setOrganization(organizationRepository.findById(input.getOrganizationId()).orElse(null));
        if (!input.getImages().isEmpty())
            employee.setImages(imageService.imagesIdToImages(input.getImages()));
        return employee;
    }
}
