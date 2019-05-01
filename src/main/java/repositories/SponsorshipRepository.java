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

import domain.Sponsorship;

@Repository
public interface SponsorshipRepository extends JpaRepository<Sponsorship, Integer> {

	@Query("select s from Provider p join p.sponsorships s where p.id = ?1")
	Collection<Sponsorship> findSponsorshipsByProviderId(int providerId);

	@Query("select s from Position p join p.sponsorships s where p.id = ?1")
	Collection<Sponsorship> findSponsorshipsByPositionId(int positionId);

}
