package org.employee.db;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class Department {

	    @Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    private long deptId;
	 
	    
	    @NotNull
	    private String deptName;
	  
	    @JsonIgnore
	    @OneToMany(mappedBy="department",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	    private List<Employee> employeeList;
	    
		
		public String getDeptName() {
			return deptName;
		}

		public void setDeptName(String deptName) {
			this.deptName = deptName;
		}
		
		
		public long getDeptId() {
			return deptId;
		}

		public void setDeptId(long deptId) {
			this.deptId = deptId;
		}

		
		public List<Employee> getEmployeeList() {
			return employeeList;
		}

		public void setEmployeeList(List<Employee> employeeList) {
			this.employeeList = employeeList;
		}

		@Override
		public String toString() {
			return "Department [iddepartment=" + deptId + ", name=" + deptName
					+ "]";
		}
		
		
	    
	    
}
