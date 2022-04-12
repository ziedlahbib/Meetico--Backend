package tn.esprit.meetico.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import tn.esprit.meetico.entity.Comment;
import tn.esprit.meetico.entity.Publication;
import tn.esprit.meetico.entity.User;
import tn.esprit.meetico.repository.PublicationRepository;
import tn.esprit.meetico.repository.UserRepository;
import tn.esprit.meetico.service.CommentServiceImpl;
import tn.esprit.meetico.service.PublicationServiceImpl;
@CrossOrigin(origins = "http://localhost:4200",exposedHeaders="Access-Control-Allow-Origin")
@RestController
@RequestMapping("/publication")
@Api(tags = "Publication Management")

public class PublicationController {
	@Autowired
	PublicationServiceImpl pubService;
	@Autowired
	PublicationRepository pubRepo;
	@Autowired
	UserRepository userRepo;

	@ApiOperation(value = "Add Publication")
	@PostMapping("/add-publication")
	public void addPublication(HttpServletRequest request, @RequestBody Publication f) {
        String username = request.getUserPrincipal().getName();
        User user = userRepo.findByUsername(username);
		
		pubService.addPublication(f,user);

	}

	@ApiOperation(value = "Delete Publication")
	@DeleteMapping("/supprimer-publication/{idPublicaiton}")
	@ResponseBody
	public void deletePublication(
			@PathVariable("idPublicaiton") Long idPublicaiton) {
		pubService.deletePublication( idPublicaiton);

	}
	

	@ApiOperation(value = " Add Or Delete Like From Publication")
	@PutMapping("/addLike/{idPublicaiton}")
	public void addLike(@PathVariable("idPublicaiton") Long idPublicaiton, HttpServletRequest request) {
		 String username = request.getUserPrincipal().getName();
	        User user = userRepo.findByUsername(username);
		pubService.addLike(idPublicaiton, user);
	}

	@ApiOperation(value = "Add Or Delete Dislike From Publication")
	@PutMapping("/addDislike/{idPublicaiton}")
	public void addDislike(@PathVariable("idPublicaiton") Long idPublicaiton, HttpServletRequest request) {
		 String username = request.getUserPrincipal().getName();
	        User user = userRepo.findByUsername(username);
		pubService.addDislike(idPublicaiton, user);
	}

	@ApiOperation(value = "Number of Comments From Publication")
	@GetMapping("/nbrComments/{idPublicaiton}")
	public int nbrCommentsByPub(@PathVariable("idPublicaiton") Long idPublicaiton) {
		return pubService.nbrCommentsByPu(idPublicaiton);

	}

	@ApiOperation(value = "Number of Likes From Publication")
	@GetMapping("/nbrLike/{idPublicaiton}")
	public int NbrLikes(@PathVariable("idPublicaiton") Long idPublicaiton) {
		return pubService.nbrLikeByPub(idPublicaiton);

	}

	@ApiOperation(value = "Number of Dislikes From Publication")
	@GetMapping("/nbrDisLike/{idPublicaiton}")
	public int NbrDisLikes(@PathVariable("idPublicaiton") Long idPublicaiton) {
		return pubService.nbrDisLikeByPub(idPublicaiton);

	}

	
	@ApiOperation(value = "Daily publications")
	@GetMapping("/GetPublicationToday")
	public List<Publication> GetPublicationToday() {

		Date currentSqlDate = new Date(System.currentTimeMillis());
		List<Publication> listp = pubRepo.findAll();
		return listp;

	}
	
//	@ApiOperation(value = "Afficher All publications")
//	@GetMapping("/GetPublicationToday")
//	public List<Publication> getAllPubs() {
//
//		Date currentSqlDate = new Date(System.currentTimeMillis());
//		//List<Publication> listp = pubRepo.getPubToday(currentSqlDate);
//		List<Publication> listp = pubRepo.findAll();
//		return listp;
//
//	}
	@ApiOperation(value = "Statistics User whith the highest publications")
	@GetMapping("/MeilleurUser")
	public int MeilleurUser() {
		return pubService.MeilleurUser();

	}

}
