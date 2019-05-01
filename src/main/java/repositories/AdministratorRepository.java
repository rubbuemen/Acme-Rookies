/*
 * AdministratorRepository.java
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

import domain.Administrator;
import domain.Company;
import domain.Position;
import domain.Provider;
import domain.Rookie;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Integer> {

	@Query("select avg(c.positions.size), min(c.positions.size), max(c.positions.size), stddev(c.positions.size) from Company c")
	String dashboardQueryC1();

	@Query("select avg(h.applications.size), min(h.applications.size), max(h.applications.size), stddev(h.applications.size) from Rookie h")
	String dashboardQueryC2();

	@Query("select c from Company c join c.positions p group by c order by sum(p) desc")
	Collection<Company> dashboardQueryC3();

	@Query("select h from Rookie h join h.applications a group by h order by sum(a) desc")
	Collection<Rookie> dashboardQueryC4();

	@Query("select avg(p.salary), min(p.salary), max(p.salary), stddev(p.salary) from Position p")
	String dashboardQueryC5();

	@Query("select p1 from Position p1 where p1.salary = (select max(p2.salary) from Position p2)")
	Collection<Position> dashboardQueryC6_1();

	@Query("select p1 from Position p1 where p1.salary = (select min(p2.salary) from Position p2)")
	Collection<Position> dashboardQueryC6_2();

	@Query("select avg(1.0*(select count(c) from Curricula c where c.rookie.id = h.id and c.isCopy = 0)), min(1*(select count(c) from Curricula c where c.rookie.id = h.id and c.isCopy = 0)), max(1*(select count(c) from Curricula c where c.rookie.id = h.id and c.isCopy = 0)), stddev(1.0*(select count(c) from Curricula c where c.rookie.id = h.id and c.isCopy = 0)) from Rookie h")
	String dashboardQueryB1();

	@Query("select avg(f.positions.size), min(f.positions.size), max(f.positions.size), stddev(f.positions.size) from Finder f")
	String dashboardQueryB2();

	@Query("select sum(case when f.positions.size = 0 then 1.0 else 0.0 end)/sum(case when f.positions.size > 0 then 1.0 else 0.0 end) from Finder f")
	String dashboardQueryB3();

	@Query("select avg(a.score), min(a.score), max(a.score), stddev(a.score) from Audit a")
	String dashboardQueryAcmeRookiesC1();

	@Query("select avg(c.score), min(c.score), max(c.score), stddev(c.score) from Company c")
	String dashboardQueryAcmeRookiesC2();

	@Query("select c from Company c where c.score is not null group by c order by max(c.score) desc")
	Collection<Company> dashboardQueryAcmeRookiesC3();

	@Query("select avg(p.salary) from Company c1 join c1.positions p where c1.score  = (select max(c2.score) from Company c2)")
	String dashboardQueryAcmeRookiesC4();

	@Query("select avg(p.items.size), min(p.items.size), max(p.items.size), stddev(p.items.size) from Provider p")
	String dashboardQueryAcmeRookiesB1();

	@Query("select p from Provider p join p.items i group by p order by sum(i) desc")
	Collection<Provider> dashboardQueryAcmeRookiesB2();

	@Query("select avg(p.sponsorships.size), min(p.sponsorships.size), max(p.sponsorships.size), stddev(p.sponsorships.size) from Provider p")
	String dashboardQueryAcmeRookiesA1();

	@Query("select avg(p.sponsorships.size), min(p.sponsorships.size), max(p.sponsorships.size), stddev(p.sponsorships.size) from Position p")
	String dashboardQueryAcmeRookiesA2();

	@Query("select p1 from Provider p1 where (select count(s) from Sponsorship s join s.provider p where p in (select p2 from Provider p2 where p1.id = p2.id )) >= 1.1*(select avg(1.0*(select count(s) from Sponsorship s join s.provider p where p in (select p2 from Provider p2 where p1.id = p2.id ))) from Provider p1)")
	Collection<Provider> dashboardQueryAcmeRookiesA3();

}
