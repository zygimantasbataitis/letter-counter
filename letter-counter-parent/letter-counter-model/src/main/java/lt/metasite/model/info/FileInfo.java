package lt.metasite.model.info;

import java.util.List;

public class FileInfo {

	private String name;
	private List<WordInfo> wordInfos;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<WordInfo> getWordInfos() {
		return wordInfos;
	}

	public void setWordInfos(List<WordInfo> wordInfos) {
		this.wordInfos = wordInfos;
	}

}
