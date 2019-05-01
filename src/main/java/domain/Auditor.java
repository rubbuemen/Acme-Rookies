
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;

import cz.jirutka.validator.collection.constraints.EachNotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Auditor extends Actor {

	// Relationships
	private Collection<Audit>	audits;


	@Valid
	@EachNotNull
	@OneToMany
	public Collection<Audit> getAudits() {
		return this.audits;
	}

	public void setAudits(final Collection<Audit> audits) {
		this.audits = audits;
	}

}
