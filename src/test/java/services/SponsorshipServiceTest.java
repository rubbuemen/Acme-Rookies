/*
 * SampleTest.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

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
import domain.CreditCard;
import domain.Sponsorship;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class SponsorshipServiceTest extends AbstractTest {

	// SUT Services
	@Autowired
	private SponsorshipService	sponsorshipService;

	@Autowired
	private PositionService		positionService;

	@Autowired
	private ProviderService		providerService;

	@PersistenceContext
	EntityManager				entityManager;


	/**
	 * @author Rubén Bueno
	 *         Requisito funcional: 13.1 (Acme-Rookies)
	 *         Caso de uso: listar "Sponsorships" logeado como Provider
	 *         Tests positivos: 1
	 *         *** 1. Listar "Sponsorships" correctamente
	 *         Tests negativos: 1
	 *         *** 1. Intento de listar "Sponsorships" con una autoridad no permitida
	 *         Analisis de cobertura de sentencias: 100% 23/23 instrucciones
	 *         Analisis de cobertura de datos: alto
	 */
	@Test
	public void driverListSponsorships() {
		final Object testingData[][] = {
			{
				"provider1", null
			}, {
				"rookie1", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.listSponsorshipsTemplate((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	/**
	 * @author Rubén Bueno
	 *         Requisito funcional: 13.1 (Acme-Rookies)
	 *         Caso de uso: crear un "Sponsorship"
	 *         Tests positivos: 1
	 *         *** 1. Crear un "Sponsorship" correctamente
	 *         Tests negativos: 21
	 *         *** 1. Intento de creación de un "Sponsorship" con una autoridad no permitida
	 *         *** 2. Intento de creación de un "Sponsorship" con banner vacío
	 *         *** 3. Intento de creación de un "Sponsorship" con banner que no es URL
	 *         *** 4. Intento de creación de un "Sponsorship" con página de destino vacío
	 *         *** 5. Intento de creación de un "Sponsorship" con página de destino que no es URL
	 *         *** 6. Intento de creación de un "Sponsorship" cuya "CreditCard" tiene el propietario vacío
	 *         *** 7. Intento de creación de un "Sponsorship" cuya "CreditCard" tiene la marca vacío
	 *         *** 8. Intento de creación de un "Sponsorship" cuya "CreditCard" tiene el número vacío
	 *         *** 9. Intento de creación de un "Sponsorship" cuya "CreditCard" tiene un número que no es de tarjeta de crédito
	 *         *** 10. Intento de creación de un "Sponsorship" cuya "CreditCard" tiene el mes de caducidad nulo
	 *         *** 11. Intento de creación de un "Sponsorship" cuya "CreditCard" tiene un mes de caducidad menor a 1
	 *         *** 12. Intento de creación de un "Sponsorship" cuya "CreditCard" tiene un mes de caducidad mayor a 12
	 *         *** 13. Intento de creación de un "Sponsorship" cuya "CreditCard" tiene el año de caducidad nulo
	 *         *** 14. Intento de creación de un "Sponsorship" cuya "CreditCard" tiene un año de caducidad menor a 0
	 *         *** 15. Intento de creación de un "Sponsorship" cuya "CreditCard" tiene un año de caducidad mayor a 99
	 *         *** 16. Intento de creación de un "Sponsorship" cuya "CreditCard" tiene el CVV nulo
	 *         *** 17. Intento de creación de un "Sponsorship" cuya "CreditCard" tiene un CVV menor a 100
	 *         *** 18. Intento de creación de un "Sponsorship" cuya "CreditCard" tiene un CVV mayor a 999
	 *         *** 19. Intento de creación de un "Sponsorship" con un "Position" no disponible
	 *         *** 20. Intento de creación de un "Sponsorship" cuya "CreditCard" tiene un número no numérico
	 *         *** 21. Intento de creación de un "Sponsorship" cuya "CreditCard" está caducada
	 *         Analisis de cobertura de sentencias: 100% 134/134 instrucciones
	 *         Analisis de cobertura de datos: alto
	 */
	@Test
	public void driverCreateSponsorship() {
		final Object testingData[][] = {
			{
				"provider1", "position1", "http://www.testBanner.com", "http://www.testTargetPage.com", "testCreditCardHolder", "VISA", "4739158676192764", 10, 25, 535, null
			}, {
				"company1", "position1", "http://www.testBanner.com", "http://www.testTargetPage.com", "testCreditCardHolder", "VISA", "4739158676192764", 10, 25, 535, IllegalArgumentException.class
			}, {
				"provider1", "position1", "", "http://www.testTargetPage.com", "testCreditCardHolder", "VISA", "4739158676192764", 10, 25, 535, ConstraintViolationException.class
			}, {
				"provider1", "position1", "test", "http://www.testTargetPage.com", "testCreditCardHolder", "VISA", "4739158676192764", 10, 25, 535, ConstraintViolationException.class
			}, {
				"provider1", "position1", "http://www.testBanner.com", "", "testCreditCardHolder", "VISA", "4739158676192764", 10, 25, 535, ConstraintViolationException.class
			}, {
				"provider1", "position1", "http://www.testBanner.com", "test", "testCreditCardHolder", "VISA", "4739158676192764", 10, 25, 535, ConstraintViolationException.class
			}, {
				"provider1", "position1", "http://www.testBanner.com", "http://www.testTargetPage.com", "", "VISA", "4739158676192764", 10, 25, 535, ConstraintViolationException.class
			}, {
				"provider1", "position1", "http://www.testBanner.com", "http://www.testTargetPage.com", "testCreditCardHolder", "", "4739158676192764", 10, 25, 535, ConstraintViolationException.class
			}, {
				"provider1", "position1", "http://www.testBanner.com", "http://www.testTargetPage.com", "testCreditCardHolder", "VISA", "", 10, 25, 535, ConstraintViolationException.class
			}, {
				"provider1", "position1", "http://www.testBanner.com", "http://www.testTargetPage.com", "testCreditCardHolder", "VISA", "8534634734746", 10, 25, 535, ConstraintViolationException.class
			}, {
				"provider1", "position1", "http://www.testBanner.com", "http://www.testTargetPage.com", "testCreditCardHolder", "VISA", "4739158676192764", null, 25, 535, ConstraintViolationException.class
			}, {
				"provider1", "position1", "http://www.testBanner.com", "http://www.testTargetPage.com", "testCreditCardHolder", "VISA", "4739158676192764", 0, 25, 535, ConstraintViolationException.class
			}, {
				"provider1", "position1", "http://www.testBanner.com", "http://www.testTargetPage.com", "testCreditCardHolder", "VISA", "4739158676192764", 13, 25, 535, ConstraintViolationException.class
			}, {
				"provider1", "position1", "http://www.testBanner.com", "http://www.testTargetPage.com", "testCreditCardHolder", "VISA", "4739158676192764", 10, null, 535, ConstraintViolationException.class
			}, {
				"provider1", "position1", "http://www.testBanner.com", "http://www.testTargetPage.com", "testCreditCardHolder", "VISA", "4739158676192764", 10, -1, 535, ConstraintViolationException.class
			}, {
				"provider1", "position1", "http://www.testBanner.com", "http://www.testTargetPage.com", "testCreditCardHolder", "VISA", "4739158676192764", 10, 100, 535, ConstraintViolationException.class
			}, {
				"provider1", "position1", "http://www.testBanner.com", "http://www.testTargetPage.com", "testCreditCardHolder", "VISA", "4739158676192764", 10, 25, null, ConstraintViolationException.class
			}, {
				"provider1", "position1", "http://www.testBanner.com", "http://www.testTargetPage.com", "testCreditCardHolder", "VISA", "4739158676192764", 10, 25, 99, ConstraintViolationException.class
			}, {
				"provider1", "position1", "http://www.testBanner.com", "http://www.testTargetPage.com", "testCreditCardHolder", "VISA", "4739158676192764", 10, 25, 1000, ConstraintViolationException.class
			}, {
				"provider1", "position6", "http://www.testBanner.com", "http://www.testTargetPage.com", "testCreditCardHolder", "VISA", "4739158676192764", 10, 25, 535, IllegalArgumentException.class
			}, {
				"provider1", "position1", "http://www.testBanner.com", "http://www.testTargetPage.com", "testCreditCardHolder", "VISA", "testCreditCardNumber", 10, 25, 535, IllegalArgumentException.class
			}, {
				"provider1", "position1", "http://www.testBanner.com", "http://www.testTargetPage.com", "testCreditCardHolder", "VISA", "4739158676192764", 2, 19, 535, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.createSponsorshipTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(Integer) testingData[i][7], (Integer) testingData[i][8], (Integer) testingData[i][9], (Class<?>) testingData[i][10]);
	}

	/**
	 * @author Rubén Bueno
	 *         Requisito funcional: 13.1 (Acme-Rookies)
	 *         Caso de uso: editar un "Sponsorship"
	 *         Tests positivos: 1
	 *         *** 1. Editar un "Sponsorship" correctamente
	 *         Tests negativos: 21
	 *         *** 1. Intento de edición de un "Sponsorship" con una autoridad no permitida
	 *         *** 2. Intento de edición de un "Sponsorship" que no es del "Provider" logeado
	 *         *** 3. Intento de edición de un "Sponsorship" con banner vacío
	 *         *** 4. Intento de edición de un "Sponsorship" con banner que no es URL
	 *         *** 5. Intento de edición de un "Sponsorship" con página de destino vacío
	 *         *** 6. Intento de edición de un "Sponsorship" con página de destino que no es URL
	 *         *** 7. Intento de edición de un "Sponsorship" cuya "CreditCard" tiene el propietario vacío
	 *         *** 8. Intento de edición de un "Sponsorship" cuya "CreditCard" tiene la marca vacío
	 *         *** 9. Intento de edición de un "Sponsorship" cuya "CreditCard" tiene el número vacío
	 *         *** 10. Intento de edición de un "Sponsorship" cuya "CreditCard" tiene un número que no es de tarjeta de crédito
	 *         *** 11. Intento de edición de un "Sponsorship" cuya "CreditCard" tiene el mes de caducidad nulo
	 *         *** 12. Intento de edición de un "Sponsorship" cuya "CreditCard" tiene un mes de caducidad menor a 1
	 *         *** 13. Intento de edición de un "Sponsorship" cuya "CreditCard" tiene un mes de caducidad mayor a 12
	 *         *** 14. Intento de edición de un "Sponsorship" cuya "CreditCard" tiene el año de caducidad nulo
	 *         *** 15. Intento de edición de un "Sponsorship" cuya "CreditCard" tiene un año de caducidad menor a 0
	 *         *** 16. Intento de edición de un "Sponsorship" cuya "CreditCard" tiene un año de caducidad mayor a 99
	 *         *** 17. Intento de edición de un "Sponsorship" cuya "CreditCard" tiene el CVV nulo
	 *         *** 18. Intento de edición de un "Sponsorship" cuya "CreditCard" tiene un CVV menor a 100
	 *         *** 19. Intento de edición de un "Sponsorship" cuya "CreditCard" tiene un CVV mayor a 999
	 *         *** 20. Intento de edición de un "Sponsorship" cuya "CreditCard" tiene un número no numérico
	 *         *** 21. Intento de edición de un "Sponsorship" cuya "CreditCard" está caducada
	 *         Analiis de cobertura de sentencias: 100% 114/114 instrucciones
	 *         Analisis de cobertura de datos: alto
	 */
	@Test
	public void driverEditSponsorship() {
		final Object testingData[][] = {
			{
				"provider1", "sponsorship1", "http://www.testBanner.com", "http://www.testTargetPage.com", "testCreditCardHolder", "VISA", "4739158676192764", 10, 25, 535, null
			}, {
				"company1", "sponsorship1", "http://www.testBanner.com", "http://www.testTargetPage.com", "testCreditCardHolder", "VISA", "4739158676192764", 10, 25, 535, IllegalArgumentException.class
			}, {
				"provider3", "sponsorship1", "http://www.testBanner.com", "http://www.testTargetPage.com", "testCreditCardHolder", "VISA", "4739158676192764", 10, 25, 535, IllegalArgumentException.class
			}, {
				"provider1", "sponsorship1", "", "http://www.testTargetPage.com", "testCreditCardHolder", "VISA", "4739158676192764", 10, 25, 535, ConstraintViolationException.class
			}, {
				"provider1", "sponsorship1", "test", "http://www.testTargetPage.com", "testCreditCardHolder", "VISA", "4739158676192764", 10, 25, 535, ConstraintViolationException.class
			}, {
				"provider1", "sponsorship1", "http://www.testBanner.com", "", "testCreditCardHolder", "VISA", "4739158676192764", 10, 25, 535, ConstraintViolationException.class
			}, {
				"provider1", "sponsorship1", "http://www.testBanner.com", "test", "testCreditCardHolder", "VISA", "4739158676192764", 10, 25, 535, ConstraintViolationException.class
			}, {
				"provider1", "sponsorship1", "http://www.testBanner.com", "http://www.testTargetPage.com", "", "VISA", "4739158676192764", 10, 25, 535, ConstraintViolationException.class
			}, {
				"provider1", "sponsorship1", "http://www.testBanner.com", "http://www.testTargetPage.com", "testCreditCardHolder", "", "4739158676192764", 10, 25, 535, ConstraintViolationException.class
			}, {
				"provider1", "sponsorship1", "http://www.testBanner.com", "http://www.testTargetPage.com", "testCreditCardHolder", "VISA", "", 10, 25, 535, ConstraintViolationException.class
			}, {
				"provider1", "sponsorship1", "http://www.testBanner.com", "http://www.testTargetPage.com", "testCreditCardHolder", "VISA", "8534634734746", 10, 25, 535, ConstraintViolationException.class
			}, {
				"provider1", "sponsorship1", "http://www.testBanner.com", "http://www.testTargetPage.com", "testCreditCardHolder", "VISA", "4739158676192764", null, 25, 535, ConstraintViolationException.class
			}, {
				"provider1", "sponsorship1", "http://www.testBanner.com", "http://www.testTargetPage.com", "testCreditCardHolder", "VISA", "4739158676192764", 0, 25, 535, ConstraintViolationException.class
			}, {
				"provider1", "sponsorship1", "http://www.testBanner.com", "http://www.testTargetPage.com", "testCreditCardHolder", "VISA", "4739158676192764", 13, 25, 535, ConstraintViolationException.class
			}, {
				"provider1", "sponsorship1", "http://www.testBanner.com", "http://www.testTargetPage.com", "testCreditCardHolder", "VISA", "4739158676192764", 10, null, 535, ConstraintViolationException.class
			}, {
				"provider1", "sponsorship1", "http://www.testBanner.com", "http://www.testTargetPage.com", "testCreditCardHolder", "VISA", "4739158676192764", 10, -1, 535, ConstraintViolationException.class
			}, {
				"provider1", "sponsorship1", "http://www.testBanner.com", "http://www.testTargetPage.com", "testCreditCardHolder", "VISA", "4739158676192764", 10, 100, 535, ConstraintViolationException.class
			}, {
				"provider1", "sponsorship1", "http://www.testBanner.com", "http://www.testTargetPage.com", "testCreditCardHolder", "VISA", "4739158676192764", 10, 25, null, ConstraintViolationException.class
			}, {
				"provider1", "sponsorship1", "http://www.testBanner.com", "http://www.testTargetPage.com", "testCreditCardHolder", "VISA", "4739158676192764", 10, 25, 99, ConstraintViolationException.class
			}, {
				"provider1", "sponsorship1", "http://www.testBanner.com", "http://www.testTargetPage.com", "testCreditCardHolder", "VISA", "4739158676192764", 10, 25, 1000, ConstraintViolationException.class
			}, {
				"provider1", "sponsorship1", "http://www.testBanner.com", "http://www.testTargetPage.com", "testCreditCardHolder", "VISA", "testCreditCardNumber", 10, 25, 535, IllegalArgumentException.class
			}, {
				"provider1", "sponsorship1", "http://www.testBanner.com", "http://www.testTargetPage.com", "testCreditCardHolder", "VISA", "4739158676192764", 2, 19, 535, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.editSponsorshipTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(Integer) testingData[i][7], (Integer) testingData[i][8], (Integer) testingData[i][9], (Class<?>) testingData[i][10]);
	}

	/**
	 * @author Rubén Bueno
	 *         Requisito funcional: 13.1 (Acme-Rookies)
	 *         Caso de uso: eliminar un "Sponsorship"
	 *         Tests positivos: 1
	 *         *** 1. Eliminar un "Sponsorship" correctamente
	 *         Tests negativos: 2
	 *         *** 1. Intento de eliminación de un "Sponsorship" con una autoridad no permitida
	 *         *** 2. Intento de eliminación de un "Sponsorship" que no es del "Sponsor" logeado
	 *         Analisis de cobertura de sentencias: 98,7% 77/78 instrucciones
	 *         Analisis de cobertura de datos: alto
	 */
	@Test
	public void driverDeleteSponsorship() {
		final Object testingData[][] = {
			{
				"provider1", "sponsorship1", null
			}, {
				"rookie1", "sponsorship1", IllegalArgumentException.class
			}, {
				"provider3", "sponsorship1", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.deleteSponsorshipTemplate((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	// Template methods ------------------------------------------------------

	protected void listSponsorshipsTemplate(final String username, final Class<?> expected) {
		Class<?> caught = null;
		Collection<Sponsorship> sponsorships;

		super.startTransaction();

		try {
			super.authenticate(username);
			sponsorships = this.sponsorshipService.findSponsorshipsByProviderLogged();
			Assert.notNull(sponsorships);
		} catch (final Throwable oops) {
			caught = oops.getClass();
			this.entityManager.clear();
		}

		this.checkExceptions(expected, caught);
		super.unauthenticate();
		super.rollbackTransaction();
	}

	protected void createSponsorshipTemplate(final String username, final String position, final String banner, final String targetPage, final String creditCardHolder, final String creditCardMake, final String creditCardNumber,
		final Integer creditCardExpirationMonth, final Integer creditCardExpirationYear, final Integer creditCardCVV, final Class<?> expected) {
		Class<?> caught = null;
		Sponsorship sponsorship;

		super.startTransaction();

		try {
			super.authenticate(username);
			sponsorship = this.sponsorshipService.create();
			final CreditCard creditCard = new CreditCard();
			creditCard.setHolder(creditCardHolder);
			creditCard.setMakeCreditCard(creditCardMake);
			creditCard.setNumber(creditCardNumber);
			creditCard.setExpirationMonth(creditCardExpirationMonth);
			creditCard.setExpirationYear(creditCardExpirationYear);
			creditCard.setCvv(creditCardCVV);
			sponsorship.setBanner(banner);
			sponsorship.setTargetPage(targetPage);
			sponsorship.setCreditCard(creditCard);
			sponsorship.setPosition(this.positionService.findOne(super.getEntityId(position)));
			sponsorship.setProvider(this.providerService.findOne(super.getEntityId(username)));
			this.sponsorshipService.save(sponsorship);
			this.sponsorshipService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
			this.entityManager.clear();
		}

		this.checkExceptions(expected, caught);
		super.unauthenticate();
		super.rollbackTransaction();
	}

	protected void editSponsorshipTemplate(final String username, final String sponsorship, final String banner, final String targetPage, final String creditCardHolder, final String creditCardMake, final String creditCardNumber,
		final Integer creditCardExpirationMonth, final Integer creditCardExpirationYear, final Integer creditCardCVV, final Class<?> expected) {
		Class<?> caught = null;
		Sponsorship sponsorshipEntity;

		super.startTransaction();

		try {
			super.authenticate(username);
			sponsorshipEntity = this.sponsorshipService.findSponsorshipProviderLogged(super.getEntityId(sponsorship));
			final CreditCard creditCard = new CreditCard();
			creditCard.setHolder(creditCardHolder);
			creditCard.setMakeCreditCard(creditCardMake);
			creditCard.setNumber(creditCardNumber);
			creditCard.setExpirationMonth(creditCardExpirationMonth);
			creditCard.setExpirationYear(creditCardExpirationYear);
			creditCard.setCvv(creditCardCVV);
			sponsorshipEntity.setBanner(banner);
			sponsorshipEntity.setTargetPage(targetPage);
			sponsorshipEntity.setCreditCard(creditCard);
			sponsorshipEntity.setProvider(this.providerService.findOne(super.getEntityId(username)));
			this.sponsorshipService.save(sponsorshipEntity);
			this.sponsorshipService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
			this.entityManager.clear();
		}

		this.checkExceptions(expected, caught);
		super.unauthenticate();
		super.rollbackTransaction();
	}

	protected void deleteSponsorshipTemplate(final String username, final String sponsorship, final Class<?> expected) {
		Class<?> caught = null;
		Sponsorship sponsorshipEntity;

		super.startTransaction();

		try {
			super.authenticate(username);
			sponsorshipEntity = this.sponsorshipService.findSponsorshipProviderLogged(super.getEntityId(sponsorship));
			this.sponsorshipService.delete(sponsorshipEntity);
			this.sponsorshipService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
			this.entityManager.clear();
		}

		this.checkExceptions(expected, caught);
		super.unauthenticate();
		super.rollbackTransaction();
	}
}
