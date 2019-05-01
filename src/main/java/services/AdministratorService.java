
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.AdministratorRepository;
import security.Authority;
import security.UserAccount;
import domain.Actor;
import domain.Administrator;
import domain.Company;
import domain.Position;
import domain.Provider;
import domain.Rookie;
import forms.AdministratorForm;

@Service
@Transactional
public class AdministratorService {

	// Managed repository
	@Autowired
	private AdministratorRepository	administratorRepository;

	// Supporting services
	@Autowired
	private ActorService			actorService;

	@Autowired
	private UserAccountService		userAccountService;


	// Simple CRUD methods
	// R11.1
	public Administrator create() {
		Administrator result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginAdministrator(actorLogged);

		result = new Administrator();
		final UserAccount userAccount = this.userAccountService.create();
		final Authority auth = new Authority();

		auth.setAuthority(Authority.ADMIN);
		userAccount.addAuthority(auth);
		result.setUserAccount(userAccount);

		return result;
	}

	public Collection<Administrator> findAll() {
		Collection<Administrator> result;

		result = this.administratorRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Administrator findOne(final int administratorId) {
		Assert.isTrue(administratorId != 0);
		Administrator result;

		result = this.administratorRepository.findOne(administratorId);
		Assert.notNull(result);

		return result;
	}

	// R8.2, R11.1
	public Administrator save(final Administrator administrator) {
		Assert.notNull(administrator);

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginAdministrator(actorLogged);

		Administrator result;

		result = (Administrator) this.actorService.save(administrator);
		result = this.administratorRepository.save(result);

		return result;
	}

	public void delete(final Administrator administrator) {
		Assert.notNull(administrator);
		Assert.isTrue(administrator.getId() != 0);
		Assert.isTrue(this.administratorRepository.exists(administrator.getId()));

		this.administratorRepository.delete(administrator);
	}

	// Other business methods
	//Queries Dashboard
	public String dashboardQueryC1() {
		String result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginAdministrator(actorLogged);

		result = this.administratorRepository.dashboardQueryC1();

		return result;
	}

	public String dashboardQueryC2() {
		String result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginAdministrator(actorLogged);

		result = this.administratorRepository.dashboardQueryC2();

		return result;
	}

	public Collection<Company> dashboardQueryC3() {
		Collection<Company> result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginAdministrator(actorLogged);

		result = this.administratorRepository.dashboardQueryC3();

		return result;
	}

	public Collection<Rookie> dashboardQueryC4() {
		Collection<Rookie> result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginAdministrator(actorLogged);

		result = this.administratorRepository.dashboardQueryC4();

		return result;
	}

	public String dashboardQueryC5() {
		String result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginAdministrator(actorLogged);

		result = this.administratorRepository.dashboardQueryC5();

		return result;
	}

	public Position dashboardQueryC6_1() {
		Position result = null;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginAdministrator(actorLogged);

		if (!this.administratorRepository.dashboardQueryC6_1().isEmpty())
			result = this.administratorRepository.dashboardQueryC6_1().iterator().next();

		return result;
	}

	public Position dashboardQueryC6_2() {
		Position result = null;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginAdministrator(actorLogged);

		if (!this.administratorRepository.dashboardQueryC6_2().isEmpty())
			result = this.administratorRepository.dashboardQueryC6_2().iterator().next();

		return result;
	}

	public String dashboardQueryB1() {
		String result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginAdministrator(actorLogged);

		result = this.administratorRepository.dashboardQueryB1();

		return result;
	}

	public String dashboardQueryB2() {
		String result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginAdministrator(actorLogged);

		result = this.administratorRepository.dashboardQueryB2();

		return result;
	}

	public String dashboardQueryB3() {
		String result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginAdministrator(actorLogged);

		result = this.administratorRepository.dashboardQueryB3();

		return result;
	}

	public String dashboardQueryAcmeRookiesC1() {
		String result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginAdministrator(actorLogged);

		result = this.administratorRepository.dashboardQueryAcmeRookiesC1();

		return result;
	}

	public String dashboardQueryAcmeRookiesC2() {
		String result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginAdministrator(actorLogged);

		result = this.administratorRepository.dashboardQueryAcmeRookiesC2();

		return result;
	}

	public Collection<Company> dashboardQueryAcmeRookiesC3() {
		Collection<Company> result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginAdministrator(actorLogged);

		result = this.administratorRepository.dashboardQueryAcmeRookiesC3();

		return result;
	}

	public String dashboardQueryAcmeRookiesC4() {
		String result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginAdministrator(actorLogged);

		result = this.administratorRepository.dashboardQueryAcmeRookiesC4();

		return result;
	}

	public String dashboardQueryAcmeRookiesB1() {
		String result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginAdministrator(actorLogged);

		result = this.administratorRepository.dashboardQueryAcmeRookiesB1();

		return result;
	}

	public Collection<Provider> dashboardQueryAcmeRookiesB2() {
		Collection<Provider> result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginAdministrator(actorLogged);

		result = this.administratorRepository.dashboardQueryAcmeRookiesB2();

		final List<Provider> resultList = new ArrayList<>(result);

		if (resultList.size() > 5)
			result = resultList.subList(0, 5);

		return result;
	}

	public String dashboardQueryAcmeRookiesA1() {
		String result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginAdministrator(actorLogged);

		result = this.administratorRepository.dashboardQueryAcmeRookiesA1();

		return result;
	}

	public String dashboardQueryAcmeRookiesA2() {
		String result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginAdministrator(actorLogged);

		result = this.administratorRepository.dashboardQueryAcmeRookiesA2();

		return result;
	}

	public Collection<Provider> dashboardQueryAcmeRookiesA3() {
		Collection<Provider> result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginAdministrator(actorLogged);

		result = this.administratorRepository.dashboardQueryAcmeRookiesA3();

		return result;
	}


	// Reconstruct methods
	@Autowired
	private Validator	validator;


	public AdministratorForm reconstruct(final AdministratorForm administratorForm, final BindingResult binding) {
		AdministratorForm result;
		final Administrator administrator = administratorForm.getActor();

		if (administrator.getId() == 0) {
			final UserAccount userAccount = this.userAccountService.create();
			final Authority auth = new Authority();
			auth.setAuthority(Authority.ADMIN);
			userAccount.addAuthority(auth);
			userAccount.setUsername(administratorForm.getActor().getUserAccount().getUsername());
			userAccount.setPassword(administratorForm.getActor().getUserAccount().getPassword());
			administrator.setUserAccount(userAccount);
			administratorForm.setActor(administrator);
		} else {
			final Administrator res = this.administratorRepository.findOne(administrator.getId());
			res.setName(administrator.getName());
			res.setSurnames(administrator.getSurnames());
			res.setVATNumber(administrator.getVATNumber());
			res.setCreditCard(administrator.getCreditCard());
			res.setPhoto(administrator.getPhoto());
			res.setEmail(administrator.getEmail());
			res.setPhoneNumber(administrator.getPhoneNumber());
			res.setAddress(administrator.getAddress());
			administratorForm.setActor(res);
		}

		result = administratorForm;

		this.validator.validate(result, binding);

		return result;
	}

	public void flush() {
		this.administratorRepository.flush();
	}

}
