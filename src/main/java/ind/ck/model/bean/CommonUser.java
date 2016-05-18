package ind.ck.model.bean;

import java.io.Serializable;

public class CommonUser implements Serializable {
	
	/**
	 * 
	 */
	
	
	private static final long serialVersionUID = -5324297202113709543L;

	public CommonUser(){
		
	}
	
	
	public CommonUser(String username,String pwd){
		this.uname = username;
		this.upwd = pwd;
	}
	
	public CommonUser(String id,String username,String pwd){
		this.userId = id;
		this.uname = username;
		this.upwd = pwd;
	}
	
	private String uname;
	
	private String upwd;
	
	private String userId;
	
	//»ý·Ö
	private int bonus;
	
	private String phoneNumber;
	
	private String portrait;
	
	private String sexual;
	
	
	
	

	public String getSexual() {
		return sexual;
	}


	public void setSexual(String sexual) {
		this.sexual = sexual;
	}


	public String getPortrait() {
		return portrait;
	}


	public void setPortrait(String portrait) {
		this.portrait = portrait;
	}


	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}
	

	public String getUpwd() {
		return upwd;
	}

	public void setUpwd(String upwd) {
		this.upwd = upwd;
	}

	
	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public int getBonus() {
		return bonus;
	}

	public void setBonus(int bonus) {
		this.bonus = bonus;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	
	
	
	
	
}
