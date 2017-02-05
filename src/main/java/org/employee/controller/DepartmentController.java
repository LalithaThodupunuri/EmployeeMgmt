package org.employee.controller;

import java.util.ArrayList;
import java.util.List;

import org.employee.db.Department;
import org.employee.repository.DepartmentRepository;
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
public class DepartmentController {
	
	private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
	@Autowired
	DepartmentRepository deptRep ;
	
	
	@RequestMapping(value = "dept/{id}", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Department getDepartment(@PathVariable("id") int deptId) {
		logger.info("Start getDepartment. ID="+deptId);		
		Department dept = deptRep.findOne((long) deptId);
		if(dept == null){
			throw new RestClientException("[" + 404 + "] " + "Couldnot Find the Department for the given DeptId");		
		}
		return dept;
	}
	
	@RequestMapping(value = "dept", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Department> getAllDepartments() {
		logger.info("Start getAllDepartments.");
		List<Department> deps = new ArrayList<Department>();
		for(Department dep : deptRep.findAll()){
			deps.add(dep);
		}
		
		return deps;
	}
	
	@RequestMapping(value = "dept", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Department createDepartment(@Validated @RequestBody Department inputDept) {
		logger.info("Start createDepartment.");	    
		Department dept = deptRep.save(inputDept);
		return dept;
	}
	
	@RequestMapping(value = "dept", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Department updateDepartment(@Validated @RequestBody Department dept) {
		logger.info("Start updateDeparment.");
		Department oldDept = deptRep.findOne((long) dept.getDeptId());		
		if(oldDept == null){
			throw new RestClientException("[" + 404 + "] " + "Couldnot Find the Department to Update");
		}
		oldDept.setDeptName(dept.getDeptName());	
		Department updatedDept = deptRep.save(oldDept);
		return updatedDept;
	}
	
	@RequestMapping(value = "dept/{id}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Department deleteDepartment(@PathVariable("id") int deptId) {
		logger.info("Start deleteDepartment.");
		Department dept = deptRep.findOne((long) deptId);
		if(dept == null){
			throw new RestClientException("[" + 404 + "] " + "Couldnot Find the Department to delete");
		}
		deptRep.delete(dept);
		return dept;
	}
		
}