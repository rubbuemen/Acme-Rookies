
package services;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

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
import domain.Position;
import domain.Problem;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class PositionServiceTest extends AbstractTest {

	// SUT Services
	@Autowired
	private PositionService	positionService;

	@Autowired
	private ProblemService	problemService;

	@PersistenceContext
	EntityManager			entityManager;


	/**
	 * @author Rub�n Bueno
	 *         Requisito funcional: 7.3
	 *         Caso de uso: listar las "Companies" disponibles sin estar logeado y navegar hacia sus "Positions"
	 *         Tests positivos: 1
	 *         *** 1. Listar las "Companies" disponibles sin estar logeado y navegar hacia sus "Positions"
	 *         Tests negativos: 1
	 *         *** 1. Intento de listar las "Companies" disponibles sin estar logeado y navegar hacia sus "Positions" de una "Company" inexistente
	 *         Analisis de cobertura de sentencias: 100% 9/9 instrucciones
	 *         Analisis de cobertura de datos: alto
	 */
	@Test
	public void driverNavegatePositionsByCompany() {

		final Object testingData[][] = {
			{
				"company1", null
			}, {
				"company32432", AssertionError.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.navegatePositionsByCompanyTemplate((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	/**
	 * @author Rub�n Bueno
	 *         Requisito funcional: 7.4
	 *         Caso de uso: buscar "Positions" usando una palabra clave sin estar logeado
	 *         Tests positivos: 1
	 *         *** 1. Buscar "Positions" usando una palabra clave sin estar logeado, correctamente
	 *         Analisis de cobertura de sentencias: 100% 9/9 instrucciones
	 *         Analisis de cobertura de datos: alto
	 */
	@Test
	public void driverSearchPositionBySingleKeyWord() {

		final Object testingData[][] = {
			{
				"programador", null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.searchPositionBySingleKeyWordTemplate((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	/**
	 * @author Rub�n Bueno
	 *         Requisito funcional: 9.1
	 *         Caso de uso: listar "Positions"
	 *         Tests positivos: 1
	 *         *** 1. Listar "Positions" correctamente
	 *         Tests negativos: 1
	 *         *** 1. Intento de listar "Positions" con una autoridad no permitida
	 *         Analisis de cobertura de sentencias: 100% 23/23 instrucciones
	 *         Analisis de cobertura de datos: alto
	 */
	@Test
	public void driverListPositionsCompany() {
		final Object testingData[][] = {
			{
				"company1", null
			}, {
				"rookie1", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.listPositionsCompanyTemplate((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	/**
	 * @author Rub�n Bueno
	 *         Requisito funcional: 9.1
	 *         Caso de uso: crear un "Position"
	 *         Tests positivos: 1
	 *         *** 1. Crear un "Position" correctamente
	 *         Tests negativos: 12
	 *         *** 1. Intento de creaci�n de un "Position" con una autoridad no permitida
	 *         *** 2. Intento de creaci�n de un "Position" con t�tulo vac�o
	 *         *** 3. Intento de creaci�n de un "Position" con descripci�n vac�a
	 *         *** 4. Intento de creaci�n de un "Position" con fecha l�mite nula
	 *         *** 5. Intento de creaci�n de un "Position" con fecha l�mite en pasado
	 *         *** 6. Intento de creaci�n de un "Position" sin habilidades
	 *         *** 7. Intento de creaci�n de un "Position" sin tecnolog�as
	 *         *** 8. Intento de creaci�n de un "Position" con salario nulo
	 *         *** 9. Intento de creaci�n de un "Position" con salario menor a 0
	 *         *** 10. Intento de creaci�n de un "Position" con perfil vacio
	 *         *** 11. Intento de creaci�n de un "Position" con "Problems" que no son de la "Company" logeada
	 *         *** 12. Intento de creaci�n de un "Position" que est� en final mode
	 *         Analisis de cobertura de sentencias: 89,2% 124/139 instrucciones
	 *         Analisis de cobertura de datos: alto
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void driverCreatePosition() {
		final Calendar cal = Calendar.getInstance();
		cal.set(2000, 9, 30);
		final Date datelinePast = cal.getTime();
		cal.set(2200, 9, 30);
		final Date datelineFuture = cal.getTime();

		final Collection<String> stringValues = this.stringValues();
		final Collection<String> emptyCollection = new HashSet<>();

		final Collection<Problem> emptyProblems = new HashSet<>();
		final Collection<Problem> badProblems = new HashSet<>();
		final Problem badProblem = this.problemService.findOne(super.getEntityId("problem10"));
		badProblems.add(badProblem);

		final Object testingData[][] = {
			{
				"company1", "titleTest", "descriptionTest", datelineFuture, stringValues, stringValues, 20.22, "testProfile", emptyProblems, false, null
			}, {
				"rookie1", "titleTest", "descriptionTest", datelineFuture, stringValues, stringValues, 20.22, "testProfile", emptyProblems, false, IllegalArgumentException.class
			}, {
				"company1", "", "descriptionTest", datelineFuture, stringValues, stringValues, 20.22, "testProfile", emptyProblems, false, ConstraintViolationException.class
			}, {
				"company1", "titleTest", "", datelineFuture, stringValues, stringValues, 20.22, "testProfile", emptyProblems, false, ConstraintViolationException.class
			}, {
				"company1", "titleTest", "descriptionTest", null, stringValues, stringValues, 20.22, "testProfile", emptyProblems, false, ConstraintViolationException.class
			}, {
				"company1", "titleTest", "descriptionTest", datelinePast, stringValues, stringValues, 20.22, "testProfile", emptyProblems, false, ConstraintViolationException.class
			}, {
				"company1", "titleTest", "descriptionTest", datelineFuture, emptyCollection, stringValues, 20.22, "testProfile", emptyProblems, false, ConstraintViolationException.class
			}, {
				"company1", "titleTest", "descriptionTest", datelineFuture, stringValues, emptyCollection, 20.22, "testProfile", emptyProblems, false, ConstraintViolationException.class
			}, {
				"company1", "titleTest", "descriptionTest", datelineFuture, stringValues, stringValues, null, "testProfile", emptyProblems, false, ConstraintViolationException.class
			}, {
				"company1", "titleTest", "descriptionTest", datelineFuture, stringValues, stringValues, -1.0, "testProfile", emptyProblems, false, ConstraintViolationException.class
			}, {
				"company1", "titleTest", "descriptionTest", datelineFuture, stringValues, stringValues, 20.22, "", emptyProblems, false, ConstraintViolationException.class
			}, {
				"company1", "titleTest", "descriptionTest", datelineFuture, stringValues, stringValues, 20.22, "testProfile", badProblems, false, IllegalArgumentException.class
			}, {
				"company1", "titleTest", "descriptionTest", datelineFuture, stringValues, stringValues, 20.22, "testProfile", emptyProblems, true, IllegalArgumentException.class
			},
		};

		for (int i = 0; i < testingData.length; i++)
			this.createPositionTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Date) testingData[i][3], (Collection<String>) testingData[i][4], (Collection<String>) testingData[i][5],
				(Double) testingData[i][6], (String) testingData[i][7], (Collection<Problem>) testingData[i][8], (Boolean) testingData[i][9], (Class<?>) testingData[i][10]);
	}

	/**
	 * @author Rub�n Bueno
	 *         Requisito funcional: 9.1
	 *         Caso de uso: editar un "Position"
	 *         Tests positivos: 1
	 *         *** 1. Editar un "Position" correctamente
	 *         Tests negativos: 13
	 *         *** 1. Intento de creaci�n de un "Position" con una autoridad no permitida
	 *         *** 2. Intento de edici�n de un "Position" que no es de la "Company" logeada
	 *         *** 3. Intento de edici�n de un "Position" con t�tulo vac�o
	 *         *** 4. Intento de edici�n de un "Position" con descripci�n vac�a
	 *         *** 5. Intento de edici�n de un "Position" con fecha l�mite nula
	 *         *** 6. Intento de edici�n de un "Position" con fecha l�mite en pasado
	 *         *** 7. Intento de edici�n de un "Position" sin habilidades
	 *         *** 8. Intento de edici�n de un "Position" sin tecnolog�as
	 *         *** 9. Intento de edici�n de un "Position" con salario nulo
	 *         *** 10. Intento de edici�n de un "Position" con salario menor a 0
	 *         *** 11. Intento de edici�n de un "Position" con perfil vacio
	 *         *** 12. Intento de edici�n de un "Position" con "Problems" que no son de la "Company" logeada
	 *         *** 13. Intento de edici�n de un "Position" que est� en final mode
	 *         Analisis de cobertura de sentencias: 83,9% 78/93 instrucciones
	 *         Analisis de cobertura de datos: alto
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void driverEditPosition() {
		final Calendar cal = Calendar.getInstance();
		cal.set(2000, 9, 30);
		final Date datelinePast = cal.getTime();
		cal.set(2200, 9, 30);
		final Date datelineFuture = cal.getTime();

		final Collection<String> stringValues = this.stringValues();
		final Collection<String> emptyCollection = new HashSet<>();

		final Collection<Problem> emptyProblems = new HashSet<>();
		final Collection<Problem> badProblems = new HashSet<>();
		final Problem badProblem = this.problemService.findOne(super.getEntityId("problem10"));
		badProblems.add(badProblem);

		final Object testingData[][] = {
			{
				"company1", "position1", "titleTest", "descriptionTest", datelineFuture, stringValues, stringValues, 20.22, "testProfile", emptyProblems, false, null
			}, {
				"rookie1", "position1", "titleTest", "descriptionTest", datelineFuture, stringValues, stringValues, 20.22, "testProfile", emptyProblems, false, IllegalArgumentException.class
			}, {
				"company2", "position1", "titleTest", "descriptionTest", datelineFuture, stringValues, stringValues, 20.22, "testProfile", emptyProblems, false, IllegalArgumentException.class
			}, {
				"company1", "position1", "", "descriptionTest", datelineFuture, stringValues, stringValues, 20.22, "testProfile", emptyProblems, false, ConstraintViolationException.class
			}, {
				"company1", "position1", "titleTest", "", datelineFuture, stringValues, stringValues, 20.22, "testProfile", emptyProblems, false, ConstraintViolationException.class
			}, {
				"company1", "position1", "titleTest", "descriptionTest", null, stringValues, stringValues, 20.22, "testProfile", emptyProblems, false, ConstraintViolationException.class
			}, {
				"company1", "position1", "titleTest", "descriptionTest", datelinePast, stringValues, stringValues, 20.22, "testProfile", emptyProblems, false, ConstraintViolationException.class
			}, {
				"company1", "position1", "titleTest", "descriptionTest", datelineFuture, emptyCollection, stringValues, 20.22, "testProfile", emptyProblems, false, ConstraintViolationException.class
			}, {
				"company1", "position1", "titleTest", "descriptionTest", datelineFuture, stringValues, emptyCollection, 20.22, "testProfile", emptyProblems, false, ConstraintViolationException.class
			}, {
				"company1", "position1", "titleTest", "descriptionTest", datelineFuture, stringValues, stringValues, null, "testProfile", emptyProblems, false, ConstraintViolationException.class
			}, {
				"company1", "position1", "titleTest", "descriptionTest", datelineFuture, stringValues, stringValues, -1.0, "testProfile", emptyProblems, false, ConstraintViolationException.class
			}, {
				"company1", "position1", "titleTest", "descriptionTest", datelineFuture, stringValues, stringValues, 20.22, "", emptyProblems, false, ConstraintViolationException.class
			}, {
				"company1", "position1", "titleTest", "descriptionTest", datelineFuture, stringValues, stringValues, 20.22, "testProfile", badProblems, false, IllegalArgumentException.class
			}, {
				"company1", "position1", "titleTest", "descriptionTest", datelineFuture, stringValues, stringValues, 20.22, "testProfile", emptyProblems, true, IllegalArgumentException.class
			},
		};

		for (int i = 0; i < testingData.length; i++)
			this.editPositionTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Date) testingData[i][4], (Collection<String>) testingData[i][5], (Collection<String>) testingData[i][6],
				(Double) testingData[i][7], (String) testingData[i][8], (Collection<Problem>) testingData[i][9], (Boolean) testingData[i][10], (Class<?>) testingData[i][11]);
	}

	/**
	 * @author Rub�n Bueno
	 *         Requisito funcional: 9.1
	 *         Caso de uso: eliminar un "Position"
	 *         Tests positivos: 1
	 *         *** 1. Eliminar un "Position" correctamente
	 *         Tests negativos: 3
	 *         *** 1. Intento de eliminaci�n de un "Position" con una autoridad no permitida
	 *         *** 2. Intento de eliminaci�n de un "Position" que no es de la "Company" logeada
	 *         *** 3. Intento de eliminaci�n de un "Position" que est� en modo final
	 *         Analisis de cobertura de sentencias: 98,6% 68/69 instrucciones
	 *         Analisis de cobertura de datos: alto
	 */
	@Test
	public void driverDeletePosition() {
		final Object testingData[][] = {
			{
				"company1", "position2", null
			}, {
				"rookie1", "position2", IllegalArgumentException.class
			}, {
				"company2", "position2", IllegalArgumentException.class
			}, {
				"company1", "position1", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.deletePositionTemplate((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	/**
	 * @author Rub�n Bueno
	 *         Requisito funcional: 9.1
	 *         Caso de uso: cambiar a modo final un "Position"
	 *         Tests positivos: 1
	 *         *** 1. Cambiar a modo final un "Position" correctamente
	 *         Tests negativos: 4
	 *         *** 1. Intento de cambiar a modo final un "Position" con una autoridad no permitida
	 *         *** 2. Intento de cambiar a modo final un "Position" que no es de la "Company" logeada
	 *         *** 3. Intento de cambiar a modo final un "Position" que ya est� en modo final
	 *         *** 4. Intento de cambiar a modo final un "Position" que que no tiene como m�nimo 2 "Problems" asignados
	 *         Analisis de cobertura de sentencias: 86,9% 113/130 instrucciones
	 *         Analisis de cobertura de datos: alto
	 */
	@Test
	public void driverChangeFinalModePosition() {
		final Object testingData[][] = {
			{
				"company1", "position2", null
			}, {
				"rookie1", "position2", IllegalArgumentException.class
			}, {
				"company2", "position2", IllegalArgumentException.class
			}, {
				"company1", "position1", IllegalArgumentException.class
			}, {
				"company2", "position5", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.changeFinalModePositionTemplate((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	/**
	 * @author Rub�n Bueno
	 *         Requisito funcional: 9.1
	 *         Caso de uso: cancelar un "Position"
	 *         Tests positivos: 1
	 *         *** 1. Cancelar un "Position" correctamente
	 *         Tests negativos: 4
	 *         *** 1. Intento de cancelar un "Position" con una autoridad no permitida
	 *         *** 2. Intento de cancelar un "Position" que no es de la "Company" logeada
	 *         *** 3. Intento de cancelar un "Position" que ya est� cancelada
	 *         *** 4. Intento de cancelar un "Position" que no est� en modo final
	 *         Analisis de cobertura de sentencias: 87% 168/193 instrucciones
	 *         Analisis de cobertura de datos: alto
	 */
	@Test
	public void driverCancelPosition() {
		final Object testingData[][] = {
			{
				"company1", "position1", null
			}, {
				"rookie1", "position1", IllegalArgumentException.class
			}, {
				"company2", "position1", IllegalArgumentException.class
			}, {
				"company1", "position3", IllegalArgumentException.class
			}, {
				"company1", "position2", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.cancelPositionTemplate((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	// Template methods ------------------------------------------------------

	protected void navegatePositionsByCompanyTemplate(final String company, final Class<?> expected) {
		Class<?> caught = null;
		Collection<Position> positions;

		super.startTransaction();

		try {
			positions = this.positionService.findPositionsFinalModeNotCancelledNotDeadlineByCompanyId(super.getEntityId(company));
			Assert.notNull(positions);
		} catch (final Throwable oops) {
			caught = oops.getClass();
			this.entityManager.clear();
		}

		this.checkExceptions(expected, caught);
		super.rollbackTransaction();
	}

	protected void searchPositionBySingleKeyWordTemplate(final String wordKey, final Class<?> expected) {
		Class<?> caught = null;
		Collection<Position> positions;

		super.startTransaction();

		try {
			positions = this.positionService.findPositionsFinalModeNotCancelledNotDeadlineBySingleKeyWord(wordKey);
			Assert.notNull(positions);
		} catch (final Throwable oops) {
			caught = oops.getClass();
			this.entityManager.clear();
		}

		this.checkExceptions(expected, caught);
		super.rollbackTransaction();
	}

	protected void listPositionsCompanyTemplate(final String username, final Class<?> expected) {
		Class<?> caught = null;
		Collection<Position> positions;

		super.startTransaction();

		try {
			super.authenticate(username);
			positions = this.positionService.findPositionsByCompanyLogged();
			Assert.notNull(positions);
		} catch (final Throwable oops) {
			caught = oops.getClass();
			this.entityManager.clear();
		}

		this.checkExceptions(expected, caught);
		super.unauthenticate();
		super.rollbackTransaction();
	}

	protected void createPositionTemplate(final String username, final String title, final String description, final Date deadline, final Collection<String> skills, final Collection<String> technologies, final Double salary, final String profile,
		final Collection<Problem> problems, final Boolean isFinalMode, final Class<?> expected) {
		Class<?> caught = null;
		Position positionEntity;

		super.startTransaction();

		try {
			super.authenticate(username);
			positionEntity = this.positionService.create();
			positionEntity.setTicker(this.positionService.generateTicker());
			positionEntity.setTitle(title);
			positionEntity.setDescription(description);
			positionEntity.setDeadline(deadline);
			positionEntity.setSkills(skills);
			positionEntity.setTechnologies(technologies);
			positionEntity.setSalary(salary);
			positionEntity.setProfile(profile);
			positionEntity.setIsFinalMode(isFinalMode);
			positionEntity.setProblems(problems);
			this.positionService.save(positionEntity);
			this.positionService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
			this.entityManager.clear();
		}

		this.checkExceptions(expected, caught);
		super.unauthenticate();
		super.rollbackTransaction();
	}

	protected void editPositionTemplate(final String username, final String position, final String title, final String description, final Date deadline, final Collection<String> skills, final Collection<String> technologies, final Double salary,
		final String profile, final Collection<Problem> problems, final Boolean isFinalMode, final Class<?> expected) {
		Class<?> caught = null;
		Position positionEntity;

		super.startTransaction();

		try {
			super.authenticate(username);
			positionEntity = this.positionService.findPositionCompanyLogged(super.getEntityId(position));
			positionEntity.setTitle(title);
			positionEntity.setDescription(description);
			positionEntity.setDeadline(deadline);
			positionEntity.setSkills(skills);
			positionEntity.setTechnologies(technologies);
			positionEntity.setSalary(salary);
			positionEntity.setProfile(profile);
			positionEntity.setIsFinalMode(isFinalMode);
			positionEntity.setProblems(problems);
			this.positionService.save(positionEntity);
			this.positionService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
			this.entityManager.clear();
		}

		this.checkExceptions(expected, caught);
		super.unauthenticate();
		super.rollbackTransaction();
	}

	protected void deletePositionTemplate(final String username, final String position, final Class<?> expected) {
		Class<?> caught = null;
		Position positionEntity;

		super.startTransaction();

		try {
			super.authenticate(username);
			positionEntity = this.positionService.findPositionCompanyLogged(super.getEntityId(position));
			this.positionService.delete(positionEntity);
			this.positionService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
			this.entityManager.clear();
		}

		this.checkExceptions(expected, caught);
		super.unauthenticate();
		super.rollbackTransaction();
	}

	protected void changeFinalModePositionTemplate(final String username, final String position, final Class<?> expected) {
		Class<?> caught = null;
		Position positionEntity;

		super.startTransaction();

		try {
			super.authenticate(username);
			positionEntity = this.positionService.findPositionCompanyLogged(super.getEntityId(position));
			this.positionService.changeFinalMode(positionEntity);
			this.positionService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
			this.entityManager.clear();
		}

		this.checkExceptions(expected, caught);
		super.unauthenticate();
		super.rollbackTransaction();
	}

	protected void cancelPositionTemplate(final String username, final String position, final Class<?> expected) {
		Class<?> caught = null;
		Position positionEntity;

		super.startTransaction();

		try {
			super.authenticate(username);
			positionEntity = this.positionService.findPositionCompanyLogged(super.getEntityId(position));
			this.positionService.changeCancelled(positionEntity);
			this.positionService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
			this.entityManager.clear();
		}

		this.checkExceptions(expected, caught);
		super.unauthenticate();
		super.rollbackTransaction();
	}

	// Auxiliar methods ------------------------------------------------------

	private Collection<String> stringValues() {
		final Collection<String> stringValues = new HashSet<>();
		stringValues.add("test1");
		stringValues.add("test2");
		stringValues.add("test3");
		stringValues.add("test4");
		stringValues.add("test5");

		return stringValues;
	}
}
