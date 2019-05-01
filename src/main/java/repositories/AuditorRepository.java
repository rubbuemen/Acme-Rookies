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

import domain.Auditor;

@Repository
public interface AuditorRepository extends JpaRepository<Auditor, Integer> {

	@Query("select a from Auditor a join a.audits auds where auds.id = ?1")
	Auditor findAuditorByAuditId(int auditId);

	@Query("select distinct a from Auditor a join a.audits auds join auds.position p where p.id = ?1")
	Collection<Auditor> findAuditorsByPositionId(int positionId);

}
