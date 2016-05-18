package ind.ck.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;

public class VelocityUtils {
	
	public static String HTML_FILE_PATH = "";

	public static String PROJECT_REAL_PATH = "";
	
	public static String TEMPLATE_PATH = "";
	
	public static String HTML_FILE_HEAD = "";

	private static VelocityEngine ve = new VelocityEngine();
	
	static {
		System.out.println("velocity static block init...");
		
	}
	
	public VelocityUtils(){
		System.out.println("VelocityEngine static init......");
	}
	
	public static boolean fileIsExist(String newsId){
		File file = new File(VelocityUtils.HTML_FILE_PATH+"/"+HTML_FILE_HEAD+newsId+".html");
		if (file.exists()){
			return true;
		}
		return false;
	}
	/**
	 * 20160420 ck
	 * 静态化开发流程
	 * 1，输入输出实现静态
	 * 2，调输入输出位置，不作为工程下url
	 * 3，实现自己的注解，配置是否生成模板（延后）
	 * 
	 * 指定目录下生成html文件
	 * @param newsId
	 */
	public static void htmlStaticlize(String newsId,Map<String,String> paramMap){
		//ve.setProperty(Velocity.RESOURCE_LOADER, "class");
		ve.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, PROJECT_REAL_PATH);
		
        try {  
            //进行初始化操作  
            ve.init();  
            //加载模板，设定模板编码  
            Template t=ve.getTemplate(TEMPLATE_PATH,"utf-8");  
            //设置初始化数据  
            VelocityContext context = new VelocityContext();
            Set<Entry<String, String>> set = paramMap.entrySet();
            for (Entry<String, String> entry : set) {
            	context.put(entry.getKey(), entry.getValue());
			}
            
            //context.put("NewsP2", "BB");
            //设置输出  
            PrintWriter writer = new PrintWriter(
            		new OutputStreamWriter(
            				new FileOutputStream(HTML_FILE_PATH+"/"+HTML_FILE_HEAD+newsId+".html"),
            				"utf-8"
            				)
            		);
            //将环境数据转化输出  
            t.merge(context, writer);  
            //简化操作  
            //ve.mergeTemplate("test/velocity/simple1.vm", "gbk", context, writer );  
            writer.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }   
		
	}
	
	public String takeGenerateHtml(String newsId){
	
		return null;
	}
}
