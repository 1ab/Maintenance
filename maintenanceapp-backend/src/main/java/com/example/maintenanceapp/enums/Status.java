package com.example.maintenanceapp.enums;

public enum Status {
	NOT_DUE("Not Due"), NEW_JOB("New job"), DUE("Due");

	private final String name;

	private Status(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}
}
