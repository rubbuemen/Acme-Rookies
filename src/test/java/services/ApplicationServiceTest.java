
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
import domain.Application;
import domain.Curricula;
import domain.Position;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ApplicationServiceTest extends AbstractTest {

	// SUT Services
	@Autowired
	private ApplicationService	applicationService;

	@Autowired
	private PositionService		positionService;

	@Autowired
	private CurriculaService	curriculaService;

	@Autowired
	private ProblemService		problemService;

	@PersistenceContext
	EntityManager				entityManager;


	/**
	 * @author Rub�n Bueno
	 *         Requisito funcional: 9.3
	 *         Caso de uso: listar "Applications" logeado como "Company"
	 *         Tests positivos: 1
	 *         *** 1. Listar "Applications" correctamente logeado como "Company"
	 *         Tests negativos: 1
	 *         *** 1. Intento de listar "Applications" con una autoridad no permitida
	 *         Analisis de cobertura de sentencias: 100% 23/23 instrucciones
	 *         Analisis de cobertura de datos: alto
	 */
	@Test
	public void driverListApplicationsCompany() {
		final Object testingData[][] = {
			{
				"company1", null
			}, {
				"rookie1", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.listApplicationsCompanyTemplate((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	/**
	 * @author Rub�n Bueno
	 *         Requisito funcional: 9.3
	 *         Caso de uso: aceptar una "Application"
	 *         Tests positivos: 1
	 *         *** 1. Aceptar una "Application" correctamente
	 *         Tests negativos: 3
	 *         *** 1. Intento de aceptar una "Application" con una autoridad no permitida
	 *         *** 2. Intento de aceptar una "Application" sin tener permisos para ello
	 *         *** 3. Intento de aceptar una "Application" que no tiene el estado "submitted"
	 *         Analisis de cobertura de sentencias: 83,3% 125/150 instrucciones
	 *         Analisis de cobertura de datos: alto
	 */
	@Test
	public void driverAcceptApplicationsCompany() {

		final Object testingData[][] = {
			{
				"company1", "application3", null
			}, {
				"rookie1", "application3", IllegalArgumentException.class
			}, {
				"company2", "application3", IllegalArgumentException.class
			}, {
				"company1", "application1", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.acceptApplicationsCompanyTemplate((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	/**
	 * @author Rub�n Bueno
	 *         Requisito funcional: 9.3
	 *         Caso de uso: rechazar una "Application"
	 *         Tests positivos: 1
	 *         *** 1. Rechazar una "Application" correctamente
	 *         Tests negativos: 3
	 *         *** 1. Intento de rechazar una "Application" con una autoridad no permitida
	 *         *** 2. Intento de rechazar una "Application" sin tener permisos para ello
	 *         *** 3. Intento de rechazar una "Application" que no tiene el estado "submitted"
	 *         Analisis de cobertura de sentencias: 82,6% 119/144 instrucciones
	 *         Analisis de cobertura de datos: alto
	 */
	@Test
	public void driverRejectApplicationsCompany() {

		final Object testingData[][] = {
			{
				"company1", "application3", null
			}, {
				"rookie1", "application3", IllegalArgumentException.class
			}, {
				"company2", "application3", IllegalArgumentException.class
			}, {
				"company1", "application1", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.rejectApplicationsCompanyTemplate((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	/**
	 * @author Rub�n Bueno
	 *         Requisito funcional: 10.1
	 *         Caso de uso: listar "Applications" logeado como "Rookie"
	 *         Tests positivos: 1
	 *         *** 1. Listar "Applications" correctamente logeado como "Rookie"
	 *         Tests negativos: 1
	 *         *** 1. Intento de listar "Applications" con una autoridad no permitida
	 *         Analisis de cobertura de sentencias: 100% 23/23 instrucciones
	 *         Analisis de cobertura de datos: alto
	 */
	@Test
	public void driverListApplicationsRookie() {
		final Object testingData[][] = {
			{
				"rookie1", null
			}, {
				"company1", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.listApplicationsRookieTemplate((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	/**
	 * @author Rub�n Bueno
	 *         Requisito funcional: 10.1
	 *         Caso de uso: crear una "Application"
	 *         Tests positivos: 1
	 *         *** 1. Crear una "Application" correctamente
	 *         Tests negativos: 3
	 *         *** 1. Intento de creaci�n de una "Application" con una autoridad no permitida
	 *         *** 2. Intento de creaci�n de una "Application" seleccionando una "Position" no disponible
	 *         *** 3. Intento de creaci�n de una "Application" seleccionando un "Curricula" que no es tuyo
	 *         Analisis de cobertura de sentencias: 84,7% 265/307 instrucciones
	 *         Analisis de cobertura de datos: alto
	 */
	@Test
	public void driverCreateApplication() {
		final Object testingData[][] = {
			{
				"rookie1", "position4", "curricula1", null
			}, {
				"company1", "position4", "curricula1", IllegalArgumentException.class
			}, {
				"rookie1", "position1", "curricula1", IllegalArgumentException.class
			}, {
				"rookie1", "position4", "curricula3", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.createApplicationTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
	}

	/**
	 * @author Rub�n Bueno
	 *         Requisito funcional: 10.1
	 *         Caso de uso: resolver una "Application"
	 *         Tests positivos: 1
	 *         *** 1. Resolver una "Application" correctamente
	 *         Tests negativos: 5
	 *         *** 1. Intento de resoluci�n de una "Application" con una autoridad no permitida
	 *         *** 2. Intento de resoluci�n de una "Application" que no es del "Rookie" logeado
	 *         *** 3. Intento de resoluci�n de una "Application" con explicaciones vac�o
	 *         *** 4. Intento de resoluci�n de una "Application" con enlace de c�digo vac�o
	 *         *** 5. Intento de resoluci�n de una "Application" con enlace de c�digo que no es URL
	 *         Analisis de cobertura de sentencias: 84,7% 232/274 instrucciones
	 *         Analisis de cobertura de datos: alto
	 */
	@Test
	public void driverSubmitApplication() {
		final Object testingData[][] = {
			{
				"rookie1", "application1", "testExplications", "http://www.testCodeLink.com", null
			}, {
				"company1", "application1", "testExplications", "http://www.testCodeLink.com", IllegalArgumentException.class
			}, {
				"rookie2", "application1", "testExplications", "http://www.testCodeLink.com", IllegalArgumentException.class
			}, {
				"rookie1", "application1", "", "http://www.testCodeLink.com", IllegalArgumentException.class
			}, {
				"rookie1", "application1", "testExplications", "", IllegalArgumentException.class
			}, {
				"rookie1", "application1", "testExplications", "test", ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.submitApplicationTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Class<?>) testingData[i][4]);
	}

	// Template methods ------------------------------------------------------

	protected void listApplicationsCompanyTemplate(final String username, final Class<?> expected) {
		Class<?> caught = null;
		Collection<Application> applications;

		super.startTransaction();

		try {
			super.authenticate(username);
			applications = this.applicationService.findApplicationsOrderByStatusByCompanyLogged();
			Assert.notNull(applications);
		} catch (final Throwable oops) {
			caught = oops.getClass();
			this.entityManager.clear();
		}

		this.checkExceptions(expected, caught);
		super.unauthenticate();
		super.rollbackTransaction();
	}

	protected void acceptApplicationsCompanyTemplate(final String username, final String application, final Class<?> expected) {
		Class<?> caught = null;
		Application applicationEntity;

		super.startTransaction();

		try {
			super.authenticate(username);
			applicationEntity = this.applicationService.findApplicationCompanyLogged(super.getEntityId(application));
			this.applicationService.acceptApplication(applicationEntity);
			this.applicationService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
			this.entityManager.clear();
		}

		this.checkExceptions(expected, caught);
		super.unauthenticate();
		super.rollbackTransaction();
	}

	protected void rejectApplicationsCompanyTemplate(final String username, final String application, final Class<?> expected) {
		Class<?> caught = null;
		Application applicationEntity;

		super.startTransaction();

		try {
			super.authenticate(username);
			applicationEntity = this.applicationService.findApplicationCompanyLogged(super.getEntityId(application));
			this.applicationService.rejectApplication(applicationEntity);
			this.applicationService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
			this.entityManager.clear();
		}

		this.checkExceptions(expected, caught);
		super.unauthenticate();
		super.rollbackTransaction();
	}

	protected void listApplicationsRookieTemplate(final String username, final Class<?> expected) {
		Class<?> caught = null;
		Collection<Application> applications;

		super.startTransaction();

		try {
			super.authenticate(username);
			applications = this.applicationService.findApplicationsOrderByStatusByRookieLogged();
			Assert.notNull(applications);
		} catch (final Throwable oops) {
			caught = oops.getClass();
			this.entityManager.clear();
		}

		this.checkExceptions(expected, caught);
		super.unauthenticate();
		super.rollbackTransaction();
	}

	protected void createApplicationTemplate(final String username, final String position, final String curricula, final Class<?> expected) {
		Class<?> caught = null;
		Application applicationEntity;
		Position positionEntity;
		Curricula curriculaEntity;

		super.startTransaction();

		try {
			super.authenticate(username);
			applicationEntity = this.applicationService.create();
			positionEntity = this.positionService.findOne(super.getEntityId(position));
			curriculaEntity = this.curriculaService.findOne(super.getEntityId(curricula));
			applicationEntity.setPosition(positionEntity);
			applicationEntity.setCurricula(curriculaEntity);
			applicationEntity.setProblem(this.problemService.getRandomProblemByPosition(positionEntity));
			this.applicationService.save(applicationEntity);
			this.applicationService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
			this.entityManager.clear();
		}

		this.checkExceptions(expected, caught);
		super.unauthenticate();
		super.rollbackTransaction();
	}

	protected void submitApplicationTemplate(final String username, final String application, final String explications, final String codeLink, final Class<?> expected) {
		Class<?> caught = null;
		Application applicationEntity;

		super.startTransaction();

		try {
			super.authenticate(username);
			applicationEntity = this.applicationService.findOne(super.getEntityId(application));
			applicationEntity.setExplications(explications);
			applicationEntity.setCodeLink(codeLink);
			this.applicationService.save(applicationEntity);
			this.applicationService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
			this.entityManager.clear();
		}

		this.checkExceptions(expected, caught);
		super.unauthenticate();
		super.rollbackTransaction();
	}

}
