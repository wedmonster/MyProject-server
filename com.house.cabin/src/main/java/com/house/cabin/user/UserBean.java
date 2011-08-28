package com.house.cabin.user;

public class UserBean {
	private String u_code;
	private String phone_number;
	private String password;
	private String email_address;
	private String name;
	private String on_line_id;
	private String join_date;
	private String photo_path;
	public UserBean(String phone_number2, String name2) {
		// TODO Auto-generated constructor stub
		this.setPhone_number(phone_number2);
		this.setName(name2);
	}
	public String getU_code() {
		return u_code;
	}
	public void setU_code(String u_code) {
		this.u_code = u_code;
	}
	public String getPhone_number() {
		return phone_number;
	}
	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail_address() {
		return email_address;
	}
	public void setEmail_address(String email_address) {
		this.email_address = email_address;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOn_line_id() {
		return on_line_id;
	}
	public void setOn_line_id(String on_line_id) {
		this.on_line_id = on_line_id;
	}
	public String getJoin_date() {
		return join_date;
	}
	public void setJoin_date(String join_date) {
		this.join_date = join_date;
	}
	public String getPhoto_path() {
		return photo_path;
	}
	public void setPhoto_path(String photo_path) {
		this.photo_path = photo_path;
	}
	
	
}
