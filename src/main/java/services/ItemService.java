
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ItemRepository;
import domain.Item;

@Service
@Transactional
public class ItemService {

	// Managed repository
	@Autowired
	private ItemRepository	itemRepository;

	// Supporting services
	@Autowired
	private ActorService	actorService;


	// Simple CRUD methods
	public Item create() {
		Item result;

		result = new Item();

		return result;
	}

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

	public Item save(final Item item) {
		Assert.notNull(item);

		Item result;

		result = this.itemRepository.save(item);

		return result;
	}

	public void delete(final Item item) {
		Assert.notNull(item);
		Assert.isTrue(item.getId() != 0);
		Assert.isTrue(this.itemRepository.exists(item.getId()));

		this.itemRepository.delete(item);
	}


	// Other business methods

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
		}

		this.validator.validate(result, binding);

		return result;
	}

	public void flush() {
		this.itemRepository.flush();
	}

}
