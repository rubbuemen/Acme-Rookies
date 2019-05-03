
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
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.CreditCard;
import domain.Provider;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ProviderServiceTest extends AbstractTest {

	// SUT Services
	@Autowired
	private ProviderService	providerService;

	@PersistenceContext
	EntityManager			entityManager;


	/**
	 * @author Rubén Bueno
	 *         Requisito funcional: 9.3 (Acme-Rookies)
	 *         Caso de uso: registrarse como "Provider" en el sistema
	 *         Tests positivos: 1
	 *         *** 1. Registrarse como "Provider" correctamente
	 *         Tests negativos: 25
	 *         *** 1. Intento de registro como "Provider" con el nombre vacío
	 *         *** 2. Intento de registro como "Provider" con los apellidos vacíos
	 *         *** 3. Intento de registro como "Provider" con el número de VAT vacío
	 *         *** 4. Intento de registro como "Provider" cuya "CreditCard" tiene el propietario vacío
	 *         *** 5. Intento de registro como "Provider" cuya "CreditCard" tiene la marca vacío
	 *         *** 6. Intento de registro como "Provider" cuya "CreditCard" tiene el número vacío
	 *         *** 7. Intento de registro como "Provider" cuya "CreditCard" tiene un número que no es de tarjeta de crédito
	 *         *** 8. Intento de registro como "Provider" cuya "CreditCard" tiene el mes de caducidad nulo
	 *         *** 9. Intento de registro como "Provider" cuya "CreditCard" tiene un mes de caducidad menor a 1
	 *         *** 10. Intento de registro como "Provider" cuya "CreditCard" tiene un mes de caducidad mayor a 12
	 *         *** 11. Intento de registro como "Provider" cuya "CreditCard" tiene el año de caducidad nulo
	 *         *** 12. Intento de registro como "Provider" cuya "CreditCard" tiene un año de caducidad menor a 0
	 *         *** 13. Intento de registro como "Provider" cuya "CreditCard" tiene un año de caducidad mayor a 99
	 *         *** 14. Intento de registro como "Provider" cuya "CreditCard" tiene el CVV nulo
	 *         *** 15. Intento de registro como "Provider" cuya "CreditCard" tiene un CVV menor a 100
	 *         *** 16. Intento de registro como "Provider" cuya "CreditCard" tiene un CVV mayor a 999
	 *         *** 17. Intento de registro como "Provider" con el email vacío
	 *         *** 18. Intento de registro como "Provider" con el email sin cumplir el patrón adecuado
	 *         *** 19. Intento de registro como "Provider" con el nombre comercial vacío
	 *         *** 20. Intento de registro como "Provider" con el usuario vacío
	 *         *** 21. Intento de registro como "Provider" con tamaño del usuario menor a 5 caracteres
	 *         *** 22. Intento de registro como "Provider" con tamaño del usuario mayor a 32 caracteres
	 *         *** 23. Intento de registro como "Provider" usuario ya usado
	 *         *** 24. Intento de registro como "Provider" cuya "CreditCard" tiene un número no numérico
	 *         *** 25. Intento de registro como "Provider" cuya "CreditCard" está caducada
	 *         Analisis de cobertura de sentencias: 100% 155/155 instrucciones
	 *         Analisis de cobertura de datos: alto
	 */
	@Test
	public void driverRegisterAsProvider() {

		final Object testingData[][] = {
			{
				"testName", "testSurnames", "testVatNumber", "testCreditCardHolder", "VISA", "4739158676192764", 10, 25, 535, "testEmail@testemail.com", "testMake", "testUser", "testPass", null
			},
			{
				"", "testSurnames", "testVatNumber", "testCreditCardHolder", "VISA", "4739158676192764", 10, 25, 535, "testEmail@testemail.com", "testMake", "testUser", "testPass", ConstraintViolationException.class
			},
			{
				"testName", "", "testVatNumber", "testCreditCardHolder", "VISA", "4739158676192764", 10, 25, 535, "testEmail@testemail.com", "testMake", "testUser", "testPass", ConstraintViolationException.class
			},
			{
				"testName", "testSurnames", "", "testCreditCardHolder", "VISA", "4739158676192764", 10, 25, 535, "testEmail@testemail.com", "testMake", "testUser", "testPass", ConstraintViolationException.class
			},
			{
				"testName", "testSurnames", "testVatNumber", "", "VISA", "4739158676192764", 10, 25, 535, "testEmail@testemail.com", "testMake", "testUser", "testPass", ConstraintViolationException.class
			},
			{
				"testName", "testSurnames", "testVatNumber", "testCreditCardHolder", "", "4739158676192764", 10, 25, 535, "testEmail@testemail.com", "testMake", "testUser", "testPass", ConstraintViolationException.class
			},
			{
				"testName", "testSurnames", "testVatNumber", "testCreditCardHolder", "VISA", "", 10, 25, 535, "testEmail@testemail.com", "testMake", "testUser", "testPass", ConstraintViolationException.class
			},
			{
				"testName", "testSurnames", "testVatNumber", "testCreditCardHolder", "VISA", "8534634734746", 10, 25, 535, "testEmail@testemail.com", "testMake", "testUser", "testPass", ConstraintViolationException.class
			},
			{
				"testName", "testSurnames", "testVatNumber", "testCreditCardHolder", "VISA", "4739158676192764", null, 25, 535, "testEmail@testemail.com", "testMake", "testUser", "testPass", ConstraintViolationException.class
			},
			{
				"testName", "testSurnames", "testVatNumber", "testCreditCardHolder", "VISA", "4739158676192764", 0, 25, 535, "testEmail@testemail.com", "testMake", "testUser", "testPass", ConstraintViolationException.class
			},
			{
				"testName", "testSurnames", "testVatNumber", "testCreditCardHolder", "VISA", "4739158676192764", 13, 25, 535, "testEmail@testemail.com", "testMake", "testUser", "testPass", ConstraintViolationException.class
			},
			{
				"testName", "testSurnames", "testVatNumber", "testCreditCardHolder", "VISA", "4739158676192764", 10, null, 535, "testEmail@testemail.com", "testMake", "testUser", "testPass", ConstraintViolationException.class
			},
			{
				"testName", "testSurnames", "testVatNumber", "testCreditCardHolder", "VISA", "4739158676192764", 10, -1, 535, "testEmail@testemail.com", "testMake", "testUser", "testPass", ConstraintViolationException.class
			},
			{
				"testName", "testSurnames", "testVatNumber", "testCreditCardHolder", "VISA", "4739158676192764", 10, 100, 535, "testEmail@testemail.com", "testMake", "testUser", "testPass", ConstraintViolationException.class
			},
			{
				"testName", "testSurnames", "testVatNumber", "testCreditCardHolder", "VISA", "4739158676192764", 10, 25, null, "testEmail@testemail.com", "testMake", "testUser", "testPass", ConstraintViolationException.class
			},
			{
				"testName", "testSurnames", "testVatNumber", "testCreditCardHolder", "VISA", "4739158676192764", 10, 25, 99, "testEmail@testemail.com", "testMake", "testUser", "testPass", ConstraintViolationException.class
			},
			{
				"testName", "testSurnames", "testVatNumber", "testCreditCardHolder", "VISA", "4739158676192764", 10, 25, 1000, "testEmail@testemail.com", "testMake", "testUser", "testPass", ConstraintViolationException.class
			},
			{
				"testName", "testSurnames", "testVatNumber", "testCreditCardHolder", "VISA", "4739158676192764", 10, 25, 535, "", "testMake", "testUser", "testPass", ConstraintViolationException.class
			},
			{
				"testName", "testSurnames", "testVatNumber", "testCreditCardHolder", "VISA", "4739158676192764", 10, 25, 535, "testEmail", "testMake", "testUser", "testPass", ConstraintViolationException.class
			},
			{
				"testName", "testSurnames", "testVatNumber", "testCreditCardHolder", "VISA", "4739158676192764", 10, 25, 535, "testEmail@testemail.com", "", "testUser", "testPass", ConstraintViolationException.class
			},
			{
				"testName", "testSurnames", "testVatNumber", "testCreditCardHolder", "VISA", "4739158676192764", 10, 25, 535, "testEmail@testemail.com", "testMake", "", "testPass", ConstraintViolationException.class
			},
			{
				"testName", "testSurnames", "testVatNumber", "testCreditCardHolder", "VISA", "4739158676192764", 10, 25, 535, "testEmail@testemail.com", "testMake", "test", "testPass", ConstraintViolationException.class
			},
			{
				"testName", "testSurnames", "testVatNumber", "testCreditCardHolder", "VISA", "4739158676192764", 10, 25, 535, "testEmail@testemail.com", "testMake", "testUsertestUsertestUsertestUsertestUsertestUsertestUsertestUser", "testPass",
				ConstraintViolationException.class
			}, {
				"testName", "testSurnames", "testVatNumber", "testCreditCardHolder", "VISA", "4739158676192764", 10, 25, 535, "testEmail@testemail.com", "testMake", "provider1", "testPass", DataIntegrityViolationException.class
			}, {
				"testName", "testSurnames", "testVatNumber", "testCreditCardHolder", "VISA", "testCreditCardNumber", 10, 25, 535, "testEmail@testemail.com", "testMake", "testUser", "testPass", IllegalArgumentException.class
			}, {
				"testName", "testSurnames", "testVatNumber", "testCreditCardHolder", "VISA", "4739158676192764", 2, 19, 535, "testEmail@testemail.com", "testMake", "testUser", "testPass", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.registerAsProviderTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (Integer) testingData[i][6],
				(Integer) testingData[i][7], (Integer) testingData[i][8], (String) testingData[i][9], (String) testingData[i][10], (String) testingData[i][11], (String) testingData[i][12], (Class<?>) testingData[i][13]);
	}

	/**
	 * @author Rubén Bueno
	 *         Requisito funcional: 8.2 (Acme-Hacker-Rank)
	 *         Caso de uso: editar sus datos estando logeado
	 *         Tests positivos: 1
	 *         *** 1. Editar sus datos correctamente
	 *         Tests negativos: 22
	 *         *** 1. Intento de edición de datos de un actor que no es el logeado
	 *         *** 2. Intento de edición como "Provider" con el nombre vacío
	 *         *** 3. Intento de edición como "Provider" con los apellidos vacíos
	 *         *** 4. Intento de edición como "Provider" con el número de VAT vacío
	 *         *** 5. Intento de edición como "Provider" cuya "CreditCard" tiene el propietario vacío
	 *         *** 6. Intento de edición como "Provider" cuya "CreditCard" tiene la marca vacío
	 *         *** 7. Intento de edición como "Provider" cuya "CreditCard" tiene el número vacío
	 *         *** 8. Intento de edición como "Provider" cuya "CreditCard" tiene un número que no es de tarjeta de crédito
	 *         *** 9. Intento de edición como "Provider" cuya "CreditCard" tiene el mes de caducidad nulo
	 *         *** 10. Intento de edición como "Provider" cuya "CreditCard" tiene un mes de caducidad menor a 1
	 *         *** 11. Intento de edición como "Provider" cuya "CreditCard" tiene un mes de caducidad mayor a 12
	 *         *** 12. Intento de edición como "Provider" cuya "CreditCard" tiene el año de caducidad nulo
	 *         *** 13. Intento de edición como "Provider" cuya "CreditCard" tiene un año de caducidad menor a 0
	 *         *** 14. Intento de edición como "Provider" cuya "CreditCard" tiene un año de caducidad mayor a 99
	 *         *** 15. Intento de edición como "Provider" cuya "CreditCard" tiene el CVV nulo
	 *         *** 16. Intento de edición como "Provider" cuya "CreditCard" tiene un CVV menor a 100
	 *         *** 17. Intento de edición como "Provider" cuya "CreditCard" tiene un CVV mayor a 999
	 *         *** 18. Intento de edición como "Provider" con el email vacío
	 *         *** 19. Intento de edición como "Provider" con el email sin cumplir el patrón adecuado
	 *         *** 20. Intento de edición como "Provider" con el nombre comercial vacío
	 *         *** 21. Intento de edición como "Provider" cuya "CreditCard" tiene un número no numérico
	 *         *** 22. Intento de edición como "Provider" cuya "CreditCard" está caducada
	 *         Analisis de cobertura de sentencias: 100% 118/118 instrucciones
	 *         Analisis de cobertura de datos: alto
	 */
	@Test
	public void driverEditData() {
		final Object testingData[][] = {
			{
				"provider1", "provider1", "testName", "testSurnames", "testVatNumber", "testCreditCardHolder", "VISA", "4739158676192764", 10, 25, 535, "testEmail@testemail.com", "testMake", null
			}, {
				"provider1", "provider2", "testName", "testSurnames", "testVatNumber", "testCreditCardHolder", "VISA", "4739158676192764", 10, 25, 535, "testEmail@testemail.com", "testMake", IllegalArgumentException.class
			}, {
				"provider1", "provider1", "", "testSurnames", "testVatNumber", "testCreditCardHolder", "VISA", "4739158676192764", 10, 25, 535, "testEmail@testemail.com", "testMake", ConstraintViolationException.class
			}, {
				"provider1", "provider1", "testName", "", "testVatNumber", "testCreditCardHolder", "VISA", "4739158676192764", 10, 25, 535, "testEmail@testemail.com", "testMake", ConstraintViolationException.class
			}, {
				"provider1", "provider1", "testName", "testSurnames", "", "testCreditCardHolder", "VISA", "4739158676192764", 10, 25, 535, "testEmail@testemail.com", "testMake", ConstraintViolationException.class
			}, {
				"provider1", "provider1", "testName", "testSurnames", "testVatNumber", "", "VISA", "4739158676192764", 10, 25, 535, "testEmail@testemail.com", "testMake", ConstraintViolationException.class
			}, {
				"provider1", "provider1", "testName", "testSurnames", "testVatNumber", "testCreditCardHolder", "", "4739158676192764", 10, 25, 535, "testEmail@testemail.com", "testMake", ConstraintViolationException.class
			}, {
				"provider1", "provider1", "testName", "testSurnames", "testVatNumber", "testCreditCardHolder", "VISA", "", 10, 25, 535, "testEmail@testemail.com", "testMake", ConstraintViolationException.class
			}, {
				"provider1", "provider1", "testName", "testSurnames", "testVatNumber", "testCreditCardHolder", "VISA", "8534634734746", 10, 25, 535, "testEmail@testemail.com", "testMake", ConstraintViolationException.class
			}, {
				"provider1", "provider1", "testName", "testSurnames", "testVatNumber", "testCreditCardHolder", "VISA", "4739158676192764", null, 25, 535, "testEmail@testemail.com", "testMake", ConstraintViolationException.class
			}, {
				"provider1", "provider1", "testName", "testSurnames", "testVatNumber", "testCreditCardHolder", "VISA", "4739158676192764", 0, 25, 535, "testEmail@testemail.com", "testMake", ConstraintViolationException.class
			}, {
				"provider1", "provider1", "testName", "testSurnames", "testVatNumber", "testCreditCardHolder", "VISA", "4739158676192764", 13, 25, 535, "testEmail@testemail.com", "testMake", ConstraintViolationException.class
			}, {
				"provider1", "provider1", "testName", "testSurnames", "testVatNumber", "testCreditCardHolder", "VISA", "4739158676192764", 10, null, 535, "testEmail@testemail.com", "testMake", ConstraintViolationException.class
			}, {
				"provider1", "provider1", "testName", "testSurnames", "testVatNumber", "testCreditCardHolder", "VISA", "4739158676192764", 10, -1, 535, "testEmail@testemail.com", "testMake", ConstraintViolationException.class
			}, {
				"provider1", "provider1", "testName", "testSurnames", "testVatNumber", "testCreditCardHolder", "VISA", "4739158676192764", 10, 100, 535, "testEmail@testemail.com", "testMake", ConstraintViolationException.class
			}, {
				"provider1", "provider1", "testName", "testSurnames", "testVatNumber", "testCreditCardHolder", "VISA", "4739158676192764", 10, 25, null, "testEmail@testemail.com", "testMake", ConstraintViolationException.class
			}, {
				"provider1", "provider1", "testName", "testSurnames", "testVatNumber", "testCreditCardHolder", "VISA", "4739158676192764", 10, 25, 99, "testEmail@testemail.com", "testMake", ConstraintViolationException.class
			}, {
				"provider1", "provider1", "testName", "testSurnames", "testVatNumber", "testCreditCardHolder", "VISA", "4739158676192764", 10, 25, 1000, "testEmail@testemail.com", "testMake", ConstraintViolationException.class
			}, {
				"provider1", "provider1", "testName", "testSurnames", "testVatNumber", "testCreditCardHolder", "VISA", "4739158676192764", 10, 25, 535, "", "testMake", ConstraintViolationException.class
			}, {
				"provider1", "provider1", "testName", "testSurnames", "testVatNumber", "testCreditCardHolder", "VISA", "4739158676192764", 10, 25, 535, "testEmail", "testMake", ConstraintViolationException.class
			}, {
				"provider1", "provider1", "testName", "testSurnames", "testVatNumber", "testCreditCardHolder", "VISA", "4739158676192764", 10, 25, 535, "testEmail@testemail.com", "", ConstraintViolationException.class
			}, {
				"provider1", "provider1", "testName", "testSurnames", "testVatNumber", "testCreditCardHolder", "VISA", "testCreditCardNumber", 10, 25, 535, "testEmail@testemail.com", "testMake", IllegalArgumentException.class
			}, {
				"provider1", "provider1", "testName", "testSurnames", "testVatNumber", "testCreditCardHolder", "VISA", "4739158676192764", 2, 19, 535, "testEmail@testemail.com", "testMake", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.editDataTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (Integer) testingData[i][8], (Integer) testingData[i][9], (Integer) testingData[i][10], (String) testingData[i][11], (String) testingData[i][12], (Class<?>) testingData[i][13]);
	}

	/**
	 * @author Rubén Bueno
	 *         Requisito funcional: 9.2 (Acme-Rookies)
	 *         Caso de uso: listar los "Items" disponibles sin estar logeado y navegar hacia su "Provider"
	 *         Tests positivos: 1
	 *         *** 1. Listar los "Items" disponibles sin estar logeado y navegar hacia su "Provider"
	 *         Tests negativos: 1
	 *         *** 1. Intento de listar los "Items" disponibles sin estar logeado y navegar hacia su "Provider" de un "Item" inexistente
	 *         Analisis de cobertura de sentencias: 100% 7/7 instrucciones
	 *         Analisis de cobertura de datos: alto
	 */
	@Test
	public void driverNavegateProviderByItem() {

		final Object testingData[][] = {
			{
				"item1", null
			}, {
				"item2432", AssertionError.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.navegateProviderByItemTemplate((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	// Template methods ------------------------------------------------------

	protected void registerAsProviderTemplate(final String name, final String surnames, final String VATNumber, final String creditCardHolder, final String creditCardMakeCreditCard, final String creditCardNumber, final Integer creditCardExpirationMonth,
		final Integer creditCardExpirationYear, final Integer creditCardCVV, final String email, final String make, final String username, final String password, final Class<?> expected) {
		Class<?> caught = null;
		Provider provider;

		super.startTransaction();

		try {
			provider = this.providerService.create();
			final CreditCard creditCard = new CreditCard();
			creditCard.setHolder(creditCardHolder);
			creditCard.setMakeCreditCard(creditCardMakeCreditCard);
			creditCard.setNumber(creditCardNumber);
			creditCard.setExpirationMonth(creditCardExpirationMonth);
			creditCard.setExpirationYear(creditCardExpirationYear);
			creditCard.setCvv(creditCardCVV);
			provider.setName(name);
			provider.setSurnames(surnames);
			provider.setVATNumber(VATNumber);
			provider.setCreditCard(creditCard);
			provider.setEmail(email);
			provider.setMake(make);
			provider.getUserAccount().setUsername(username);
			provider.getUserAccount().setPassword(password);
			this.providerService.save(provider);
			this.providerService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
			this.entityManager.clear();
		}

		this.checkExceptions(expected, caught);
		super.rollbackTransaction();
	}

	protected void editDataTemplate(final String username, final String actorData, final String name, final String surnames, final String VATNumber, final String creditCardHolder, final String creditCardMakeCreditCard, final String creditCardNumber,
		final Integer creditCardExpirationMonth, final Integer creditCardExpirationYear, final Integer creditCardCVV, final String email, final String make, final Class<?> expected) {
		Class<?> caught = null;
		Provider provider;

		super.startTransaction();

		try {
			super.authenticate(username);
			provider = this.providerService.findOne(super.getEntityId(actorData));
			final CreditCard creditCard = new CreditCard();
			creditCard.setHolder(creditCardHolder);
			creditCard.setMakeCreditCard(creditCardMakeCreditCard);
			creditCard.setNumber(creditCardNumber);
			creditCard.setExpirationMonth(creditCardExpirationMonth);
			creditCard.setExpirationYear(creditCardExpirationYear);
			creditCard.setCvv(creditCardCVV);
			provider.setName(name);
			provider.setSurnames(surnames);
			provider.setVATNumber(VATNumber);
			provider.setCreditCard(creditCard);
			provider.setEmail(email);
			provider.setMake(make);
			this.providerService.save(provider);
			this.providerService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
			this.entityManager.clear();
		}

		super.unauthenticate();
		this.checkExceptions(expected, caught);
		super.rollbackTransaction();
	}

	protected void navegateProviderByItemTemplate(final String item, final Class<?> expected) {
		Class<?> caught = null;
		Provider provider;

		super.startTransaction();

		try {
			provider = this.providerService.findProviderByItemId(super.getEntityId(item));
			Assert.notNull(provider);
		} catch (final Throwable oops) {
			caught = oops.getClass();
			this.entityManager.clear();
		}

		this.checkExceptions(expected, caught);
		super.rollbackTransaction();
	}

}
