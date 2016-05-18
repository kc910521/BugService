package ind.ck.service.news;

import java.io.IOException;
import java.net.URI;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import net.sf.json.JSONObject;

import org.apache.commons.collections.map.LinkedMap;
import org.apache.http.Header;
import org.apache.http.HttpClientConnection;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ConnectionRequest;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service("newsServiceImpl")
@Scope("prototype")
public class NewsServiceImpl implements INewsService {
	//singleton
	
	@Autowired
	private HttpClient defaultHttpClient = null;
	
	private String urlBody = null;
	
	private String urlParams = null;
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, String> takeHtmlContent(String newsId,int pageNo) {
		// TODO Auto-generated method stub
		String resultStr = null;
		try {
			resultStr = this.takeAllHtml(newsId, pageNo);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject jsObj = JSONObject.fromObject(resultStr);
		jsObj.put("NewsId", newsId);
		System.out.println("sssss:"+jsObj.toString());
		return jsObj;
	}
	
	private String takeAllHtml(String newsId,int pageNo) throws IOException{
		HttpResponse response = null;
		HttpClient httpClient = new DefaultHttpClient();
		String resultString = null;
		
		try {
		  HttpGet hget = new HttpGet();
		  String url = urlBody+"?newsid="+newsId;//http://lchbl.com/api/ruanwen
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
		NewsServiceImpl ns = new NewsServiceImpl();
		System.out.println("WQNBB:"+ns.takeHtmlContent("13",1));
	}

	public HttpClient getDefaultHttpClient() {
		return defaultHttpClient;
	}

	public void setDefaultHttpClient(HttpClient defaultHttpClient) {
		this.defaultHttpClient = defaultHttpClient;
	}

	public String getUrlBody() {
		return urlBody;
	}

	public void setUrlBody(String urlBody) {
		this.urlBody = urlBody;
	}

	public String getUrlParams() {
		return urlParams;
	}

	public void setUrlParams(String urlParams) {
		this.urlParams = urlParams;
	}
	
	
	
	

	
	/**
	 * 
	 *         String resultString = null;
        
        try  
        {  
              
            // �ͻ���ִ��get���� ������Ӧʵ��  
            HttpResponse response = httpClient.execute(httpget);  
              
            // ��������Ӧ״̬��  
            System.out.println(response.getStatusLine());  
              
            Header[] heads = response.getAllHeaders();  
            // ��ӡ������Ӧͷ  
            for(Header h:heads){  
                System.out.println(h.getName()+":"+h.getValue());  
            }  
              
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
              
        } catch (ClientProtocolException e){  
            e.printStackTrace();  
        } catch (IOException e){  
            e.printStackTrace();  
        }finally{  
            httpClient.getConnectionManager().shutdown();  
        }  
		
		return resultString;
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
	
	

}
