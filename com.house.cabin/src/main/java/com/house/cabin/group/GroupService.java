package com.house.cabin.group;

import java.util.ArrayList;
import java.util.HashMap;

public interface GroupService {
	
	/*
	 * Usable Method
	 * */
	public ArrayList getChatWallLiteByDate(HashMap paraHm);
	public ArrayList getAllChatWallByWriteDate(HashMap paraHm);
	public HashMap getGroupJoinDate(HashMap paraHm);
	public void updateGroupName(HashMap paraHm);
	public void withdrawFromGroup(HashMap paraHm);
	public void deleteGroup(HashMap paraHm);
	
	/*
	 * Deprecated
	 * */
	
	public ArrayList getGroup(HashMap ParaHm);
	public ArrayList getGroupLite(HashMap parahm);
	public ArrayList getChatWall(HashMap paraHm);
	public ArrayList getChatWallLite(HashMap paraHm);
	public int makeGroup(HashMap parahm);
	public int writeChatWall(HashMap parahm);
	public int inviteGroup(HashMap parahm);
	public ArrayList getGroupMember(HashMap ParaHm);
	public ArrayList getGroupMemberLite(HashMap ParaHm);
	public boolean isHeGroupMember(HashMap parahm);
	public boolean isInvitationExist(HashMap parahm);
	public HashMap getWhoOfMessage(HashMap paraHm);
	public ArrayList getGroupMemeberRegistrationId(HashMap paraHm);
	public ArrayList getAllChatWall(HashMap paraHm);
	public ArrayList getMemeberRegistrationId(HashMap paraHm);
	
}
