package ind.ck.dao.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

public class PropertiesUtil {

	private String srcPath = "";
	
	private String appPath = "";
	
	private Properties properties = null;
	
	public PropertiesUtil(HttpServletRequest request){
		
		this.srcPath = "/WEB-INF/classes/myconf.properties";
		
		//µÃµ½Â·¾¶
		this.appPath = request.getServletContext().getRealPath(srcPath);
		
		System.out.println("rtPath:"+ this.appPath);
		
		this.properties = this.initResources();
		
	}
	
	public String getProp(String propKey){
		return this.properties.getProperty(propKey);
	}
	
	
	private Properties initResources(){
		File myPropFile = new File(appPath);
		
		if (myPropFile.exists() && myPropFile.isFile()){
			InputStreamReader inputStream = null;
			try {
				inputStream = new InputStreamReader(new FileInputStream(appPath));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Properties prop = new Properties();
			try {
				prop.load(inputStream);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return prop;
			
		}else{
			try {
				throw new FileNotFoundException();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
		
		
	}
	
	
	
	
	
	
	
	
	
	
}
