package lt.metasite.bl.dao;

import lt.metasite.model.File;

public interface FileDao extends Dao<File, Long> {
	
	void removeAll();

}