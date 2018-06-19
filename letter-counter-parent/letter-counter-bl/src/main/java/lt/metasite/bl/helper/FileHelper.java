/**
 * 
 */
package lt.metasite.bl.helper;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author zygis
 *
 */
public interface FileHelper {

	List<File> processFiles(Map<String, MultipartFile> files);
	
	void processFile(MultipartFile files);

}
