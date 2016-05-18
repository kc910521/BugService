package ind.ck.dao.user;

import ind.ck.model.bean.CommonUser;

public interface IUserCacheDao {

	public boolean userInCache(String userId);
	
	public boolean putUserToCache(CommonUser cuser);
}
