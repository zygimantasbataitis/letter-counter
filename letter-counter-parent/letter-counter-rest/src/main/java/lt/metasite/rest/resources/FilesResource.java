package lt.metasite.rest.resources;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lt.metasite.bl.helper.FileHelper;

@RestController
@RequestMapping("/files")
public class FilesResource {
	
	@Autowired
	private FileHelper fileHelper;

//	@Autowired
//	private NewsEntryDao newsEntryDao;
//
//	@GetMapping(RestPaths.ID_PATH)
//	public NewsEntry read(@PathVariable(RestPaths.ID) Long id) {
//		return newsEntryDao.find(id);
//	}
//
//	@PostMapping(RestPaths.ID_PATH)
//	public NewsEntry store(@RequestBody @Valid NewsEntry newsEntry) {
//		return newsEntryDao.save(newsEntry);
//	}
//
//	@DeleteMapping(RestPaths.ID_PATH)
//	public void delete(@PathVariable(RestPaths.ID) Long id) {
//		newsEntryDao.delete(id);
//	}
//	
//	@GetMapping
//	public List<NewsEntry> list() {
//		return newsEntryDao.findAll();
//	}	
	
	@PostMapping("/uploadFiles")
	public @ResponseBody String uploadMultipleFileHandler(@RequestParam("files") MultipartFile[] files) {
		fileHelper.processFiles(/*new ArrayList<MultipartFile>()*/);

		return "";
	}	
	

}