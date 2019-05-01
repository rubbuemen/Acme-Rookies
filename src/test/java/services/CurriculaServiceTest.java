
package services;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Curricula;
import domain.PersonalData;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CurriculaServiceTest extends AbstractTest {

	// SUT Services
	@Autowired
	private CurriculaService	curriculaService;

	@Autowired
	private PersonalDataService	personalDataService;

	@PersistenceContext
	EntityManager				entityManager;


	/**
	 * @author Rub�n Bueno
	 *         Requisito funcional: 17.1 (Acme-Hacker-Rank)
	 *         Caso de uso: listar "Curriculas" del "Rookie" logeado
	 *         Tests positivos: 1
	 *         *** 1. Listar "Curriculas" del "Rookie" logeado correctamente
	 *         Tests negativos: 1
	 *         *** 1. Intento de listar "Curriculas" con una autoridad no permitida
	 *         Analisis de cobertura de sentencias: 100% 23/23 instrucciones
	 *         Analisis de cobertura de datos: alto
	 */
	@Test
	public void driverListCurriculaAuthenticated() {

		final Object testingData[][] = {
			{
				"rookie1", null
			}, {
				"company1", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.listCurriculaAuthenticatedTemplate((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	/**
	 * @author Rub�n Bueno
	 *         Requisito funcional: 17.1 (Acme-Hacker-Rank)
	 *         Caso de uso: crear un "Curricula"
	 *         Tests positivos: 1
	 *         *** 1. Creaci�n de un "Curricula" correctamente
	 *         Tests negativos: 8
	 *         *** 1. Intento de creaci�n de un "Curricula" con una autoridad no permitida
	 *         *** 2. Intento de creaci�n de un "Curricula" con nombre de "Personal Data" vac�o
	 *         *** 3. Intento de creaci�n de un "Curricula" con estado de "Personal Data" vac�o
	 *         *** 4. Intento de creaci�n de un "Curricula" con n�mero de tel�fono de "Personal Data" vac�o
	 *         *** 5. Intento de creaci�n de un "Curricula" con enlace de GitHub de "Personal Data" vac�o
	 *         *** 6. Intento de creaci�n de un "Curricula" con enlace de GitHub de "Personal Data" que no es URL
	 *         *** 7. Intento de creaci�n de un "Curricula" con enlace de LinkedIn de "Personal Data" vac�o
	 *         *** 8. Intento de creaci�n de un "Curricula" con enlace de LinkedIn de "Personal Data" que no es URL
	 *         Analisis de cobertura de sentencias: 100% 112/112 instrucciones
	 *         Analisis de cobertura de datos: alto
	 */

	@Test
	public void driverCreateCurricula() {
		final Object testingData[][] = {
			{
				"rookie1", "namePersonalData", "statementPersonalData", "phoneNumberPersonalData", "http://www.gitHubProfilePersonalData.com", "http://www.linkedInPersonalData.com", null
			}, {
				"company1", "namePersonalData", "statementPersonalData", "phoneNumberPersonalData", "http://www.gitHubProfilePersonalData.com", "http://www.linkedInPersonalData.com", IllegalArgumentException.class
			}, {
				"rookie1", "", "statementPersonalData", "phoneNumberPersonalData", "http://www.gitHubProfilePersonalData.com", "http://www.linkedInPersonalData.com", ConstraintViolationException.class
			}, {
				"rookie1", "namePersonalData", "", "phoneNumberPersonalData", "http://www.gitHubProfilePersonalData.com", "http://www.linkedInPersonalData.com", ConstraintViolationException.class
			}, {
				"rookie1", "namePersonalData", "statementPersonalData", "", "http://www.gitHubProfilePersonalData.com", "http://www.linkedInPersonalData.com", ConstraintViolationException.class
			}, {
				"rookie1", "namePersonalData", "statementPersonalData", "phoneNumberPersonalData", "", "http://www.linkedInPersonalData.com", ConstraintViolationException.class
			}, {
				"rookie1", "namePersonalData", "statementPersonalData", "phoneNumberPersonalData", "gitHubProfilePersonalData", "http://www.linkedInPersonalData.com", ConstraintViolationException.class
			}, {
				"rookie1", "namePersonalData", "statementPersonalData", "phoneNumberPersonalData", "http://www.gitHubProfilePersonalData.com", "", ConstraintViolationException.class
			}, {
				"rookie1", "namePersonalData", "statementPersonalData", "phoneNumberPersonalData", "http://www.gitHubProfilePersonalData.com", "linkedInPersonalData", ConstraintViolationException.class
			},
		};

		for (int i = 0; i < testingData.length; i++)
			this.createCurriculaTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (Class<?>) testingData[i][6]);
	}

	/**
	 * @author Rub�n Bueno
	 *         Requisito funcional: 17.1 (Acme-Hacker-Rank)
	 *         Caso de uso: eliminar un "Curricula"
	 *         Tests positivos: 1
	 *         *** 1. Eliminar un "Curricula" correctamente
	 *         Tests negativos: 3
	 *         *** 1. Intento de eliminaci�n de un "Curricula" con una autoridad no permitida
	 *         *** 2. Intento de eliminaci�n de un "Curricula" que no es del "Rookie" logeado
	 *         *** 3. Intento de eliminaci�n de un "Curricula" que es una copia
	 *         Analisis de cobertura de sentencias: 97.5% 39/40 instrucciones
	 *         Analisis de cobertura de datos: alto
	 */
	@Test
	public void driverDeleteCurricula() {
		final Object testingData[][] = {
			{
				"rookie1", "curricula1", null
			}, {
				"company1", "curricula1", IllegalArgumentException.class
			}, {
				"rookie2", "curricula1", IllegalArgumentException.class
			}, {
				"rookie1", "curricula5", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.deleteCurriculaTemplate((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	// Template methods ------------------------------------------------------

	protected void listCurriculaAuthenticatedTemplate(final String username, final Class<?> expected) {
		Class<?> caught = null;
		Collection<Curricula> curriculas;

		super.startTransaction();

		try {
			super.authenticate(username);
			curriculas = this.curriculaService.findCurriculasByRookieLogged();
			Assert.notNull(curriculas);
		} catch (final Throwable oops) {
			caught = oops.getClass();
			this.entityManager.clear();
		}

		this.checkExceptions(expected, caught);
		super.unauthenticate();
		super.rollbackTransaction();
	}

	protected void createCurriculaTemplate(final String username, final String name, final String statement, final String phoneNumber, final String gitHubProfile, final String linkedInProfile, final Class<?> expected) {
		Class<?> caught = null;
		Curricula curricula;
		PersonalData personalData;

		super.startTransaction();

		try {
			super.authenticate(username);
			curricula = this.curriculaService.create();
			personalData = curricula.getPersonalData();
			personalData.setName(name);
			personalData.setStatement(statement);
			personalData.setPhoneNumber(phoneNumber);
			personalData.setGitHubProfile(gitHubProfile);
			personalData.setLinkedInProfile(linkedInProfile);
			personalData = this.personalDataService.save(personalData);
			this.personalDataService.flush();
			this.curriculaService.save(curricula);
		} catch (final Throwable oops) {
			caught = oops.getClass();
			this.entityManager.clear();
		}

		this.checkExceptions(expected, caught);
		super.unauthenticate();
		super.rollbackTransaction();
	}

	protected void deleteCurriculaTemplate(final String username, final String curricula, final Class<?> expected) {
		Class<?> caught = null;
		Curricula curriculaEntity;

		super.startTransaction();

		try {
			super.authenticate(username);
			curriculaEntity = this.curriculaService.findCurriculaRookieLogged(super.getEntityId(curricula));
			this.curriculaService.delete(curriculaEntity);
			this.curriculaService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
			this.entityManager.clear();
		}

		this.checkExceptions(expected, caught);
		super.unauthenticate();
		super.rollbackTransaction();
	}

}
