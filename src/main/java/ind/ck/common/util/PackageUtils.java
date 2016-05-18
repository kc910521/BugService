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
	 * ��ͻ��˷���Լ��
	 * @param flag �Ƿ�ɹ� 0false;1 true
	 * @param originalData ԭʼ����
	 * @param message ����ѶϢ
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
	 * ��ͻ��˷���Լ��
	 * �Զ�װ��flag ,msg
	 * @param originalData ԭʼ����
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
