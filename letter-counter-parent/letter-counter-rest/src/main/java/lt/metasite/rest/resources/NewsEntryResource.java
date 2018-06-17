package lt.metasite.rest.resources;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lt.metasite.bl.dao.NewsEntryDao;
import lt.metasite.model.NewsEntry;
import lt.metasite.rest.utils.RestPaths;

@RestController
@RequestMapping("/news")
public class NewsEntryResource {

	@Autowired
	private NewsEntryDao newsEntryDao;

	@GetMapping(RestPaths.ID_PATH)
	public NewsEntry read(@PathVariable(RestPaths.ID) Long id) {
		return newsEntryDao.find(id);
	}

	@PostMapping(RestPaths.ID_PATH)
	public NewsEntry store(@RequestBody @Valid NewsEntry newsEntry) {
		return newsEntryDao.save(newsEntry);
	}

	@DeleteMapping(RestPaths.ID_PATH)
	public void delete(@PathVariable(RestPaths.ID) Long id) {
		newsEntryDao.delete(id);
	}
	
	@GetMapping
	public List<NewsEntry> list() {
		return newsEntryDao.findAll();
	}	

}