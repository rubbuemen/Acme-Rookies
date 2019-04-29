
package forms;

import javax.validation.Valid;

import domain.Rookie;

public class RookieForm {

	// Attributes
	@Valid
	private Rookie	actor;
	private String	passwordCheck;
	private Boolean	termsConditions;


	// Constructors
	public RookieForm() {
		super();
	}

	public RookieForm(final Rookie actor) {
		this.actor = actor;
		this.passwordCheck = "";
		this.termsConditions = false;
	}

	// Getters and Setters
	public Rookie getActor() {
		return this.actor;
	}

	public void setActor(final Rookie actor) {
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
