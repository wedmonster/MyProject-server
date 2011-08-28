package com.house.cabin.user;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@Transactional
public class UserServiceImpl implements UserService{
	@Autowired
	private SqlMapClientTemplate sqlMapClientTemplate;
	
	public void updateUserName(HashMap paraHm) {
		// TODO Auto-generated method stub
		sqlMapClientTemplate.update("updateUserName", paraHm);
	}
	/**
	 * @author kotaro	 
	 * @since 2011.07.14
	 */
	public HashMap getUserInfo(HashMap paraHm) {
		// TODO Auto-generated method stub
		return (HashMap) sqlMapClientTemplate.queryForObject("getUserInfo", paraHm);
	}
	
	
	
	
	
	
	public boolean isHeMember(HashMap paraHm) {
		// TODO Auto-generated method stub					
		return ((Integer) sqlMapClientTemplate.queryForObject("isHeMember?", paraHm) == 1)? true:false;
	}

	public boolean join(UserBean userbean) {
		// TODO Auto-generated method stub
		
		if(userbean.getPassword()==null){
			//join as app
			sqlMapClientTemplate.insert("joinAsApp", userbean);
			//System.out.println(i);
		}else{
			//join as web
		}
		
		return true;
	}
	public ArrayList getInvitationList(HashMap ParaHm) {
		// TODO Auto-generated method stub
		return (ArrayList)sqlMapClientTemplate.queryForList("getInvitationList", ParaHm);
	}

	public int joinGroupMember(HashMap paraHm) {
		// TODO Auto-generated method stub
		int t = (Integer)sqlMapClientTemplate.insert("joinGroupMember", paraHm);
		return t;
	}

	public boolean deleteInvitation(HashMap paraHm) {
		// TODO Auto-generated method stub
		sqlMapClientTemplate.insert("deleteInvitation", paraHm);
		
		return true;
	}

	public void updateRegistrationId(HashMap paraHm) {
		// TODO Auto-generated method stub
		sqlMapClientTemplate.update("updateRegistrationId", paraHm);
	}

	public HashMap getMaxCode(HashMap paraHm) {
		// TODO Auto-generated method stub
		return (HashMap) sqlMapClientTemplate.queryForObject("getMaxCode", paraHm);
	
	}

	public HashMap getJoinDate(HashMap paraHm) {
		// TODO Auto-generated method stub
		return (HashMap) sqlMapClientTemplate.queryForObject("getJoinDate", paraHm);
	}



	

	

}
