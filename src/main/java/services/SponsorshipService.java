
package services;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Locale;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.SponsorshipRepository;
import domain.Actor;
import domain.CreditCard;
import domain.Message;
import domain.Position;
import domain.Provider;
import domain.Sponsorship;

@Service
@Transactional
public class SponsorshipService {

	// Managed repository
	@Autowired
	private SponsorshipRepository		sponsorshipRepository;

	// Supporting services
	@Autowired
	private ActorService				actorService;

	@Autowired
	private ProviderService				providerService;

	@Autowired
	private PositionService				positionService;

	@Autowired
	private MessageService				messageService;

	@Autowired
	private SystemConfigurationService	systemConfigurationService;


	// Simple CRUD methods
	// R13.1 (Acme-Rookies)
	public Sponsorship create() {
		Sponsorship result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginProvider(actorLogged);

		result = new Sponsorship();

		result.setProvider((Provider) actorLogged);

		return result;
	}
	public Collection<Sponsorship> findAll() {
		Collection<Sponsorship> result;

		result = this.sponsorshipRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Sponsorship findOne(final int sponsorshipId) {
		Assert.isTrue(sponsorshipId != 0);

		Sponsorship result;

		result = this.sponsorshipRepository.findOne(sponsorshipId);
		Assert.notNull(result);

		return result;
	}

	// R13.1 (Acme-Rookies)
	public Sponsorship save(final Sponsorship sponsorship) {
		Assert.notNull(sponsorship);

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginProvider(actorLogged);

		final Provider providerLogged = (Provider) actorLogged;

		Sponsorship result;

		if (!sponsorship.getCreditCard().getNumber().isEmpty())
			Assert.isTrue(this.isNumeric(sponsorship.getCreditCard().getNumber()), "Invalid credit card");
		if (sponsorship.getCreditCard().getExpirationYear() != null && sponsorship.getCreditCard().getExpirationMonth() != null && sponsorship.getCreditCard().getExpirationYear() >= 0)
			Assert.isTrue(this.checkCreditCard(sponsorship.getCreditCard()), "Expired credit card");

		Assert.isTrue(sponsorship.getPosition().getIsFinalMode(), "To sponsor a position, it must be in final mode");

		if (sponsorship.getId() == 0) {
			result = this.sponsorshipRepository.save(sponsorship);
			final Position positionSponsorship = result.getPosition();
			final Collection<Sponsorship> sponsorshipsPosition = positionSponsorship.getSponsorships();
			sponsorshipsPosition.add(result);
			positionSponsorship.setSponsorships(sponsorshipsPosition);
			this.positionService.saveAuxiliar(positionSponsorship);
			final Collection<Sponsorship> sponsorshipsProvider = providerLogged.getSponsorships();
			sponsorshipsProvider.add(result);
			providerLogged.setSponsorships(sponsorshipsProvider);
			this.providerService.save(providerLogged);
		} else {
			final Provider providerOwner = this.providerService.findProviderBySponsorshipId(sponsorship.getId());
			Assert.isTrue(actorLogged.equals(providerOwner), "The logged actor is not the owner of this entity");
			result = this.sponsorshipRepository.save(sponsorship);
		}

		return result;
	}

	// R13.1 (Acme-Rookies)
	public void delete(final Sponsorship sponsorship) {
		Assert.notNull(sponsorship);
		Assert.isTrue(sponsorship.getId() != 0);
		Assert.isTrue(this.sponsorshipRepository.exists(sponsorship.getId()));

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginProvider(actorLogged);

		final Provider providerLogged = (Provider) actorLogged;

		final Provider providerOwner = this.providerService.findProviderBySponsorshipId(sponsorship.getId());
		Assert.isTrue(actorLogged.equals(providerOwner), "The logged actor is not the owner of this entity");

		final Position positionSponsorship = sponsorship.getPosition();
		final Collection<Sponsorship> sponsorshipsPosition = positionSponsorship.getSponsorships();
		sponsorshipsPosition.remove(sponsorship);
		positionSponsorship.setSponsorships(sponsorshipsPosition);
		this.positionService.saveAuxiliar(positionSponsorship);
		final Collection<Sponsorship> sponsorshipsProvider = providerLogged.getSponsorships();
		sponsorshipsProvider.remove(sponsorship);
		providerLogged.setSponsorships(sponsorshipsProvider);
		this.providerService.save(providerLogged);

		this.sponsorshipRepository.delete(sponsorship);
	}

	public void deleteAuxiliar(final Sponsorship sponsorship) {
		Assert.notNull(sponsorship);
		Assert.isTrue(sponsorship.getId() != 0);
		Assert.isTrue(this.sponsorshipRepository.exists(sponsorship.getId()));

		this.sponsorshipRepository.delete(sponsorship);
	}

	// Other business methods
	// R13.1 (Acme-Rookies)
	public Collection<Sponsorship> findSponsorshipsByProviderLogged() {
		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginProvider(actorLogged);

		Collection<Sponsorship> result;

		final Provider providerLogged = (Provider) actorLogged;

		result = this.sponsorshipRepository.findSponsorshipsByProviderId(providerLogged.getId());
		Assert.notNull(result);

		return result;
	}

	public Sponsorship findSponsorshipProviderLogged(final int sponsorshipId) {
		Assert.isTrue(sponsorshipId != 0);

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginProvider(actorLogged);

		final Provider providerOwner = this.providerService.findProviderBySponsorshipId(sponsorshipId);
		Assert.isTrue(actorLogged.equals(providerOwner), "The logged actor is not the owner of this entity");

		Sponsorship result;

		result = this.sponsorshipRepository.findOne(sponsorshipId);
		Assert.notNull(result);

		return result;
	}

	public boolean isNumeric(final String cadena) {

		boolean resultado;

		try {
			Long.parseLong(cadena);
			resultado = true;
		} catch (final NumberFormatException excepcion) {
			resultado = false;
		}

		return resultado;
	}

	public boolean checkCreditCard(final CreditCard creditCard) {
		boolean result;
		Calendar calendar;
		int actualYear, actualMonth;

		result = false;
		calendar = new GregorianCalendar();
		actualYear = calendar.get(Calendar.YEAR);
		actualMonth = calendar.get(Calendar.MONTH) + 1;
		actualYear = actualYear % 100;
		if (creditCard.getExpirationYear() > actualYear)
			result = true;
		else if (creditCard.getExpirationYear() == actualYear && creditCard.getExpirationMonth() >= actualMonth)
			result = true;
		return result;
	}

	public Collection<Sponsorship> findSponsorshipsByPositionId(final int positionId) {
		final Collection<Sponsorship> result;

		result = this.sponsorshipRepository.findSponsorshipsByPositionId(positionId);
		Assert.notNull(result);

		return result;
	}

	//R15 (Acme-Rookies)
	public Sponsorship findRandomSponsorship(final int positionId) {
		Sponsorship result = null;

		final Random r = new Random();
		final Collection<Sponsorship> sponsorships = this.findSponsorshipsByPositionId(positionId);
		if (!sponsorships.isEmpty()) {
			final int i = r.nextInt(sponsorships.size());
			result = (Sponsorship) sponsorships.toArray()[i];

			final Provider provider = result.getProvider();
			final Message message = this.messageService.createAuxiliar();
			final Double fare = this.systemConfigurationService.getConfiguration().getFare();
			final Double vatPercentage = this.systemConfigurationService.getConfiguration().getVATPercentage();
			Double total = fare + (fare * (vatPercentage / 100));
			final DecimalFormat formatDecimals = new DecimalFormat(".##");
			final DecimalFormatSymbols dfs = new DecimalFormatSymbols();
			dfs.setDecimalSeparator('.');
			formatDecimals.setDecimalFormatSymbols(dfs);
			total = Double.valueOf(formatDecimals.format(total));

			final Locale locale = LocaleContextHolder.getLocale();
			if (locale.getLanguage().equals("es")) {
				message.setSubject("Se le ha realizado un cargo en la tarjeta de crédito");
				message.setBody("Se le ha realizado un cargo de " + total + " [" + fare + " + IVA (" + vatPercentage + "%)] en la tarjeta de crédito con número " + result.getCreditCard().getNumber());
			} else {
				message.setSubject("A charge has been made to your credit card");
				message.setBody("You have been charged " + total + " [" + fare + " + VAT (" + vatPercentage + "%)] on the credit card number " + result.getCreditCard().getNumber());
			}

			final Actor sender = this.actorService.getSystemActor();
			message.setSender(sender);

			final Collection<Actor> recipients = new HashSet<>();
			recipients.add(provider);
			message.setRecipients(recipients);
			this.messageService.saveAuxiliar(message);
		}

		return result;
	}


	// Reconstruct methods
	@Autowired
	private Validator	validator;


	public Sponsorship reconstruct(final Sponsorship sponsorship, final BindingResult binding) {
		Sponsorship result;

		if (sponsorship.getId() == 0) {
			final Actor actorLogged = this.actorService.findActorLogged();
			sponsorship.setProvider((Provider) actorLogged);
			result = sponsorship;
		} else {
			final Sponsorship originalSponsorship = this.sponsorshipRepository.findOne(sponsorship.getId());
			Assert.notNull(originalSponsorship, "This entity does not exist");
			result = sponsorship;
			result.setProvider(originalSponsorship.getProvider());
			result.setPosition(originalSponsorship.getPosition());
		}

		this.validator.validate(result, binding);

		return result;
	}
	public void flush() {
		this.sponsorshipRepository.flush();
	}

}
