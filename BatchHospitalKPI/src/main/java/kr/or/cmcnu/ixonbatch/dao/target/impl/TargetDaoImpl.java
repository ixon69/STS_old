package kr.or.cmcnu.ixonbatch.dao.target.impl;

import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import kr.or.cmcnu.ixonbatch.dao.target.TargetDao;

@Repository
public class TargetDaoImpl implements TargetDao {

    @Autowired
    @Qualifier("tSqlSessionTemplate")
    private SqlSession sqlSession;
    
    @Override
    public void insertPhoneInside(HashMap<String, Object> map) {
          sqlSession.insert("TargetMaper.insertPhoneInside", map);
    }

}
