package com.house.cabin.user;

import java.util.ArrayList;
import java.util.HashMap;

public interface UserService {
	/*
	 * Usable Method
	 * */
	public HashMap getJoinDate(HashMap paraHm);
	public void updateUserName(HashMap paraHm);
	public HashMap getUserInfo(HashMap paraHm);
	
	/*
	 * Deprecated
	 * */
	
	public boolean isHeMember(HashMap paraHm);
	public boolean join(UserBean userbean);
	public ArrayList getInvitationList(HashMap ParaHm);
	public int joinGroupMember(HashMap paraHm);
	public boolean deleteInvitation(HashMap paraHm);
	public void updateRegistrationId(HashMap paraHm);
	public HashMap getMaxCode(HashMap paraHm);
	
}
