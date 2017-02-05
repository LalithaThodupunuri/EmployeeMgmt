package org.employee.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.employee.db.Employee;
import org.employee.repository.DepartmentRepository;
import org.employee.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;


/**
 * Handles requests for the Employee service.
 */
@RestController
public class EmployeeController {
	
	private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
	@Autowired
	EmployeeRepository empRep ;
	@Autowired
	DepartmentRepository deptRep;
	
	@RequestMapping(value = "emp/{id}", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Employee getEmployee(@Validated @NotNull @PathVariable("id") int empId) {
		logger.info("Start getEmployee. ID="+empId);	
		Employee emp = empRep.findOne((long) empId);
		if(emp == null){
			throw new RestClientException("[" + 404 + "] " + "Couldnot Find the Employee for the given id");
		}
		return emp;
	}
	
	@RequestMapping(value = "emp", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Employee> getAllEmployees() {
		logger.info("Start getAllEmployees.");
		List<Employee> emps = new ArrayList<Employee>();
		for(Employee emp : empRep.findAll()){
			emps.add(emp);
		}
		
		return emps;
	}
	
	@RequestMapping(value = "emp", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Employee createEmployee(@Validated @RequestBody Employee emp) {
		logger.info("Start createEmployee.");	
		Long deptId = deptRep.findByDeptName(emp.getDepartment().getDeptName());	
		if(deptId == null){
			logger.error("Cannot find department. Department needs to be created");						
			throw new RestClientException("[" + 500 + "] " + "Department is not found.");
		}
		emp.setDepartment(deptRep.findOne(deptId));
		Employee newEmp = empRep.save(emp);
		return newEmp;
	}
	
	@RequestMapping(value = "emp/{id}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Employee deleteEmployee(@PathVariable("id") int empId) {
		logger.info("Start deleteEmployee.");
		Employee emp = empRep.findOne((long) empId);
		if(emp == null){
			throw new RestClientException("[" + 404 + "] " + "Couldnot Find the Employee to delete");
		}
		empRep.delete(emp);
		return emp;
	}
	
	
	@RequestMapping(value = "emp", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Employee updateEmployee(@Validated @RequestBody Employee emp) {
		logger.info("Start updateEmployee.");		
	    Employee oldEmp = empRep.findOne((long) emp.getId());
	    if(oldEmp == null){
			throw new RestClientException("[" + 404 + "] " + "Couldnot Find the Employee to update");
		}
		oldEmp.setFirstName(emp.getFirstName());
		oldEmp.setLastName(emp.getLastName());
		Employee updatedEmp = empRep.save(oldEmp);			
		return updatedEmp;	
		
	}
	

}