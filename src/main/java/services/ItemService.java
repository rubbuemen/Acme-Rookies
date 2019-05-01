
package services;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ItemRepository;
import domain.Actor;
import domain.Item;
import domain.Provider;

@Service
@Transactional
public class ItemService {

	// Managed repository
	@Autowired
	private ItemRepository	itemRepository;

	// Supporting services
	@Autowired
	private ActorService	actorService;

	@Autowired
	private ProviderService	providerService;


	// Simple CRUD methods
	//R10.1 (Acme-Rookies)
	public Item create() {
		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginProvider(actorLogged);

		Item result;

		result = new Item();
		return result;
	}

	//R9.2 (Acme-Rookies)
	public Collection<Item> findAll() {
		Collection<Item> result;

		result = this.itemRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Item findOne(final int itemId) {
		Assert.isTrue(itemId != 0);

		Item result;

		result = this.itemRepository.findOne(itemId);
		Assert.notNull(result);

		return result;
	}

	//R10.1 (Acme-Rookies)
	public Item save(final Item item) {
		Assert.notNull(item);

		Item result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginProvider(actorLogged);

		final Provider providerLogged = (Provider) actorLogged;

		if (item.getId() == 0) {
			result = this.itemRepository.save(item);
			final Collection<Item> itemsProviderLogged = providerLogged.getItems();
			itemsProviderLogged.add(result);
			providerLogged.setItems(itemsProviderLogged);
			this.providerService.save(providerLogged);
		} else {
			final Provider providerOwner = this.providerService.findProviderByItemId(item.getId());
			Assert.isTrue(actorLogged.equals(providerOwner), "The logged actor is not the owner of this entity");
			result = this.itemRepository.save(item);
		}

		return result;
	}

	//R10.1 (Acme-Rookies)
	public void delete(final Item item) {
		Assert.notNull(item);
		Assert.isTrue(item.getId() != 0);
		Assert.isTrue(this.itemRepository.exists(item.getId()));

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginProvider(actorLogged);

		final Provider providerOwner = this.providerService.findProviderByItemId(item.getId());
		Assert.isTrue(actorLogged.equals(providerOwner), "The logged actor is not the owner of this entity");

		final Provider providerLogged = (Provider) actorLogged;

		final Collection<Item> itemsActorLogged = providerLogged.getItems();
		itemsActorLogged.remove(item);
		providerLogged.setItems(itemsActorLogged);
		this.providerService.save(providerLogged);

		this.itemRepository.delete(item);
	}

	public void deleteAuxiliar(final Item item) {
		Assert.notNull(item);
		Assert.isTrue(item.getId() != 0);
		Assert.isTrue(this.itemRepository.exists(item.getId()));

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);

		final Provider providerLogged = (Provider) actorLogged;

		final Collection<Item> itemsActorLogged = providerLogged.getItems();
		itemsActorLogged.remove(item);
		providerLogged.setItems(itemsActorLogged);
		this.providerService.save(providerLogged);

		this.itemRepository.delete(item);
	}

	// Other business methods
	public Collection<Item> findItemsByProviderId(final int providerId) {
		Collection<Item> result;

		result = this.itemRepository.findItemsByProviderId(providerId);
		Assert.notNull(result);

		return result;
	}

	public Map<Item, Provider> getMapItemProvider(final Collection<Item> items) {
		final Map<Item, Provider> result = new HashMap<>();

		if (items != null)
			for (final Item i : items) {
				final Provider provider = this.providerService.findProviderByItemId(i.getId());
				result.put(i, provider);
			}

		return result;
	}

	//R10.1 (Acme-Rookies)
	public Collection<Item> findItemsByProviderLogged() {
		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginProvider(actorLogged);

		Collection<Item> result;

		final Provider providerLogged = (Provider) actorLogged;

		result = this.itemRepository.findItemsByProviderId(providerLogged.getId());
		Assert.notNull(result);

		return result;
	}

	public Item findItemProviderLogged(final int itemId) {
		Assert.isTrue(itemId != 0);

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginProvider(actorLogged);

		final Provider providerOwner = this.providerService.findProviderByItemId(itemId);
		Assert.isTrue(actorLogged.equals(providerOwner), "The logged actor is not the owner of this entity");

		Item result;

		result = this.itemRepository.findOne(itemId);
		Assert.notNull(result);

		return result;
	}


	// Reconstruct methods
	@Autowired
	private Validator	validator;


	public Item reconstruct(final Item item, final BindingResult binding) {
		Item result;

		if (item.getId() == 0)
			result = item;
		else {
			result = this.itemRepository.findOne(item.getId());
			Assert.notNull(result, "This entity does not exist");
			result.setName(item.getName());
			result.setDescription(item.getDescription());
			result.setLink(item.getLink());
			result.setPicture(item.getPicture());
		}

		this.validator.validate(result, binding);

		this.itemRepository.flush();

		return result;
	}

	public void flush() {
		this.itemRepository.flush();
	}

}
