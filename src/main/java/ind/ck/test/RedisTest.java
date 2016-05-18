package ind.ck.test;

import java.util.HashMap;
import java.util.Map;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisTest {

	JedisPool jpool = null; 
	protected Jedis jedis = null; 
	
	public RedisTest(){
		this.getConn();
	}
	
	private void getConn(){
		jpool = new JedisPool(new JedisPoolConfig(), "192.168.0.106");  
		jedis = jpool.getResource(); 
	}
	
	//¿É mset
	public String insertJd(String key,String val){
		return jedis.set(key,val);
		//jedis.append("name","jarorwar"); 
	}
	
	public String insertBachJd(String ...keyAndVal){
		if (keyAndVal.length%2 != 0){
			try {
				throw new Exception("Params error!");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			return jedis.mset(keyAndVal);
		}
		return null;
		
		//jedis.append("name","jarorwar"); 
	}
	
	public void insertOrAppend(String key,String val){
		jedis.append(key, val);
	}
	
	
	public String getJdBy(String key){
		return jedis.get(key);
	}
	
	public void delJd(String key){
		jedis.del(key); 
	}
	
	public void hmset(){
/*		Map<String,String> amap = new HashMap<>();
		amap.put("name", "ck");
		amap.put("pwd", "=*none*=");
		jedis.hmset("olol", amap);*/
		System.out.println(jedis.hmget("olol", "name","pwd"));
	}
	
	public void listOper(){
		jedis.lpush("java framework","spring"); 
		jedis.lpush("java framework","2222"); 
		//-1 mean all
		jedis.lrange("java framework",0,-1);
	}
	
	public static void main(String[] args) {
		RedisTest redisTest = new RedisTest();
		//redisTest.delJd("connections");
		//redisTest.insertOrAppend("connections","oo"+(System.currentTimeMillis()));4
		//redisTest.insertJd("121212", "ok");
		System.out.println(redisTest.getJdBy("121212"));

		//redisTest.hmset();
		
	}
	

	
}
