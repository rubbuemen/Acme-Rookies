
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.AuditRepository;
import domain.Audit;

@Service
@Transactional
public class AuditService {

	// Managed repository
	@Autowired
	private AuditRepository	auditRepository;

	// Supporting services
	@Autowired
	private ActorService	actorService;


	// Simple CRUD methods
	public Audit create() {
		Audit result;

		result = new Audit();

		return result;
	}

	public Collection<Audit> findAll() {
		Collection<Audit> result;

		result = this.auditRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Audit findOne(final int auditId) {
		Assert.isTrue(auditId != 0);

		Audit result;

		result = this.auditRepository.findOne(auditId);
		Assert.notNull(result);

		return result;
	}

	public Audit save(final Audit audit) {
		Assert.notNull(audit);

		Audit result;

		result = this.auditRepository.save(audit);

		return result;
	}

	public void delete(final Audit audit) {
		Assert.notNull(audit);
		Assert.isTrue(audit.getId() != 0);
		Assert.isTrue(this.auditRepository.exists(audit.getId()));

		this.auditRepository.delete(audit);
	}


	// Other business methods

	// Reconstruct methods
	@Autowired
	private Validator	validator;


	public Audit reconstruct(final Audit audit, final BindingResult binding) {
		Audit result;

		if (audit.getId() == 0)
			result = audit;
		else {
			result = this.auditRepository.findOne(audit.getId());
			Assert.notNull(result, "This entity does not exist");
		}

		this.validator.validate(result, binding);

		return result;
	}

	public void flush() {
		this.auditRepository.flush();
	}

}
