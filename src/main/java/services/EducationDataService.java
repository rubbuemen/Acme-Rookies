
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.EducationDataRepository;
import domain.Actor;
import domain.Curricula;
import domain.EducationData;
import domain.Rookie;

@Service
@Transactional
public class EducationDataService {

	// Managed repository
	@Autowired
	private EducationDataRepository	educationDataRepository;

	// Supporting services
	@Autowired
	private ActorService			actorService;

	@Autowired
	private RookieService			rookieService;

	@Autowired
	private CurriculaService		curriculaService;


	// Simple CRUD methods
	// R17.1
	public EducationData create() {

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginRookie(actorLogged);

		EducationData result;

		result = new EducationData();

		return result;
	}

	public Collection<EducationData> findAll() {
		Collection<EducationData> result;

		result = this.educationDataRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public EducationData findOne(final int educationDataId) {
		Assert.isTrue(educationDataId != 0);
		EducationData result;

		result = this.educationDataRepository.findOne(educationDataId);
		Assert.notNull(result);

		return result;
	}

	// R17.1
	public EducationData save(final EducationData educationData, final Curricula curricula) {
		Assert.notNull(educationData);

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginRookie(actorLogged);

		EducationData result;

		final Date startDate = educationData.getStartDate();
		final Date endDate = educationData.getEndDate();

		if (startDate != null && endDate != null)
			Assert.isTrue(startDate.before(endDate), "Start date must be before end date");

		if (educationData.getId() == 0) {
			result = this.educationDataRepository.save(educationData);
			final Collection<EducationData> educationDatasHistory = curricula.getEducationDatas();
			educationDatasHistory.add(result);
			curricula.setEducationDatas(educationDatasHistory);
			this.curriculaService.saveAuxiliar(curricula);
		} else {
			final Rookie rookieOwner = this.rookieService.findRookieByCurriculaId(curricula.getId());
			Assert.isTrue(actorLogged.equals(rookieOwner), "The logged actor is not the owner of this entity");
			final Rookie rookieOwner2 = this.rookieService.findRookieByEducationDataId(educationData.getId());
			Assert.isTrue(actorLogged.equals(rookieOwner2), "The logged actor is not the owner of this entity");
			final Curricula cur = this.curriculaService.findCurriculaByEducationDataId(educationData.getId());
			Assert.isTrue(!cur.getIsCopy(), "You can not edit a copy of your curricula");
			result = this.educationDataRepository.save(educationData);
		}

		return result;
	}

	public EducationData saveAuxiliar(final EducationData educationData) {
		Assert.notNull(educationData);
		EducationData result;

		result = this.educationDataRepository.save(educationData);

		return result;
	}

	// R17.1
	public void delete(final EducationData educationData) {
		Assert.notNull(educationData);
		Assert.isTrue(educationData.getId() != 0);
		Assert.isTrue(this.educationDataRepository.exists(educationData.getId()));

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginRookie(actorLogged);

		final Curricula curricula = this.curriculaService.findCurriculaByEducationDataId(educationData.getId());
		Assert.isTrue(!curricula.getIsCopy(), "You can not edit a copy of your curricula");

		final Collection<EducationData> educationDatasCurricula = curricula.getEducationDatas();
		educationDatasCurricula.remove(educationData);
		this.curriculaService.saveAuxiliar(curricula);

		this.educationDataRepository.delete(educationData);
	}

	// Other business methods
	public EducationData findEducationDataRookieLogged(final int educationDataId) {
		Assert.isTrue(educationDataId != 0);

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginRookie(actorLogged);

		final Rookie rookieOwner = this.rookieService.findRookieByEducationDataId(educationDataId);
		Assert.isTrue(actorLogged.equals(rookieOwner), "The logged actor is not the owner of this entity");

		EducationData result;

		result = this.educationDataRepository.findOne(educationDataId);
		Assert.notNull(result);

		return result;
	}


	// Reconstruct methods
	@Autowired
	private Validator	validator;


	public EducationData reconstruct(final EducationData educationData, final BindingResult binding) {
		EducationData result;

		if (educationData.getId() == 0)
			result = educationData;
		else {
			result = this.educationDataRepository.findOne(educationData.getId());
			Assert.notNull(result, "This entity does not exist");
			result.setDegree(educationData.getDegree());
			result.setInstitution(educationData.getInstitution());
			result.setMark(educationData.getMark());
			result.setStartDate(educationData.getStartDate());
			result.setEndDate(educationData.getEndDate());
		}

		this.validator.validate(result, binding);

		return result;
	}

	public void flush() {
		this.educationDataRepository.flush();
	}

}
