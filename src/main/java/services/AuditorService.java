
package services;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.AuditorRepository;
import security.Authority;
import security.UserAccount;
import domain.Actor;
import domain.Audit;
import domain.Auditor;
import forms.AuditorForm;

@Service
@Transactional
public class AuditorService {

	// Managed repository
	@Autowired
	private AuditorRepository	auditorRepository;

	// Supporting services
	@Autowired
	private ActorService		actorService;

	@Autowired
	private UserAccountService	userAccountService;

	@Autowired
	private AuditService		auditService;


	// Simple CRUD methods
	// R4.2(Acme-Rookies)
	public Auditor create() {
		Auditor result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginAdministrator(actorLogged);

		result = new Auditor();
		final Collection<Audit> audits = new HashSet<>();
		final UserAccount userAccount = this.userAccountService.create();
		final Authority auth = new Authority();

		auth.setAuthority(Authority.AUDITOR);
		userAccount.addAuthority(auth);
		result.setUserAccount(userAccount);
		result.setAudits(audits);

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

	// R4.2(Acme-Rookies)
	public Auditor save(final Auditor auditor) {
		Assert.notNull(auditor);

		Auditor result;

		result = (Auditor) this.actorService.save(auditor);
		result = this.auditorRepository.save(result);

		return result;
	}

	public Auditor saveAuxiliar(final Auditor auditor) {
		Assert.notNull(auditor);

		Auditor result;

		result = this.auditorRepository.save(auditor);

		return result;
	}

	public void delete(final Auditor auditor) {
		Assert.notNull(auditor);
		Assert.isTrue(auditor.getId() != 0);
		Assert.isTrue(this.auditorRepository.exists(auditor.getId()));

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginAuditor(actorLogged);

		final Auditor auditorLogged = (Auditor) actorLogged;

		this.actorService.deleteEntities(auditorLogged);

		final Collection<Audit> audits = new HashSet<>(auditorLogged.getAudits());
		for (final Audit aud : audits)
			this.auditService.deleteAuxiliar(aud);

		this.auditorRepository.flush();
		this.auditorRepository.delete(auditor);
	}

	// Other business methods
	public Auditor findAuditorByAuditId(final int auditId) {
		Auditor result;

		result = this.auditorRepository.findAuditorByAuditId(auditId);

		return result;
	}

	public Collection<Auditor> findAuditorsByPositionId(final int positionId) {
		Collection<Auditor> result;

		result = this.auditorRepository.findAuditorsByPositionId(positionId);

		return result;
	}


	// Reconstruct methods
	@Autowired
	private Validator	validator;


	public AuditorForm reconstruct(final AuditorForm auditorForm, final BindingResult binding) {
		AuditorForm result;
		final Auditor auditor = auditorForm.getActor();

		if (auditor.getId() == 0) {
			final UserAccount userAccount = this.userAccountService.create();
			final Collection<Audit> audits = new HashSet<>();
			final Authority auth = new Authority();
			auth.setAuthority(Authority.AUDITOR);
			userAccount.addAuthority(auth);
			userAccount.setUsername(auditorForm.getActor().getUserAccount().getUsername());
			userAccount.setPassword(auditorForm.getActor().getUserAccount().getPassword());
			auditor.setUserAccount(userAccount);
			auditor.setAudits(audits);
			auditorForm.setActor(auditor);
		} else {
			final Auditor res = this.auditorRepository.findOne(auditor.getId());
			res.setName(auditor.getName());
			res.setSurnames(auditor.getSurnames());
			res.setVATNumber(auditor.getVATNumber());
			res.setCreditCard(auditor.getCreditCard());
			res.setPhoto(auditor.getPhoto());
			res.setEmail(auditor.getEmail());
			res.setPhoneNumber(auditor.getPhoneNumber());
			res.setAddress(auditor.getAddress());
			auditorForm.setActor(res);
		}

		result = auditorForm;

		this.validator.validate(result, binding);

		return result;
	}

	public void flush() {
		this.auditorRepository.flush();
	}

}
