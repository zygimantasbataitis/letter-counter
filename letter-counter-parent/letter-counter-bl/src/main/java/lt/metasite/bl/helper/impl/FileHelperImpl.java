/**
 * 
 */
package lt.metasite.bl.helper.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.ImmutableMap;

import lt.metasite.bl.helper.FileHelper;
import lt.metasite.model.info.FileInfo;
import lt.metasite.model.info.WordInfo;

/**
 * @author zygis
 *
 */
@Component
public class FileHelperImpl implements FileHelper {

	private HashMap<String, Integer> wordsCountMap = new HashMap<String, Integer>();
	private HashMap<String, Integer> wordsCountSortedMap = new HashMap<String, Integer>();
	private static final String REGEX = "[!?,.]";
	private static final ImmutableMap<String, String> RESULT_FILE_NAMES =
		       new ImmutableMap.Builder<String, String>()
		           .put("A-G", "[a-g]")
		           .put("H-N", "[h-n]")
		           .put("O-U", "[o-u]")
		           .put("V-Z", "[v-z]")
		           .build();

	@Override
	public void processFiles(Map<String, MultipartFile> files) {
		clearMaps();
		files.forEach((key, value) -> {
			processFile(value);
		});
		sortMap();
		List<FileInfo> resultFiles = wrapToInfo();
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

	private void clearMaps() {
		wordsCountMap = new HashMap<>();
		wordsCountSortedMap = new HashMap<>();
	}

	private void sortMap() {
		wordsCountSortedMap = wordsCountMap.entrySet().stream()
				.sorted(Map.Entry.<String, Integer>comparingByKey().reversed())
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
	}
	
	private List<FileInfo> wrapToInfo() {
		List<FileInfo> result = new ArrayList<FileInfo>();
		
		RESULT_FILE_NAMES.keySet().forEach(key -> {
			String regex = RESULT_FILE_NAMES.get(key);
			FileInfo f = new FileInfo(key);			
			
			wordsCountSortedMap.forEach((k, v) -> {
				if (k.substring(0, 1).matches(regex)) {
					f.getWordInfos().add(new WordInfo(k, v));
				}
			});
			result.add(f);
		});
		
		return result;
	}


}
