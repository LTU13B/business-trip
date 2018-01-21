package com.group1.view;

import java.util.Objects;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.group1.component.EmpInfoDesign;
import com.group1.model.Employee;

@Component
@Scope("prototype")
public class EmpInfoView extends EmpInfoDesign {
	
	@PostConstruct
	private void init() {
		
	}
	
	public void setReadOnly(boolean readOnly) {
		empNameTf.setReadOnly(readOnly);
		empEmailTf.setReadOnly(readOnly);
		empChucVuTf.setReadOnly(readOnly);
	}
	
	public void showEmployee(Employee emp) {
		empNameTf.setValue(Objects.toString(emp.getName(), ""));
		empEmailTf.setValue(Objects.toString(emp.getEmail(), ""));
		empChucVuTf.setValue(Objects.toString(emp.getChucVu().getName(), ""));
	}
}
