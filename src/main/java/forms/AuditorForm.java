
package forms;

import javax.validation.Valid;

import domain.Auditor;

public class AuditorForm {

	// Attributes
	@Valid
	private Auditor	actor;
	private String	passwordCheck;
	private Boolean	termsConditions;


	// Constructors
	public AuditorForm() {
		super();
	}

	public AuditorForm(final Auditor actor) {
		this.actor = actor;
		this.passwordCheck = "";
		this.termsConditions = false;
	}

	// Getters and Setters
	public Auditor getActor() {
		return this.actor;
	}

	public void setActor(final Auditor actor) {
		this.actor = actor;
	}

	public String getPasswordCheck() {
		return this.passwordCheck;
	}

	public void setPasswordCheck(final String passwordCheck) {
		this.passwordCheck = passwordCheck;
	}

	public Boolean getTermsConditions() {
		return this.termsConditions;
	}

	public void setTermsConditions(final Boolean termsConditions) {
		this.termsConditions = termsConditions;
	}

}
