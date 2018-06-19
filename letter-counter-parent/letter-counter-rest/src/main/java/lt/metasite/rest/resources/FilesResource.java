package lt.metasite.rest.resources;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartRequest;

import com.google.common.io.ByteStreams;

import lt.metasite.bl.dao.FileDao;
import lt.metasite.bl.helper.FileHelper;
import lt.metasite.model.File;
import lt.metasite.rest.utils.RestPaths;

@RestController
@RequestMapping("/files")
public class FilesResource {

	@Autowired
	private FileHelper fileHelper;

	@Autowired
	private FileDao fileDao;

	@GetMapping(RestPaths.ID_PATH)
	public File read(@PathVariable(RestPaths.ID) Long id) {
		return fileDao.find(id);
	}

	@PostMapping(RestPaths.ID_PATH)
	public File store(@RequestBody @Valid File file) {
		return fileDao.save(file);
	}

	@DeleteMapping(RestPaths.ID_PATH)
	public void delete(@PathVariable(RestPaths.ID) Long id) {
		fileDao.delete(id);
	}

	@GetMapping
	public List<File> list() {
		return fileDao.findAll();
	}

	@PostMapping("/upload_files")
	public void uploadMultipleFileHandler(MultipartRequest request, HttpServletResponse response) {
		fileHelper.processFiles(request.getFileMap());
	}

	@GetMapping(value = "/download_file/" + RestPaths.ID_PATH, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<byte[]> downloadFile(@PathVariable Long id) {
		File file = fileDao.find(id);

		java.io.File downloadFile = new java.io.File(file.getPath());
										// ID
		if (!downloadFile.exists()) { // handle FNF
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}

		try {
			FileSystemResource fileResource = new FileSystemResource(downloadFile);

			byte[] base64Bytes = Base64.getEncoder().encode((ByteStreams.toByteArray(fileResource.getInputStream())));

			HttpHeaders headers = new HttpHeaders();
			headers.add("filename", fileResource.getFilename());

			return ResponseEntity.ok().headers(headers).body(base64Bytes);
		} catch (IOException e) {
			//log.error("Failed to download file ", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
}