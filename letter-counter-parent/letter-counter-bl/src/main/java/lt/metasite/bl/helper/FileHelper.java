/**
 * 
 */
package lt.metasite.bl.helper;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import lt.metasite.model.User;

/**
 * @author zygis
 *
 */
public interface FileHelper {

	void processFiles(Map<String, MultipartFile> uploadedfiles, User currentUse);
	
}
