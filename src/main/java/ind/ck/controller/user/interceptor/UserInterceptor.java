package ind.ck.controller.user.interceptor;

import java.io.PrintWriter;

import ind.ck.common.anno.VertifyAnnotation;
import ind.ck.common.util.PackageUtils;
import ind.ck.enums.StaticEnums;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class UserInterceptor implements HandlerInterceptor{

	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2) throws Exception {
		// TODO Auto-generated method stub
        if(arg2.getClass().isAssignableFrom(HandlerMethod.class)){
        	VertifyAnnotation authPassport = ((HandlerMethod) arg2).getMethodAnnotation(VertifyAnnotation.class);
            
            //û��������ҪȨ��,������������֤Ȩ��
            if(authPassport == null || authPassport.vertifyUser() == false)
                return true;
            else{                
                //������ʵ���Լ���Ȩ����֤�߼�
                if(this.isCacheLogined(arg0.getCookies()))
                    return true;
                else//�����֤ʧ��
                {
                    //���ص���¼����
                	//arg1.sendRedirect("/account/login.html");
                	
                	PrintWriter sOut = arg1.getWriter();
                	JSONObject jsonObject = 
                			JSONObject.fromObject(PackageUtils.packageData(0,"[]","NO-LOGIN"));
                	sOut.write(jsonObject.toString());
                	sOut.flush();
                	sOut.close();
                    return false;
                }       
            }
        }
        return true;   
		
	}
	
	/**
	 * �����ж����˵�½������Ҫ�ҳ���˭
	 * @param cookieArrs
	 * @return
	 */
	private boolean isCacheLogined(Cookie[] cookieArrs) {
		// TODO Auto-generated method stub
		if(cookieArrs != null){
			for(Cookie cok : cookieArrs){
				if((StaticEnums.COOKIE_USER+"").equals(cok.getName())){
					return true;
				}
			}
		}
		return false;
	}
	
}
