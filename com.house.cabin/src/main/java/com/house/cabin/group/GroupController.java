package com.house.cabin.group;

import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;


@Controller
public class GroupController {
	private static final Logger logger = LoggerFactory.getLogger(GroupController.class);
	private String HOST = "http://android.apis.google.com/c2dm/send";
    private String AUTH = "DQAAAL4AAAD9y-BH-WfkKeeY0KzzsbzkeLKSQtdeJPru9Xewdz_Xeez6TPmVLWZtUFMvnbJRiEAqOV1xFuwjVS4MvhIO00Fgaql-eXEhWaaGhwTgekAa6QOWhHAQRwadD6ZAuxd2G8AaBbdOn5ag7gNB4aPGovuoNmMnsFDKVjlPOsxSNCEzx_V2OAs6-qhOH8lsMfBk9UmAr0ekAmxlim-tml3jv27sml9N5b6a5eR5E9EsZzOJduy6PZKbDPmGluUpV4ETErY";
    
	@Autowired
	private GroupService groupService;
	
	private ExecutorService executorService;
	private final int MAX_THREAD = 50;
	public GroupController(){
		logger.info("GroupController Creator");
		executorService = Executors.newFixedThreadPool(MAX_THREAD);		
	}	
	
	/*
	 * Usable Method
	 * */
	
	
	
	/**
	 * @author kotaro
	 * 
	 * <p>name : getChatWallListByDate </p>
	 * 
	 * <p>description : return JSON Sets which are message data after 'write_date_time' given one of parameters. </p> 
	 * 
	 * @param g_code
	 * @param phone_number
	 * @param write_date_time
	 * @param modelMap
	 * 
	 * @return JSON Set
	 * 
	 * result_set : [
	 * 	 {"g_code" : "?", 
	 * 	  "m_code" : "?",
	 * 	  "u_code" : "?",
	 * 	  "message" : "?",
	 * 	  "write_date_time" : "?",
	 * 	  "name" : "?",
	 * 	  "photo_path" : "?"}, 
	 *    {...} ] 
	 *    
	 * 
	 */
	@RequestMapping(value="/{g_code}/{phone_number}/getChatWallLiteByDate.do", method=RequestMethod.POST)
	public ModelAndView getChatWallLiteByDate(@PathVariable String g_code, 
			@PathVariable String phone_number, HttpServletRequest request, String write_date_time, Map modelMap){
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("=====getChatWallLiteByDate.do=====");
		logger.info("==param== g_code is " + g_code);
		logger.info("==param== write_date_time is " + write_date_time);
		
					
		HashMap ParaHm = new HashMap();
		ParaHm.put("phone_number", phone_number);
		ParaHm.put("g_code", g_code);
		ParaHm.put("write_date_time", write_date_time);
		
		ModelAndView model = new ModelAndView();
		logger.info("==result_set== result_set are " + groupService.getChatWallLiteByDate(ParaHm));
		model.addObject("result_set", groupService.getChatWallLiteByDate(ParaHm));			
		model.setView(new MappingJacksonJsonView(){	});
		
		return model;
		
	}
	
	@RequestMapping(value="/{phone_number}/getAllChatWallByDate.do", method=RequestMethod.POST)
	public ModelAndView getAllChatWallByDate(@PathVariable String phone_number, HttpServletRequest request, Map modelMap){
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("=====getAllChatWallByDate=====");
		
		
		String[] gm_code = request.getParameterValues("gm_code");
		ArrayList sum_arr = new ArrayList();
		if(gm_code!=null){
			for(int i = 0; i<gm_code.length; i++){				
				String[] before_str = gm_code[i].split("/");
				HashMap parahm = new HashMap();
				
				if(before_str[2].equals("need")){
					parahm.put("g_code", before_str[0]);
					parahm.put("phone_number", phone_number);
					HashMap ghm = groupService.getGroupJoinDate(parahm);					
					parahm.put("write_date_time", ghm.get("group_join_date"));				
					
				}else{
					parahm.put("g_code", before_str[0]);
					parahm.put("write_date_time", before_str[1]);								
				}
				ArrayList arr = groupService.getAllChatWallByWriteDate(parahm);
				for(int j = 0; j<arr.size();j++){
					sum_arr.add((HashMap)arr.get(j));
				}
			}	
		}
		ModelAndView model = new ModelAndView();
		logger.info("==result_set== result_set are " + sum_arr);
		model.addObject("result_set",  sum_arr);			
		model.setView(new MappingJacksonJsonView(){	});		
		return model;
		
	}
	
	
	
	
	
	
	
