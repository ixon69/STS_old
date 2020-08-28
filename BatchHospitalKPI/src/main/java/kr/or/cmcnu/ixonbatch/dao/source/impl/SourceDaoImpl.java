package kr.or.cmcnu.ixonbatch.dao.source.impl;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import kr.or.cmcnu.ixonbatch.dao.source.SourceDao;

@Repository
public class SourceDaoImpl implements SourceDao {
	@Autowired
	@Qualifier("sSqlSessionTemplate")
	private SqlSession sqlSession;
	
	@Override
	public List<HashMap<String, Object>> selectPhoneInside(String part) {
		return sqlSession.selectList("SourceMapper.selectPhoneInside", part);
	}

}
