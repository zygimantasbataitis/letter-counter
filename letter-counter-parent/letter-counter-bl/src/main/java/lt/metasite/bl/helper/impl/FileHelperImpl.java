/**
 * 
 */
package lt.metasite.bl.helper.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import lt.metasite.bl.helper.FileHelper;

/**
 * @author zygis
 *
 */
@Component
public class FileHelperImpl implements FileHelper {

	private HashMap<String, Integer> wordsCountMap = new HashMap<>();
	private static final String REGEX = "[!?,.]";
	
	@Override
	public void processFiles(Map<String, MultipartFile> files) {
		wordsCountMap = new HashMap<>();
		files.forEach((key, value) -> {
			processFile(value);
		});
	}

	@Override
	public void processFile(MultipartFile file) {
		if (!file.isEmpty()) {
	        try {
	            byte[] bytes = file.getBytes();
	            String completeData = new String(bytes);
	            
	            completeData = completeData.replaceAll(REGEX, StringUtils.EMPTY);
	    		String[] words = completeData.split("\\s+");
	    		
	    		Arrays.asList(words).forEach(word -> {
	    			if (!wordsCountMap.containsKey(word)) {
	    				wordsCountMap.put(word, 1);
	    			} else {
	    				wordsCountMap.put(word, wordsCountMap.get(word) + 1);
	    			}
	    		});
	        } catch (Exception e) {
	        	
			}			
		}
	}

}
