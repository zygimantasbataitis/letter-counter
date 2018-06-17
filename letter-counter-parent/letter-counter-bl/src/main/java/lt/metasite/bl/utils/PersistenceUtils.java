package lt.metasite.bl.utils;

import java.util.List;

import javax.persistence.criteria.Predicate;

public class PersistenceUtils {

	public static Predicate[] toArray(List<Predicate> predicates) {
		return predicates.toArray(new Predicate[predicates.size()]);
	}

}