	/**
	 * <p><b>/phone_number/getGroup.do</b></p>
	 * 	  
	 * @param phone_number
	 * @param modelMap
	 * @return 
	 * 
	 * <p> </p>
	 * 
	 */	
	@RequestMapping(value="/{phone_number}/getGroup.do", method=RequestMethod.POST)
	public ModelAndView getGroup(@PathVariable String phone_number, Map modelMap){
		logger.info("=====getGroup.do=====");
		logger.info("==param== phone_number is " + phone_number);
		
		HashMap ParaHm = new HashMap();
		ParaHm.put("phone_number", phone_number);
		
		logger.info(groupService.getGroup(ParaHm)+"");
		
		ModelAndView model = new ModelAndView();
		
		model.addObject("result_set", groupService.getGroup(ParaHm));			
		model.setView(new MappingJacksonJsonView(){	});
		
		return model;
		
	}
	/**
	 * <p><b>/g_code/phone_number/updateGroupName.do</b></p>
	 * 
	 * @param g_code	  
	 * @param phone_number 
	 * @param modelMap
	 * @return {update_result : ok}
	 * 
	 * <p> </p>
	 * 
	 */	
	@RequestMapping(value="/{g_code}/{phone_number}/updateGroupName.do", method=RequestMethod.POST)
	public ModelAndView updateGroupName(@PathVariable String phone_number, @PathVariable String g_code, HttpServletRequest request, Map modelMap){
		
		logger.info("=====updateGroupName.do=====");
		logger.info("==param== phone_number is " + phone_number);
		logger.info("==param== g_code is " + g_code);
		
		
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		String group_name = (String) request.getParameter("group_name");
		logger.info("==param== group_name is " + group_name);
		HashMap ParaHm = new HashMap();
		ParaHm.put("phone_number", phone_number);
		ParaHm.put("g_code", g_code);
		ParaHm.put("group_name", group_name);
		
		groupService.updateGroupName(ParaHm);
		
		
		ModelAndView model = new ModelAndView();
		model.addObject("update_result", "ok");
		model.setView(new MappingJacksonJsonView(){	});
		
		return model;
		
		
	}
	
	
	/**
	 * <p><b>/g_code/phone_number/withdrawFromGroup.do</b></p>
	 * 
	 * @param g_code	  
	 * @param phone_number 
	 * @param modelMap
	 * @return {delete_result : ok}
	 * 
	 * <p> </p>
	 * 
	 */		
	@RequestMapping(value="/{g_code}/{phone_number}/withdrawFromGroup.do", method=RequestMethod.POST)
	public ModelAndView withdrawFromGroup(@PathVariable String phone_number, @PathVariable String g_code, Map modelMap){
		
		logger.info("=====withdrawFromGroup.do=====");
		logger.info("==param== phone_number is " + phone_number);
		logger.info("==param== g_code is " + g_code);
		
		HashMap ParaHm = new HashMap();
		ParaHm.put("phone_number", phone_number);
		ParaHm.put("g_code", g_code);
		
		groupService.withdrawFromGroup(ParaHm);		
		
		ModelAndView model = new ModelAndView();
		model.addObject("delete_result", "ok");
		model.setView(new MappingJacksonJsonView(){	});
		
		return model;
		
		
	}
	/**
	 * <p><b>/g_code/phone_number/deleteGroup.do</b></p>
	 * 
	 * @param g_code	  
	 * @param phone_number 
	 * @param modelMap
	 * @return {delete_result : ok}
	 * 
	 * <p> </p>
	 * 
	 */	
	@RequestMapping(value="/{g_code}/{phone_number}/deleteGroup.do", method=RequestMethod.POST)
	public ModelAndView deleteGroup(@PathVariable String phone_number, @PathVariable String g_code, Map modelMap){
		
		logger.info("=====deleteGroup.do=====");
		logger.info("==param== phone_number is " + phone_number);
		logger.info("==param== g_code is " + g_code);
		
		HashMap ParaHm = new HashMap();
		ParaHm.put("phone_number", phone_number);
		ParaHm.put("g_code", g_code);
		
		groupService.deleteGroup(ParaHm);		
		
		ModelAndView model = new ModelAndView();
		model.addObject("delete_group_result", "ok");
		model.setView(new MappingJacksonJsonView(){	});
		
		return model;
		
		
	}
	
	
	
