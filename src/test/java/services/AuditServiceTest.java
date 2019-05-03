
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
import domain.Audit;
import domain.Position;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class AuditServiceTest extends AbstractTest {

	// SUT Services
	@Autowired
	private AuditService	auditService;

	@Autowired
	private PositionService	positionService;

	@PersistenceContext
	EntityManager			entityManager;


	/**
	 * @author Rubén Bueno
	 *         Requisito funcional: 3.1, 3.2 (Acme-Rookies)
	 *         Caso de uso: listar "Audits"
	 *         Tests positivos: 1
	 *         *** 1. Listar "Audits" correctamente
	 *         Tests negativos: 1
	 *         *** 1. Intento de listar "Audits" con una autoridad no permitida
	 *         Analisis de cobertura de sentencias: 100% 23/23 instrucciones
	 *         Analisis de cobertura de datos: alto
	 */
	@Test
	public void driverListAuditsAuditor() {
		final Object testingData[][] = {
			{
				"auditor1", null
			}, {
				"rookie1", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.listAuditsAuditorTemplate((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	/**
	 * @author Rubén Bueno
	 *         Requisito funcional: 3.1, 3.2 (Acme-Rookies)
	 *         Caso de uso: crear un "Audit"
	 *         Tests positivos: 1
	 *         *** 1. Crear un "Audit" correctamente
	 *         Tests negativos: 7
	 *         *** 1. Intento de creación de un "Audit" con una autoridad no permitida
	 *         *** 2. Intento de creación de un "Audit" con texto vacío
	 *         *** 3. Intento de creación de un "Audit" con puntuación nulo
	 *         *** 4. Intento de creación de un "Audit" con puntuación menor a 0
	 *         *** 5. Intento de creación de un "Audit" con puntuación mayor a 10
	 *         *** 6. Intento de creación de un "Audit" con una "Position" que no está disponible para auditar
	 *         *** 7. Intento de creación de un "Audit" que está en final mode
	 *         Analisis de cobertura de sentencias: 100% 118/118 instrucciones
	 *         Analisis de cobertura de datos: alto
	 */
	@Test
	public void driverCreateAudit() {
		final Object testingData[][] = {
			{
				"auditor1", "textTest", 6, "position4", false, null
			}, {
				"rookie1", "textTest", 6, "position4", false, IllegalArgumentException.class
			}, {
				"auditor1", "", 6, "position4", false, ConstraintViolationException.class
			}, {
				"auditor1", "textTest", null, "position4", false, ConstraintViolationException.class
			}, {
				"auditor1", "textTest", -1, "position4", false, ConstraintViolationException.class
			}, {
				"auditor1", "textTest", 11, "position4", false, ConstraintViolationException.class
			}, {
				"auditor1", "textTest", 6, "position6", false, IllegalArgumentException.class
			}, {
				"auditor1", "textTest", 6, "position4", true, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.createAuditTemplate((String) testingData[i][0], (String) testingData[i][1], (Integer) testingData[i][2], (String) testingData[i][3], (Boolean) testingData[i][4], (Class<?>) testingData[i][5]);
	}

	/**
	 * @author Rubén Bueno
	 *         Requisito funcional: 3.2 (Acme-Rookies)
	 *         Caso de uso: editar un "Audit"
	 *         Tests positivos: 1
	 *         *** 1. Editar un "Audit" correctamente
	 *         Tests negativos: 13
	 *         *** 1. Intento de creación de un "Audit" con una autoridad no permitida
	 *         *** 2. Intento de edición de un "Audit" que no es del "Auditor" logeado
	 *         *** 3. Intento de edición de un "Audit" con texto vacío
	 *         *** 4. Intento de edición de un "Audit" con puntuación nulo
	 *         *** 5. Intento de edición de un "Audit" con puntuación menor a 0
	 *         *** 6. Intento de edición de un "Audit" con puntuación mayor a 10
	 *         *** 7. Intento de edición de un "Audit" que está en final mode
	 *         Analisis de cobertura de sentencias: 100% 88/88 instrucciones
	 *         Analisis de cobertura de datos: alto
	 */
	@Test
	public void driverEditAudit() {
		final Object testingData[][] = {
			{
				"auditor1", "audit1", "textTest", 6, false, null
			}, {
				"rookie1", "audit1", "textTest", 6, false, IllegalArgumentException.class
			}, {
				"auditor2", "audit1", "textTest", 6, false, IllegalArgumentException.class
			}, {
				"auditor1", "audit1", "", 6, false, ConstraintViolationException.class
			}, {
				"auditor1", "audit1", "textTest", null, false, ConstraintViolationException.class
			}, {
				"auditor1", "audit1", "textTest", -1, false, ConstraintViolationException.class
			}, {
				"auditor1", "audit1", "textTest", 11, false, ConstraintViolationException.class
			}, {
				"auditor1", "audit1", "textTest", 6, true, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.editAuditTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Integer) testingData[i][3], (Boolean) testingData[i][4], (Class<?>) testingData[i][5]);
	}

	/**
	 * @author Rubén Bueno
	 *         Requisito funcional: 3.2 (Acme-Rookies)
	 *         Caso de uso: eliminar un "Audit"
	 *         Tests positivos: 1
	 *         *** 1. Eliminar un "Audit" correctamente
	 *         Tests negativos: 3
	 *         *** 1. Intento de eliminación de un "Audit" con una autoridad no permitida
	 *         *** 2. Intento de eliminación de un "Audit" que no es de la "Auditor" logeada
	 *         *** 3. Intento de eliminación de un "Audit" que está en modo final
	 *         Analisis de cobertura de sentencias: 98,6% 68/69 instrucciones
	 *         Analisis de cobertura de datos: alto
	 */
	@Test
	public void driverDeleteAudit() {
		final Object testingData[][] = {
			{
				"auditor4", "audit5", null
			}, {
				"rookie1", "audit5", IllegalArgumentException.class
			}, {
				"auditor2", "audit5", IllegalArgumentException.class
			}, {
				"auditor1", "audit1", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.deleteAuditTemplate((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	/**
	 * @author Rubén Bueno
	 *         Requisito funcional: 3.2 (Acme-Rookies)
	 *         Caso de uso: cambiar a modo final un "Audit"
	 *         Tests positivos: 1
	 *         *** 1. Cambiar a modo final un "Audit" correctamente
	 *         Tests negativos: 3
	 *         *** 1. Intento de cambiar a modo final un "Audit" con una autoridad no permitida
	 *         *** 2. Intento de cambiar a modo final un "Audit" que no es de la "Auditor" logeada
	 *         *** 3. Intento de cambiar a modo final un "Audit" que ya está en modo final
	 *         Analisis de cobertura de sentencias: 97,3% 36/37instrucciones
	 *         Analisis de cobertura de datos: alto
	 */
	@Test
	public void driverChangeFinalModeAudit() {
		final Object testingData[][] = {
			{
				"auditor4", "audit5", null
			}, {
				"rookie1", "audit5", IllegalArgumentException.class
			}, {
				"auditor2", "audit5", IllegalArgumentException.class
			}, {
				"auditor1", "audit1", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.changeFinalModeAuditTemplate((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	// Template methods ------------------------------------------------------

	protected void listAuditsAuditorTemplate(final String username, final Class<?> expected) {
		Class<?> caught = null;
		Collection<Audit> audits;

		super.startTransaction();

		try {
			super.authenticate(username);
			audits = this.auditService.findAuditsByAuditorLogged();
			Assert.notNull(audits);
		} catch (final Throwable oops) {
			caught = oops.getClass();
			this.entityManager.clear();
		}

		this.checkExceptions(expected, caught);
		super.unauthenticate();
		super.rollbackTransaction();
	}

	protected void createAuditTemplate(final String username, final String text, final Integer score, final String position, final Boolean isFinalMode, final Class<?> expected) {
		Class<?> caught = null;
		Audit auditEntity;
		Position positionEntity;

		super.startTransaction();

		try {
			super.authenticate(username);
			auditEntity = this.auditService.create();
			positionEntity = this.positionService.findOne(super.getEntityId(position));
			auditEntity.setText(text);
			auditEntity.setScore(score);
			auditEntity.setPosition(positionEntity);
			auditEntity.setIsFinalMode(isFinalMode);
			this.auditService.save(auditEntity);
			this.auditService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
			this.entityManager.clear();
		}

		this.checkExceptions(expected, caught);
		super.unauthenticate();
		super.rollbackTransaction();
	}

	protected void editAuditTemplate(final String username, final String audit, final String text, final Integer score, final Boolean isFinalMode, final Class<?> expected) {
		Class<?> caught = null;
		Audit auditEntity;

		super.startTransaction();

		try {
			super.authenticate(username);
			auditEntity = this.auditService.findAuditAuditorLogged(super.getEntityId(audit));
			auditEntity.setText(text);
			auditEntity.setScore(score);
			auditEntity.setIsFinalMode(isFinalMode);
			this.auditService.save(auditEntity);
			this.auditService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
			this.entityManager.clear();
		}

		this.checkExceptions(expected, caught);
		super.unauthenticate();
		super.rollbackTransaction();
	}

	protected void deleteAuditTemplate(final String username, final String audit, final Class<?> expected) {
		Class<?> caught = null;
		Audit auditEntity;

		super.startTransaction();

		try {
			super.authenticate(username);
			auditEntity = this.auditService.findAuditAuditorLogged(super.getEntityId(audit));
			this.auditService.delete(auditEntity);
			this.auditService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
			this.entityManager.clear();
		}

		this.checkExceptions(expected, caught);
		super.unauthenticate();
		super.rollbackTransaction();
	}

	protected void changeFinalModeAuditTemplate(final String username, final String audit, final Class<?> expected) {
		Class<?> caught = null;
		Audit auditEntity;

		super.startTransaction();

		try {
			super.authenticate(username);
			auditEntity = this.auditService.findAuditAuditorLogged(super.getEntityId(audit));
			this.auditService.changeFinalMode(auditEntity);
			this.auditService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
			this.entityManager.clear();
		}

		this.checkExceptions(expected, caught);
		super.unauthenticate();
		super.rollbackTransaction();
	}

}
