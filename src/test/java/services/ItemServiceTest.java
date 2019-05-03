
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
import domain.Item;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ItemServiceTest extends AbstractTest {

	// SUT Services
	@Autowired
	private ItemService	itemService;

	@PersistenceContext
	EntityManager		entityManager;


	/**
	 * @author Rubén Bueno
	 *         Requisito funcional: 9.1 (Acme-Rookies)
	 *         Caso de uso: listar los "Providers" disponibles sin estar logeado y navegar hacia sus "Items"
	 *         Tests positivos: 1
	 *         *** 1. Listar los "Providers" disponibles sin estar logeado y navegar hacia sus "Items"
	 *         Tests negativos: 1
	 *         *** 1. Intento de listar los "Providers" disponibles sin estar logeado y navegar hacia sus "Items" de un "Provider" inexistente
	 *         Analisis de cobertura de sentencias: 100% 9/9 instrucciones
	 *         Analisis de cobertura de datos: alto
	 */
	@Test
	public void driverNavegateItemsByProvider() {

		final Object testingData[][] = {
			{
				"provider1", null
			}, {
				"provider32432", AssertionError.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.navegateItemsByProviderTemplate((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	/**
	 * @author Rubén Bueno
	 *         Requisito funcional: 10.1 (Acme-Rookies)
	 *         Caso de uso: listar "Items"
	 *         Tests positivos: 1
	 *         *** 1. Listar "Items" correctamente
	 *         Tests negativos: 1
	 *         *** 1. Intento de listar "Items" con una autoridad no permitida
	 *         Analisis de cobertura de sentencias: 100% 23/23 instrucciones
	 *         Analisis de cobertura de datos: alto
	 */
	@Test
	public void driverListItemsProvider() {
		final Object testingData[][] = {
			{
				"provider1", null
			}, {
				"rookie1", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.listItemsProviderTemplate((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	/**
	 * @author Rubén Bueno
	 *         Requisito funcional: 10.1 (Acme-Rookies)
	 *         Caso de uso: crear un "Item"
	 *         Tests positivos: 1
	 *         *** 1. Crear un "Item" correctamente
	 *         Tests negativos: 6
	 *         *** 1. Intento de creación de un "Item" con una autoridad no permitida
	 *         *** 2. Intento de creación de un "Item" con nombre vacío
	 *         *** 3. Intento de creación de un "Item" con descripción vacía
	 *         *** 4. Intento de creación de un "Item" con enlace vacío
	 *         *** 5. Intento de creación de un "Item" con enlace que no es una url
	 *         *** 6. Intento de creación de un "Item" con imagen que no es una url
	 *         Analisis de cobertura de sentencias: 100% 75/75 instrucciones
	 *         Analisis de cobertura de datos: alto
	 */
	@Test
	public void driverCreateItem() {
		final Object testingData[][] = {
			{
				"provider1", "nameTest", "descriptionTest", "http://www.testEnlace.com", "http://www.testImagen.com", null
			}, {
				"rookie1", "nameTest", "descriptionTest", "http://www.testEnlace.com", "http://www.testImagen.com", IllegalArgumentException.class
			}, {
				"provider1", "", "descriptionTest", "http://www.testEnlace.com", "http://www.testImagen.com", ConstraintViolationException.class
			}, {
				"provider1", "nameTest", "", "http://www.testEnlace.com", "http://www.testImagen.com", ConstraintViolationException.class
			}, {
				"provider1", "nameTest", "descriptionTest", "", "http://www.testImagen.com", ConstraintViolationException.class
			}, {
				"provider1", "nameTest", "descriptionTest", "testEnlace", "http://www.testImagen.com", ConstraintViolationException.class
			}, {
				"provider1", "nameTest", "descriptionTest", "http://www.testEnlace.com", "testImagen", ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.createItemTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (Class<?>) testingData[i][5]);
	}

	/**
	 * @author Rubén Bueno
	 *         Requisito funcional: 10.1 (Acme-Rookies)
	 *         Caso de uso: editar un "Item"
	 *         Tests positivos: 1
	 *         *** 1. Editar un "Item" correctamente
	 *         Tests negativos: 7
	 *         *** 1. Intento de creación de un "Item" con una autoridad no permitida
	 *         *** 2. Intento de edición de un "Item" que no es de la "Provider" logeada
	 *         *** 3. Intento de edición de un "Item" con nombre vacío
	 *         *** 4. Intento de edición de un "Item" con descripción vacía
	 *         *** 5. Intento de edición de un "Item" con enlace vacío
	 *         *** 6. Intento de edición de un "Item" con enlace que no es una url
	 *         *** 7. Intento de edición de un "Item" con imagen que no es una url
	 *         Analisis de cobertura de sentencias: 100% 59/59 instrucciones
	 *         Analisis de cobertura de datos: alto
	 */

	@Test
	public void driverEditItem() {
		final Object testingData[][] = {
			{
				"provider1", "item1", "nameTest", "descriptionTest", "http://www.testEnlace.com", "http://www.testImagen.com", null
			}, {
				"rookie1", "item1", "nameTest", "descriptionTest", "http://www.testEnlace.com", "http://www.testImagen.com", IllegalArgumentException.class
			}, {
				"provider2", "item1", "nameTest", "descriptionTest", "http://www.testEnlace.com", "http://www.testImagen.com", IllegalArgumentException.class
			}, {
				"provider1", "item1", "", "descriptionTest", "http://www.testEnlace.com", "http://www.testImagen.com", ConstraintViolationException.class
			}, {
				"provider1", "item1", "nameTest", "", "http://www.testEnlace.com", "http://www.testImagen.com", ConstraintViolationException.class
			}, {
				"provider1", "item1", "nameTest", "descriptionTest", "", "http://www.testImagen.com", ConstraintViolationException.class
			}, {
				"provider1", "item1", "nameTest", "descriptionTest", "testEnlace", "http://www.testImagen.com", ConstraintViolationException.class
			}, {
				"provider1", "item1", "nameTest", "descriptionTest", "http://www.testEnlace.com", "testImagen", ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.editItemTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (Class<?>) testingData[i][6]);
	}

	/**
	 * @author Rubén Bueno
	 *         Requisito funcional: 10.1 (Acme-Rookies)
	 *         Caso de uso: eliminar un "Item"
	 *         Tests positivos: 1
	 *         *** 1. Eliminar un "Item" correctamente
	 *         Tests negativos: 2
	 *         *** 1. Intento de eliminación de un "Item" con una autoridad no permitida
	 *         *** 2. Intento de eliminación de un "Item" que no es del "Provider" logeada
	 *         Analisis de cobertura de sentencias: 98,3% 59/60 instrucciones
	 *         Analisis de cobertura de datos: alto
	 */
	@Test
	public void driverDeleteItem() {
		final Object testingData[][] = {
			{
				"provider1", "item1", null
			}, {
				"rookie1", "item1", IllegalArgumentException.class
			}, {
				"provider4", "item1", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.deleteItemTemplate((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	// Template methods ------------------------------------------------------

	protected void navegateItemsByProviderTemplate(final String provider, final Class<?> expected) {
		Class<?> caught = null;
		Collection<Item> items;

		super.startTransaction();

		try {
			items = this.itemService.findItemsByProviderId(super.getEntityId(provider));
			Assert.notNull(items);
		} catch (final Throwable oops) {
			caught = oops.getClass();
			this.entityManager.clear();
		}

		this.checkExceptions(expected, caught);
		super.rollbackTransaction();
	}

	protected void listItemsProviderTemplate(final String username, final Class<?> expected) {
		Class<?> caught = null;
		Collection<Item> items;

		super.startTransaction();

		try {
			super.authenticate(username);
			items = this.itemService.findItemsByProviderLogged();
			Assert.notNull(items);
		} catch (final Throwable oops) {
			caught = oops.getClass();
			this.entityManager.clear();
		}

		this.checkExceptions(expected, caught);
		super.unauthenticate();
		super.rollbackTransaction();
	}

	protected void createItemTemplate(final String username, final String name, final String description, final String link, final String picture, final Class<?> expected) {
		Class<?> caught = null;
		Item itemEntity;

		super.startTransaction();

		try {
			super.authenticate(username);
			itemEntity = this.itemService.create();
			itemEntity.setName(name);
			itemEntity.setDescription(description);
			itemEntity.setLink(link);
			itemEntity.setPicture(picture);
			this.itemService.save(itemEntity);
			this.itemService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
			this.entityManager.clear();
		}

		this.checkExceptions(expected, caught);
		super.unauthenticate();
		super.rollbackTransaction();
	}

	protected void editItemTemplate(final String username, final String item, final String name, final String description, final String link, final String picture, final Class<?> expected) {
		Class<?> caught = null;
		Item itemEntity;

		super.startTransaction();

		try {
			super.authenticate(username);
			itemEntity = this.itemService.findItemProviderLogged(super.getEntityId(item));
			itemEntity.setName(name);
			itemEntity.setDescription(description);
			itemEntity.setLink(link);
			itemEntity.setPicture(picture);
			this.itemService.save(itemEntity);
			this.itemService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
			this.entityManager.clear();
		}

		this.checkExceptions(expected, caught);
		super.unauthenticate();
		super.rollbackTransaction();
	}

	protected void deleteItemTemplate(final String username, final String item, final Class<?> expected) {
		Class<?> caught = null;
		Item itemEntity;

		super.startTransaction();

		try {
			super.authenticate(username);
			itemEntity = this.itemService.findItemProviderLogged(super.getEntityId(item));
			this.itemService.delete(itemEntity);
			this.itemService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
			this.entityManager.clear();
		}

		this.checkExceptions(expected, caught);
		super.unauthenticate();
		super.rollbackTransaction();
	}

}