	/**
	 * <p>{phone_number}/getGroupLite.do</p>
	 * 
	 * @param phone_number
	 * @param request
	 * @param modelMap
	 * @return JSON set
	 * 
	 * result_set :[
	 *    {"g_code" : "?",
	 * 	   "group_name" : "?",
	 * 	   "name" : "?",
	 *     "leader_u_code" : "?"}, 
	 *     { ... }]    
	 *     
	 */			
	@RequestMapping(value="/{phone_number}/getGroupLite.do", method=RequestMethod.POST)
	public ModelAndView getGroupLite(@PathVariable String phone_number, HttpServletRequest request, Map modelMap){
		logger.info("=====getGroup.do=====");
		logger.info("==param== phone_number is " + phone_number);		
		
		HashMap ParaHm = new HashMap();
		ParaHm.put("phone_number", phone_number);
						
		logger.info(groupService.getGroupLite(ParaHm)+"");
		
		ModelAndView model = new ModelAndView();
		
		model.addObject("result_set", groupService.getGroupLite(ParaHm));			
		model.setView(new MappingJacksonJsonView(){	});
		
		return model;		
	}
	
	
	@RequestMapping(value="/{phone_number}/makeGroup.do", method=RequestMethod.POST)
	public ModelAndView makeGroup(@PathVariable String phone_number, HttpServletRequest request, Map modelMap){
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("=====makeGroup.do=====");
		logger.info("==param== phone_number is " + phone_number);
		
		
		String group_name = request.getParameter("group_name");
		String group_discription = request.getParameter("group_discription");
		String is_private = request.getParameter("is_private");
		logger.info("==param== group_name is " + group_name);
		logger.info("==param== group_discription is " + group_discription);
		logger.info("==param== is_private is " + is_private);
		
		HashMap ParaHm = new HashMap();
		ParaHm.put("phone_number", phone_number);
		ParaHm.put("group_name", group_name);
		ParaHm.put("is_private", is_private);
		
		
		int id = groupService.makeGroup(ParaHm);
		
		ModelAndView model = new ModelAndView();
		
		model.addObject("insert_result", id);			
		model.setView(new MappingJacksonJsonView(){	});
		
		return model;
		
	}
	
	@RequestMapping(value="/{g_code}/{phone_number}/getChatWall.do", method=RequestMethod.POST)
	public ModelAndView getChatWall(@PathVariable String g_code, @PathVariable String phone_number, HttpServletRequest request, String i, String j, Map modelMap){
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("=====getChatWall.do=====");
		logger.info("==param== g_code is " + g_code);
		logger.info("==param== i " + i);
		logger.info("==param== j " + j);
		
		
		
		HashMap ParaHm = new HashMap();
		ParaHm.put("phone_number", phone_number);
		ParaHm.put("g_code", g_code);
		ParaHm.put("i", i);
		ParaHm.put("j", j);
		ModelAndView model = new ModelAndView();
		logger.info("==result_set== result_set are " + groupService.getChatWall(ParaHm));
		model.addObject("result_set", groupService.getChatWall(ParaHm));			
		model.setView(new MappingJacksonJsonView(){	});
		
		return model;
		
	}
	@RequestMapping(value="/{phone_number}/getAllChatWall.do", method=RequestMethod.POST)
	public ModelAndView getAllChatWall(@PathVariable String phone_number, HttpServletRequest request, Map modelMap){
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("=====getAllChatWall=====");
		
		
		String[] gm_code = request.getParameterValues("gm_code");
		ArrayList sum_arr = new ArrayList();
		if(gm_code!=null){
			for(int i = 0; i<gm_code.length; i++){				
				String[] before_str = gm_code[i].split("/");
				HashMap parahm = new HashMap();
				parahm.put("g_code", before_str[0]);
				parahm.put("m_code", before_str[1]);
				ArrayList arr = groupService.getAllChatWall(parahm);
				for(int j = 0; j<arr.size();j++){
					sum_arr.add((HashMap)arr.get(j));
				}
			}
		}
		ModelAndView model = new ModelAndView();
		logger.info("==result_set== result_set are " + sum_arr);
		model.addObject("result_set",  sum_arr);			
		model.setView(new MappingJacksonJsonView(){	});		
		return model;
		
	}
	
