package com.employee.service.impl;

import com.employee.model.dto.EmployeeDto;
import com.employee.model.entity.Employee;
import com.employee.repository.EmployeeRepository;
import com.employee.service.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;
    public EmployeeServiceImpl (EmployeeRepository employeeRepository,ModelMapper modelMapper){
        this.employeeRepository=employeeRepository;
        this.modelMapper=modelMapper;
    }
    @Override
    public EmployeeDto saveEmployee(EmployeeDto employeeDto) {
        if(employeeDto.getId()!=null){
            throw new RuntimeException("Employee already exists");
        }
        Employee employeeEntity=modelMapper.map(employeeDto, Employee.class);
        Employee savedEmpEntity = employeeRepository.save(employeeEntity);
        return modelMapper.map(savedEmpEntity,EmployeeDto.class);
    }

    @Override
    public EmployeeDto updateEmployee(Long id, EmployeeDto employeeDto) {

        if(id==null || employeeDto.getId()==null){
            throw new RuntimeException("Please provide employee id");
        }
        if(!Objects.equals(id,employeeDto.getId())){
            throw new RuntimeException("Id mismatch");
        }
        employeeRepository.findById(id).orElseThrow(()->new RuntimeException("Employee not found"));
        Employee employeeEntity=modelMapper.map(employeeDto, Employee.class);
        Employee updatedEmpEntity = employeeRepository.save(employeeEntity);
        return modelMapper.map(updatedEmpEntity,EmployeeDto.class);
    }

    @Override
    public void deleteEmployee(Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(()-> new RuntimeException("Employee not found"));
        employeeRepository.delete(employee);
    }

    @Override
    public EmployeeDto getSingleEmployee(Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(()->new RuntimeException("Employee not found"));
        return modelMapper.map(employee, EmployeeDto.class);
    }

    @Override
    public List<EmployeeDto> getAllEmployees() {
        List<Employee> employeeList = employeeRepository.findAll();
        return employeeList.stream().map(employee -> modelMapper.map(employee,EmployeeDto.class)).toList();
    }
}
