package com.example.pinminder.dto;

public class Dream {
	
	Integer id;
	String zone;
	String todo;	
	double lat;
	double lon;
	String location;
	String memo;
	String category;
	Integer check;
	Integer noti;
	Integer pin;
	
	public Dream(){
		
	}
	public Dream(Integer id, String zone, String todo, double lat, double lon,
			String location, String memo, String category, Integer check,
			Integer noti,Integer pin) {
		super();
		this.id = id;
		this.zone = zone;
		this.todo = todo;
		this.lat = lat;
		this.lon = lon;
		this.location = location;
		this.memo = memo;
		this.category = category;
		this.check = check;
		this.noti = noti;
		this.pin = pin;
	}
	
	/*
	 * Getter Setter
	 */
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getZone() {
		return zone;
	}
	public void setZone(String zone) {
		this.zone = zone;
	}
	public String getTodo() {
		return todo;
	}
	public void setTodo(String todo) {
		this.todo = todo;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLon() {
		return lon;
	}
	public void setLon(double lon) {
		this.lon = lon;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	public Integer getCheck() {
		return check;
	}
	public void setCheck(Integer check) {
		this.check = check;
	}
	public Integer getNoti() {
		return noti;
	}
	public void setNoti(Integer noti) {
		this.noti = noti;
	}
	public Integer getPin() {
		return pin;
	}
	public void setPin(Integer pin) {
		this.pin = pin;
	}
	
	
	

}
