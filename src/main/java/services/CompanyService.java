
package services;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.CompanyRepository;
import security.Authority;
import security.UserAccount;
import domain.Actor;
import domain.Application;
import domain.Audit;
import domain.Auditor;
import domain.Company;
import domain.Curricula;
import domain.Position;
import domain.Problem;
import domain.Provider;
import domain.Rookie;
import domain.Sponsorship;
import forms.CompanyForm;

@Service
@Transactional
public class CompanyService {

	// Managed repository
	@Autowired
	private CompanyRepository	companyRepository;

	// Supporting services
	@Autowired
	private UserAccountService	userAccountService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private PositionService		positionService;

	@Autowired
	private ApplicationService	applicationService;

	@Autowired
	private CurriculaService	curriculaService;

	@Autowired
	private ProblemService		problemService;

	@Autowired
	private RookieService		rookieService;

	@Autowired
	private SponsorshipService	sponsorshipService;

	@Autowired
	private ProviderService		providerService;

	@Autowired
	private AuditService		auditService;

	@Autowired
	private AuditorService		auditorService;


	// Simple CRUD methods
	// R7.1
	public Company create() {
		Company result;

		result = new Company();
		final Collection<Position> positions = new HashSet<>();
		final Collection<Problem> problems = new HashSet<>();
		final UserAccount userAccount = this.userAccountService.create();
		final Authority auth = new Authority();

		auth.setAuthority(Authority.COMPANY);
		userAccount.addAuthority(auth);
		result.setPositions(positions);
		result.setProblems(problems);
		result.setUserAccount(userAccount);

		return result;
	}

	//R7.3
	public Collection<Company> findAll() {
		Collection<Company> result;

		result = this.companyRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Company findOne(final int companyId) {
		Assert.isTrue(companyId != 0);
		Company result;

		result = this.companyRepository.findOne(companyId);
		Assert.notNull(result);

		return result;
	}

	// R7.1, R8.2
	public Company save(final Company company) {
		Assert.notNull(company);

		Company result;

		result = (Company) this.actorService.save(company);
		result = this.companyRepository.save(result);

		return result;
	}

	public Company saveForComputes(final Company company) {
		Assert.notNull(company);

		Company result;

		result = this.companyRepository.save(company);

		return result;
	}

	public void delete(final Company company) {
		Assert.notNull(company);
		Assert.isTrue(company.getId() != 0);
		Assert.isTrue(this.companyRepository.exists(company.getId()));

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginCompany(actorLogged);

		final Company companyLogged = (Company) actorLogged;

		this.actorService.deleteEntities(companyLogged);

		final Collection<Position> positions = new HashSet<>(companyLogged.getPositions());
		for (final Position p : positions) {
			final Collection<Application> applications = new HashSet<>(p.getApplications());
			final Collection<Sponsorship> sponsorships = new HashSet<>(p.getSponsorships());
			final Collection<Audit> audits = new HashSet<>(this.auditService.findAuditsFinalModeByPositionId(p.getId()));
			for (final Application a : applications) {
				final Rookie h = a.getRookie();
				final Curricula c = a.getCurricula();
				p.getApplications().remove(a);
				h.getApplications().remove(a);
				this.applicationService.delete(a);
				this.curriculaService.deleteAuxiliar(c);
				this.rookieService.saveAuxiliar(h);
			}
			for (final Sponsorship s : sponsorships) {
				final Provider pr = s.getProvider();
				p.getSponsorships().remove(s);
				pr.getSponsorships().remove(s);
				this.sponsorshipService.deleteAuxiliar(s);
				this.providerService.saveAuxiliar(pr);
			}
			for (final Audit a : audits) {
				final Auditor aud = this.auditorService.findAuditorByAuditId(a.getId());
				aud.getAudits().remove(a);
				this.auditService.deleteAuxiliar(a);
				this.auditorService.saveAuxiliar(aud);
			}
			this.positionService.deleteAuxiliar(p);
		}

		final Collection<Problem> problems = new HashSet<>(companyLogged.getProblems());
		for (final Problem p : problems)
			this.problemService.deleteAuxiliar(p);

		this.companyRepository.flush();
		this.companyRepository.delete(company);
	}

	// Other business methods
	public Company findCompanyByPositionId(final int positionId) {
		Company result;

		result = this.companyRepository.findCompanyByPositionId(positionId);

		return result;
	}

	public Company findCompanyByProblemId(final int problemId) {
		Company result;

		result = this.companyRepository.findCompanyByProblemId(problemId);

		return result;
	}

	public Company findCompanyByApplicationId(final int applicationId) {
		Company result;

		result = this.companyRepository.findCompanyByApplicationId(applicationId);

		return result;
	}

	public Collection<Company> findCompaniesByCurriculaId(final int curriculaId) {
		Collection<Company> result;

		result = this.companyRepository.findCompaniesByCurriculaId(curriculaId);

		return result;
	}

	public Collection<Company> findCompaniesWithAudits() {
		Collection<Company> result;

		result = this.companyRepository.findCompaniesWithAudits();

		return result;
	}


	// Reconstruct methods
	@Autowired
	private Validator	validator;


	public CompanyForm reconstruct(final CompanyForm companyForm, final BindingResult binding) {
		CompanyForm result;
		final Company company = companyForm.getActor();

		if (company.getId() == 0) {
			final Collection<Position> positions = new HashSet<>();
			final Collection<Problem> problems = new HashSet<>();
			final UserAccount userAccount = this.userAccountService.create();
			final Authority auth = new Authority();
			auth.setAuthority(Authority.COMPANY);
			userAccount.addAuthority(auth);
			userAccount.setUsername(companyForm.getActor().getUserAccount().getUsername());
			userAccount.setPassword(companyForm.getActor().getUserAccount().getPassword());
			company.setPositions(positions);
			company.setProblems(problems);
			company.setUserAccount(userAccount);
			companyForm.setActor(company);
		} else {
			final Company res = this.companyRepository.findOne(company.getId());
			Assert.notNull(res, "This entity does not exist");
			res.setName(company.getName());
			res.setSurnames(company.getSurnames());
			res.setVATNumber(company.getVATNumber());
			res.setCreditCard(company.getCreditCard());
			res.setPhoto(company.getPhoto());
			res.setEmail(company.getEmail());
			res.setPhoneNumber(company.getPhoneNumber());
			res.setAddress(company.getAddress());
			res.setCommercialName(company.getCommercialName());
			companyForm.setActor(res);
		}

		result = companyForm;

		this.validator.validate(result, binding);

		return result;
	}

	public void flush() {
		this.companyRepository.flush();
	}

}
