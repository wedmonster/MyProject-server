package com.house.cabin.user;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import org.springframework.web.servlet.view.AbstractView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;


@Controller
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired
	private UserService userService;
	
	
	@RequestMapping(value="/login.do", method=RequestMethod.POST)
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response, String phone_number, String registration_id, Map modelMap) {
		logger.info("=====lodin.do=====");
		logger.info("==param== phone_number is " + phone_number);
		logger.info("==param== registration_id is "+registration_id);		
		logger.info(request.getHeader("User-Agent"));		
		
		//logger.info("==request.getHeaderNames==" + request.getHeaderNames());
		// java.util.Enumeration enumeration = request.getHeaderNames();
	  //    while (enumeration.hasMoreElements()) {
	   // 	  String str = (String)enumeration.nextElement();
	   //    logger.info(""+str+"="+request.getHeader(str));
	   //   }
		boolean isAuthenticated = false;
		
		if(phone_number == null) {
			//Check parameter. : phone_number
		}
		
		HashMap paraHm = new HashMap();
		paraHm.put("phone_number", phone_number);
		
		//branch 		
		//if(request.getHeader("User-Agent").equalsIgnoreCase("app") == true){
			//This is for Application.
			isAuthenticated = userService.isHeMember(paraHm);
			logger.info("==result== isAuthenticated : "+isAuthenticated);			
		//}else{
			//This is for Web Browser.
			if(registration_id == null){
				//Check parameter. : password
				
			}
			paraHm.put("registration_id", registration_id);
			//isAuthenticated = userService.isHeMemberWithPassword(paraHm);
		//}		
		
		if(isAuthenticated == true){
			//Set Session
			//HttpSession session = request.getSession(true);
			//session.setAttribute("phone_number", phone_number);
			//logger.info("==session== id is " + session.getId());
			//logger.info("==result== session _ phone_number : "+session.getAttribute("phone_number"));
			if(!registration_id.equalsIgnoreCase(""))
				userService.updateRegistrationId(paraHm);
			//response.addCookie(cookie);
			
		}
		
		//FROM : Make JSON DATA 
		
		//JSONObject obj = new JSONObject();
		//obj.put("isAuthenticated", isAuthenticated);
		
		//TO : Make JSON DATA
		
		//logger.info("==result== JSON obj : "+obj);
				
		ModelAndView model = new ModelAndView();
		
		model.addObject("isAuthenticated", isAuthenticated);			
		model.setView(new MappingJacksonJsonView(){	});
		
		return model;
	}
	
	/**
	 * @author kotaro
	 * 
	 * <p>name : joinGroup </p>
	 * 
	 * <p>description : return JSON Sets which are results of join the group. </p> 
	 * 
	 * @param phone_number
	 * @param invitation_id
	 * @param modelMap
	 * 
	 * @return JSON Set
	 * 
	 * write_result : {
	 * 	{g_code : ?, 
	 * 	group_join_date : ?}
	 * } 
	 * 
	 */
	@RequestMapping(value="/{phone_number}/joinGroupMember.do", method=RequestMethod.POST)
	public ModelAndView joinGroupMember(@PathVariable String phone_number, String invitation_id, Map modelMap){
		
		logger.info("=====joinGroupMember.do=====");
		logger.info("invitation_id : "+invitation_id);
					
		HashMap<String, String> ParaHm = new HashMap<String, String>();
		ParaHm.put("phone_number", phone_number);
		ParaHm.put("invitation_id", invitation_id);
		
		ModelAndView model = new ModelAndView();
		int member_id = userService.joinGroupMember(ParaHm);
		ParaHm.put("member_id", member_id+"");
		
		HashMap resultMap = userService.getJoinDate(ParaHm);
		
		logger.info("==write_result== resultMap are " + resultMap);
		
		userService.deleteInvitation(ParaHm);
		
		model.addObject("write_result", resultMap);			
		model.setView(new MappingJacksonJsonView(){	});
		
		return model;
		
	}
	
	
	
	/**
	 * @author kotaro
	 * @since 2011.07.14
	 * <p>name : updateUserName </p>
	 * 
	 * <p>description : update name of user </p> 
	 * 
	 * @param phone_number
	 * @param name	  
	 * @param modelMap
	 * 
	 * @return null   
	 * 
	 */	
	@RequestMapping(value="/{phone_number}/updateUserName.do", method=RequestMethod.POST)
	public ModelAndView updateUserName(@PathVariable String phone_number, HttpServletRequest request, Map modelMap) {
		logger.info("=====updateUserName.do=====");
		logger.info("==param== phone_number is " + phone_number);
					
		String name = "";
		try {
			request.setCharacterEncoding("UTF-8");
			name = request.getParameter("name");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("==param== name is " + name);	
		
		HashMap paraHm = new HashMap();
		paraHm.put("phone_number", phone_number);
		paraHm.put("name", name);
		
		userService.updateUserName(paraHm);		
		
		ModelAndView model = new ModelAndView();					
		model.setView(new MappingJacksonJsonView(){	});		
		return model;
		
	}
	/**
	 * @author kotaro
	 * @since 2011.07.14
	 * <p>name : getUserInfo </p>
	 * 
	 * <p>description : return information about user </p> 
	 * 
	 * @param phone_number	  
	 * @param modelMap
	 * 
	 * @return JSON set
	 * 
	 *    result_set : {
	 *    	{u_code : ?},
	 *      {phone_number : ?}, 
	 *      {name : ?}, 
	 *      {join_date : ?}, 
	 *      {photo_path ; ?}	     
	 *    }
	 * 
	 */	
	@RequestMapping(value="/{phone_number}/getUserInfo.do", method=RequestMethod.POST)
	public ModelAndView getUserInfo(@PathVariable String phone_number, HttpServletRequest request, Map modelMap) {
		logger.info("=====getUserInfo.do=====");
		logger.info("==param== phone_number is " + phone_number);
		
		
		HashMap paraHm = new HashMap();
		paraHm.put("phone_number", phone_number);		
		
		logger.info("reuslt set is " + userService.getUserInfo(paraHm));		
		
		ModelAndView model = new ModelAndView();		
		model.addObject("result_set", userService.getUserInfo(paraHm));
		model.setView(new MappingJacksonJsonView(){	});		
		return model;
		
	}
	
	
	@RequestMapping(value="/checkMember.do", method=RequestMethod.POST)
	public ModelAndView checkMember(HttpServletRequest request, HttpServletResponse response, String phone_number, Map modelMap) {
		logger.info("=====checkMember.do=====");
		logger.info("==param== phone_number is " + phone_number);
		
		logger.info(request.getHeader("User-Agent"));		
		
		boolean isAuthenticated = false;
		
		if(phone_number == null) {
			//Check parameter. : phone_number
		}
		
		HashMap paraHm = new HashMap();
		paraHm.put("phone_number", phone_number);				
		isAuthenticated = userService.isHeMember(paraHm);
		logger.info("==result== isAuthenticated : "+isAuthenticated);			
								
		ModelAndView model = new ModelAndView();
		
		model.addObject("isAuthenticated", isAuthenticated);			
		model.setView(new MappingJacksonJsonView(){	});
		
		return model;
	}
	
	@RequestMapping(value="/{phone_number}/updateReigstrationId.do", method=RequestMethod.POST)
	public ModelAndView updateReigstrationId(@PathVariable String phone_number, HttpServletRequest request, String registration_id, Map modelMap) {
		logger.info("===========updateReigstrationId===========");
		logger.info("phone_number : "+phone_number);
		logger.info("registration_id : "+registration_id);
		HashMap paraHm = new HashMap();
		paraHm.put("phone_number", phone_number);
		paraHm.put("registration_id", registration_id);
		
		userService.updateRegistrationId(paraHm);
		ModelAndView model = new ModelAndView();
		return model;
		
	}
	
	@RequestMapping(value="/join.do", method=RequestMethod.POST)
	public ModelAndView checkId(HttpServletRequest request, Map modelMap) {
		
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String phone_number = (String) request.getParameter("phone_number"); 
		String name = (String) request.getParameter("name");		
		
		logger.info("=====join.do====="+request.getCharacterEncoding());
		logger.info("=====join.do=====");
		logger.info("==param== phone_number is " + phone_number);
		logger.info("==param== name is "+name);		
		logger.info(request.getHeader("User-Agent"));
		
		
		if(phone_number == null){
			
		}
		
		if(name == null){
						
		}	
		
		UserBean userbean = new UserBean(phone_number, name);
		
		boolean joinResult = userService.join(userbean);

		//FROM : Make JSON DATA 
		
		JSONObject obj = new JSONObject();
		obj.put("checkResult", joinResult);
		
		//TO : Make JSON DATA
		
		logger.info("==result== JSON obj : "+obj);
				
		ModelAndView model = new ModelAndView();
		
		model.addObject("joinResult", joinResult);			
		model.setView(new MappingJacksonJsonView(){	});
		
		return model;	
		
		
	}
	
	
	/**
	 * <p>/{phone_number}/getInvitationList.do</p> 
	 * 
	 * @param phone_number
	 * @param request
	 * @param modelMap
	 * @return JSON set
	 * 
	 * result_set : [
	 *    {"invitation_id" : "?", 
	 *     "group_name" : "?", 
	 *     "name" : "?",
	 *     "invite_date" : "?",
	 *     "state" : "?"}, 
	 *     { ... } ]
	 *   
	 */
	@RequestMapping(value="/{phone_number}/getInvitationList.do", method=RequestMethod.POST)
	public ModelAndView getInvitationList(@PathVariable String phone_number, HttpServletRequest request, Map modelMap){
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("=====getInvitationList.do=====");
							
		HashMap ParaHm = new HashMap();
		ParaHm.put("phone_number", phone_number);
		
		ModelAndView model = new ModelAndView();
		logger.info("==result_set== result_set are " + userService.getInvitationList(ParaHm));
		model.addObject("result_set", userService.getInvitationList(ParaHm));			
		model.setView(new MappingJacksonJsonView(){	});
		
		return model;
		
	}
	
	
	@RequestMapping(value="/test.do", method=RequestMethod.GET)
	public String home(HttpServletRequest request) {
		logger.info("Welcome test!");
		HttpSession session = request.getSession();
		logger.info("==session=="+session.getAttribute("u_id"));
		return "home";
	}
	
	
	
	//////////////////////////////////////////////////////
	//Start Line of DEPRECATED
	//////////////////////////////////////////////////////
	/**
	 * @author kotaro
	 * 
	 * <p>name : joinGroup </p>
	 * 
	 * @deprecated joinGroup
	 * 
	 */
	@RequestMapping(value="/{phone_number}/joinGroup.do", method=RequestMethod.POST)
	public ModelAndView joinGroup(@PathVariable String phone_number, String invitation_id, Map modelMap){
		
		logger.info("=====joinGroup.do=====");
		logger.info("invitation_id : "+invitation_id);
					
		HashMap ParaHm = new HashMap();
		ParaHm.put("phone_number", phone_number);
		ParaHm.put("invitation_id", invitation_id);
		
		ModelAndView model = new ModelAndView();
		int gm_code = userService.joinGroupMember(ParaHm);
		ParaHm.put("member_id", gm_code);
		HashMap resultMap = userService.getMaxCode(ParaHm);
		
		logger.info("==write_result== resultMap are " + resultMap);
		
		userService.deleteInvitation(ParaHm);
		
		model.addObject("write_result", resultMap);			
		model.setView(new MappingJacksonJsonView(){	});
		
		return model;
		
	}
	
}
