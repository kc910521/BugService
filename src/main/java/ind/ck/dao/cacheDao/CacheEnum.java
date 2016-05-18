package ind.ck.dao.cacheDao;

/**
 * redis ´íÎó¼¯
 * @author KCSTATION
 *
 */
public enum CacheEnum {

	
	//String key;
	
	TARGET_NO_FOUND("REDIS","OBJECT EXPIRED"),
	
	FATAL_ERROR("REDIS","REDIS ERROR");
	
	private  String key;
	
	private  String value;
	
	CacheEnum(String key,String value){
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}
	
	
	
}
