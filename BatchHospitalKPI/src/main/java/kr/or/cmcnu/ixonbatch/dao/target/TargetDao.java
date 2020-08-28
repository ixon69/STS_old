package kr.or.cmcnu.ixonbatch.dao.target;

import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TargetDao {
	public void insertPhoneInside(HashMap<String,Object> map);
}
