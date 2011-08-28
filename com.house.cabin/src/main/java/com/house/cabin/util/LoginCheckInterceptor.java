package com.house.cabin.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginCheckInterceptor extends HandlerInterceptorAdapter {
	 
	 @Override
	 public boolean preHandle(HttpServletRequest request,
	   HttpServletResponse response, Object handler) throws Exception {
	  // TODO Auto-generated method stub
	  System.out.println("handler!!!");
	  // session검사
	  HttpSession session = request.getSession(false);
	  
	   if (session == null) {
	   // 처리를 끝냄 - 컨트롤로 요청이 가지 않음.
	   response.sendRedirect("/");
	   return false;
	  }
	  
	  String userId = (String)session.getAttribute("u_id");
	  if (userId == null) { 
	   response.sendRedirect("/");  
	   return false;
	  }
	  return true;
	 }
}
	
