
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.AuditRepository;
import domain.Actor;
import domain.Audit;
import domain.Auditor;
import domain.Position;

@Service
@Transactional
public class AuditService {

	// Managed repository
	@Autowired
	private AuditRepository	auditRepository;

	// Supporting services
	@Autowired
	private ActorService	actorService;

	@Autowired
	private PositionService	positionService;

	@Autowired
	private AuditorService	auditorService;


	// Simple CRUD methods
	//R3.1 y R3.2 (Acme-Rookies)
	public Audit create() {
		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginAuditor(actorLogged);

		Audit result;

		result = new Audit();

		final Date moment = new Date(System.currentTimeMillis() - 1);

		result.setMoment(moment);
		result.setIsFinalMode(false);

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

	//R3.1 y R3.2 (Acme-Rookies)
	public Audit save(final Audit audit) {
		Assert.notNull(audit);

		Audit result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginAuditor(actorLogged);

		final Auditor auditorLogged = (Auditor) actorLogged;

		Assert.isTrue(!audit.getIsFinalMode(), "You can only save audits that are not in final mode");

		if (audit.getId() == 0) {
			final Collection<Position> findPositionsToAudit = this.positionService.findPositionsToAudit();

			Assert.isTrue(findPositionsToAudit.contains(audit.getPosition()), "This position is not available to audit");
			final Date moment = new Date(System.currentTimeMillis() - 1);
			audit.setMoment(moment);
			result = this.auditRepository.save(audit);
			final Collection<Audit> auditsAuditorLogged = auditorLogged.getAudits();
			auditsAuditorLogged.add(result);
			auditorLogged.setAudits(auditsAuditorLogged);
			this.auditorService.save(auditorLogged);
		} else {
			final Auditor auditorOwner = this.auditorService.findAuditorByAuditId(audit.getId());
			Assert.isTrue(actorLogged.equals(auditorOwner), "The logged actor is not the owner of this entity");
			result = this.auditRepository.save(audit);
		}

		return result;
	}

	//R3.2 (Acme-Rookies)
	public void delete(final Audit audit) {
		Assert.notNull(audit);
		Assert.isTrue(audit.getId() != 0);
		Assert.isTrue(this.auditRepository.exists(audit.getId()));

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginAuditor(actorLogged);

		final Auditor auditorOwner = this.auditorService.findAuditorByAuditId(audit.getId());
		Assert.isTrue(actorLogged.equals(auditorOwner), "The logged actor is not the owner of this entity");

		Assert.isTrue(!audit.getIsFinalMode(), "You can only delete audits that are not in final mode");

		final Auditor auditorLogged = (Auditor) actorLogged;

		final Collection<Audit> auditsActorLogged = auditorLogged.getAudits();
		auditsActorLogged.remove(audit);
		auditorLogged.setAudits(auditsActorLogged);
		this.auditorService.save(auditorLogged);

		this.auditRepository.delete(audit);
	}

	public void deleteAuxiliar(final Audit audit) {
		Assert.notNull(audit);
		Assert.isTrue(audit.getId() != 0);
		Assert.isTrue(this.auditRepository.exists(audit.getId()));

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);

		final Auditor auditorLogged = (Auditor) actorLogged;

		final Collection<Audit> auditsActorLogged = auditorLogged.getAudits();
		auditsActorLogged.remove(audit);
		auditorLogged.setAudits(auditsActorLogged);
		this.auditorService.save(auditorLogged);

		this.auditRepository.delete(audit);
	}

	// Other business methods
	//R3.2 (Acme-Rookies)
	public Collection<Audit> findAuditsByAuditorLogged() {
		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginAuditor(actorLogged);

		Collection<Audit> result;

		final Auditor auditorLogged = (Auditor) actorLogged;

		result = this.auditRepository.findAuditsByAuditorId(auditorLogged.getId());
		Assert.notNull(result);

		return result;
	}

	public Audit findAuditAuditorLogged(final int auditId) {
		Assert.isTrue(auditId != 0);

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginAuditor(actorLogged);

		final Auditor auditorOwner = this.auditorService.findAuditorByAuditId(auditId);
		Assert.isTrue(actorLogged.equals(auditorOwner), "The logged actor is not the owner of this entity");

		Audit result;

		result = this.auditRepository.findOne(auditId);
		Assert.notNull(result);

		return result;
	}

	//R3.2 (Acme-Rookies)
	public Audit changeFinalMode(final Audit audit) {
		Audit result;
		Assert.notNull(audit);
		Assert.isTrue(audit.getId() != 0);
		Assert.isTrue(this.auditRepository.exists(audit.getId()));

		Assert.isTrue(!audit.getIsFinalMode(), "This audit is already in final mode");
		audit.setIsFinalMode(true);

		result = this.auditRepository.save(audit);

		return result;
	}

	public Collection<Audit> findAuditsByCompanyId(final int companyId) {
		Collection<Audit> result;

		result = this.auditRepository.findAuditsByCompanyId(companyId);
		Assert.notNull(result);

		return result;
	}

	public Collection<Audit> findAuditsFinalModeByPositionId(final int positionId) {
		Collection<Audit> result;

		result = this.auditRepository.findAuditsFinalModeByPositionId(positionId);

		return result;
	}


	// Reconstruct methods
	@Autowired
	private Validator	validator;


	public Audit reconstruct(final Audit audit, final BindingResult binding) {
		Audit result;

		if (audit.getId() == 0) {
			final Date moment = new Date(System.currentTimeMillis() - 1);
			audit.setMoment(moment);
			audit.setIsFinalMode(false);
			result = audit;
		} else {
			final Audit originalAudit = this.auditRepository.findOne(audit.getId());
			Assert.notNull(originalAudit, "This entity does not exist");
			result = audit;
			result.setIsFinalMode(originalAudit.getIsFinalMode());
			result.setMoment(originalAudit.getMoment());
			result.setPosition(originalAudit.getPosition());
		}

		this.validator.validate(result, binding);

		this.auditRepository.flush();

		return result;
	}

	public void flush() {
		this.auditRepository.flush();
	}

}
