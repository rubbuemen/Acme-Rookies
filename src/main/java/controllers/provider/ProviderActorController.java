/*
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import services.ActorService;
import services.ProviderService;
import services.UserAccountService;
import controllers.AbstractController;
import domain.Actor;
import domain.Provider;
import forms.ProviderForm;

@Controller
@RequestMapping("/actor/provider")
public class ProviderActorController extends AbstractController {

	@Autowired
	ActorService		actorService;

	@Autowired
	ProviderService		providerService;

	@Autowired
	UserAccountService	userAccountService;


	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		Provider actor;

		actor = (Provider) this.actorService.findActorLogged();
		final ProviderForm actorForm = new ProviderForm(actor);

		result = new ModelAndView("actor/edit");

		result.addObject("authority", Authority.PROVIDER);
		result.addObject("actionURL", "actor/provider/edit.do");
		result.addObject("actorForm", actorForm);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(@ModelAttribute("actorForm") ProviderForm actorForm, final BindingResult binding) {
		ModelAndView result;

		try {
			actorForm = this.providerService.reconstruct(actorForm, binding);
			if (binding.hasErrors())
				result = this.createEditModelAndView(actorForm.getActor());
			else {
				this.providerService.save(actorForm.getActor());
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

		if (actor instanceof Provider)
			result.addObject("authority", Authority.PROVIDER);
		result.addObject("actor", actor);
		result.addObject("message", message);

		return result;
	}

}
