package org.jqiaofu.spring.repository;

import org.jqiaofu.spring.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User>{
	
	public User findByName(String name);
	
	public User findByAgeLessThan(int age);
	
	@Query(value="select o from User o where o.age > (?1-10)")
	public User findByAgeDemo(int age);
	
	@Query(value="select o from User o where o.age > 10")
	public Page<User> findUserByAge(Pageable pageable);
}
