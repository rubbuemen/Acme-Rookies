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
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ItemService;
import domain.Item;
import domain.Provider;

@Controller
@RequestMapping("/item")
public class ItemController extends AbstractController {

	@Autowired
	ItemService	itemService;


	@RequestMapping(value = "/listGeneric", method = RequestMethod.GET)
	public ModelAndView listItems(@RequestParam(required = false) final Integer providerId) {
		ModelAndView result;
		Collection<Item> items;
		Map<Item, Provider> mapItemProvider = null;

		if (providerId != null)
			items = this.itemService.findItemsByProviderId(providerId);
		else {
			items = this.itemService.findAll();
			mapItemProvider = this.itemService.getMapItemProvider(items);
		}

		result = new ModelAndView("item/listGeneric");

		result.addObject("items", items);
		result.addObject("mapItemProvider", mapItemProvider);
		result.addObject("requestURI", "item/listGeneric.do");

		return result;
	}
}
