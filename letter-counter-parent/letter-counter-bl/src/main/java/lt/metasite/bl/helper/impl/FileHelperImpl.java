/**
 * 
 */
package lt.metasite.bl.helper.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.ImmutableMap;

import lt.metasite.bl.dao.FileDao;
import lt.metasite.bl.helper.FileHelper;
import lt.metasite.model.info.FileInfo;
import lt.metasite.model.info.WordInfo;
import lt.metasite.model.utils.Consts;

/**
 * @author zygis
 *
 */
@Component
public class FileHelperImpl implements FileHelper {
	
	@Autowired
	private FileDao fileDao;

	private HashMap<String, Integer> wordsCountMap = new HashMap<String, Integer>();
	private HashMap<String, Integer> wordsCountSortedMap = new HashMap<String, Integer>();
	private static final String REGEX = "[!?,.;]";
	private static final ImmutableMap<String, String> RESULT_FILE_NAMES = new ImmutableMap.Builder<String, String>()
			.put("A-G", "[a-g]")
			.put("H-N", "[h-n]")
			.put("O-U", "[o-u]")
			.put("V-Z", "[v-z]")
			.build();

	@Override
	public List<File> processFiles(Map<String, MultipartFile> files) {
		clearMaps();
		files.forEach((key, value) -> {
			processFile(value);
		});
		sortMap();	
		List<File> resultFiles = createNewFiles(wrapToInfo());
		
		fileDao.removeAll();

		resultFiles.forEach(f -> {		
			fileDao.save(new lt.metasite.model.File(f, new byte[(int) f.length()]));		
		} );
		
		return resultFiles;
	}

	private List<File> createNewFiles(List<FileInfo> fileInfos) {
		List<File> result = new ArrayList<File>();
		fileInfos.forEach(f -> {
			try {
				result.add((createFile(f)));
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		return result;
	}

	private File createFile(FileInfo f) throws IOException {
		File file = new File("c://temp//" + f.getName() + ".txt");

		// Write Content
		FileWriter writer = new FileWriter(file);

		f.getWordInfos().forEach(w -> {
			try {
				writer.write(w.getWord() + Consts.SPACE + w.getCount() + Consts.NEW_LINE);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});

		writer.close();
		return file;
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
		wordsCountMap.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEachOrdered(x -> wordsCountSortedMap.put(x.getKey(), x.getValue()));
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
