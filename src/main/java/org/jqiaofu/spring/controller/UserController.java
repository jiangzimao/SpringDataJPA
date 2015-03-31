package org.jqiaofu.spring.controller;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jqiaofu.spring.domain.User;
import org.jqiaofu.spring.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/user")
public class UserController {
	
	private final static Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Resource(name="userService")
	private UserService userService;

	@RequestMapping(value = "/{id}/detail",method = RequestMethod.GET, produces={MediaType.APPLICATION_JSON_VALUE+";charset=UTF-8", MediaType.APPLICATION_XML_VALUE+";charset=UTF-8"}) 
	@ResponseStatus(HttpStatus.OK)
	public User view(@PathVariable("id") Integer id) {
		logger.debug("get ....");
		logger.info("dddd");
		User user = new User();
		user.setId(id);
		user.setName("测试数据");
		user.setInDate(new Date());
		user.setState(false);
		user = userService.getUserInfo(user);
		return user;
	}
	
	@RequestMapping(value = "/{name}/user" ,method = RequestMethod.GET)
	public ModelAndView userDetail(@PathVariable("name") String name, HttpServletRequest request, HttpServletResponse response,
            ModelMap mode){
		User user = new User();
		user.setId(1231);
		user.setName(name);
		user.setInDate(new Date());
		user.setState(false);
		mode.addAttribute("user", user);
		return new ModelAndView("User", mode);
	}
}