package org.jqiaofu.spring.job;


import org.jqiaofu.spring.domain.User;
import org.springframework.batch.item.ItemProcessor;

public class testProcessor implements ItemProcessor<User, User> {

	public User process(User item) throws Exception {
		item.setState(false);
		item.setAge(item.getAge()+1);
		return item;
	}

}
