package tn.esprit.service;


import java.text.ParseException;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import tn.esprit.entity.Reclamation;
import tn.esprit.entity.User;
import tn.esprit.entity.reclamationPriority;
import tn.esprit.entity.reclamationType;
import tn.esprit.repository.UserRepository;
import tn.esprit.repository.reclamationRepository;







@Service
@Slf4j
public class serviceReclamation implements Ireclamation {
	@Autowired
	reclamationRepository reclamationrepository;
	@Autowired
	UserRepository userrepository;
	

	@Override
	public Reclamation addAffectReclamationUser(Reclamation reclamation, Long userId) {
		User user = userrepository.findById(userId).orElse(null);
		reclamation.setUser(user);
		return reclamationrepository.save(reclamation);
	}

	@Override
	public Reclamation retrieveReclamation(Integer idReclamation) {
		
		return reclamationrepository.findById(idReclamation).orElse(null);
	}
	@Override
	public void updateReclamation(Reclamation reclamation) {
		
		Reclamation R = retrieveReclamation(reclamation.getIdReclamation());
		R.setDescription(reclamation.getDescription());
		R.setFile(reclamation.getFile());
		R.setLastModificationDate(reclamation.getLastModificationDate());
		R.setPicture(reclamation.getPicture());
		R.setPriority(reclamation.getPriority());
		R.setType(reclamation.getType());
		reclamationrepository.save(R);	
	}


	@Override
	public void deleteReclamation(Integer idReclamation) {
		
		reclamationrepository.deleteById(idReclamation);
		
	}


	@Override
	public List<Reclamation> ListAllReclamationsAdmin() {
		
		return reclamationrepository.findAll();
	}


	@Override
	public Set<Reclamation> listReclamationByPriorityAdmin(reclamationPriority pr) {
		
		return reclamationrepository.getReclamationByPriority(pr);
	}


	@Override
	public Set<Reclamation> listReclamationByTypeAdmin(reclamationType rt) {
		
		return reclamationrepository.getReclamationByType(rt);
	}


	@Override
	public Set<Reclamation> listReclamationByPriorityAndTypeAdmin(reclamationPriority pr, reclamationType rt) {
		
		return reclamationrepository.getReclamationByPriorityAndType(pr, rt);
	}


	@Override
	public Set<Reclamation> ListReclamationByStatusClient(Long userId) {
		
		return reclamationrepository.getAllReclamationsClientByStatus(userId);
		
	}


	@Override
	public boolean verif(Integer idReclamation) {
		Reclamation f=retrieveReclamation( idReclamation);
		if(f.getStatus() == false) {
		f.setStatus(true);
		}
		reclamationrepository.save(f);
		return f.getStatus();
	}

	@Override
	public List<Reclamation> ListAllReclamationsClient(Long userId) {
		
		return reclamationrepository.getAllReclamationsClient(userId);
	}

	@Override
	public float statWatingReclamation(reclamationType type ,reclamationPriority priority) {
		int n =0,tn;
		float P;
		if(type==null && priority==null )
		 n = reclamationrepository.nbrWaitingReclamation();
		if((type.equals("SOFTWER") ||
				type.equals("USER")||
				type.equals("TRIP")|| 
				type.equals("OTHER")) && 
				(priority.equals("NORMAL") || 
				 priority.equals("IMPORTANT") ||
				 priority.equals("URGENTE")) ) {
			
			n=reclamationrepository.nbrWaitingReclamationByPriorityAndType(priority, type);
		}
	/*	if((type.contains("SOFTWER") ||
				type.contains("SOFTWER")||
				type.contains("SOFTWER")||
				type.contains("SOFTWER")) && priority==null) {
			n=reclamationrepository.nbrWaitingReclamationByType(type);
		}
		if(type==null  &&(priority.contains("NORMAL") || 
						priority.contains("IMPORTANT") ||
						priority.contains("URGENTE"))) {
			n=reclamationrepository.nbrWaitingReclamationByPriority(priority);
		}*/
		
		tn=(int) reclamationrepository.count();
		
		P=(n*100)/tn;
		
		return P;
	}
	
	

	



	

	

	
	
	
	

}
