/**
 * 
 */
package lt.metasite.bl.helper;

import java.util.ArrayList;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author zygis
 *
 */
public interface FileHelper {

	void processFiles(/*ArrayList<MultipartFile> arrayList*/);
	
	void processFile(MultipartFile files);

}
