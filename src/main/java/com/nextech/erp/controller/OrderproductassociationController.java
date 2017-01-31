package com.nextech.erp.controller;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.validation.Valid;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nextech.erp.model.Orderproductassociation;
import com.nextech.erp.service.OrderproductassociationService;
import com.nextech.erp.status.UserStatus;

@Controller
@RequestMapping("/orderproductassociation")
public class OrderproductassociationController {

	@Autowired
	OrderproductassociationService orderproductassociationService;

	@RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public @ResponseBody UserStatus addOrderproductassociation(
			@Valid @RequestBody Orderproductassociation orderproductassociation, BindingResult bindingResult) {
		try {
			if (bindingResult.hasErrors()) {
				return new UserStatus(0, bindingResult.getFieldError()
						.getDefaultMessage());
			}
			orderproductassociationService.addOrderproductassociation(orderproductassociation);
			return new UserStatus(1, "Orderproductassociation added Successfully !");
		} catch (ConstraintViolationException cve) {
			System.out.println("Inside ConstraintViolationException");
			cve.printStackTrace();
			return new UserStatus(0, cve.getCause().getMessage());
		} catch (PersistenceException pe) {
			System.out.println("Inside PersistenceException");
			pe.printStackTrace();
			return new UserStatus(0, pe.getCause().getMessage());
		} catch (Exception e) {
			System.out.println("Inside Exception");
			e.printStackTrace();
			return new UserStatus(0, e.getCause().getMessage());
		}
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody Orderproductassociation getOrderproductassociation(@PathVariable("id") long id) {
		Orderproductassociation Orderproductassociation = null;
		try {
			Orderproductassociation = orderproductassociationService.getOrderproductassociationById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Orderproductassociation;
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT, headers = "Accept=application/json")
	public @ResponseBody UserStatus updateOrderproductassociation(@RequestBody Orderproductassociation orderproductassociation) {
		try {
			orderproductassociationService.updateOrderproductassociation(orderproductassociation);
			return new UserStatus(1, "Orderproductassociation update Successfully !");
		} catch (Exception e) {
			e.printStackTrace();
			return new UserStatus(0, e.toString());
		}
	}

	@CrossOrigin(origins = "http://localhost:8080")
	@RequestMapping(value = "/list", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody List<Orderproductassociation> getOrderproductassociation() {

		List<Orderproductassociation> orderproductassociationList = null;
		try {
			orderproductassociationList = orderproductassociationService.getOrderproductassociationList();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return orderproductassociationList;
	}

	@RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public @ResponseBody UserStatus deleteOrderproductassociation(@PathVariable("id") long id) {

		try {
			Orderproductassociation orderproductassociation = orderproductassociationService.getOrderproductassociationById(id);
			orderproductassociation.setIsactive(false);
			orderproductassociationService.updateOrderproductassociation(orderproductassociation);
			return new UserStatus(1, "Orderproductassociation deleted Successfully !");
		} catch (Exception e) {
			return new UserStatus(0, e.toString());
		}

	}
}
