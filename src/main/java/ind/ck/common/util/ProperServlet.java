package ind.ck.common.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class ProperServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2366731664400463455L;
	
	public static String PORTRAIT_PATH = "";
	
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		System.out.println("Velocity properties init ...");
		Properties prop = new Properties(); 
		String baseHome = getServletConfig().getInitParameter("base_home");
		System.out.println("in"+baseHome);
		InputStream ins = this.getClass().getResourceAsStream("/myconf.properties");
		if (ins == null){
			new FileNotFoundException("配置文件丢失！！！");
		}else{
			System.err.println("====== 配置文件已获取到========");
			try {
				prop.load(ins);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			VelocityUtils.HTML_FILE_PATH = baseHome+prop.getProperty("HTML_FILE_PATH");
			
			VelocityUtils.HTML_FILE_HEAD = prop.getProperty("HTML_FILE_HEAD");
			
			VelocityUtils.TEMPLATE_PATH = prop.getProperty("TEMPLATE_PATH");
			
			VelocityUtils.PROJECT_REAL_PATH = baseHome;
			
			ProperServlet.PORTRAIT_PATH = prop.getProperty("PORTRAIT_FILE_PATH");
			
			try {
				ins.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				prop = null;
				ins = null;
				this.configLog4j();
			}
		}
	}
	
	private void configLog4j(){
		InputStream ins = this.getClass().getResourceAsStream("/log4j.properties");
		if (ins == null){
			System.out.println("log4j 配置丢失");
			BasicConfigurator.configure();
		}else{
			PropertyConfigurator.configure(ins);
			org.apache.ibatis.logging.LogFactory.useLog4JLogging();
			System.out.println("log4j init ed");
		}
		
		
	}
	

}
