package tn.esprit.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tn.esprit.entity.Trip;
import tn.esprit.entity.User;



@Repository
public interface TripRepository extends JpaRepository<Trip, Integer>{
	@Query("Select r FROM User r join r.trips t where t.destination =:destination AND t.startDate =:startdate ")
	List<User> matchingutilisateur(@Param("destination") String destination,@Param("startdate") Date startDate);
			
	//AND t.domainedactivité=:domaine AND perimetre:=perimetre
	
}
