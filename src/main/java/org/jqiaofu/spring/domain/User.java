package org.jqiaofu.spring.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.jqiaofu.spring.adapter.JaxbDateAdapter;

@XmlAccessorType(XmlAccessType.PROPERTY)  
@XmlRootElement(name = "user")  
@XmlType(name="userType")
@Entity
@Table(name = "user_info", uniqueConstraints={@UniqueConstraint(columnNames = { "id" })})
public class User extends PersistableEntity<Integer>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "age")
	private int age;
	
	@Column(name = "qq")
	private int qq;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "inDate")
	private Date inDate;
	
	@Column(name = "state")
	private Boolean state;
	
    public enum Gender{
        //通过括号赋值,而且必须带有一个参构造器和一个属性跟方法，否则编译出错
        //赋值必须都赋值或都不赋值，不能一部分赋值一部分不赋值；如果不赋值则不能写构造器，赋值编译也出错
        MAN("MAN"), WOMEN("WOMEN");
        
        private final String value;

        //构造器默认也只能是private, 从而保证构造函数只能在内部使用
        Gender(String value) {
            this.value = value;
        }
        
        public String getValue() {
            return value;
        }
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getQq() {
		return qq;
	}

	public void setQq(int qq) {
		this.qq = qq;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getInDate() {
		return inDate;
	}

	public void setInDate(Date inDate) {
		this.inDate = inDate;
	}

	public Boolean getState() {
		return state;
	}

	public void setState(Boolean state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "User [name=" + name + ", age=" + age + ", qq=" + qq
				+ ", email=" + email + ", inDate=" + inDate + ", state="
				+ state + "]";
	}

	
}
