/*
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.rookie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.RookieService;
import services.UserAccountService;
import controllers.AbstractController;
import domain.Actor;
import domain.Rookie;
import forms.RookieForm;

@Controller
@RequestMapping("/actor/rookie")
public class RookieActorController extends AbstractController {

	@Autowired
	ActorService		actorService;

	@Autowired
	RookieService		rookieService;

	@Autowired
	UserAccountService	userAccountService;


	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		Rookie actor;

		actor = (Rookie) this.actorService.findActorLogged();
		final RookieForm actorForm = new RookieForm(actor);

		result = new ModelAndView("actor/edit");

		result.addObject("actionURL", "actor/rookie/edit.do");
		result.addObject("actorForm", actorForm);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(@ModelAttribute("actorForm") RookieForm actorForm, final BindingResult binding) {
		ModelAndView result;

		try {
			actorForm = this.rookieService.reconstruct(actorForm, binding);
			if (binding.hasErrors())
				result = this.createEditModelAndView(actorForm.getActor());
			else {
				this.rookieService.save(actorForm.getActor());
				result = new ModelAndView("redirect:/welcome/index.do");
			}

		} catch (final Throwable oops) {
			if (oops.getMessage().equals("The logged actor is not the owner of this entity"))
				result = this.createEditModelAndView(actorForm.getActor(), "hacking.logged.error");
			else if (oops.getMessage().equals("Invalid credit card"))
				result = this.createEditModelAndView(actorForm.getActor(), "creditCard.error.invalid");
			else if (oops.getMessage().equals("Expired credit card"))
				result = this.createEditModelAndView(actorForm.getActor(), "creditCard.error.expired");
			else if (oops.getMessage().equals("This entity does not exist"))
				result = this.createEditModelAndView(null, "hacking.notExist.error");
			else
				result = this.createEditModelAndView(actorForm.getActor(), "commit.error");
		}

		return result;
	}

	// Ancillary methods

	protected ModelAndView createEditModelAndView(final Actor actor) {
		ModelAndView result;
		result = this.createEditModelAndView(actor, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Actor actor, final String message) {
		ModelAndView result;
		if (actor == null)
			result = new ModelAndView("redirect:/welcome/index.do");
		else
			result = new ModelAndView("actor/edit");

		result.addObject("actor", actor);
		result.addObject("message", message);

		return result;
	}

}