	@RequestMapping(value="/{g_code}/{phone_number}/getChatWallLite.do", method=RequestMethod.POST)
	public ModelAndView getChatWallLite(@PathVariable String g_code, @PathVariable String phone_number, HttpServletRequest request, String m_code, Map modelMap){
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("=====getChatWallLite.do=====");
		logger.info("==param== g_code is " + g_code);
		logger.info("==param== m_code is " + m_code);
		
					
		HashMap ParaHm = new HashMap();
		ParaHm.put("phone_number", phone_number);
		ParaHm.put("g_code", g_code);
		ParaHm.put("m_code", m_code);
		
		ModelAndView model = new ModelAndView();
		logger.info("==result_set== result_set are " + groupService.getChatWallLite(ParaHm));
		model.addObject("result_set", groupService.getChatWallLite(ParaHm));			
		model.setView(new MappingJacksonJsonView(){	});
		
		return model;
		
	}
	
	@RequestMapping(value="/{g_code}/{phone_number}/writeChatWall.do", method=RequestMethod.POST)
	public ModelAndView writeChatWall(@PathVariable String g_code, @PathVariable String phone_number, HttpServletRequest request, Map modelMap){
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("=====writeChatWall.do=====");
		logger.info("==param== g_code is " + g_code);
		
		String message = (String) request.getParameter("message");
		String i = (String) request.getParameter("i");
		String j = (String) request.getParameter("j");
		logger.info("==param== message is " + message);
		logger.info("==param== j is " + j);
		//session Check
		
		//else if
					
		HashMap ParaHm = new HashMap();
		ParaHm.put("phone_number", phone_number);
		ParaHm.put("g_code", g_code);
		ParaHm.put("message", message);
		ParaHm.put("i", i);
		ParaHm.put("j", j);
		int t = groupService.writeChatWall(ParaHm);
		
		ParaHm.put("m_code", t+"");
		HashMap Who = groupService.getWhoOfMessage(ParaHm);
		
		ModelAndView model = new ModelAndView();
		
		HashMap hm = new HashMap();
		hm.put("m_id", t);
		hm.put("g_code", g_code);
		hm.put("message", message);
		hm.put("name", (String)Who.get("name"));
		hm.put("group_name", (String)Who.get("group_name"));
		hm.put("type", "message");
		
		ParaHm.put("u_code", (Integer)Who.get("u_code"));
		ArrayList groupRegi = groupService.getGroupMemeberRegistrationId(ParaHm);
		executorService.execute(new pushThread(groupRegi, hm));	
		
		logger.info(hm.toString());
		
		model.addObject("write_result", hm);
		
		model.setView(new MappingJacksonJsonView(){	});
		
		return model;
		
	}
	public class pushThread implements Runnable{
		private ArrayList member;
		private String message;
		private String g_name;
		private HashMap hm;
		public pushThread(){}
		public pushThread(ArrayList member, HashMap hm){
			this.member = member;
			this.hm = hm;
			
		}
		public void run(){
			if(member!=null){
				Iterator it = member.iterator();
				while(it.hasNext()){
					HashMap regis = (HashMap) it.next();
					
					String r_id = (String)regis.get("registration_id");
					if(r_id!=null && !r_id.equalsIgnoreCase("")){
						logger.info((String)regis.get("registration_id")+"");
						try {
							androidPush((String)regis.get("registration_id"), hm);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}
	}
	public void androidPush(String  regId, HashMap hm) throws Exception {
        try {
            StringBuffer postDataBuilder = new StringBuffer();
            postDataBuilder.append("registration_id=" + regId); 
            postDataBuilder.append("&collapse_key=1");
            postDataBuilder.append("&delay_while_idle=1");
            postDataBuilder.append("&data.msg=" + URLEncoder.encode((String)hm.get("message"), "UTF-8")); // 내용
            postDataBuilder.append("&data.apptype=thehouse");
            postDataBuilder.append("&data.type=" + URLEncoder.encode((String)hm.get("type"), "UTF-8")); 
            postDataBuilder.append("&data.group_name=" + URLEncoder.encode((String)hm.get("group_name"), "UTF-8"));
            postDataBuilder.append("&data.name=" + URLEncoder.encode((String)hm.get("name"), "UTF-8"));
            postDataBuilder.append("&data.g_code=" + URLEncoder.encode((String)hm.get("g_code"), "UTF-8"));
             
            byte[] postData = postDataBuilder.toString().getBytes("UTF8");
             
            URL url = new URL(HOST);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
             
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            conn.setRequestProperty("Content-Length",Integer.toString(postData.length));
            conn.setRequestProperty("Authorization", "GoogleLogin auth="+AUTH);
            OutputStream out = conn.getOutputStream();
            out.write(postData);
            out.close();
            conn.getInputStream();            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	
	@RequestMapping(value="/{g_code}/{phone_number}/getGroupMember.do", method=RequestMethod.POST)
	public ModelAndView getGroupMember(@PathVariable String g_code, @PathVariable String phone_number, HttpServletRequest request, Map modelMap){
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("=====getGroupMember.do=====");
		logger.info("==param== g_code is " + g_code);
		
		
		HashMap ParaHm = new HashMap();
		ParaHm.put("phone_number", phone_number);
		ParaHm.put("g_code", g_code);
		
		//int t = groupService.writeChatWall(ParaHm);
		ModelAndView model = new ModelAndView();
		logger.info("==result_set== result_set are " + groupService.getGroupMember(ParaHm));
		model.addObject("result_set", groupService.getGroupMember(ParaHm));			
		model.setView(new MappingJacksonJsonView(){	});
		
		return model;
		
	}
	
	
	/**
	 * 
	 * <p>/{g_code}/{phone_number}/getGroupMemberLite.do</p>
	 * 
	 * @param g_code
	 * @param phone_number
	 * @param request
	 * @param modelMap
	 * @return JSON set
	 * result_set : [
	 *    {"g_code" : "?",
	 *     "u_code" : "?",
	 *     "phone_numeber" : "?",
	 *     "name" : "?",
	 *     "photo_path" : "?"},
	 *     { ... } ]
	 */
	
	@RequestMapping(value="/{g_code}/{phone_number}/getGroupMemberLite.do", method=RequestMethod.POST)
	public ModelAndView getGroupMemberLite(@PathVariable String g_code, @PathVariable String phone_number, HttpServletRequest request, Map modelMap){
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("=====getGroupMemberLite.do=====");
		logger.info("==param== g_code is " + g_code);
		
		
		
					
		HashMap ParaHm = new HashMap();
		ParaHm.put("phone_number", phone_number);
		ParaHm.put("g_code", g_code);
		
		//int t = groupService.writeChatWall(ParaHm);
		ModelAndView model = new ModelAndView();
		logger.info("==result_set== result_set are " + groupService.getGroupMemberLite(ParaHm));
		model.addObject("result_set", groupService.getGroupMemberLite(ParaHm));			
		model.setView(new MappingJacksonJsonView(){	});
		
		return model;
		
	}
	@RequestMapping(value="/{g_code}/{phone_number}/inviteGroup.do", method=RequestMethod.POST)
	public ModelAndView inviteGroup(@PathVariable String g_code, @PathVariable String phone_number, HttpServletRequest request, Map modelMap){
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("=====inviteGroup.do=====");
		logger.info("==param== g_code is " + g_code);
		Enumeration enum1 = request.getParameterNames();
		
		
		
		String[] phones = request.getParameterValues("phone_numbers");
		ArrayList alreadyMember = new ArrayList();
		if(phones!=null){
			for(int i = 0; i<phones.length; i++){
				logger.info("==param== phone_numbers is " + phones[i]);
				HashMap ParaHm = new HashMap();
				ParaHm.put("phone_number", phone_number);
				ParaHm.put("g_code", g_code);
				ParaHm.put("invite_phone_number", phones[i]);
				
				if(groupService.isHeGroupMember(ParaHm)) alreadyMember.add(phones[i]);
				else	{
					if(groupService.isInvitationExist(ParaHm)){
						alreadyMember.add(phones[i]);
					}else{
						int i_id = groupService.inviteGroup(ParaHm);
						HashMap ppm = new HashMap();
						ppm.put("phone_number", phones[i]);
						ArrayList arr = groupService.getMemeberRegistrationId(ppm);
						logger.info("member id = "+arr);
						HashMap pushMap = new HashMap();
						pushMap.put("message", "");
						pushMap.put("type", "invite");
						pushMap.put("g_code", g_code);
						pushMap.put("group_name", "");
						pushMap.put("name", "");					
						
						executorService.execute(new pushThread(arr, pushMap));	
					}
				}				
			}
		}
		
		
		
		
		//int t = groupService.writeChatWall(ParaHm);
		ModelAndView model = new ModelAndView();
		logger.info("==invite_result== invite_result are " + alreadyMember);
		model.addObject("invite_result", alreadyMember);			
		model.setView(new MappingJacksonJsonView(){	});
		
		return model;
		
	}
	
	
	
	
}
