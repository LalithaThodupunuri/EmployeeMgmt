package org.employee.repository;


import org.employee.db.Department;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
	 
@RepositoryRestResource
public interface DepartmentRepository extends CrudRepository<Department, Long>{
	
	 @Query("SELECT deptId FROM Department p WHERE LOWER(p.deptName) = LOWER(:deptName)")
	 public Long findByDeptName(@Param("deptName")String deptName);

}