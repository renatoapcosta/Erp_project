package com.nextech.erp.controller;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.validation.Valid;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.nextech.erp.model.Qualitycheckrawmaterial;
import com.nextech.erp.model.Rawmaterial;
import com.nextech.erp.model.Rawmaterialinventoryhistory;
import com.nextech.erp.model.Rawmaterialorderhistory;
import com.nextech.erp.model.Rawmaterialorderinvoice;
import com.nextech.erp.model.Rmorderinvoiceintakquantity;
import com.nextech.erp.service.QualitycheckrawmaterialService;
import com.nextech.erp.service.RawmaterialService;
import com.nextech.erp.service.RawmaterialinventoryhistoryService;
import com.nextech.erp.service.RawmaterialorderhistoryService;
import com.nextech.erp.service.RawmaterialorderinvoiceService;
import com.nextech.erp.service.RmorderinvoiceintakquantityService;
import com.nextech.erp.status.UserStatus;

@Controller
@RequestMapping("/qualitycheckrawmaterial")
public class QualitycheckrawmaterialController {
	@Autowired
	QualitycheckrawmaterialService qualitycheckrawmaterialService;

	@Autowired
	RmorderinvoiceintakquantityService rmorderinvoiceintakquantityService;

	@Autowired
	RawmaterialService rawmaterialService;

	@Autowired
	RawmaterialorderinvoiceService rawmaterialorderinvoiceService;
	
	@Autowired
	RawmaterialorderhistoryService rawmaterialorderhistoryService;
	
	@Autowired
	RawmaterialinventoryhistoryService rawmaterialinventoryhistoryService;

	@RequestMapping(value = "/qualitycheck", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public @ResponseBody UserStatus addRawmaterialorderinvoice(
			@Valid @RequestBody Rawmaterialorderinvoice rawmaterialorderinvoice,
			BindingResult bindingResult) {
		try {
			if (bindingResult.hasErrors()) {
				return new UserStatus(0, bindingResult.getFieldError()
						.getDefaultMessage());
			}
			List<Qualitycheckrawmaterial> qualitycheckrawmaterials = rawmaterialorderinvoice.getQualitycheckrawmaterials();
			if (qualitycheckrawmaterials != null&& !qualitycheckrawmaterials.isEmpty()) {
				for (Qualitycheckrawmaterial qualitycheckrawmaterial : qualitycheckrawmaterials) {
					rawmaterialorderinvoice= rawmaterialorderinvoiceService.getEntityById(Rawmaterialorderinvoice.class,qualitycheckrawmaterial.getRawmaterialorderinvoice().getId());
					qualitycheckrawmaterial.setRawmaterialorderinvoice(rawmaterialorderinvoice);
				long inid=	qualitycheckrawmaterialService.addEntity(qualitycheckrawmaterial);
				System.out.println("inid " + inid);	
				}
			}
			// TODO  call to order history
			Qualitycheckrawmaterial qualitycheckrawmaterial = new Qualitycheckrawmaterial();
			List<Rawmaterialorderhistory> rawmaterialorderhistories = rawmaterialorderinvoice.getRawmaterialorderhistories();
			for(Rawmaterialorderhistory rawmaterialorderhistory: rawmaterialorderhistories){
				rawmaterialorderinvoice= rawmaterialorderinvoiceService.getEntityById(Rawmaterialorderinvoice.class,rawmaterialorderhistory.getRawmaterialorderinvoice().getId());
				rawmaterialorderhistory.setQualitycheckrawmaterial(qualitycheckrawmaterial);
				rawmaterialorderhistoryService.addEntity(rawmaterialorderhistory);
				
			}	
			// TODO  update inventory
			
			// TODO  call to inventory history
			List<Rawmaterialinventoryhistory> rawmaterialinventoryhistories = qualitycheckrawmaterial.getRawmaterialinventoryhistories();
			for(Rawmaterialinventoryhistory rawmaterialinventoryhistory:rawmaterialinventoryhistories){
				qualitycheckrawmaterial=qualitycheckrawmaterialService.getEntityById(Qualitycheckrawmaterial.class, rawmaterialinventoryhistory.getRawmaterialinventory().getId());
				rawmaterialinventoryhistory.setQualitycheckrawmaterial(qualitycheckrawmaterial);
				rawmaterialinventoryhistoryService.addEntity(rawmaterialinventoryhistory);
			}
			// TODO  call to trigger notification (will do it later )
			return new UserStatus(1,
					"Qualitycheckrawmaterial added Successfully !");
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

	@RequestMapping(value = "listrm/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody List<Rawmaterial> getRmorderinvoiceintakquantityByRMOInvoiceId(
			@PathVariable("id") long id) {
		List<Rawmaterial> rawmaterialList = null;
		try {
			List<Rmorderinvoiceintakquantity> rmorderinvoiceintakquantities = rmorderinvoiceintakquantityService
					.getRmorderinvoiceintakquantityByRMOInvoiceId(id);
			rawmaterialList = new ArrayList<Rawmaterial>();
			System.out.println("list size "
					+ rmorderinvoiceintakquantities.size());
			for (Rmorderinvoiceintakquantity rmorderinvoiceintakquantity : rmorderinvoiceintakquantities) {
				Rawmaterial rawmaterial = rawmaterialService.getEntityById(
						Rawmaterial.class, rmorderinvoiceintakquantity
								.getRawmaterial().getId());
				rawmaterialList.add(rawmaterial);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rawmaterialList;
	}
}
