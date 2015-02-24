package com.mayo.inspiee.data;

public class AlarmTimers {
	private int amHours;
	private int amMinutes;
	private int pmHours;
	private int pmMinutes;
	
	public AlarmTimers(int amHours, int amMinutes, int pmHours, int pmMinutes) {
		super();
		this.amHours = amHours;
		this.amMinutes = amMinutes;
		this.pmHours = pmHours;
		this.pmMinutes = pmMinutes;
	}

	public int getAmHours() {
		return amHours;
	}

	public int getAmMinutes() {
		return amMinutes;
	}

	public int getPmHours() {
		return pmHours;
	}

	public int getPmMinutes() {
		return pmMinutes;
	}
}
