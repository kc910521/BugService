package ind.ck.common.util;

import java.io.IOException;
import java.net.URI;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class MsgSender {

	//被叫号码
	private String clientMobileNb;
	
	//登录用户ID
	private String uid;
	//MD5(企业代码+用户密码),32位加密小写
	private String auth;
	
	//信息模板
	private String msgContentTp = "您的注册确认码:{code},请不要向他人透露此数字！";
	
	/**
	 * 
	 * @param tarMobNb
	 * @param msgContent
	 * @param registerCode 6位注册码
	 */
	private String assembleUrl(String tarMobNb,String registerCode){
		StringBuffer sb = new StringBuffer();
		sb.append("http://sms.10690221.com:9011/hy/?");
		sb.append("uid=80863");
		sb.append("&auth=67adf5d991870efd15d39fe99a06f83d");
		sb.append("&mobile="+tarMobNb);
		sb.append("&msg="+msgContentTp.replace("{code}", "["+registerCode+"]"));
		sb.append("&expid=0");
		sb.append("&encode=utf-8");
		return sb.toString();
	}
	
	public String sendMsg(String tarMobNb,String registerCode) throws ParseException, IOException{
		HttpResponse response = null;
		HttpClient httpClient = new DefaultHttpClient();
		String resultString = null;
		
		try {
		  HttpGet hget = new HttpGet();
		  String url = this.assembleUrl(tarMobNb,registerCode);
		  hget.setURI(new URI(url));
		  response = httpClient.execute(hget);
		//处理响应
		} catch (Exception e) {
			e.printStackTrace();
		  //处理异常
		} finally {
			if(response != null) { 
	            // 获取响应消息实体  
	            HttpEntity entity = response.getEntity();  
	              
	            System.out.println("------------------------------------");  
	              
	            if(entity != null){  
	                                  
	                //响应内容  
	            	resultString = EntityUtils.toString(entity);  
	                  
	                System.out.println("----------------------------------------");  
	                // 响应内容长度  
	                System.out.println("响应内容长度："+entity.getContentLength());  
	            }  
				
				
				EntityUtils.consume(response.getEntity()); //会自动释放连接
			}
		  //如下方法也是可以的，但是存在一些风险；不要用
		  //InputStream is = response.getEntity().getContent();
		  //is.close();
		}
		// 创建get请求实例  
        //HttpGet httpget = new HttpGet("http://lchbl.com/api/ruanwen?newsid="+newsId);
        
		return resultString;  
	}
	
	public static void main(String[] args) {
		MsgSender msgSender = new MsgSender();
		String haveATry = null;
		try {
			haveATry = msgSender.sendMsg("15222288847",Tools.takeVertifyCode());
		} catch (ParseException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(haveATry);
	}
	
}
