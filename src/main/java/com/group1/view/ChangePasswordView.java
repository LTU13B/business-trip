package com.group1.view;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.group1.component.ChangePasswordDesign;
import com.group1.model.User;
import com.group1.repository.UserRepository;
import com.vaadin.ui.Notification;

@Component
@Scope("prototype")
public class ChangePasswordView extends ChangePasswordDesign {
	
	private User user;
	
	@Autowired
	private UserRepository userRepository;
	
	@PostConstruct
	private void init() {
		okBtn.addClickListener(event->{
			user.setPassword(newPassTf.getValue());
			userRepository.save(user);
			Notification.show("Đổi mật khẩu thành công", Notification.Type.WARNING_MESSAGE);
		});
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
}
