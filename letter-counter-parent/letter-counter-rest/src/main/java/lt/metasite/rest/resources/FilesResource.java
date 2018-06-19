package lt.metasite.rest.resources;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartRequest;

import lt.metasite.bl.dao.FileDao;
import lt.metasite.bl.helper.FileHelper;
import lt.metasite.model.File;
import lt.metasite.rest.utils.RestPaths;

@RestController
@RequestMapping("/files")
public class FilesResource {
	
	private static final int BUFFER_SIZE = 4096;

	@Autowired
	private FileHelper fileHelper;

	@Autowired
	private ServletContext servletContext;

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
	public void uploadMultipleFileHandler(MultipartRequest request,
			HttpServletResponse response) {
		fileHelper.processFiles(request.getFileMap());
	}

	@GetMapping("/download_file/"+RestPaths.ID_PATH)
	public void downloadFile(@PathVariable(RestPaths.ID) Long id, HttpServletRequest request, HttpServletResponse response) throws IOException {
		File file = fileDao.find(id);
		
	    java.io.File downloadFile = new java.io.File(file.getPath());
	    FileInputStream inputStream = new FileInputStream(downloadFile);

	    // get MIME type of the file
	    String mimeType = servletContext.getMimeType(file.getPath());
	    if (mimeType == null) {
	        // set to binary type if MIME mapping not found
	        mimeType = "application/octet-stream";
	    }
	    System.out.println("MIME type: " + mimeType);

	    // set content attributes for the response
	    response.setContentType(mimeType);
	    response.setContentLength((int) downloadFile.length());

	    // set headers for the response
	    String headerKey = "Content-Disposition";
	    String headerValue = String.format("attachment; filename=\"%s\"",
	            downloadFile.getName());
	    response.setHeader(headerKey, headerValue);

	    // get output stream of the response
	    OutputStream outStream = response.getOutputStream();

	    byte[] buffer = new byte[BUFFER_SIZE];
	    int bytesRead = -1;

	    // write bytes read from the input stream into the output stream
	    while ((bytesRead = inputStream.read(buffer)) != -1) {
	        outStream.write(buffer, 0, bytesRead);
	    }

	    inputStream.close();
	    outStream.close();
	}

}