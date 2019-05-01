/*
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AuditService;
import domain.Audit;

@Controller
@RequestMapping("/audit")
public class AuditController extends AbstractController {

	@Autowired
	AuditService	auditService;


	@RequestMapping(value = "/listGeneric", method = RequestMethod.GET)
	public ModelAndView listAudits(@RequestParam final Integer positionId) {
		ModelAndView result;
		Collection<Audit> audits;

		audits = this.auditService.findAuditsFinalModeByPositionId(positionId);

		result = new ModelAndView("audit/listGeneric");

		result.addObject("audits", audits);
		result.addObject("requestURI", "audit/listGeneric.do");

		return result;
	}
}
