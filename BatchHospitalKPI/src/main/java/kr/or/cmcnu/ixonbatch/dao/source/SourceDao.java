package kr.or.cmcnu.ixonbatch.dao.source;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SourceDao {
    public List<HashMap<String, Object>> selectPhoneInside(String part);
}
