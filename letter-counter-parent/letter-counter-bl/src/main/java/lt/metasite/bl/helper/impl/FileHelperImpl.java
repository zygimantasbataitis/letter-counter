/**
 * 
 */
package lt.metasite.bl.helper.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import lt.metasite.bl.helper.FileHelper;

/**
 * @author zygis
 *
 */
@Component
public class FileHelperImpl implements FileHelper {

	@Override
	public void processFiles(/*MultipartFile[] files*/) {
		
	/*	
		String message = "";
		for (int i = 0; i < files.length; i++) {
			MultipartFile file = files[i];
			//String name = names[i];
			try {
				byte[] bytes = file.getBytes();

				// Creating the directory to store file
				String rootPath = System.getProperty("catalina.home");
				File dir = new File(rootPath + File.separator + "tmpFiles");
				if (!dir.exists())
					dir.mkdirs();

				// Create the file on server
				File serverFile = new File(dir.getAbsolutePath()
						+ File.separator + name);
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();

				//logger.info("Server File Location="
				//		+ serverFile.getAbsolutePath());

				//message = message + "You successfully uploaded file=" + name
				//		+ "<br />";
			} catch (Exception e) {
				//String aa = "You failed to upload " + name + " => " + e.getMessage();
			}
		}*/
	}

	@Override
	public void processFile(MultipartFile files) {
		URL url = getClass().getResource("Info/file1.txt");
		File file = new File(url.getPath());
		
	}

}
