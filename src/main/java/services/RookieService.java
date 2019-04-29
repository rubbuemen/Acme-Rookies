
package services;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.RookieRepository;
import security.Authority;
import security.UserAccount;
import domain.Actor;
import domain.Application;
import domain.Curricula;
import domain.Finder;
import domain.Rookie;
import domain.Position;
import forms.RookieForm;

@Service
@Transactional
public class RookieService {

	// Managed repository
	@Autowired
	private RookieRepository	rookieRepository;

	// Supporting services
	@Autowired
	private UserAccountService	userAccountService;

	@Autowired
	private FinderService		finderService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private PositionService		positionService;

	@Autowired
	private ApplicationService	applicationService;

	@Autowired
	private CurriculaService	curriculaService;


	// Simple CRUD methods
	// R7.1
	public Rookie create() {

		Rookie result;

		result = new Rookie();
		final Collection<Application> applications = new HashSet<>();
		final Finder finder = this.finderService.create();
		final UserAccount userAccount = this.userAccountService.create();
		final Authority auth = new Authority();

		auth.setAuthority(Authority.ROOKIE);
		userAccount.addAuthority(auth);
		result.setApplications(applications);
		result.setFinder(finder);
		result.setUserAccount(userAccount);

		return result;
	}

	public Collection<Rookie> findAll() {
		Collection<Rookie> result;

		result = this.rookieRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Rookie findOne(final int rookieId) {
		Assert.isTrue(rookieId != 0);
		Rookie result;

		result = this.rookieRepository.findOne(rookieId);
		Assert.notNull(result);

		return result;
	}

	// R7.1, R8.2
	public Rookie save(final Rookie rookie) {
		Assert.notNull(rookie);

		Rookie result;

		if (rookie.getId() == 0) {
			final Finder finder = this.finderService.save(rookie.getFinder());
			rookie.setFinder(finder);
		}

		result = (Rookie) this.actorService.save(rookie);
		result = this.rookieRepository.save(result);

		return result;
	}

	public void delete(final Rookie rookie) {
		Assert.notNull(rookie);
		Assert.isTrue(rookie.getId() != 0);
		Assert.isTrue(this.rookieRepository.exists(rookie.getId()));

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginRookie(actorLogged);

		final Rookie rookieLogged = (Rookie) actorLogged;

		this.actorService.deleteEntities(rookieLogged);

		final Collection<Application> applications = new HashSet<>(rookieLogged.getApplications());
		for (final Application a : applications) {
			final Position p = a.getPosition();
			final Curricula c = a.getCurricula();
			p.getApplications().remove(a);
			rookieLogged.getApplications().remove(a);
			this.applicationService.delete(a);
			this.curriculaService.deleteAuxiliar(c);
		}

		final Collection<Curricula> curriculasRookie = this.curriculaService.findCurriculasByRookieLogged();
		for (final Curricula c : curriculasRookie)
			this.curriculaService.deleteAuxiliar(c);

		final Finder finder = rookie.getFinder();

		this.rookieRepository.flush();
		this.rookieRepository.delete(rookie);
		this.finderService.delete(finder);
	}

	// Other business methods
	public Rookie saveAuxiliar(final Rookie rookie) {
		Assert.notNull(rookie);

		Rookie result;

		result = this.rookieRepository.save(rookie);

		return result;
	}

	public Collection<Rookie> findRookiesByPositionId(final int positionId) {
		Collection<Rookie> result;

		result = this.rookieRepository.findRookiesByPositionId(positionId);

		return result;
	}

	public Collection<Rookie> findRookiesByProblemId(final int problemId) {
		Collection<Rookie> result;

		result = this.rookieRepository.findRookiesByProblemId(problemId);

		return result;
	}

	public Rookie findRookieByApplicationId(final int applicationId) {
		Rookie result;

		result = this.rookieRepository.findRookieByApplicationId(applicationId);

		return result;
	}

	public Rookie findRookieByCurriculaId(final int curriculaId) {
		Rookie result;

		result = this.rookieRepository.findRookieByCurriculaId(curriculaId);

		return result;
	}

	public Rookie findRookieByPersonalDataId(final int personalDataId) {
		Rookie result;

		result = this.rookieRepository.findRookieByPersonalDataId(personalDataId);

		return result;
	}

	public Rookie findRookieByPositionDataId(final int positionDataId) {
		Rookie result;

		result = this.rookieRepository.findRookieByPositionDataId(positionDataId);

		return result;
	}

	public Rookie findRookieByEducationDataId(final int educationDataId) {
		Rookie result;

		result = this.rookieRepository.findRookieByEducationDataId(educationDataId);

		return result;
	}

	public Rookie findRookieByMiscellaneousDataId(final int miscellaneousDataId) {
		Rookie result;

		result = this.rookieRepository.findRookieByMiscellaneousDataId(miscellaneousDataId);

		return result;
	}

	public Collection<Rookie> findRookiesByFinderCriteria(final int positionId) {
		final Position positionCheck = this.positionService.findOne(positionId);
		final Collection<Rookie> result = new HashSet<>();
		final Collection<Rookie> rookies = this.rookieRepository.findAll();

		for (final Rookie h : rookies) {
			final Finder f = h.getFinder();
			final Calendar cal = Calendar.getInstance();
			Collection<Position> positionsFinder = new HashSet<>();
			if (f.getKeyWord() != null)
				if (!(f.getKeyWord().isEmpty() && f.getDeadline() == null && f.getMinSalary() == null && f.getMaxDeadline() == null)) {
					final String keyWord = f.getKeyWord().toLowerCase();
					final Date deadline = f.getDeadline();
					Double minSalary = f.getMinSalary();
					Date maxDeadline = f.getMaxDeadline();

					if (f.getMinSalary() == null)
						minSalary = 0.0;
					if (maxDeadline == null) {
						cal.set(3000, 0, 1);
						maxDeadline = cal.getTime();
					}
					positionsFinder = this.positionService.findPositionsFromFinder(keyWord, deadline, minSalary, maxDeadline);
				}

			if (positionsFinder.contains(positionCheck))
				result.add(h);
		}

		return result;
	}


	// Reconstruct methods
	@Autowired
	private Validator	validator;


	public RookieForm reconstruct(final RookieForm rookieForm, final BindingResult binding) {
		RookieForm result;
		final Rookie rookie = rookieForm.getActor();

		if (rookie.getId() == 0) {
			final Collection<Application> applications = new HashSet<>();
			final Finder finder = this.finderService.create();
			final UserAccount userAccount = this.userAccountService.create();
			final Authority auth = new Authority();
			auth.setAuthority(Authority.ROOKIE);
			userAccount.addAuthority(auth);
			userAccount.setUsername(rookieForm.getActor().getUserAccount().getUsername());
			userAccount.setPassword(rookieForm.getActor().getUserAccount().getPassword());
			rookie.setApplications(applications);
			rookie.setFinder(finder);
			rookie.setUserAccount(userAccount);
			rookieForm.setActor(rookie);
		} else {
			final Rookie res = this.rookieRepository.findOne(rookie.getId());
			Assert.notNull(res, "This entity does not exist");
			res.setName(rookie.getName());
			res.setSurnames(rookie.getSurnames());
			res.setVATNumber(rookie.getVATNumber());
			res.setCreditCard(rookie.getCreditCard());
			res.setPhoto(rookie.getPhoto());
			res.setEmail(rookie.getEmail());
			res.setPhoneNumber(rookie.getPhoneNumber());
			res.setAddress(rookie.getAddress());
			rookieForm.setActor(res);
		}

		result = rookieForm;

		this.validator.validate(result, binding);

		return result;
	}

	public void flush() {
		this.rookieRepository.flush();
	}

}
