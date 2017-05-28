package com.example.maintenanceapp.data;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.example.maintenanceapp.enums.Priority;
import com.example.maintenanceapp.enums.Status;

public class Job implements Serializable {

    @NotNull
    private int id = -1;
    
    @NotNull
    @Size(min = 2, message = "item name must have at least two characters")
    private String item = "";
    
    @NotNull
    @Size(min = 2, message = "Work Procedure name must have at least two characters")
    private String workProcedure = "";
    
    @Min(0)
    private String Tag = "";
    
    @NotNull
    @Size(min = 2, message = "Department name must have at least two characters")
    private String department = "";
    
    @NotNull
    private Priority priority = Priority.LOW;
    
    
    @Min(value = 0, message = "Can't have negative amount of time between checks")
    private int frequency = 0;
    
    @Min(value = 0, message = "Can't have negative amount of time between checks")
    private int whenDue = 0;
    
    @NotNull
    private Status status = Status.NOT_DUE;
    
	@NotNull
    @Size(min = 2, message = "counter name must have at least two characters")
    private String counter = "";
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getWorkProcedure() {
		return workProcedure;
	}

	public void setWorkProcedure(String workProcedure) {
		this.workProcedure = workProcedure;
	}

	public String getTag() {
		return Tag;
	}

	public void setTag(String tag) {
		Tag = tag;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	public int getWhenDue() {
		return whenDue;
	}

	public void setWhenDue(int whenDue) {
		this.whenDue = whenDue;
	}
	
    public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getCounter() {
		return counter;
	}

	public void setCounter(String counter) {
		this.counter = counter;
	}


}
