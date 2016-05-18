package ind.ck.common.util;

import ind.ck.enums.StaticEnums;
import ind.ck.model.bean.CommonUser;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;

/**
 * 工程工具类
 * @author KCSTATION
 *
 */
public class Tools {

	/**
	 * 手机号验证
	 * @param phoneNumber
	 * @return
	 */
	public static boolean isPhoneNumber(String phoneNumber){
		if (StringUtils.isBlank(phoneNumber)){
			return false;
		}
	    Pattern p = null;  
		Matcher m = null;  
		boolean b = false;   
		p = Pattern.compile("^[1][3,4,5,8][0-9]{9}$"); // 验证手机号  
		m = p.matcher(phoneNumber);  
		b = m.matches();   
        return b;  
	}
	
	private final static String[] codeSeed = new String[]{"8","5","8","6","9","8","2"};
	/**
	 * 得到一个vertify验证码 6位
	 * @return
	 */
	public static String takeVertifyCode(){
		final StringBuffer returnStr = new StringBuffer();
		while (returnStr.length() < 6){
			returnStr.append(codeSeed[RandomUtils.nextInt(codeSeed.length)]);
		}
		return returnStr.toString();
	}
	
	/**
	 * 添加用户cookies到客户端
	 * @param ckName
	 * @return
	 */
	public static Cookie feedCookies(String ckName){
		Cookie c1 = new Cookie(StaticEnums.COOKIE_USER+"",ckName);
		c1.setMaxAge(3600*24);
		c1.setPath("/");
		return c1;
	}
	/**
	 * 移除客户端cookies
	 * @return
	 */
	public static Cookie removeUserCookies(){
		Cookie c1 = new Cookie(StaticEnums.COOKIE_USER+"",null);
		c1.setMaxAge(0);
		c1.setPath("/");
		return c1;
	}
	/**
	 * 计算token
	 * @param uname
	 * @param mobileNumber
	 * @return
	 */
	public static String takeUserToken(String uname,String mobileNumber){
		
		return uname.hashCode()+","+mobileNumber.hashCode();
	}
	
	public static byte[] convertToBytes(String byString){
		return byString.getBytes();
	}
	
	public static byte[] convertToBytes(Serializable object){
	    byte[] bytes = null;  
	    try {  
	        // object to bytearray  
	        ByteArrayOutputStream bo = new ByteArrayOutputStream();  
	        ObjectOutputStream oo = new ObjectOutputStream(bo);  
	        oo.writeObject(object);  
	        bytes = bo.toByteArray();  
	        bo.close();  
	        oo.close();  
	    } catch (Exception e) {  
	        System.out.println("translation" + e.getMessage());  
	        e.printStackTrace();  
	    }  
	    return bytes;  
	}
	
	public static Object ByteToObject(byte[] bytes) {  
		  Object obj = null;  
		  try {  
		      // bytearray to object  
		      ByteArrayInputStream bi = new ByteArrayInputStream(bytes);  
		      ObjectInputStream oi = new ObjectInputStream(bi);  
		    
		      obj = oi.readObject();  
		      bi.close();  
		      oi.close();  
		  } catch (Exception e) {  
		      System.out.println("translation" + e.getMessage());  
		      e.printStackTrace();  
		  }  
		  return obj;  
	} 
	
	/**
	 * 修正性别
	 * @param sexualCode
	 * @return
	 */
	public static String fixSexual(String sexualCode){
		if (StringUtils.isBlank(sexualCode) ){
			return "null";
		}
		
		switch (sexualCode){
			case "1":
				sexualCode = "男";break;
			case "0":
				sexualCode = "女";break;
			case "男":
				sexualCode = "1";break;
			case "女":
				sexualCode = "0";break;
			default:
				try {
					throw new Exception("SEXUAL ERROR");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
		}
		return sexualCode;
	}
	
	public static boolean isSameDate(Date date1, Date date2) {
       Calendar cal1 = Calendar.getInstance();
       cal1.setTime(date1);
       Calendar cal2 = Calendar.getInstance();
       cal2.setTime(date2);

       boolean isSameYear = cal1.get(Calendar.YEAR) == cal2
               .get(Calendar.YEAR);
       boolean isSameMonth = isSameYear
               && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
       boolean isSameDate = isSameMonth
               && cal1.get(Calendar.DAY_OF_MONTH) == cal2
                       .get(Calendar.DAY_OF_MONTH);

       return isSameDate;
	}
	
	public static void main(String[] args) {
		CommonUser cu = new CommonUser();
		//cu.setId("21212121d");
		byte[] bt = Tools.convertToBytes("11");
		System.out.println(bt.length);
		String obj = (String)Tools.ByteToObject(bt);
		System.out.println("dd:"+obj);
		
	}
	
}
