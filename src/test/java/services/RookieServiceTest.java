
package services;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import utilities.AbstractTest;
import domain.CreditCard;
import domain.Rookie;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class RookieServiceTest extends AbstractTest {

	// SUT Services
	@Autowired
	private RookieService	rookieService;

	@PersistenceContext
	EntityManager			entityManager;


	/**
	 * @author Rubén Bueno
	 *         Requisito funcional: 7.1
	 *         Caso de uso: registrarse como "Rookie" en el sistema
	 *         Tests positivos: 1
	 *         *** 1. Registrarse como "Rookie" correctamente
	 *         Tests negativos: 24
	 *         *** 1. Intento de registro como "Rookie" con el nombre vacío
	 *         *** 2. Intento de registro como "Rookie" con los apellidos vacíos
	 *         *** 3. Intento de registro como "Rookie" con el número de VAT vacío
	 *         *** 4. Intento de registro como "Rookie" cuya "CreditCard" tiene el propietario vacío
	 *         *** 5. Intento de registro como "Rookie" cuya "CreditCard" tiene la marca vacío
	 *         *** 6. Intento de registro como "Rookie" cuya "CreditCard" tiene el número vacío
	 *         *** 7. Intento de registro como "Rookie" cuya "CreditCard" tiene un número que no es de tarjeta de crédito
	 *         *** 8. Intento de registro como "Rookie" cuya "CreditCard" tiene el mes de caducidad nulo
	 *         *** 9. Intento de registro como "Rookie" cuya "CreditCard" tiene un mes de caducidad menor a 1
	 *         *** 10. Intento de registro como "Rookie" cuya "CreditCard" tiene un mes de caducidad mayor a 12
	 *         *** 11. Intento de registro como "Rookie" cuya "CreditCard" tiene el año de caducidad nulo
	 *         *** 12. Intento de registro como "Rookie" cuya "CreditCard" tiene un año de caducidad menor a 0
	 *         *** 13. Intento de registro como "Rookie" cuya "CreditCard" tiene un año de caducidad mayor a 99
	 *         *** 14. Intento de registro como "Rookie" cuya "CreditCard" tiene el CVV nulo
	 *         *** 15. Intento de registro como "Rookie" cuya "CreditCard" tiene un CVV menor a 100
	 *         *** 16. Intento de registro como "Rookie" cuya "CreditCard" tiene un CVV mayor a 999
	 *         *** 17. Intento de registro como "Rookie" con el email vacío
	 *         *** 18. Intento de registro como "Rookie" con el email sin cumplir el patrón adecuado
	 *         *** 19. Intento de registro como "Rookie" con el usuario vacío
	 *         *** 20. Intento de registro como "Rookie" con tamaño del usuario menor a 5 caracteres
	 *         *** 21. Intento de registro como "Rookie" con tamaño del usuario mayor a 32 caracteres
	 *         *** 22. Intento de registro como "Rookie" usuario ya usado
	 *         *** 23. Intento de registro como "Rookie" cuya "CreditCard" tiene un número no numérico
	 *         *** 24. Intento de registro como "Rookie" cuya "CreditCard" está caducada
	 *         Analisis de cobertura de sentencias: 100% 167/167 instrucciones
	 *         Analisis de cobertura de datos: alto
	 */
	@Test
	public void driverRegisterAsRookie() {

		final Object testingData[][] = {
			{
				"testName", "testSurnames", "testVatNumber", "testCreditCardHolder", "VISA", "4739158676192764", 10, 25, 535, "testEmail@testemail.com", "testUser", "testPass", null
			},
			{
				"", "testSurnames", "testVatNumber", "testCreditCardHolder", "VISA", "4739158676192764", 10, 25, 535, "testEmail@testemail.com", "testUser", "testPass", ConstraintViolationException.class
			},
			{
				"testName", "", "testVatNumber", "testCreditCardHolder", "VISA", "4739158676192764", 10, 25, 535, "testEmail@testemail.com", "testUser", "testPass", ConstraintViolationException.class
			},
			{
				"testName", "testSurnames", "", "testCreditCardHolder", "VISA", "4739158676192764", 10, 25, 535, "testEmail@testemail.com", "testUser", "testPass", ConstraintViolationException.class
			},
			{
				"testName", "testSurnames", "testVatNumber", "", "VISA", "4739158676192764", 10, 25, 535, "testEmail@testemail.com", "testUser", "testPass", ConstraintViolationException.class
			},
			{
				"testName", "testSurnames", "testVatNumber", "testCreditCardHolder", "", "4739158676192764", 10, 25, 535, "testEmail@testemail.com", "testUser", "testPass", ConstraintViolationException.class
			},
			{
				"testName", "testSurnames", "testVatNumber", "testCreditCardHolder", "VISA", "", 10, 25, 535, "testEmail@testemail.com", "testUser", "testPass", ConstraintViolationException.class
			},
			{
				"testName", "testSurnames", "testVatNumber", "testCreditCardHolder", "VISA", "8534634734746", 10, 25, 535, "testEmail@testemail.com", "testUser", "testPass", ConstraintViolationException.class
			},
			{
				"testName", "testSurnames", "testVatNumber", "testCreditCardHolder", "VISA", "4739158676192764", null, 25, 535, "testEmail@testemail.com", "testUser", "testPass", ConstraintViolationException.class
			},
			{
				"testName", "testSurnames", "testVatNumber", "testCreditCardHolder", "VISA", "4739158676192764", 0, 25, 535, "testEmail@testemail.com", "testUser", "testPass", ConstraintViolationException.class
			},
			{
				"testName", "testSurnames", "testVatNumber", "testCreditCardHolder", "VISA", "4739158676192764", 13, 25, 535, "testEmail@testemail.com", "testUser", "testPass", ConstraintViolationException.class
			},
			{
				"testName", "testSurnames", "testVatNumber", "testCreditCardHolder", "VISA", "4739158676192764", 10, null, 535, "testEmail@testemail.com", "testUser", "testPass", ConstraintViolationException.class
			},
			{
				"testName", "testSurnames", "testVatNumber", "testCreditCardHolder", "VISA", "4739158676192764", 10, -1, 535, "testEmail@testemail.com", "testUser", "testPass", ConstraintViolationException.class
			},
			{
				"testName", "testSurnames", "testVatNumber", "testCreditCardHolder", "VISA", "4739158676192764", 10, 100, 535, "testEmail@testemail.com", "testUser", "testPass", ConstraintViolationException.class
			},
			{
				"testName", "testSurnames", "testVatNumber", "testCreditCardHolder", "VISA", "4739158676192764", 10, 25, null, "testEmail@testemail.com", "testUser", "testPass", ConstraintViolationException.class
			},
			{
				"testName", "testSurnames", "testVatNumber", "testCreditCardHolder", "VISA", "4739158676192764", 10, 25, 99, "testEmail@testemail.com", "testUser", "testPass", ConstraintViolationException.class
			},
			{
				"testName", "testSurnames", "testVatNumber", "testCreditCardHolder", "VISA", "4739158676192764", 10, 25, 1000, "testEmail@testemail.com", "testUser", "testPass", ConstraintViolationException.class
			},
			{
				"testName", "testSurnames", "testVatNumber", "testCreditCardHolder", "VISA", "4739158676192764", 10, 25, 535, "", "testUser", "testPass", ConstraintViolationException.class
			},
			{
				"testName", "testSurnames", "testVatNumber", "testCreditCardHolder", "VISA", "4739158676192764", 10, 25, 535, "testEmail", "testUser", "testPass", ConstraintViolationException.class
			},
			{
				"testName", "testSurnames", "testVatNumber", "testCreditCardHolder", "VISA", "4739158676192764", 10, 25, 535, "testEmail@testemail.com", "", "testPass", ConstraintViolationException.class
			},
			{
				"testName", "testSurnames", "testVatNumber", "testCreditCardHolder", "VISA", "4739158676192764", 10, 25, 535, "testEmail@testemail.com", "test", "testPass", ConstraintViolationException.class
			},
			{
				"testName", "testSurnames", "testVatNumber", "testCreditCardHolder", "VISA", "4739158676192764", 10, 25, 535, "testEmail@testemail.com", "testUsertestUsertestUsertestUsertestUsertestUsertestUsertestUser", "testPass",
				ConstraintViolationException.class
			}, {
				"testName", "testSurnames", "testVatNumber", "testCreditCardHolder", "VISA", "4739158676192764", 10, 25, 535, "testEmail@testemail.com", "rookie1", "testPass", DataIntegrityViolationException.class
			}, {
				"testName", "testSurnames", "testVatNumber", "testCreditCardHolder", "VISA", "testCreditCardNumber", 10, 25, 535, "testEmail@testemail.com", "testUser", "testPass", IllegalArgumentException.class
			}, {
				"testName", "testSurnames", "testVatNumber", "testCreditCardHolder", "VISA", "4739158676192764", 2, 19, 535, "testEmail@testemail.com", "testUser", "testPass", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.registerAsRookieTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (Integer) testingData[i][6],
				(Integer) testingData[i][7], (Integer) testingData[i][8], (String) testingData[i][9], (String) testingData[i][10], (String) testingData[i][11], (Class<?>) testingData[i][12]);
	}

	/**
	 * @author Rubén Bueno
	 *         Requisito funcional: 8.2
	 *         Caso de uso: editar sus datos estando logeado
	 *         Tests positivos: 1
	 *         *** 1. Editar sus datos correctamente
	 *         Tests negativos: 21
	 *         *** 1. Intento de edición de datos de un actor que no es el logeado
	 *         *** 2. Intento de edición como "Rookie" con el nombre vacío
	 *         *** 3. Intento de edición como "Rookie" con los apellidos vacíos
	 *         *** 4. Intento de edición como "Rookie" con el número de VAT vacío
	 *         *** 5. Intento de edición como "Rookie" cuya "CreditCard" tiene el propietario vacío
	 *         *** 6. Intento de edición como "Rookie" cuya "CreditCard" tiene la marca vacío
	 *         *** 7. Intento de edición como "Rookie" cuya "CreditCard" tiene el número vacío
	 *         *** 8. Intento de edición como "Rookie" cuya "CreditCard" tiene un número que no es de tarjeta de crédito
	 *         *** 9. Intento de edición como "Rookie" cuya "CreditCard" tiene el mes de caducidad nulo
	 *         *** 10. Intento de edición como "Rookie" cuya "CreditCard" tiene un mes de caducidad menor a 1
	 *         *** 11. Intento de edición como "Rookie" cuya "CreditCard" tiene un mes de caducidad mayor a 12
	 *         *** 12. Intento de edición como "Rookie" cuya "CreditCard" tiene el año de caducidad nulo
	 *         *** 13. Intento de edición como "Rookie" cuya "CreditCard" tiene un año de caducidad menor a 0
	 *         *** 14. Intento de edición como "Rookie" cuya "CreditCard" tiene un año de caducidad mayor a 99
	 *         *** 15. Intento de edición como "Rookie" cuya "CreditCard" tiene el CVV nulo
	 *         *** 16. Intento de edición como "Rookie" cuya "CreditCard" tiene un CVV menor a 100
	 *         *** 17. Intento de edición como "Rookie" cuya "CreditCard" tiene un CVV mayor a 999
	 *         *** 18. Intento de edición como "Rookie" con el email vacío
	 *         *** 19. Intento de edición como "Rookie" con el email sin cumplir el patrón adecuado
	 *         *** 20. Intento de edición como "Rookie" cuya "CreditCard" tiene un número no numérico
	 *         *** 21. Intento de edición como "Rookie" cuya "CreditCard" está caducada
	 *         Analisis de cobertura de sentencias: 100% 130/130 instrucciones
	 *         Analisis de cobertura de datos: alto
	 */
	@Test
	public void driverEditData() {
		final Object testingData[][] = {
			{
				"rookie1", "rookie1", "testName", "testSurnames", "testVatNumber", "testCreditCardHolder", "VISA", "4739158676192764", 10, 25, 535, "testEmail@testemail.com", null
			}, {
				"rookie1", "rookie2", "testName", "testSurnames", "testVatNumber", "testCreditCardHolder", "VISA", "4739158676192764", 10, 25, 535, "testEmail@testemail.com", IllegalArgumentException.class
			}, {
				"rookie1", "rookie1", "", "testSurnames", "testVatNumber", "testCreditCardHolder", "VISA", "4739158676192764", 10, 25, 535, "testEmail@testemail.com", ConstraintViolationException.class
			}, {
				"rookie1", "rookie1", "testName", "", "testVatNumber", "testCreditCardHolder", "VISA", "4739158676192764", 10, 25, 535, "testEmail@testemail.com", ConstraintViolationException.class
			}, {
				"rookie1", "rookie1", "testName", "testSurnames", "", "testCreditCardHolder", "VISA", "4739158676192764", 10, 25, 535, "testEmail@testemail.com", ConstraintViolationException.class
			}, {
				"rookie1", "rookie1", "testName", "testSurnames", "testVatNumber", "", "VISA", "4739158676192764", 10, 25, 535, "testEmail@testemail.com", ConstraintViolationException.class
			}, {
				"rookie1", "rookie1", "testName", "testSurnames", "testVatNumber", "testCreditCardHolder", "", "4739158676192764", 10, 25, 535, "testEmail@testemail.com", ConstraintViolationException.class
			}, {
				"rookie1", "rookie1", "testName", "testSurnames", "testVatNumber", "testCreditCardHolder", "VISA", "", 10, 25, 535, "testEmail@testemail.com", ConstraintViolationException.class
			}, {
				"rookie1", "rookie1", "testName", "testSurnames", "testVatNumber", "testCreditCardHolder", "VISA", "8534634734746", 10, 25, 535, "testEmail@testemail.com", ConstraintViolationException.class
			}, {
				"rookie1", "rookie1", "testName", "testSurnames", "testVatNumber", "testCreditCardHolder", "VISA", "4739158676192764", null, 25, 535, "testEmail@testemail.com", ConstraintViolationException.class
			}, {
				"rookie1", "rookie1", "testName", "testSurnames", "testVatNumber", "testCreditCardHolder", "VISA", "4739158676192764", 0, 25, 535, "testEmail@testemail.com", ConstraintViolationException.class
			}, {
				"rookie1", "rookie1", "testName", "testSurnames", "testVatNumber", "testCreditCardHolder", "VISA", "4739158676192764", 13, 25, 535, "testEmail@testemail.com", ConstraintViolationException.class
			}, {
				"rookie1", "rookie1", "testName", "testSurnames", "testVatNumber", "testCreditCardHolder", "VISA", "4739158676192764", 10, null, 535, "testEmail@testemail.com", ConstraintViolationException.class
			}, {
				"rookie1", "rookie1", "testName", "testSurnames", "testVatNumber", "testCreditCardHolder", "VISA", "4739158676192764", 10, -1, 535, "testEmail@testemail.com", ConstraintViolationException.class
			}, {
				"rookie1", "rookie1", "testName", "testSurnames", "testVatNumber", "testCreditCardHolder", "VISA", "4739158676192764", 10, 100, 535, "testEmail@testemail.com", ConstraintViolationException.class
			}, {
				"rookie1", "rookie1", "testName", "testSurnames", "testVatNumber", "testCreditCardHolder", "VISA", "4739158676192764", 10, 25, null, "testEmail@testemail.com", ConstraintViolationException.class
			}, {
				"rookie1", "rookie1", "testName", "testSurnames", "testVatNumber", "testCreditCardHolder", "VISA", "4739158676192764", 10, 25, 99, "testEmail@testemail.com", ConstraintViolationException.class
			}, {
				"rookie1", "rookie1", "testName", "testSurnames", "testVatNumber", "testCreditCardHolder", "VISA", "4739158676192764", 10, 25, 1000, "testEmail@testemail.com", ConstraintViolationException.class
			}, {
				"rookie1", "rookie1", "testName", "testSurnames", "testVatNumber", "testCreditCardHolder", "VISA", "4739158676192764", 10, 25, 535, "", ConstraintViolationException.class
			}, {
				"rookie1", "rookie1", "testName", "testSurnames", "testVatNumber", "testCreditCardHolder", "VISA", "4739158676192764", 10, 25, 535, "testEmail", ConstraintViolationException.class
			}, {
				"rookie1", "rookie1", "testName", "testSurnames", "testVatNumber", "testCreditCardHolder", "VISA", "testCreditCardNumber", 10, 25, 535, "testEmail@testemail.com", IllegalArgumentException.class
			}, {
				"rookie1", "rookie1", "testName", "testSurnames", "testVatNumber", "testCreditCardHolder", "VISA", "4739158676192764", 2, 19, 535, "testEmail@testemail.com", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.editDataTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (Integer) testingData[i][8], (Integer) testingData[i][9], (Integer) testingData[i][10], (String) testingData[i][11], (Class<?>) testingData[i][12]);
	}

	// Template methods ------------------------------------------------------

	protected void registerAsRookieTemplate(final String name, final String surnames, final String VATNumber, final String creditCardHolder, final String creditCardMakeCreditCard, final String creditCardNumber, final Integer creditCardExpirationMonth,
		final Integer creditCardExpirationYear, final Integer creditCardCVV, final String email, final String username, final String password, final Class<?> expected) {
		Class<?> caught = null;
		Rookie rookie;

		super.startTransaction();

		try {
			rookie = this.rookieService.create();
			final CreditCard creditCard = new CreditCard();
			creditCard.setHolder(creditCardHolder);
			creditCard.setMakeCreditCard(creditCardMakeCreditCard);
			creditCard.setNumber(creditCardNumber);
			creditCard.setExpirationMonth(creditCardExpirationMonth);
			creditCard.setExpirationYear(creditCardExpirationYear);
			creditCard.setCvv(creditCardCVV);
			rookie.setName(name);
			rookie.setSurnames(surnames);
			rookie.setVATNumber(VATNumber);
			rookie.setCreditCard(creditCard);
			rookie.setEmail(email);
			rookie.getUserAccount().setUsername(username);
			rookie.getUserAccount().setPassword(password);
			this.rookieService.save(rookie);
			this.rookieService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
			this.entityManager.clear();
		}

		this.checkExceptions(expected, caught);
		super.rollbackTransaction();
	}

	protected void editDataTemplate(final String username, final String actorData, final String name, final String surnames, final String VATNumber, final String creditCardHolder, final String creditCardMakeCreditCard, final String creditCardNumber,
		final Integer creditCardExpirationMonth, final Integer creditCardExpirationYear, final Integer creditCardCVV, final String email, final Class<?> expected) {
		Class<?> caught = null;
		Rookie rookie;

		super.startTransaction();

		try {
			super.authenticate(username);
			rookie = this.rookieService.findOne(super.getEntityId(actorData));
			final CreditCard creditCard = new CreditCard();
			creditCard.setHolder(creditCardHolder);
			creditCard.setMakeCreditCard(creditCardMakeCreditCard);
			creditCard.setNumber(creditCardNumber);
			creditCard.setExpirationMonth(creditCardExpirationMonth);
			creditCard.setExpirationYear(creditCardExpirationYear);
			creditCard.setCvv(creditCardCVV);
			rookie.setName(name);
			rookie.setSurnames(surnames);
			rookie.setVATNumber(VATNumber);
			rookie.setCreditCard(creditCard);
			rookie.setEmail(email);
			this.rookieService.save(rookie);
			this.rookieService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
			this.entityManager.clear();
		}

		super.unauthenticate();
		this.checkExceptions(expected, caught);
		super.rollbackTransaction();
	}
}
