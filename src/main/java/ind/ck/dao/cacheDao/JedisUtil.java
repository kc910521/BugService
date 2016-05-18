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
     * ������redis�в���H��:key(tableName:hcode) value(pcode) 
     * ������Ѵ����򷵻�false,������,��ֹ���ǡ�ʹ��pipeline������ʽ(����ע����ֵ) 
     *  @param list  һ��map����һ�м�¼,2��key:hcode & pcode�� 
     *  @param tableName redis��key��ֵΪtableName:hcode  ��ӦvalueֵΪpcode�� 
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
	 * ��redis�л�ȡ(��ȡ������־) rPop������β������(�������־) 
	 * ���̲߳�����ȡ��־���ȵ�ʱ�򣬱��綼�õ������1000���� 
	 * �����߳�ÿ���� ѭ��1000�� pop���� ��־��ʱ�� 
	 * �����Ƕ��߳�һ��pop������ÿ���̻߳�õ������ж������ null  �����е�ȫ��null 
	 * @return 
	 */  
	public List<String> getLogFromRedis() {  
	      
	    final RedisSerializer<String> serializer = redisTemplate.getStringSerializer();  
	    //������־�ĳ���  
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
	      
	    //  ȥ������е�null  
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
				throw new Exception("REDIS ��ʼ��ʧ��");
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
