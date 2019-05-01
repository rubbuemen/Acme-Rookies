/*
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.provider;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ItemService;
import controllers.AbstractController;
import domain.Item;

@Controller
@RequestMapping("/item/provider")
public class ProviderItemController extends AbstractController {

	@Autowired
	ItemService	itemService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Item> items;

		items = this.itemService.findItemsByProviderLogged();

		result = new ModelAndView("item/list");

		result.addObject("items", items);
		result.addObject("requestURI", "item/provider/list.do");

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Item item;

		item = this.itemService.create();

		result = this.createEditModelAndView(item);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int itemId) {
		ModelAndView result;
		Item item = null;

		try {
			item = this.itemService.findItemProviderLogged(itemId);
			result = this.createEditModelAndView(item);
		} catch (final Throwable oops) {
			if (oops.getMessage().equals("The logged actor is not the owner of this entity"))
				result = this.createEditModelAndView(item, "hacking.logged.error");
			else
				result = this.createEditModelAndView(item, "commit.error");
		}

		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView createOrEdit(Item item, final BindingResult binding) {
		ModelAndView result;

		try {
			item = this.itemService.reconstruct(item, binding);
			if (binding.hasErrors())
				result = this.createEditModelAndView(item);
			else {
				this.itemService.save(item);
				result = new ModelAndView("redirect:/item/provider/list.do");
			}
		} catch (final Throwable oops) {
			if (oops.getMessage().equals("The logged actor is not the owner of this entity"))
				result = this.createEditModelAndView(item, "hacking.logged.error");
			else if (oops.getMessage().equals("This entity does not exist"))
				result = this.createEditModelAndView(null, "hacking.notExist.error");
			else
				result = this.createEditModelAndView(item, "commit.error");
		}

		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int itemId) {
		ModelAndView result;

		final Item item = this.itemService.findItemProviderLogged(itemId);

		try {
			this.itemService.delete(item);
			result = new ModelAndView("redirect:/item/provider/list.do");

		} catch (final Throwable oops) {
			if (oops.getMessage().equals("The logged actor is not the owner of this entity"))
				result = this.createEditModelAndView(item, "hacking.logged.error");
			else if (oops.getMessage().equals("This entity does not exist"))
				result = this.createEditModelAndView(null, "hacking.notExist.error");
			else
				result = this.createEditModelAndView(item, "commit.error");
		}

		return result;
	}

	// Ancillary methods
	protected ModelAndView createEditModelAndView(final Item item) {
		ModelAndView result;
		result = this.createEditModelAndView(item, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Item item, final String message) {
		ModelAndView result;

		if (item == null)
			result = new ModelAndView("redirect:/welcome/index.do");
		else if (item.getId() == 0)
			result = new ModelAndView("item/create");
		else
			result = new ModelAndView("item/edit");

		result.addObject("item", item);
		result.addObject("actionURL", "item/provider/edit.do");
		result.addObject("message", message);

		return result;
	}

}
