package com.rupesh.blog.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Role implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -301824859167970509L;
	@Id
	private int id;
	private String name;
}
