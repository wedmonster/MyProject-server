package com.house.cabin;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String home(HttpServletRequest request) {
		logger.info("Welcome home!");
		HttpSession session = request.getSession();
		logger.info("==session=="+session.getAttribute("u_id"));
		return "home";
	}
	
	/*@RequestMapping(value="/polling", method=RequestMethod.GET)
	public void polling(HttpServletRequest request, HttpServletResponse response) {
		logger.info("polling test!");
		//HttpSession session = request.getSession();
		
		//logger.info("==session=="+session.getAttribute("u_id"));
		testLongPolling tlp = new testLongPolling(response);
		tlp.start();
		//try {
		//	tlp.join();
		//} catch (InterruptedException e) {
		//	// TODO Auto-generated catch block
		//	e.printStackTrace();
		//}
		
		
	}
	
	class testLongPolling extends Thread{
		private int count = 0;
		HttpServletResponse response;
		public testLongPolling(HttpServletResponse response){
			this.response = response;
		}
		public void run(){
			while(true){
				count++;
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(count == 100) break;
				
			}
			logger.info(this.getName());		}
	}*/
	
}

