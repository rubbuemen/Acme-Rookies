
package forms;

import javax.validation.Valid;

import domain.Provider;

public class ProviderForm {

	// Attributes
	@Valid
	private Provider	actor;
	private String		passwordCheck;
	private Boolean		termsConditions;


	// Constructors
	public ProviderForm() {
		super();
	}

	public ProviderForm(final Provider actor) {
		this.actor = actor;
		this.passwordCheck = "";
		this.termsConditions = false;
	}

	// Getters and Setters
	public Provider getActor() {
		return this.actor;
	}

	public void setActor(final Provider actor) {
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
