/**
 * 
 */
package lt.metasite.bl.helper;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author zygis
 *
 */
public interface FileHelper {

	void processFiles(Map<String, MultipartFile> files);
	
	void processFile(MultipartFile files);

}
