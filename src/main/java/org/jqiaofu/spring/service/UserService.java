package org.jqiaofu.spring.service;


import org.jqiaofu.spring.domain.User;
import org.jqiaofu.spring.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value="userService")
@Transactional
public class UserService {
	
	public final static Logger log = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private UserRepository userRepository;
	
	public User getUserInfo(User user){
		User u = userRepository.findByAgeDemo(user.getAge());
		System.out.println(u);
		return userRepository.findByName(user.getName());
	}
	
}
