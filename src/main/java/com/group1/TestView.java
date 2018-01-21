package com.group1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.group1.model.User;
import com.group1.view.*;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.UI;

@SpringUI(path = "/")
@Title("QUẢN LÝ CÔNG TÁC")
@Theme("mytheme")
public class TestView extends UI {
	@Autowired
	private ApplicationContext appContext;

	@Override
	protected void init(VaadinRequest request) {
		setContent(appContext.getBean(LoginView.class));
	}

}
