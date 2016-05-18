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

	//���к���
	private String clientMobileNb;
	
	//��¼�û�ID
	private String uid;
	//MD5(��ҵ����+�û�����),32λ����Сд
	private String auth;
	
	//��Ϣģ��
	private String msgContentTp = "����ע��ȷ����:{code},�벻Ҫ������͸¶�����֣�";
	
	/**
	 * 
	 * @param tarMobNb
	 * @param msgContent
	 * @param registerCode 6λע����
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
		//������Ӧ
		} catch (Exception e) {
			e.printStackTrace();
		  //�����쳣
		} finally {
			if(response != null) { 
	            // ��ȡ��Ӧ��Ϣʵ��  
	            HttpEntity entity = response.getEntity();  
	              
	            System.out.println("------------------------------------");  
	              
	            if(entity != null){  
	                                  
	                //��Ӧ����  
	            	resultString = EntityUtils.toString(entity);  
	                  
	                System.out.println("----------------------------------------");  
	                // ��Ӧ���ݳ���  
	                System.out.println("��Ӧ���ݳ��ȣ�"+entity.getContentLength());  
	            }  
				
				
				EntityUtils.consume(response.getEntity()); //���Զ��ͷ�����
			}
		  //���·���Ҳ�ǿ��Եģ����Ǵ���һЩ���գ���Ҫ��
		  //InputStream is = response.getEntity().getContent();
		  //is.close();
		}
		// ����get����ʵ��  
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
