/*
 * RookieRepository.java
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

import domain.Rookie;

@Repository
public interface RookieRepository extends JpaRepository<Rookie, Integer> {

	@Query("select h from Rookie h join h.applications a where a.id = ?1")
	Rookie findRookieByApplicationId(int applicationId);

	@Query("select h from Curricula c join c.rookie h where c.id = ?1")
	Rookie findRookieByCurriculaId(int curriculaId);

	@Query("select h from Curricula c join c.rookie h join c.personalData pd where pd.id = ?1")
	Rookie findRookieByPersonalDataId(int personalDataId);

	@Query("select h from Curricula c join c.rookie h join c.positionDatas pd where pd.id = ?1")
	Rookie findRookieByPositionDataId(int positionDataId);

	@Query("select h from Curricula c join c.rookie h join c.educationDatas ed where ed.id = ?1")
	Rookie findRookieByEducationDataId(int educationDataId);

	@Query("select h from Curricula c join c.rookie h join c.miscellaneousDatas md where md.id = ?1")
	Rookie findRookieByMiscellaneousDataId(int miscellaneousDataId);

	@Query("select distinct h from Rookie h join h.applications a join a.position p where p.id = ?1")
	Collection<Rookie> findRookiesByPositionId(int positionId);

	@Query("select distinct h from Rookie h join h.applications a join a.problem p where p.id = ?1")
	Collection<Rookie> findRookiesByProblemId(int problemId);

}
