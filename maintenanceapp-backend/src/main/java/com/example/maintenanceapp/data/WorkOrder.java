package com.example.maintenanceapp.data;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class WorkOrder implements Serializable {

	@NotNull
	private int id = -1;

	@NotNull
	@Size(min = 2, message = "name must have at least two characters")
	private String name = "";

	@NotNull
	@Size(min = 2, message = "Work Procedure name must have at least two characters")
	private String type = "";

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
