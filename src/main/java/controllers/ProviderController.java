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

import security.Authority;
import services.ProviderService;
import domain.Provider;

@Controller
@RequestMapping("/provider")
public class ProviderController extends AbstractController {

	@Autowired
	ProviderService	providerService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView listProviders() {
		ModelAndView result;
		Collection<Provider> providers;

		providers = this.providerService.findAll();

		result = new ModelAndView("provider/list");

		result.addObject("providers", providers);
		result.addObject("requestURI", "provider/list.do");

		return result;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView showProvider(@RequestParam final int providerId) {
		ModelAndView result;
		Provider actor;

		actor = this.providerService.findOne(providerId);

		result = new ModelAndView("actor/show");

		result.addObject("actor", actor);
		result.addObject("authority", Authority.PROVIDER);

		return result;
	}
}
