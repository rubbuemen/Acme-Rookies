/*
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.auditor;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AuditService;
import services.PositionService;
import controllers.AbstractController;
import domain.Audit;
import domain.Position;

@Controller
@RequestMapping("/audit/auditor")
public class AuditorAuditController extends AbstractController {

	@Autowired
	AuditService	auditService;

	@Autowired
	PositionService	positionService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Audit> audits;

		audits = this.auditService.findAuditsByAuditorLogged();

		result = new ModelAndView("audit/list");

		result.addObject("audits", audits);
		result.addObject("requestURI", "audit/auditor/list.do");

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Audit audit;

		audit = this.auditService.create();

		result = this.createEditModelAndView(audit);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int auditId) {
		ModelAndView result;
		Audit audit = null;

		try {
			audit = this.auditService.findAuditAuditorLogged(auditId);
			result = this.createEditModelAndView(audit);
		} catch (final Throwable oops) {
			if (oops.getMessage().equals("The logged actor is not the owner of this entity"))
				result = this.createEditModelAndView(audit, "hacking.logged.error");
			else
				result = this.createEditModelAndView(audit, "commit.error");
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView createOrEdit(Audit audit, final BindingResult binding) {
		ModelAndView result;

		try {
			audit = this.auditService.reconstruct(audit, binding);
			if (binding.hasErrors())
				result = this.createEditModelAndView(audit);
			else {
				this.auditService.save(audit);
				result = new ModelAndView("redirect:/audit/auditor/list.do");
			}
		} catch (final Throwable oops) {
			if (oops.getMessage().equals("You can only save audits that are not in final mode"))
				result = this.createEditModelAndView(audit, "audit.error.save.finalMode");
			else if (oops.getMessage().equals("This position is not available to audit"))
				result = this.createEditModelAndView(audit, "audit.error.save.position.hacking");
			else if (oops.getMessage().equals("The logged actor is not the owner of this entity"))
				result = this.createEditModelAndView(audit, "hacking.logged.error");
			else if (oops.getMessage().equals("This entity does not exist"))
				result = this.createEditModelAndView(null, "hacking.notExist.error");
			else
				result = this.createEditModelAndView(audit, "commit.error");
		}

		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int auditId) {
		ModelAndView result;

		final Audit audit = this.auditService.findAuditAuditorLogged(auditId);

		try {
			this.auditService.delete(audit);
			result = new ModelAndView("redirect:/audit/auditor/list.do");

		} catch (final Throwable oops) {
			if (oops.getMessage().equals("You can only delete audits that are not in final mode"))
				result = this.createEditModelAndView(audit, "audit.error.delete.finalMode");
			else if (oops.getMessage().equals("The logged actor is not the owner of this entity"))
				result = this.createEditModelAndView(audit, "hacking.logged.error");
			else if (oops.getMessage().equals("This entity does not exist"))
				result = this.createEditModelAndView(null, "hacking.notExist.error");
			else
				result = this.createEditModelAndView(audit, "commit.error");
		}

		return result;
	}

	@RequestMapping(value = "/change", method = RequestMethod.GET)
	public ModelAndView changeFinalMode(@RequestParam final int auditId) {
		ModelAndView result;

		final Audit audit = this.auditService.findAuditAuditorLogged(auditId);

		try {
			this.auditService.changeFinalMode(audit);
			result = new ModelAndView("redirect:/audit/auditor/list.do");

		} catch (final Throwable oops) {
			if (oops.getMessage().equals("This audit is already in final mode"))
				result = this.createEditModelAndView(audit, "audit.error.change.finalMode");
			else if (oops.getMessage().equals("The logged actor is not the owner of this entity"))
				result = this.createEditModelAndView(audit, "hacking.logged.error");
			else if (oops.getMessage().equals("This entity does not exist"))
				result = this.createEditModelAndView(null, "hacking.notExist.error");
			else
				result = this.createEditModelAndView(audit, "commit.error");
		}

		return result;
	}

	// Ancillary methods
	protected ModelAndView createEditModelAndView(final Audit audit) {
		ModelAndView result;
		result = this.createEditModelAndView(audit, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Audit audit, final String message) {
		ModelAndView result;

		if (audit == null)
			result = new ModelAndView("redirect:/welcome/index.do");
		else {
			if (audit.getId() == 0)
				result = new ModelAndView("audit/create");
			else
				result = new ModelAndView("audit/edit");
			final Collection<Position> findPositionsToAudit = this.positionService.findPositionsToAudit();
			result.addObject("positions", findPositionsToAudit);
		}

		result.addObject("audit", audit);
		result.addObject("actionURL", "audit/auditor/edit.do");
		result.addObject("message", message);

		return result;
	}

}
