package ind.ck.dao.cacheDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Component
@Scope("singleton")
@Lazy
public final class JedisUtil extends AbsBaseRedisDao{

	
	private JedisPool jpool = null; 
	
	private static Jedis jedis = null;
	
	private String redis_url = "";
	
	/**
	 * this cons-function will initialize in container start
	 */
	public JedisUtil(){
		jpool = new JedisPool(new JedisPoolConfig(), redis_url);  
		JedisUtil.jedis = jpool.getResource(); 
		System.out.println("REDIS INITIALIZED.");
	}
	
	
    /** 
     * 批量向redis中插入H码:key(tableName:hcode) value(pcode) 
     * 如果键已存在则返回false,不更新,防止覆盖。使用pipeline批处理方式(不关注返回值) 
     *  @param list  一个map代表一行记录,2个key:hcode & pcode。 
     *  @param tableName redis中key的值为tableName:hcode  对应value值为pcode。 
     *  @return 
     */  
    public boolean addHcode(final List<Map<String, Object>> list,final String tableName) {  
        @SuppressWarnings("unchecked")
		boolean result = (boolean) redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();  
                for (Map<String, Object> map : list) {  
                    byte[] key  = serializer.serialize(tableName+":"+map.get("hcode").toString());  
                    byte[] name = serializer.serialize(map.get("pcode").toString());  
                    connection.setNX(key, name);  
                }  
                return true;  
            }  
        }, false, true);  
        return result;  
    }  
	
	/** 
	 * 从redis中获取(获取密码日志) rPop从链表尾部弹出(最早的日志) 
	 * 多线程并发读取日志长度的时候，比如都得到结果是1000条。 
	 * 当多线程每个都 循环1000次 pop弹出 日志的时候， 
	 * 由于是多线程一起pop，所以每个线程获得的数组中都会包含 null  甚至有的全是null 
	 * @return 
	 */  
	public List<String> getLogFromRedis() {  
	      
	    final RedisSerializer<String> serializer = redisTemplate.getStringSerializer();  
	    //密码日志的长度  
	    @SuppressWarnings("unchecked")
		final Long pwdLogSize=redisTemplate.opsForList().size("getpwdList");  
	      
	    @SuppressWarnings("unchecked")
		List<Object> pwdLogList=redisTemplate.executePipelined(new RedisCallback<String>() {  
	        @Override  
	        public String doInRedis(RedisConnection conn)  
	                throws DataAccessException {  
	            for (int i=0 ;i<pwdLogSize ;i++) {  
	                byte[] listName  = serializer.serialize("getpwdList");  
	                conn.rPop(listName);  
	            }  
	            return null;  
	        }  
	    }, serializer);  
	      
	    //  去除结果中的null  
	    ArrayList<String> newList=new ArrayList<String>();  
	    for (Object o : pwdLogList) {  
	        if(o!=null)  
	            newList.add(String.valueOf(o));  
	    }  
	    return newList;  
	}  
/*	public static Jedis takeJedis(){
		if (JedisUtil.jedis == null){
			try {
				throw new Exception("REDIS 初始化失败");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return JedisUtil.jedis;
	}
	
	public static void main(String[] args) {
		System.out.println(JedisUtil.takeJedis().get("12121"));
	}

	public String getRedis_url() {
		return redis_url;
	}

	public void setRedis_url(String redis_url) {
		this.redis_url = redis_url;
	}*/
	
	

}
