package tn.esprit.meetico.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import io.swagger.annotations.Api;
import tn.esprit.meetico.entity.FileDB;
import tn.esprit.meetico.service.FileStorageService;
import tn.esprit.meetico.util.FileResponse;
import tn.esprit.meetico.util.MessageResponse;

@RestController
@Api(tags = "File Management")
@RequestMapping("/File")
public class FileController {

	@Autowired
	private FileStorageService storageService;

	@PostMapping("/upload")
	public ResponseEntity<MessageResponse> uploadFile(@RequestPart("file") MultipartFile file) {
		String message = "";
		try {
			storageService.store(file);
			message = "Uploaded the file successfully: " + file.getOriginalFilename();
			return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(message));
		} catch (Exception e) {
			message = "Could not upload the file: " + file.getOriginalFilename() + "!";
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MessageResponse(message));
		}
	}

	@DeleteMapping("/delete-file/{id-file}")
	@ResponseBody
	public void deletetrip(@PathVariable("id-file") Long idfile) {
		storageService.deletefile(idfile);
	}

	@GetMapping("/files")
	public ResponseEntity<List<FileResponse>> getListFiles() {
		List<FileResponse> files = storageService.getAllFiles().map(dbFile -> {
			String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/SpringMVC/File/files/")
					.path(dbFile.getId().toString()).toUriString();
			return new FileResponse(dbFile.getName(), fileDownloadUri, dbFile.getType(), dbFile.getData().length);
		}).collect(Collectors.toList());
		return ResponseEntity.status(HttpStatus.OK).body(files);
	}

	@GetMapping("/files/{id}")
	public ResponseEntity<byte[]> getFile(@PathVariable Long id) {
		FileDB fileDB = storageService.getFile(id);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getName() + "\"")
				.body(fileDB.getData());
	}

	@GetMapping("/filesByTrip/{id}")
	public ResponseEntity<List<FileResponse>> getListFilesByTRip(@PathVariable Integer id) {
		List<FileResponse> files = storageService.getAllFilesBytrip(id).map(dbFile -> {
			String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/SpringMVC/File/files/")
					.path(dbFile.getId().toString()).toUriString();
			return new FileResponse(dbFile.getName(), fileDownloadUri, dbFile.getType(), dbFile.getData().length);
		}).collect(Collectors.toList());
		return ResponseEntity.status(HttpStatus.OK).body(files);
	}

	@GetMapping("/filesByTRipp/{id}")
	@ResponseBody
	public ResponseEntity<byte[]> getFilebytripp(@PathVariable Integer id) {
		List<FileDB> fileDB = storageService.getFileByTrip(id);

		// for(FileDB f :fileDB) {
		// return ResponseEntity.ok()
		// .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" +
		// f.getName() + "\"")
		// .body(f.getData()) ;

		// }
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION,
						"attachment; filename=\"" + fileDB.iterator().next().getName() + "\"")
				.body(fileDB.iterator().next().getData());
	}

	@GetMapping("/filesByTRippp/{id}")
	@ResponseBody
	public List<ResponseEntity<byte[]>> getFileBytrippp(@PathVariable Integer id) {
		/*
		 * List<FileDB> fileDB = storageService.getFileByTrip(id); List<byte[]> b = new
		 * ArrayList<byte[]>() ; //new ArrayList(ResponseEntity<byte[]>); for(FileDB f
		 * :fileDB) { byte[] s=f.getData(); b.add(s); } return ResponseEntity.ok()
		 * .header(HttpHeaders.CONTENT_DISPOSITION, "attachment") .body(b) ; }
		 */
		List<FileDB> fileDB = storageService.getFileByTrip(id);
		List<ResponseEntity<byte[]>> b = new ArrayList<ResponseEntity<byte[]>>();
		ResponseEntity<byte[]> s = new ResponseEntity<byte[]>(null, null, HttpStatus.OK);
		for (FileDB f : fileDB) {
			s = ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + f.getName() + "\"")
					.body(f.getData());
			b.add(s);
		}
		return b;
	}

	@GetMapping("/filesByTRipppp/{id}")
	@ResponseBody
	public ResponseEntity<List<byte[]>> getFileBytripppp(@PathVariable Integer id) {

		List<FileDB> fileDB = storageService.getFileByTrip(id);
		List<byte[]> b = new ArrayList<byte[]>();
		// new ArrayList(ResponseEntity<byte[]>);
		for (FileDB f : fileDB) {
			byte[] s = f.getData();
			b.add(s);
		}
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment").body(b);
	}

}