
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

import cz.jirutka.validator.collection.constraints.EachNotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Provider extends Actor {

	// Attributes
	private String	make;


	// Getters and Setters
	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getMake() {
		return this.make;
	}

	public void setMake(final String make) {
		this.make = make;
	}


	// Relationships
	private Collection<Item>		items;
	private Collection<Sponsorship>	sponsorships;


	@Valid
	@EachNotNull
	@OneToMany
	public Collection<Item> getItems() {
		return this.items;
	}

	public void setItems(final Collection<Item> items) {
		this.items = items;
	}

	@Valid
	@EachNotNull
	@OneToMany(mappedBy = "provider")
	public Collection<Sponsorship> getSponsorships() {
		return this.sponsorships;
	}

	public void setSponsorships(final Collection<Sponsorship> sponsorships) {
		this.sponsorships = sponsorships;
	}

}
