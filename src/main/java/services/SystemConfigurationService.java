
package services;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.SystemConfigurationRepository;
import domain.Actor;
import domain.Audit;
import domain.Company;
import domain.Message;
import domain.SystemConfiguration;

@Service
@Transactional
public class SystemConfigurationService {

	// Managed repository
	@Autowired
	private SystemConfigurationRepository	systemConfigurationRepository;

	// Supporting services
	@Autowired
	private ActorService					actorService;

	@Autowired
	private MessageService					messageService;

	@Autowired
	private CompanyService					companyService;

	@Autowired
	private AuditService					auditService;


	// Simple CRUD methods
	public SystemConfiguration create() {
		SystemConfiguration result;

		result = new SystemConfiguration();

		return result;
	}

	public Collection<SystemConfiguration> findAll() {
		Collection<SystemConfiguration> result;

		result = this.systemConfigurationRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public SystemConfiguration findOne(final int systemConfigurationId) {
		Assert.isTrue(systemConfigurationId != 0);

		SystemConfiguration result;

		result = this.systemConfigurationRepository.findOne(systemConfigurationId);
		Assert.notNull(result);

		return result;
	}

	public SystemConfiguration save(final SystemConfiguration systemConfiguration) {
		Assert.notNull(systemConfiguration);

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginAdministrator(actorLogged);

		SystemConfiguration result;

		result = this.systemConfigurationRepository.save(systemConfiguration);

		return result;
	}

	public void delete(final SystemConfiguration systemConfiguration) {
		Assert.notNull(systemConfiguration);
		Assert.isTrue(systemConfiguration.getId() != 0);
		Assert.isTrue(this.systemConfigurationRepository.exists(systemConfiguration.getId()));

		this.systemConfigurationRepository.delete(systemConfiguration);
	}

	// Other business methods
	public SystemConfiguration getConfiguration() {
		SystemConfiguration result;

		result = this.systemConfigurationRepository.getConfiguration();
		Assert.notNull(result);

		return result;
	}

	private Pattern patternSpamWords() {
		final Collection<String> spamWords = this.getConfiguration().getSpamWords();
		String pattern = "";
		for (final String sw : spamWords)
			pattern = pattern + (sw + "|");
		pattern = pattern.substring(0, pattern.length() - 1);
		pattern = pattern + "$";
		final Pattern result = Pattern.compile(pattern);
		return result;
	}

	// R24.2
	public Map<Actor, Boolean> mapActorSpammer() {
		final Map<Actor, Boolean> result = new HashMap<>();

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginAdministrator(actorLogged);

		Matcher spamWordsSubject, spamWordsBody;
		Integer hasSpamCount = 0;

		final Collection<Actor> actors = this.actorService.findAll();
		final Actor system = this.actorService.getSystemActor();
		final Actor deletedUser = this.actorService.getDeletedActor();
		actors.remove(system);
		actors.remove(deletedUser);

		for (final Actor a : actors) {
			boolean isSpammer = false;
			final Collection<Message> messagesActor = this.messageService.findMessagesSentByActorId(a.getId());
			for (final Message m : messagesActor) {
				spamWordsSubject = this.patternSpamWords().matcher(m.getSubject().toLowerCase());
				spamWordsBody = this.patternSpamWords().matcher(m.getBody().toLowerCase());
				if (spamWordsSubject.find() || spamWordsBody.find())
					hasSpamCount++;
			}
			final Integer sizeMessages = messagesActor.size();
			Double spamPorcentMessages = 0.0;
			if (sizeMessages != 0)
				spamPorcentMessages = (double) (hasSpamCount / sizeMessages) * 100;
			if (spamPorcentMessages >= 10)
				isSpammer = true;
			result.put(a, isSpammer);
		}
		return result;
	}

	// R24.2
	public void computeSpammers() {
		Boolean res = null;

		final Map<Actor, Boolean> spammerMap = this.mapActorSpammer();

		final Collection<Actor> actors = this.actorService.findAll();
		final Actor system = this.actorService.getSystemActor();
		final Actor deletedUser = this.actorService.getDeletedActor();
		actors.remove(system);
		actors.remove(deletedUser);

		for (final Actor a : actors) {
			res = spammerMap.get(a);
			a.setIsSpammer(res);
			this.actorService.saveAuxiliar(a);
		}
	}

	// R4.1 (Acme-Rookies)
	public void notifyRebranding() {
		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginAdministrator(actorLogged);

		final SystemConfiguration systemConfiguration = this.systemConfigurationRepository.getConfiguration();

		Assert.isTrue(!systemConfiguration.getIsNotifiedRebrand(), "The notify rebranding process only can be runned once");

		final Message result = this.messageService.create();
		result.setSubject("Rebranding in the system");
		result.setBody("Dear user, you are notified that there has been a change of brand in the system so that you are informed of it.");
		result.getTags().add("REBRANDING");
		final Collection<Actor> actorsSystem = this.actorService.findAllActorsExceptLogged();
		result.setRecipients(actorsSystem);
		if (result.getRecipients().size() == 0) {
			final Actor system = this.actorService.getSystemActor();
			result.getRecipients().add(system);
		}
		this.messageService.save(result, true);
		systemConfiguration.setIsNotifiedRebrand(true);
		this.systemConfigurationRepository.save(systemConfiguration);
	}
	// R4.3 (Acme-Rookies)
	public Map<Company, Double> mapCompanyScore() {
		final Map<Company, Double> result = new HashMap<>();

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginAdministrator(actorLogged);

		final Collection<Company> companies = this.companyService.findCompaniesWithAudits();

		final Map<Company, Double> scoreAuditsCompanies = new HashMap<>();

		for (final Company c : companies) {
			final Collection<Audit> auditsCompany = this.auditService.findAuditsByCompanyId(c.getId());

			Double totalScoreAudits = 0.0;
			for (final Audit a : auditsCompany)
				totalScoreAudits = totalScoreAudits + a.getScore();

			totalScoreAudits = totalScoreAudits / auditsCompany.size();

			scoreAuditsCompanies.put(c, totalScoreAudits);
		}

		Double maxScoreCompanies = 0.0;
		Double minScoreCompanies = 0.0;

		if (!scoreAuditsCompanies.values().isEmpty()) {
			maxScoreCompanies = Collections.max(scoreAuditsCompanies.values());
			minScoreCompanies = Collections.min(scoreAuditsCompanies.values());
		}

		for (final Company c : scoreAuditsCompanies.keySet()) {
			final Double scoreCompany = scoreAuditsCompanies.get(c);
			Double lineaHomotheticTransformation = 0.0;
			if ((maxScoreCompanies - minScoreCompanies) != 0)
				lineaHomotheticTransformation = 0 + ((scoreCompany - minScoreCompanies) * (1 - 0) / (maxScoreCompanies - minScoreCompanies));
			final DecimalFormat formatDecimals = new DecimalFormat(".##");
			final DecimalFormatSymbols dfs = new DecimalFormatSymbols();
			dfs.setDecimalSeparator('.');
			formatDecimals.setDecimalFormatSymbols(dfs);
			lineaHomotheticTransformation = Double.valueOf(formatDecimals.format(lineaHomotheticTransformation));
			result.put(c, lineaHomotheticTransformation);
		}

		return result;
	}

	// R4.3 (Acme-Rookies)
	public void computeScore() {
		Double res = null;

		final Map<Company, Double> scoresMap = this.mapCompanyScore();

		final Collection<Company> companies = this.companyService.findCompaniesWithAudits();

		for (final Company c : companies) {
			res = scoresMap.get(c);
			c.setScore(res);
			this.companyService.saveForComputes(c);
		}
	}


	// Reconstruct methods
	@Autowired
	private Validator	validator;


	public SystemConfiguration reconstruct(final SystemConfiguration systemConfiguration, final BindingResult binding) {
		SystemConfiguration result;

		result = this.systemConfigurationRepository.findOne(systemConfiguration.getId());
		Assert.notNull(result, "This entity does not exist");
		result.setNameSystem(systemConfiguration.getNameSystem());
		result.setBannerUrl(systemConfiguration.getBannerUrl());
		result.setWelcomeMessageEnglish(systemConfiguration.getWelcomeMessageEnglish());
		result.setWelcomeMessageSpanish(systemConfiguration.getWelcomeMessageSpanish());
		result.setPhoneCountryCode(systemConfiguration.getPhoneCountryCode());
		result.setPeriodFinder(systemConfiguration.getPeriodFinder());
		result.setMaxResultsFinder(systemConfiguration.getMaxResultsFinder());
		result.setSpamWords(systemConfiguration.getSpamWords());
		result.setFare(systemConfiguration.getFare());
		result.setVATPercentage(systemConfiguration.getVATPercentage());

		this.validator.validate(result, binding);

		return result;
	}

	public void flush() {
		this.systemConfigurationRepository.flush();
	}

}
