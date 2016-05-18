package ind.ck.common.util;

import ind.ck.enums.StaticEnums;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;



public class PackageUtils {

	private static final Map<String,Object> resultMap = new HashMap<String,Object>();
	
	/**
	 * 向客户端返回约定
	 * @param flag 是否成功 0false;1 true
	 * @param originalData 原始数据
	 * @param message 返回讯息
	 * @return
	 */
	public static Map<String,Object> packageData(
			int flag,
			Object originalData,
			String message
			){
		resultMap.clear();
		resultMap.put("FLAG", flag);
		resultMap.put("DATA", originalData);
		resultMap.put("MSG", message);
		return resultMap;
	}
	
	/**
	 * 向客户端返回约定
	 * 自动装载flag ,msg
	 * @param originalData 原始数据
	 * @return
	 */
	public static synchronized Map<String,Object> packageData(
			Object originalData,
			String message
			){
		resultMap.clear();
		resultMap.put("FLAG", originalData == null?0:1);
		resultMap.put("DATA", originalData);
		resultMap.put("MSG", message == null? originalData == null?"FATAL: LOST CONNECTION":"SUCCESS" :message);
		return resultMap;
	}
	

	
	
}
