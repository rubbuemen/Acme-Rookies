
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ProviderRepository;
import domain.Provider;

@Service
@Transactional
public class ProviderService {

	// Managed repository
	@Autowired
	private ProviderRepository	providerRepository;

	// Supporting services
	@Autowired
	private ActorService		actorService;


	// Simple CRUD methods
	public Provider create() {
		Provider result;

		result = new Provider();

		return result;
	}

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

	public Provider save(final Provider provider) {
		Assert.notNull(provider);

		Provider result;

		result = this.providerRepository.save(provider);

		return result;
	}

	public void delete(final Provider provider) {
		Assert.notNull(provider);
		Assert.isTrue(provider.getId() != 0);
		Assert.isTrue(this.providerRepository.exists(provider.getId()));

		this.providerRepository.delete(provider);
	}


	// Other business methods

	// Reconstruct methods
	@Autowired
	private Validator	validator;


	public Provider reconstruct(final Provider provider, final BindingResult binding) {
		Provider result;

		if (provider.getId() == 0)
			result = provider;
		else {
			result = this.providerRepository.findOne(provider.getId());
			Assert.notNull(result, "This entity does not exist");
		}

		this.validator.validate(result, binding);

		return result;
	}

	public void flush() {
		this.providerRepository.flush();
	}

}
