package com.whut.entity;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.whut.core.BaseForm;


/**
 * TApDicContentEntity entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "user")
public class UserEntity extends BaseForm {

	// Fields

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid.hex") 
	@Column(name = "USER_ID", unique = true, nullable = false, length = 50)
	private int userId;

	@Column(name = "USERNAME", nullable = false, unique = true,length = 50)
	private String username;

	@Column(name = "PASSWORD", nullable = false, length = 50)
	private String password;

	

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

    public static void main(String[] args) {
        System.out.println("hello");
    }

}