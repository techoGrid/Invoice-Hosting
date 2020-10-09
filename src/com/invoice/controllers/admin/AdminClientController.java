package com.invoice.controllers.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.invoice.bean.Client;
import com.invoice.bean.ResponseCA;
import com.invoice.service.client.ClientService;
import com.invoice.utils.AppUtil;
import com.invoice.utils.EntityNameValidator;

@Controller
@RequestMapping("/admin")
public class AdminClientController {
	@Autowired
	ClientService clientService;

	@Autowired
	EntityNameValidator validator;

	@RequestMapping(value = "/createClient")
	public String createClientPage(ModelMap model) {
		return "client/ca_create_client";
	}

	@RequestMapping(value = "/clientEmailCheck")
	public @ResponseBody Object clientEmailCheck(@RequestParam("email1") String email1) {
		ResponseCA response = new ResponseCA();
		if (validator.isValid("clientEmail1", email1, "Client", "active")) {
			response.setSuccess(true);
		} else {
			response.setSuccess(false);
		}
		return response;
	}

	@RequestMapping(value = "/createClientRegistration")
	public @ResponseBody Object createClientRegistration(Client client, HttpServletRequest request) {
		ResponseCA response = new ResponseCA();

		String userName = null;
		try {
			userName = (String) request.getSession().getAttribute("userName");
		} catch (Exception e) {}

		client.setActive(1);
		client.setCreatedDate(AppUtil.currentDateWithTime());
		client.setCreatedBy(userName);

		if (clientService.clientSubmitDetails(client)) {
			response.setMessage("Client Created Successfully.");
		} else {
			response.setMessage("Client Created UnSuccessfully...! Try again.");
		}
		return response;
	}
	
	@RequestMapping(value = "/viewclient")
	public ModelAndView viewclient(ModelMap model, Client client) {
		List<Client> clientlist = clientService.listClients(client);
		model.addAttribute("clientList", clientlist);
		return new ModelAndView("client/ca_view_client", model);
	}
	
	@RequestMapping(value = "/updateClient")
	public @ResponseBody ModelAndView updateClientPage(Client client,ModelMap model) {
		model.addAttribute("client", clientService.getClientsforEdit(client));
		return new ModelAndView("client/ca_update_client",model);
	}
	
	@RequestMapping(value = "/updateClientRegistration")
	public @ResponseBody Object updateClientRegistration(Client client, HttpServletRequest request) {
		ResponseCA response = new ResponseCA();
		
		System.out.println("Updating Client: "+client.getClientId());
		Client exClient = clientService.getClientsforEdit(client);
		
		String userName = null;
		try {
			userName = (String) request.getSession().getAttribute("userName");
		} catch (Exception e) {}
		
		exClient.setAddress(client.getAddress());
		exClient.setClientCity(client.getClientCity());
		exClient.setClientState(client.getClientState());
		exClient.setClientPinCode(client.getClientPinCode());
		exClient.setClientMobile(client.getClientMobile());
		exClient.setOfficePhone(client.getOfficePhone());
		exClient.setClientEmail1(client.getClientEmail1());
		exClient.setPanNo(client.getPanNo());
		exClient.setGstin(client.getGstin());
		exClient.setStNo(client.getStNo());
		exClient.setVatNo(client.getVatNo());
		exClient.setCstNo(client.getCstNo());
		exClient.setTanNo(client.getTanNo());

		exClient.setUpdatedDate(AppUtil.currentDateWithTime());
		exClient.setUpdatedBy(userName);

		if (clientService.clientSubmitDetails(exClient)) {
			response.setMessage("Updated Successfully.");
		} else {
			response.setMessage("Updated UnSuccessfully...! Try again.");
		}
		return response;
	}
	

}
