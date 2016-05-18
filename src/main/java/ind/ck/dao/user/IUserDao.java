package ind.ck.dao.user;

import java.util.List;

import ind.ck.model.bean.CommonUser;
import ind.ck.model.vo.UserOperaVO;

public interface IUserDao {
	
	public UserOperaVO returnUser(CommonUser tuser);
	
	public List<CommonUser> loadUserList(CommonUser userCondition);
	
	public int registerUser(CommonUser user);
	
	int mergeUserPwd(CommonUser conditionUser);
	
	public int mergeUserName(CommonUser conditionUser);

	int mergeUserPortrait(CommonUser conditionUser);

	int mergeUserSexual(CommonUser conditionUser);

	int addUserBonus(CommonUser conditionUser);
}
