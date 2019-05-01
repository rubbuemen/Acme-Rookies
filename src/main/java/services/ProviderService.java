
package services;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ProviderRepository;
import security.Authority;
import security.UserAccount;
import domain.Actor;
import domain.Item;
import domain.Position;
import domain.Provider;
import domain.Sponsorship;
import forms.ProviderForm;

@Service
@Transactional
public class ProviderService {

	// Managed repository
	@Autowired
	private ProviderRepository	providerRepository;

	// Supporting services
	@Autowired
	private UserAccountService	userAccountService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private ItemService			itemService;

	@Autowired
	private SponsorshipService	sponsorshipService;


	// Simple CRUD methods
	// R9.3 (Acme-Rookies)
	public Provider create() {
		Provider result;

		result = new Provider();
		final Collection<Item> items = new HashSet<>();
		final Collection<Sponsorship> sponsorships = new HashSet<>();
		final UserAccount userAccount = this.userAccountService.create();
		final Authority auth = new Authority();

		auth.setAuthority(Authority.PROVIDER);
		userAccount.addAuthority(auth);
		result.setItems(items);
		result.setSponsorships(sponsorships);
		result.setUserAccount(userAccount);

		return result;
	}

	//R9.1 (Acme-Rookies)
	public Collection<Provider> findAll() {
		Collection<Provider> result;

		result = this.providerRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Provider findOne(final int providerId) {
		Assert.isTrue(providerId != 0);

		Provider result;

		result = this.providerRepository.findOne(providerId);
		Assert.notNull(result);

		return result;
	}

	// R9.3 (Acme-Rookies)
	public Provider save(final Provider provider) {
		Assert.notNull(provider);

		Provider result;

		result = (Provider) this.actorService.save(provider);
		result = this.providerRepository.save(result);

		return result;
	}

	public Provider saveAuxiliar(final Provider provider) {
		Assert.notNull(provider);

		Provider result;

		result = this.providerRepository.save(provider);

		return result;
	}

	public void delete(final Provider provider) {
		Assert.notNull(provider);
		Assert.isTrue(provider.getId() != 0);
		Assert.isTrue(this.providerRepository.exists(provider.getId()));

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginProvider(actorLogged);

		final Provider providerLogged = (Provider) actorLogged;

		this.actorService.deleteEntities(providerLogged);

		final Collection<Item> items = new HashSet<>(providerLogged.getItems());
		final Collection<Sponsorship> sponsorships = new HashSet<>(providerLogged.getSponsorships());
		for (final Item i : items)
			this.itemService.deleteAuxiliar(i);

		for (final Sponsorship s : sponsorships) {
			final Position p = s.getPosition();
			p.getSponsorships().remove(s);
			providerLogged.getSponsorships().remove(s);
			this.sponsorshipService.delete(s);
		}

		this.providerRepository.flush();
		this.providerRepository.delete(provider);
	}

	// Other business methods
	public Provider findProviderByItemId(final int itemId) {
		Provider result;

		result = this.providerRepository.findProviderByItemId(itemId);

		return result;
	}

	public Provider findProviderBySponsorshipId(final int sponsorshipId) {
		Provider result;

		result = this.providerRepository.findProviderBySponsorshipId(sponsorshipId);

		return result;
	}

	public Collection<Provider> findProvidersByPositionId(final int positionId) {
		Collection<Provider> result;

		result = this.providerRepository.findProvidersByPositionId(positionId);

		return result;
	}


	// Reconstruct methods
	@Autowired
	private Validator	validator;


	public ProviderForm reconstruct(final ProviderForm providerForm, final BindingResult binding) {
		ProviderForm result;
		final Provider provider = providerForm.getActor();

		if (provider.getId() == 0) {
			final Collection<Item> items = new HashSet<>();
			final Collection<Sponsorship> sponsorships = new HashSet<>();
			final UserAccount userAccount = this.userAccountService.create();
			final Authority auth = new Authority();
			auth.setAuthority(Authority.PROVIDER);
			userAccount.addAuthority(auth);
			userAccount.setUsername(providerForm.getActor().getUserAccount().getUsername());
			userAccount.setPassword(providerForm.getActor().getUserAccount().getPassword());
			provider.setItems(items);
			provider.setSponsorships(sponsorships);
			provider.setUserAccount(userAccount);
			providerForm.setActor(provider);
		} else {
			final Provider res = this.providerRepository.findOne(provider.getId());
			Assert.notNull(res, "This entity does not exist");
			res.setName(provider.getName());
			res.setSurnames(provider.getSurnames());
			res.setVATNumber(provider.getVATNumber());
			res.setCreditCard(provider.getCreditCard());
			res.setPhoto(provider.getPhoto());
			res.setEmail(provider.getEmail());
			res.setPhoneNumber(provider.getPhoneNumber());
			res.setAddress(provider.getAddress());
			res.setMake(provider.getMake());
			providerForm.setActor(res);
		}

		result = providerForm;

		this.validator.validate(result, binding);

		return result;
	}

	public void flush() {
		this.providerRepository.flush();
	}

}
