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

import domain.Audit;

@Repository
public interface AuditRepository extends JpaRepository<Audit, Integer> {

	@Query("select auds from Auditor a join a.audits auds where a.id = ?1")
	Collection<Audit> findAuditsByAuditorId(int auditorId);

	@Query("select auds from Audit auds join auds.position p where auds.isFinalMode = 1 and p in (select p from Company c join c.positions p where c.id = ?1)")
	Collection<Audit> findAuditsByCompanyId(int companyId);

	@Query("select auds from Audit auds join auds.position p where auds.isFinalMode = 1 and p.id = ?1")
	Collection<Audit> findAuditsFinalModeByPositionId(int positionId);

}
