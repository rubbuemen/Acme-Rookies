/*
 * PersonalDataRepository.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Provider;

@Repository
public interface ProviderRepository extends JpaRepository<Provider, Integer> {

	@Query("select p from Provider p join p.items i where i.id = ?1")
	Provider findProviderByItemId(int itemId);

	@Query("select p from Provider p join p.sponsorships s where s.id = ?1")
	Provider findProviderBySponsorshipId(int sponsorshipId);

	@Query("select distinct p from Provider p join p.sponsorships s join s.position pos where pos.id = ?1")
	Collection<Provider> findProvidersByPositionId(int positionId);

}
