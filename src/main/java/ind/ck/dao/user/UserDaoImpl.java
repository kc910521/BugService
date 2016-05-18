package ind.ck.dao.user;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ind.ck.model.bean.CommonUser;
import ind.ck.model.vo.UserOperaVO;

@Repository("userDaoImpl")
public class UserDaoImpl implements IUserDao {

	@Autowired
	private SqlSession sqlSessionTemplate;
	

	@Override
	public List<CommonUser> loadUserList(CommonUser userCondition) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList("touch.user.selectUserAll", userCondition);
	}

	@Override
	public UserOperaVO returnUser(CommonUser tuser) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectOne("touch.user.selectUser", tuser);

	}
	
	@Override
	public int registerUser(CommonUser user) {
		// TODO Auto-generated method stub
		
		return sqlSessionTemplate.insert("touch.user.insertUser", user);
	}

	public SqlSession getSqlSessionTemplate() {
		return sqlSessionTemplate;
	}

	public void setSqlSessionTemplate(SqlSession sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	@Override
	public int mergeUserPwd(CommonUser conditionUser) {
		// TODO Auto-generated method stub
		
		return sqlSessionTemplate.update("touch.user.mergeUserPwd", conditionUser);
	}
	@Override
	public int mergeUserName(CommonUser conditionUser) {
		// TODO Auto-generated method stub
		
		return sqlSessionTemplate.update("touch.user.mergeUserName", conditionUser);
	}
	@Override
	public int mergeUserPortrait(CommonUser conditionUser) {
		// TODO Auto-generated method stub
		
		return sqlSessionTemplate.update("touch.user.mergeUserPortrait", conditionUser);
	}
	@Override
	public int mergeUserSexual(CommonUser conditionUser) {
		// TODO Auto-generated method stub
		
		return sqlSessionTemplate.update("touch.user.mergeUserSexual", conditionUser);
	}
	@Override
	public int addUserBonus(CommonUser conditionUser) {
		// TODO Auto-generated method stub
		
		return sqlSessionTemplate.update("touch.user.addUserBonus", conditionUser);
	}

	

	
	
	

}
