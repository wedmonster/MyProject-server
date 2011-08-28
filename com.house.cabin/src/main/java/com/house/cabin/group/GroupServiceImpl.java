package com.house.cabin.group;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GroupServiceImpl implements GroupService{
	@Autowired
	private SqlMapClientTemplate sqlMapClientTemplate;
	
	
	/*
	 * Usable Method
	 * */
	public ArrayList getChatWallLiteByDate(HashMap paraHm) {
		// TODO Auto-generated method stub
		return (ArrayList) sqlMapClientTemplate.queryForList("getChatWallLiteByDate", paraHm);
	}


	public ArrayList getAllChatWallByWriteDate(HashMap paraHm) {
		// TODO Auto-generated method stub
		return (ArrayList) sqlMapClientTemplate.queryForList("getAllChatWallByWriteDate", paraHm);	
	}
	
	public HashMap getGroupJoinDate(HashMap paraHm) {
		// TODO Auto-generated method stub
		return (HashMap) sqlMapClientTemplate.queryForObject("getGroupJoinDate", paraHm);
	}
	
	public void updateGroupName(HashMap paraHm) {
		// TODO Auto-generated method stub
		sqlMapClientTemplate.update("updateGroupName", paraHm);
	}
	public void withdrawFromGroup(HashMap paraHm) {
		// TODO Auto-generated method stub
		sqlMapClientTemplate.delete("withdrawFromGroup", paraHm);
	}
	public void deleteGroup(HashMap paraHm) {
		// TODO Auto-generated method stub
		sqlMapClientTemplate.delete("deleteGroup", paraHm);
	}

	
	
	/*
	 * Deprecated
	 * */
	

	public ArrayList getGroup(HashMap parahm) {
		// TODO Auto-generated method stub
		
		return (ArrayList) sqlMapClientTemplate.queryForList("getGroup", parahm);
	}

	public int makeGroup(HashMap parahm) {
		// TODO Auto-generated method stub
		int r = (Integer) sqlMapClientTemplate.insert("makeGroup", parahm);
		parahm.put("g_code", r);
		int t = (Integer) sqlMapClientTemplate.insert("joinGroup", parahm);
		//sqlMapClientTemplate.insert("makeGroup", parahm);
		return t;
	}

	public ArrayList getChatWall(HashMap paraHm) {
		// TODO Auto-generated method stub
		return (ArrayList) sqlMapClientTemplate.queryForList("getChatWall", paraHm);
	}

	public int writeChatWall(HashMap parahm) {
		// TODO Auto-generated method stub
		int t = (Integer) sqlMapClientTemplate.insert("writeChatWall", parahm);
		return t;
	}

	public ArrayList getGroupMember(HashMap ParaHm) {
		// TODO Auto-generated method stub
		
		return (ArrayList)sqlMapClientTemplate.queryForList("getGroupMember", ParaHm);
	}

	public HashMap getWhoOfMessage(HashMap paraHm) {
		// TODO Auto-generated method stub
		return (HashMap) sqlMapClientTemplate.queryForObject("getWhoOfMessage", paraHm);
	}

	public int inviteGroup(HashMap parahm) {
		// TODO Auto-generated method stub
		int t = (Integer) sqlMapClientTemplate.insert("inviteGroup", parahm);
		return t;
	}

	public boolean isHeGroupMember(HashMap parahm) {
		// TODO Auto-generated method stub
		return ((Integer) sqlMapClientTemplate.queryForObject("isHeGroupMember", parahm) == 1)? true:false;
	}

	public boolean isInvitationExist(HashMap parahm) {
		// TODO Auto-generated method stub
		return ((Integer) sqlMapClientTemplate.queryForObject("isInvitationExist", parahm) >= 1)? true:false;
	}

	public ArrayList getGroupLite(HashMap parahm) {
		// TODO Auto-generated method stub
		return (ArrayList) sqlMapClientTemplate.queryForList("getGroupLite", parahm);
		
	}

	public ArrayList getChatWallLite(HashMap paraHm) {
		// TODO Auto-generated method stub
		return (ArrayList) sqlMapClientTemplate.queryForList("getChatWallLite", paraHm);
	}

	public ArrayList getGroupMemeberRegistrationId(HashMap paraHm) {
		// TODO Auto-generated method stub
		return (ArrayList) sqlMapClientTemplate.queryForList("getGroupMemeberRegistrationId", paraHm);
	}

	public ArrayList getAllChatWall(HashMap paraHm) {
		// TODO Auto-generated method stub
		return (ArrayList) sqlMapClientTemplate.queryForList("getAllChatWall", paraHm);
	}

	public ArrayList getGroupMemberLite(HashMap ParaHm) {
		// TODO Auto-generated method stub
		return (ArrayList)sqlMapClientTemplate.queryForList("getGroupMemberLite", ParaHm);
	}

	public ArrayList getMemeberRegistrationId(HashMap paraHm) {
		// TODO Auto-generated method stub
		HashMap hm = (HashMap)sqlMapClientTemplate.queryForObject("getMemeberRegistrationId", paraHm);
		ArrayList arr = new ArrayList();
		arr.add(hm);
		return arr;
	}


	

	


	


	



	
	
}
