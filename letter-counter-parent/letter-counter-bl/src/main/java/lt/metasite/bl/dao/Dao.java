package lt.metasite.bl.dao;

import java.util.List;

import lt.metasite.model.interfaces.IEntity;

public interface Dao<T extends IEntity, I> {

	List<T> findAll();

	T find(I id);

	T save(T entity);

	void delete(I id);

}