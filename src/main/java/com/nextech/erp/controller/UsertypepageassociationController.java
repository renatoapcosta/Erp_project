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
import com.nextech.erp.model.Usertypepageassociation;
import com.nextech.erp.service.UsertypepageassociationService;
import com.nextech.erp.status.UserStatus;

@Controller
@RequestMapping("/usertypepageassociation")
public class UsertypepageassociationController {

	@Autowired
	UsertypepageassociationService usertypepageassociationService;

	@RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public @ResponseBody UserStatus addPageAss(
			@Valid @RequestBody Usertypepageassociation usertypepageassociation,
			BindingResult bindingResult) {
		try {
			if (bindingResult.hasErrors()) {
				return new UserStatus(0, bindingResult.getFieldError()
						.getDefaultMessage());
			}
			usertypepageassociationService.addEntity(usertypepageassociation);
			return new UserStatus(1,
					"Usertypepageassociation added Successfully !");
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
	public @ResponseBody Usertypepageassociation getPageAss(
			@PathVariable("id") long id) {
		Usertypepageassociation usertypepageassociation = null;
		try {
			usertypepageassociation = usertypepageassociationService
					.getEntityById(Usertypepageassociation.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return usertypepageassociation;
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT, headers = "Accept=application/json")
	public @ResponseBody UserStatus updatePageAss(
			@RequestBody Usertypepageassociation usertypepageassociation) {
		try {
			usertypepageassociationService
					.updateEntity(usertypepageassociation);
			return new UserStatus(1,
					"Usertypepageassociation update Successfully !");
		} catch (Exception e) {
			e.printStackTrace();
			return new UserStatus(0, e.toString());
		}
	}

	@CrossOrigin(origins = "http://localhost:8080")
	@RequestMapping(value = "/list", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody List<Usertypepageassociation> getPageAss() {

		List<Usertypepageassociation> UsertypepageassociationList = null;
		try {
			UsertypepageassociationList = usertypepageassociationService
					.getEntityList(Usertypepageassociation.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return UsertypepageassociationList;
	}

	/* Delete an object from DB in Spring Restful Services */
	@RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public @ResponseBody UserStatus deletePageAss(@PathVariable("id") long id) {

		try {
			Usertypepageassociation usertypepageassociation = usertypepageassociationService
					.getEntityById(Usertypepageassociation.class, id);
			usertypepageassociation.setIsactive(false);
			usertypepageassociationService
					.updateEntity(usertypepageassociation);
			return new UserStatus(1,
					"Usertypepageassociation deleted Successfully !");
		} catch (Exception e) {
			e.printStackTrace();
			return new UserStatus(0, e.toString());
		}

	}
}
