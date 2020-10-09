package com.invoice.controllers.security;

import java.security.Principal;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.invoice.bean.User;
import com.invoice.service.user.UserService;

@Component
@Controller
public class LoginController implements ApplicationListener<ApplicationEvent> {
	
	@Autowired
	UserService userService;

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String executeSecurity(ModelMap model, Principal principal,HttpSession session,User user) {
		String userName = principal.getName();
		user.setUsername(userName);
		List<User> userList = userService.getUser(user);
		for (User user1 : userList) {
			session.setAttribute("userId", user1.getUserId());
			session.setAttribute("userName", user1.getUsername());
			session.setAttribute("designation", user1.getDesignation().getDesignationName());
			session.setAttribute("displayname", user1.getDisplayname());
			session.setAttribute("mobile", user1.getMobile());
			session.setAttribute("email", user1.getEmail());
			session.setAttribute("createdDate", user1.getCreatedDate());
		}
		session.setAttribute("themeName", "cerulean");
		return "redirect:/invoiceGst/home";
	}
	
	@RequestMapping(value = "/checkLock", method = RequestMethod.GET)
	public ModelAndView checkLoginPage(ModelMap model) {
		model.addAttribute("lockError", "Your account is Locked, contact your CEO to Unlock");
		return new ModelAndView("index", "model", model);
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(ModelMap model) {
		return "index";
	}

	@RequestMapping(value = "/fail2login", method = RequestMethod.GET)
	public String loginerror(ModelMap model) {
		model.addAttribute("error", "true");
		return "index";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(ModelMap model) {
		System.out.println("Logging Out");
		return "index";
	}

	@RequestMapping(value = "/checkSession", method = RequestMethod.GET)
	public @ResponseBody String checkSession(ModelMap model, Principal principal) {
		if (principal.getName() != null) {
			return "pass";
		} else {
			return "fail";
		}
	}

	@RequestMapping(value = "/sessionExpired", method = RequestMethod.GET)
	public String sessionExpired(ModelMap model, HttpSession session) {
		session.invalidate();
		return "index";
	}

	@Autowired
	ServletContext context;
	
	@Override
	public void onApplicationEvent(ApplicationEvent appEvent) {
		if (appEvent instanceof AuthenticationFailureBadCredentialsEvent) {
			AuthenticationFailureBadCredentialsEvent event = (AuthenticationFailureBadCredentialsEvent) appEvent;
			String userName = (String) event.getAuthentication().getPrincipal();
			//System.out.println("onApplicationEvent UserName: "+userName);
			context.setAttribute("lockstatus","open");
		}
	}
}
