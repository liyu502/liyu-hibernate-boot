package com.simplefitness.common;

import java.util.List;

public interface CommonDao<P, I> {
	boolean insert(P pojo);

	boolean deleteById(I id);

	boolean update(P pojo);

	P selectById(I id);

	List<P> selectAll();

}
