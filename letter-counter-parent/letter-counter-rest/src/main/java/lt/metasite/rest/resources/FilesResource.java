package lt.metasite.rest.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

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
	public @ResponseBody String uploadMultipleFileHandler(MultipartRequest request) {
		fileHelper.processFiles(request.getFileMap());

		return "";
	}	
	

}