
package services;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import utilities.AbstractTest;
import domain.PersonalData;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class PersonalDataServiceTest extends AbstractTest {

	// SUT Services
	@Autowired
	private PersonalDataService	personalDataService;

	@PersistenceContext
	EntityManager				entityManager;


	/**
	 * @author Rub�n Bueno
	 *         Requisito funcional: 17.1
	 *         Caso de uso: editar un "PersonalData"
	 *         Tests positivos: 1
	 *         *** 1. Editar de un "PersonalData" correctamente
	 *         Tests negativos: 10
	 *         *** 1. Intento de edici�n de un "PersonalData" con una autoridad no permitida
	 *         *** 2. Intento de edici�n de un "PersonalData" que no es del "Rookie" logeado
	 *         *** 3. Intento de edici�n de un "PersonalData" con nombre de "PersonalData" vac�o
	 *         *** 4. Intento de edici�n de un "PersonalData" con estado de "PersonalData" vac�o
	 *         *** 5. Intento de edici�n de un "PersonalData" con n�mero de tel�fono de "PersonalData" vac�o
	 *         *** 6. Intento de edici�n de un "PersonalData" con enlace de GitHub de "PersonalData" vac�o
	 *         *** 7. Intento de edici�n de un "PersonalData" con enlace de GitHub de "PersonalData" que no es URL
	 *         *** 8. Intento de edici�n de un "PersonalData" con enlace de LinkedIn de "PersonalData" vac�o
	 *         *** 9. Intento de edici�n de un "PersonalData" con enlace de LinkedIn de "PersonalData" que no es URL
	 *         *** 10. Intento de edici�n de un "PersonalData" de un "Curricula" que es una copia
	 *         Analisis de cobertura de sentencias: 100% 55/55 instrucciones
	 *         Analisis de cobertura de datos: alto
	 */
	@Test
	public void driverEditPersonalData() {
		final Object testingData[][] = {
			{
				"rookie1", "personalData1", "nameTest", "statementTest", "phoneNumberTest", "http://www.gitHubProfilePersonalData.com", "http://www.linkedInPersonalData.com", null
			}, {
				"company1", "personalData1", "nameTest", "statementTest", "phoneNumberTest", "http://www.gitHubProfilePersonalData.com", "http://www.linkedInPersonalData.com", IllegalArgumentException.class
			}, {
				"rookie2", "personalData1", "nameTest", "statementTest", "phoneNumberTest", "http://www.gitHubProfilePersonalData.com", "http://www.linkedInPersonalData.com", IllegalArgumentException.class
			}, {
				"rookie1", "personalData1", "", "statementTest", "phoneNumberTest", "http://www.gitHubProfilePersonalData.com", "http://www.linkedInPersonalData.com", ConstraintViolationException.class
			}, {
				"rookie1", "personalData1", "nameTest", "", "phoneNumberTest", "http://www.gitHubProfilePersonalData.com", "http://www.linkedInPersonalData.com", ConstraintViolationException.class
			}, {
				"rookie1", "personalData1", "nameTest", "statementTest", "", "http://www.gitHubProfilePersonalData.com", "http://www.linkedInPersonalData.com", ConstraintViolationException.class
			}, {
				"rookie1", "personalData1", "nameTest", "statementTest", "phoneNumberTest", "", "http://www.linkedInPersonalData.com", ConstraintViolationException.class
			}, {
				"rookie1", "personalData1", "nameTest", "statementTest", "phoneNumberTest", "gitHubProfilePersonalData", "http://www.linkedInPersonalData.com", ConstraintViolationException.class
			}, {
				"rookie1", "personalData1", "nameTest", "statementTest", "phoneNumberTest", "http://www.gitHubProfilePersonalData.com", "", ConstraintViolationException.class
			}, {
				"rookie1", "personalData1", "nameTest", "statementTest", "phoneNumberTest", "http://www.gitHubProfilePersonalData.com", "linkedInPersonalData", ConstraintViolationException.class
			}, {
				"rookie1", "personalData5", "nameTest", "statementTest", "phoneNumberTest", "http://www.gitHubProfilePersonalData.com", "http://www.linkedInPersonalData.com", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.editPersonalDataTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(Class<?>) testingData[i][7]);
	}

	// Template methods ------------------------------------------------------

	protected void editPersonalDataTemplate(final String username, final String personalData, final String name, final String statement, final String phoneNumber, final String gitHubProfile, final String linkedInProfile, final Class<?> expected) {
		Class<?> caught = null;
		PersonalData personalDataEntity;

		super.startTransaction();

		try {
			super.authenticate(username);
			personalDataEntity = this.personalDataService.findPersonalDataRookieLogged(super.getEntityId(personalData));
			personalDataEntity.setName(name);
			personalDataEntity.setStatement(statement);
			personalDataEntity.setPhoneNumber(phoneNumber);
			personalDataEntity.setGitHubProfile(gitHubProfile);
			personalDataEntity.setLinkedInProfile(linkedInProfile);
			this.personalDataService.save(personalDataEntity);
			this.personalDataService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
			this.entityManager.clear();
		}

		this.checkExceptions(expected, caught);
		super.unauthenticate();
		super.rollbackTransaction();
	}
}
