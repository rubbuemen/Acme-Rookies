
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.AuditorRepository;
import domain.Auditor;

@Service
@Transactional
public class AuditorService {

	// Managed repository
	@Autowired
	private AuditorRepository	auditorRepository;

	// Supporting services
	@Autowired
	private ActorService		actorService;


	// Simple CRUD methods
	public Auditor create() {
		Auditor result;

		result = new Auditor();

		return result;
	}

	public Collection<Auditor> findAll() {
		Collection<Auditor> result;

		result = this.auditorRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Auditor findOne(final int auditorId) {
		Assert.isTrue(auditorId != 0);

		Auditor result;

		result = this.auditorRepository.findOne(auditorId);
		Assert.notNull(result);

		return result;
	}

	public Auditor save(final Auditor auditor) {
		Assert.notNull(auditor);

		Auditor result;

		result = this.auditorRepository.save(auditor);

		return result;
	}

	public void delete(final Auditor auditor) {
		Assert.notNull(auditor);
		Assert.isTrue(auditor.getId() != 0);
		Assert.isTrue(this.auditorRepository.exists(auditor.getId()));

		this.auditorRepository.delete(auditor);
	}


	// Other business methods

	// Reconstruct methods
	@Autowired
	private Validator	validator;


	public Auditor reconstruct(final Auditor auditor, final BindingResult binding) {
		Auditor result;

		if (auditor.getId() == 0)
			result = auditor;
		else {
			result = this.auditorRepository.findOne(auditor.getId());
			Assert.notNull(result, "This entity does not exist");
		}

		this.validator.validate(result, binding);

		return result;
	}

	public void flush() {
		this.auditorRepository.flush();
	}

}
