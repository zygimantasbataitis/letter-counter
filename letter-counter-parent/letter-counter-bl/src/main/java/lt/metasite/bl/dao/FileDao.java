package lt.metasite.bl.dao;

import java.util.List;

import lt.metasite.model.File;
import lt.metasite.model.User;

public interface FileDao extends Dao<File, Long> {

	List<File> findAll(User user);

	void removeAll(User user);

}