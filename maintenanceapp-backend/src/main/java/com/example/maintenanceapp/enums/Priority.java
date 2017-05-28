package com.example.maintenanceapp.enums;

public enum Priority {
	LOW("Low"), NORMAL("Normal"), HIGH("High");

	private final String name;

	private Priority(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

}
