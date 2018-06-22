/**
 * 
 */
package lt.metasite.bl.helper.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.ImmutableMap;

import lt.metasite.bl.dao.FileDao;
import lt.metasite.bl.helper.FileHelper;
import lt.metasite.model.User;
import lt.metasite.model.info.FileInfo;
import lt.metasite.model.info.WordInfo;

/**
 * @author zygis
 *
 */
@Component
public class FileHelperImpl implements FileHelper {
	
	@Autowired
	private FileDao fileDao;

	private HashMap<String, Integer> wordsCountMap = new HashMap<String, Integer>();
	private static final String REGEX = "[!?,.;]";
	private static final ImmutableMap<String, String> RESULT_FILE_NAMES = new ImmutableMap.Builder<String, String>()
			.put("A-G", "[a-g]")
			.put("H-N", "[h-n]")
			.put("O-U", "[o-u]")
			.put("V-Z", "[v-z]")
			.build();

	@Override
	public void processFiles(Map<String, MultipartFile> uploadedfiles, User currentUser) {
		Thread th = new Thread() {
			@Override
			public void run() {
				wordsCountMap = new HashMap<>();

				uploadedfiles.forEach((key, value) -> countWordsByFile(value));
				fileDao.removeAll(currentUser);
				getWrappedFileInfos()
						.forEach(f -> fileDao.save(new lt.metasite.model.File(f, currentUser)));
			}
		};
		th.start();
	}

	private void countWordsByFile(MultipartFile file) {
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

	private List<FileInfo> getWrappedFileInfos() {
		List<FileInfo> filesInfos = new ArrayList<FileInfo>();
		
		Map<String, Integer> sortedMap = new TreeMap<String, Integer>(wordsCountMap);

		RESULT_FILE_NAMES.keySet().forEach(key -> {
			String regex = RESULT_FILE_NAMES.get(key);
			FileInfo fileInfo = new FileInfo(key);

			sortedMap.forEach((word, count) -> {
				if (word.substring(0, 1).matches(regex)) {
					fileInfo.getWordInfos().add(new WordInfo(word, count));
				}
			});
			filesInfos.add(fileInfo);
		});

		return filesInfos;
	}

}
