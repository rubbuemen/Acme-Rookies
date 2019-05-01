/*
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.rookie;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.FinderService;
import services.PositionService;
import services.SponsorshipService;
import controllers.AbstractController;
import domain.Finder;
import domain.Position;
import domain.Sponsorship;

@Controller
@RequestMapping("/finder/rookie")
public class RookieFinderController extends AbstractController {

	@Autowired
	PositionService		positionService;

	@Autowired
	FinderService		finderService;

	@Autowired
	SponsorshipService	sponsorshipService;


	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView finderSearch() {
		ModelAndView result;

		Finder finder = null;

		try {
			finder = this.finderService.findFinderRookieLogged();
			final boolean clearCache = this.finderService.cleanCache(finder);
			if (clearCache)
				this.finderService.updateCriteria(finder.getKeyWord(), finder.getDeadline(), finder.getMinSalary(), finder.getMaxDeadline());
			result = this.createEditModelAndView(finder);
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(finder, "commit.error");
		}

		return result;
	}

	@RequestMapping(value = "/clear", method = RequestMethod.GET)
	public ModelAndView clear() {
		ModelAndView result;

		Finder finder = null;

		try {
			finder = this.finderService.findFinderRookieLogged();
			finder = this.finderService.cleanFinder(finder);
			result = this.createEditModelAndView(finder);
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(finder, "commit.error");
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(Finder finder, final BindingResult binding) {
		ModelAndView result;

		try {
			finder = this.finderService.reconstruct(finder, binding);
			if (binding.hasErrors())
				result = this.createEditModelAndView(finder);
			else {
				this.finderService.updateCriteria(finder.getKeyWord(), finder.getDeadline(), finder.getMinSalary(), finder.getMaxDeadline());
				result = new ModelAndView("redirect:/finder/rookie/edit.do");
			}
		} catch (final Throwable oops) {
			if (oops.getMessage().equals("The logged actor is not the owner of this entity"))
				result = this.createEditModelAndView(finder, "hacking.logged.error");
			else if (oops.getMessage().equals("This entity does not exist"))
				result = this.createEditModelAndView(null, "hacking.notExist.error");
			else
				result = this.createEditModelAndView(finder, "commit.error");
		}

		return result;
	}

	// Ancillary methods
	protected ModelAndView createEditModelAndView(final Finder finder) {
		ModelAndView result;
		result = this.createEditModelAndView(finder, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Finder finder, final String message) {
		ModelAndView result;

		if (finder == null)
			result = new ModelAndView("redirect:/welcome/index.do");
		else {
			result = new ModelAndView("finder/edit");
			final Collection<Position> positions = finder.getPositions();
			result.addObject("positions", positions);
			if (positions == null || !positions.isEmpty()) {
				final Map<Position, Sponsorship> randomSponsorship = new HashMap<>();
				for (final Position p : positions) {
					final Sponsorship sponsorship = this.sponsorshipService.findRandomSponsorship(p.getId());
					if (sponsorship != null)
						randomSponsorship.put(p, sponsorship);
				}
				result.addObject("randomSponsorship", randomSponsorship);
			}
		}

		result.addObject("finder", finder);
		result.addObject("actionURL", "finder/rookie/edit.do");
		result.addObject("message", message);

		return result;
	}

}
