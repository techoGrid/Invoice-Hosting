package com.invoice.controllers.changePassword;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.invoice.bean.User;
import com.invoice.service.changeUserPassword.ChangeUserPasswordService;
import com.invoice.utils.MailUtil;
import com.invoice.utils.PasswordUtil;

@Controller
@RequestMapping("/ChangePassword")
public class ChangePasswordController {

	@Autowired
	ChangeUserPasswordService changePasswordService;
	
	@Autowired
	MailUtil mailutil;
	
	@RequestMapping("/changepswd")
	public String changepassword() {
		return "ca_change_password";
	} 
	
	@RequestMapping("/saveChangePassword")
	public @ResponseBody Object saveChangePassword(HttpSession session,
			@RequestParam("currentPassword") String currentPassword, 
			@RequestParam("newPassword") String newPassword,
			@RequestParam("confirmPassword") String confirmPassword, User user) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(newPassword.equals(confirmPassword)) {
			
			int userId = (Integer) session.getAttribute("userId");
			PasswordUtil util = new PasswordUtil();
			String currentMD5password = util.generateMD5(currentPassword);
			if(changePasswordService.checkPassword(userId, currentMD5password)) {
				
				String newMD5password = util.generateMD5(newPassword);
				String userEmail = (String) session.getAttribute("email");
				String userName = (String) session.getAttribute("username");
				try {
					mailutil.changePasswordEmail(userEmail, newPassword, user,userName);
					if(changePasswordService.changePassword(userId, newMD5password)) {
						resultMap.put("status", true);
						resultMap.put("msg", "New Password Updated Successfully");
					} else {
						resultMap.put("status", false);
						resultMap.put("msg", "Change Password Not Successfully");
					}
				} catch (Exception e) {
					e.printStackTrace();
					resultMap.put("status", false);
					resultMap.put("msg", "Change Password UnSuccessfully. Try Again..!");
				}
			} else {
				resultMap.put("status", false);
				resultMap.put("msg", "Wrong Current Password");
			}
		} else {
			resultMap.put("status", false);
			resultMap.put("msg", "Confirm Password did not Match");
		}
		return resultMap;
	}
	
	@RequestMapping("/forgotPassword")
	public @ResponseBody Object forgotPassword
	(@RequestParam ("forgotPasswordEmail") String forgotPasswordEmail, User user) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(changePasswordService.checkEmail(forgotPasswordEmail)) {
			PasswordUtil util = new PasswordUtil();
			String newPassword = util.generatePassword();
			String newMD5password = util.generateMD5(newPassword);
			try {
				mailutil.sendForgotPasswordEmail(forgotPasswordEmail, newPassword,user);
				if(changePasswordService.changeForgotPassword(forgotPasswordEmail, newMD5password)) {
					resultMap.put("status", true);
					resultMap.put("msg", "New Password sent to your Email");
				} else {
					resultMap.put("status", false);
					resultMap.put("msg", "Reset Password Not Successfully");
				}
			 } catch (Exception e) {
				e.printStackTrace();
				resultMap.put("status", false);
				resultMap.put("msg", "Reset Password UnSuccessfully. Try Again..!");
			}
		} else {
			resultMap.put("status", false);
			resultMap.put("msg", "Email id doesn't exist");
		}
		return resultMap;		
	}
	
}
