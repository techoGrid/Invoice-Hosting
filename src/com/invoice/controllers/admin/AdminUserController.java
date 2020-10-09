package com.invoice.controllers.admin;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.invoice.bean.Designation;
import com.invoice.bean.ResponseCA;
import com.invoice.bean.User;
import com.invoice.service.user.UserService;
import com.invoice.utils.AppUtil;
import com.invoice.utils.EntityNameValidator;
import com.invoice.utils.MailUtil;
import com.invoice.utils.PasswordUtil;

@Controller
@RequestMapping("/admin")
public class AdminUserController {

	@Autowired
	UserService userService;

	@Autowired
	MailUtil mailUtil;

	@Autowired
	EntityNameValidator validator;

	@RequestMapping(value = "/createUser")
	public ModelAndView createUserPage(Designation designation, ModelMap model) {
		List<Designation> desigList = userService.getDesignationList(designation);
		model.addAttribute("desigList", desigList);
		return new ModelAndView("user/ca_create_user", model);
	}

	@RequestMapping(value = "/saveUser")
	public @ResponseBody Object saveUser(ModelMap model, @ModelAttribute @Valid User user,
			BindingResult bindingResult) {
		ResponseCA response = new ResponseCA();
		if (!bindingResult.hasErrors()) {
			if (validator.isValidUsername("username", user.getUsername(), "User")) {
				if (validator.isValid("email", user.getEmail(), "User", "active")) {
					user.setActive(1);
					user.setLockStatus("0");
					user.setNoOfFailureAttempts(0);
					user.setFailureAttemptsDate(null);
					PasswordUtil util = new PasswordUtil();
					String genPassword = util.generatePassword();
					String password = util.generateMD5(genPassword);
					user.setPassword(password);
					user.setCreatedDate(AppUtil.currentDateWithTime());
					if (userService.saveUser(user)) {
						user.setPassword(genPassword);
						mailUtil.sendUserEmail(user);
						response.setSuccess(true);
						response.setMessage("User Creation is Successful.");
					} else {
						response.setSuccess(false);
						response.setMessage("User Creation is not Successful.");
					}
				} else {
					response.setSuccess(false);
					response.setMessage("This Email is already exists. Please change the email");
				}
			} else {
				response.setSuccess(false);
				response.setMessage("This User name is already exists. Please change the user name");
			}
		} else {
			response.setSuccess(false);
			response.setMessage("Invalid User details.");
		}
		return response;
	}

	@RequestMapping(value = "/viewUser")
	public ModelAndView listUsers(ModelMap model, User user) {
		List<User> userlist = userService.listUsers(user);
		model.addAttribute("userlist", userlist);
		return new ModelAndView("user/ca_view_user", model);
	}

	@RequestMapping(value = "/updateUser")
	public @ResponseBody ModelAndView updateUserPage(User user, Designation designation, ModelMap model) {
		model.addAttribute("user", userService.getUsersforEdit(user));
		List<Designation> desigList = userService.getDesignationList(designation);
		model.addAttribute("desigList", desigList);
		return new ModelAndView("user/ca_update_user", model);
	}

	@RequestMapping(value = "/checkExistUserEmail")
	public @ResponseBody Object checkExistClientService(@RequestParam("userId") int userId,
			@RequestParam("email") String email) {

		ResponseCA response = new ResponseCA();

		// For checking the exist client add service
		if (validator.checkExistUserEmail(userId, email)) {
			response.setSuccess(true);
		} else {
			response.setSuccess(false);
			response.setMessage("This Email is already exists, Please change the email address");
		}
		return response;
	}

	@RequestMapping(value = "/userUpdateToTable")
	public @ResponseBody Object userUpdateTable(ModelMap model, @ModelAttribute @Valid User user,
			BindingResult bindingResult) {
		User exUser = userService.getUsersforEdit(user);
		exUser.setUsername(user.getUsername());
		exUser.setDesignation(user.getDesignation());
		exUser.setDisplayname(user.getDisplayname());
		exUser.setMobile(user.getMobile());
		exUser.setEmail(user.getEmail());

		ResponseCA response = new ResponseCA();
		if (!bindingResult.hasErrors()) {
			user.setActive(1);
			exUser.setUpdatedDate(AppUtil.currentDateWithTime());
			if (userService.saveUser(exUser)) {
				response.setSuccess(true);
				response.setMessage("User is Updated Successfully.");
			} else {
				response.setSuccess(false);
				response.setMessage("User Update is not Successful.");
			}
		} else {
			response.setSuccess(false);
			response.setMessage("Invalid User details.");
		}
		return response;
	}

	@RequestMapping(value = "/deleteUser")
	public @ResponseBody String deleteUser(User user) {
		if (userService.deleteUser(user)) {
			return "success";
		} else {
			return "fail";
		}
	}

}
