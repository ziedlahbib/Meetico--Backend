package tn.esprit.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;
import tn.esprit.entity.FileDB;
import tn.esprit.entity.Trip;
import tn.esprit.entity.User;
import tn.esprit.repository.FileDBRepository;
import tn.esprit.repository.TripRepository;
import tn.esprit.repository.UserRepository;




@Service
@Slf4j
public class TripServiceUmpl implements ITripService{
	@Autowired
	UserRepository userRepo;
	@Autowired
	TripRepository tripRepo;
	@Autowired
	FileDBRepository fileRepo;
	

	@Override
	public void addTrip(Trip trip, List<Long> idUsers,Long idEnt) {
		Trip t = ajouttrip(trip, idEnt);					
		for(Long iduser :idUsers) {
			User u =userRepo.findById(iduser).orElse(null);
			u.getTrips().add(t);
			userRepo.save(u);
		}
		
	}

	@Override
	public void updateTrip(Trip trip, Integer idTrip) {
		Trip t = tripRepo.findById(idTrip).orElse(null);
		t.setDestination(trip.getDestination().toUpperCase());
		t.setEndDate(trip.getEndDate());
		t.setObject(trip.getObject());
		t.setStartDate(trip.getStartDate());
		t.setUser(t.getUser());
		t.setUsers(t.getUsers());
		tripRepo.save(t);
	
	}

	@Override
	public void deleteTrip(Integer idTrip) {
	
		tripRepo.deleteById(idTrip);
		
	}

	@Override
	public Trip affichDetailTrip(Integer idTrip) {
		
		
		return tripRepo.findById(idTrip).orElse(null);
	}

	@Override
	public List<Trip> affichTrip() {
		
		return tripRepo.findAll();
	}

	@Override
	public Trip ajouttrip(Trip trip,Long idUSer) {
		String d=trip.getDestination();
		String dm=d.toUpperCase();
		trip.setDestination(dm);
		User u = userRepo.findById(idUSer).orElse(null);
		trip.setUser(u);
		return tripRepo.save(trip);		
	}

	@Override
	public void affecterlisteutilisateurautrip(List<Long> idutilisateurs,Integer idtrip) {
		Trip t =tripRepo.findById(idtrip).orElse(null);
		for (Long idutilisateur : idutilisateurs) {
			User u = userRepo.findById(idutilisateur).orElse(null);
			u.getTrips().add(t);
			userRepo.save(u);
		}
		
	}

	@Override
	public List<User> afficherutilisateurbymatching(String destination,Date startdate,String city) {
		
		return tripRepo.matchingutilisateur(destination,startdate,city);
	}

	@Override
	public void deleteutilisateurdetrip(Integer idtrip, List<Long> idusers) {
		Trip t = tripRepo.findById(idtrip).orElse(null);
		//Set <User> u =  t.getUsers();
		for(Long iduser :idusers) {
			User user =userRepo.findById(iduser).orElse(null);
			user.getTrips().remove(t);
			userRepo.save(user);
		}
		
		//tripRepo.deleteuserfromtrip(idtrip, idusers);
		/*
		Trip t = tripRepo.findById(idtrip).orElse(null);
		Set <User> u =  t.getUsers();
		for(Long iduser :idusers) {
			User user =userRepo.findById(iduser).orElse(null);
			u.remove(user);
			}
		for (User us : u) {
			log.info("user name:"+us.getFirstName());
		}
		
		//tripRepo.flush();
		
	t.setUsers(u);
	tripRepo.save(t);
	}
		
		
		Trip t = tripRepo.findById(idtrip).orElse(null);
		for(Long iduser :idusers) {
			Utilisateur u =userRepo.findById(iduser).orElse(null);
			t.getUtilisateur().remove(u);
			
		}
		tripRepo.save(t);*/
		}

	@Override
	public void affecterFileToTip(List<Long> idFiles, Integer idTrip) {
		Trip t=tripRepo.findById(idTrip).orElse(null);
		for(Long idf :idFiles) {
			FileDB f=fileRepo.findById(idf).orElse(null);
			f.setTrip(t);
			fileRepo.save(f);
		}
		
	}

	@Override
	public int listUserByVoyage(Integer idTrip) {
		// TODO Auto-generated method stub
		int n = tripRepo.nbduserbyvoyage(idTrip);
		return n;
	}

	@Override
	public List<String> nbrUserPourChaqueVoyage() {
		// TODO Auto-generated method stub
		
		List<Trip> t =tripRepo.findAll();
		int nombre_total_voyage=t.size();
		int nombre_total_voygeur=tripRepo.nombretotatldevoyageur();
		Trip tr;
		List<Integer> n=new ArrayList<>();
		List<String> ls= new ArrayList<>();
		
		int nbr;
		for(Trip trip :t) {
			nbr=trip.getUsers().size();
			n.add(nbr);
			String s="voayge numero :"+(t.indexOf(trip)+1)+"/"+nombre_total_voyage+"id:"+trip.getIdTrip()+"nombre de voyageur"+nbr+"/"
					+nombre_total_voygeur;
			ls.add(s);
			
		}
		
			return ls;
		
		
	}
	
	
		
	

	

	

}