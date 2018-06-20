package lt.metasite.model.info;

import java.util.ArrayList;
import java.util.List;

import lt.metasite.model.utils.Consts;

public class FileInfo {

	private String name;
	private List<WordInfo> wordInfos = new ArrayList<WordInfo>();

	public FileInfo() {

	}

	public FileInfo(String name) {
		this.name = name;
	}

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
	
	public byte[] getContent() {
		StringBuilder sb = new StringBuilder();
		getWordInfos().forEach(w -> sb.append(w.getWord() + Consts.SPACE + w.getCount() + Consts.NEW_LINE));
		return sb.toString().getBytes();
	}

}
