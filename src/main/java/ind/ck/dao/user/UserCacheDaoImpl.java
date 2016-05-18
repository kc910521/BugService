package ind.ck.dao.user;

import java.util.concurrent.TimeUnit;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Repository;

import ind.ck.dao.cacheDao.AbsBaseRedisDao;
import ind.ck.model.bean.CommonUser;

@Repository
public class UserCacheDaoImpl 
	extends AbsBaseRedisDao<String, CommonUser>
	implements IUserCacheDao {

	@Override
	public boolean userInCache(String userId) {
		// TODO Auto-generated method stub
		return this.redisTemplate.hasKey(userId);
	}

	@Override
	public boolean putUserToCache(final CommonUser user) {
		// TODO Auto-generated method stub
        RedisSerializer<String> serializer = getRedisSerializer();
/*        byte[] key  = serializer.serialize(user.getId());
        byte[] name = serializer.serialize(user.getUname()); */
/*        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection)  
                    throws DataAccessException {
 
                //connection.setConfig(param, value);
                return connection.setNX(key, name);  
            }  
        });  */
		redisTemplate.opsForValue().set(user.getUserId(), user);
        redisTemplate.expire(user.getUserId(), 3600, TimeUnit.SECONDS);
        return true;  
	}

}
