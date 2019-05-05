
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.MiscellaneousDataRepository;
import domain.Actor;
import domain.Curricula;
import domain.MiscellaneousData;
import domain.Rookie;

@Service
@Transactional
public class MiscellaneousDataService {

	// Managed repository
	@Autowired
	private MiscellaneousDataRepository	miscellaneousDataRepository;

	// Supporting services
	@Autowired
	private ActorService				actorService;

	@Autowired
	private RookieService				rookieService;

	@Autowired
	private CurriculaService			curriculaService;


	// Simple CRUD methods
	// R17.1
	public MiscellaneousData create() {

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginRookie(actorLogged);

		MiscellaneousData result;

		result = new MiscellaneousData();

		return result;
	}

	public Collection<MiscellaneousData> findAll() {
		Collection<MiscellaneousData> result;

		result = this.miscellaneousDataRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public MiscellaneousData findOne(final int miscellaneousDataId) {
		Assert.isTrue(miscellaneousDataId != 0);
		MiscellaneousData result;

		result = this.miscellaneousDataRepository.findOne(miscellaneousDataId);
		Assert.notNull(result);

		return result;
	}

	// R17.1
	public MiscellaneousData save(final MiscellaneousData miscellaneousData, final Curricula curricula) {
		Assert.notNull(miscellaneousData);

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginRookie(actorLogged);

		MiscellaneousData result;

		if (miscellaneousData.getId() == 0) {
			result = this.miscellaneousDataRepository.save(miscellaneousData);
			final Collection<MiscellaneousData> miscellaneousDatasHistory = curricula.getMiscellaneousDatas();
			miscellaneousDatasHistory.add(result);
			curricula.setMiscellaneousDatas(miscellaneousDatasHistory);
			this.curriculaService.saveAuxiliar(curricula);
		} else {
			final Rookie rookieOwner = this.rookieService.findRookieByCurriculaId(curricula.getId());
			Assert.isTrue(actorLogged.equals(rookieOwner), "The logged actor is not the owner of this entity");
			final Rookie rookieOwner2 = this.rookieService.findRookieByMiscellaneousDataId(miscellaneousData.getId());
			Assert.isTrue(actorLogged.equals(rookieOwner2), "The logged actor is not the owner of this entity");
			final Curricula cur = this.curriculaService.findCurriculaByMiscellaneousDataId(miscellaneousData.getId());
			Assert.isTrue(!cur.getIsCopy(), "You can not edit a copy of your curricula");
			result = this.miscellaneousDataRepository.save(miscellaneousData);
		}

		return result;
	}

	public MiscellaneousData saveAuxiliar(final MiscellaneousData miscellaneousData) {
		Assert.notNull(miscellaneousData);
		MiscellaneousData result;

		result = this.miscellaneousDataRepository.save(miscellaneousData);

		return result;
	}

	// R17.1
	public void delete(final MiscellaneousData miscellaneousData) {
		Assert.notNull(miscellaneousData);
		Assert.isTrue(miscellaneousData.getId() != 0);
		Assert.isTrue(this.miscellaneousDataRepository.exists(miscellaneousData.getId()));

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginRookie(actorLogged);

		final Curricula curricula = this.curriculaService.findCurriculaByMiscellaneousDataId(miscellaneousData.getId());
		Assert.isTrue(!curricula.getIsCopy(), "You can not edit a copy of your curricula");

		final Collection<MiscellaneousData> miscellaneousDatasCurricula = curricula.getMiscellaneousDatas();
		miscellaneousDatasCurricula.remove(miscellaneousData);
		this.curriculaService.saveAuxiliar(curricula);

		this.miscellaneousDataRepository.delete(miscellaneousData);
	}

	// Other business methods
	public MiscellaneousData findMiscellaneousDataRookieLogged(final int miscellaneousDataId) {
		Assert.isTrue(miscellaneousDataId != 0);

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginRookie(actorLogged);

		final Rookie rookieOwner = this.rookieService.findRookieByMiscellaneousDataId(miscellaneousDataId);
		Assert.isTrue(actorLogged.equals(rookieOwner), "The logged actor is not the owner of this entity");

		MiscellaneousData result;

		result = this.miscellaneousDataRepository.findOne(miscellaneousDataId);
		Assert.notNull(result);

		return result;
	}


	// Reconstruct methods
	@Autowired
	private Validator	validator;


	public MiscellaneousData reconstruct(final MiscellaneousData miscellaneousData, final BindingResult binding) {
		MiscellaneousData result;

		if (miscellaneousData.getId() == 0)
			result = miscellaneousData;
		else {
			final MiscellaneousData originalMiscellaneousData = this.miscellaneousDataRepository.findOne(miscellaneousData.getId());
			Assert.notNull(originalMiscellaneousData, "This entity does not exist");
			result = miscellaneousData;
		}

		this.validator.validate(result, binding);

		return result;
	}

	public void flush() {
		this.miscellaneousDataRepository.flush();
	}

}
