package ind.ck.controller.user;

import ind.ck.common.anno.VertifyAnnotation;
import ind.ck.common.util.PackageUtils;
import ind.ck.common.util.Tools;
import ind.ck.dao.util.PropertiesUtil;
import ind.ck.enums.StaticEnums;
import ind.ck.model.bean.CommonUser;
import ind.ck.model.vo.UserOperaVO;
import ind.ck.service.user.IUserOper;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.Cookies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	IUserOper iUserOper = null;

	//登陆
	//用手机登录的话，uname写手机号！
	@RequestMapping(value={"/get/name/{uname}/pwd/{upwd}",
							"/get/name/{uname}",
							"/get/userId/{userId}"
						},
			method={RequestMethod.GET,RequestMethod.POST}
			)
	public @ResponseBody Map<String,Object> loginWith(
			HttpServletRequest req,
			HttpServletResponse resp,
			CommonUser cUser
			){
		return iUserOper.takeOneUser(cUser,req,resp);
	}
	
	//注册用户
	@RequestMapping(value={"/post/deprecated/name/{uname}/pwd/{upwd}",
					"/post/name/{uname}/pwd/{upwd}/phoneNumber/{phoneNumber}",
					"/post/phoneNumber/{phoneNumber}/pwd/{upwd}"
				},
			method={RequestMethod.GET,RequestMethod.POST}
			)
	public @ResponseBody Map<String,Object> registerUser(
			HttpServletRequest req,
			HttpServletResponse resp,
			CommonUser cUser){
		return iUserOper.registerUser(cUser, resp);
	}
	
	//验证手机号
	@RequestMapping(value="/get/vertify/phoneNumber/{phoneNumber}",
			method={RequestMethod.GET,RequestMethod.POST}
			)
	public @ResponseBody Map<String,Object> vertifyMobile(
			UserOperaVO cUser){
		return iUserOper.vertifyMobile(cUser);
	}
	
	//登出
	@VertifyAnnotation
	@RequestMapping(value="/post/loginOut",
			method={RequestMethod.GET,RequestMethod.POST}
			)
	public @ResponseBody Map<String,Object> loginOut(HttpServletResponse resp){
		resp.addCookie(Tools.removeUserCookies());
		return PackageUtils.packageData(1, null, "注销");
	}
	
	//修改密码
	/**
	 * 
	 * "/put/pwd/{upwd}"
			+ "/where/phoneNumber/{phoneNumber}"
			+ "/name/{uname}"
			+ "/vertifyCode/{vertifyCode}"
	 * 
	 * 
	 * @param cUser
	 * @return
	 */
	@VertifyAnnotation(vertifyUser = true)
	@RequestMapping(value="/put/pwd/{upwd}"
			+ "/where/userId/{userId}"
			+ "/vertifyCode/{vertifyCode}",
			method={RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT}
			)
	public @ResponseBody Map<String,Object> mergeUser(
			UserOperaVO cUser){
		return iUserOper.mergeUserPwdInfo(cUser);
	}
	
	//修改用户名
	@VertifyAnnotation(vertifyUser = true)
	@RequestMapping(value="/put/name/{uname}"
			+ "/where/userId/{userId}"
			+ "/vertifyCode/{vertifyCode}",
			method={RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT}
			)
	public @ResponseBody Map<String,Object> changeName(
			CommonUser cUser){
		return iUserOper.mergeUserNameInfo(cUser);
	}
	
	//签到 od once a day
	@VertifyAnnotation(vertifyUser = true)
	@RequestMapping(value="/put/bonus/od"
			+ "/userId/{userId}",
			method={RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT}
			)
	public @ResponseBody Map<String,Object> checkIn(
			CommonUser cUser){
		return iUserOper.checkUserOnce(cUser);
	}
	
	//修改性别
	@VertifyAnnotation(vertifyUser = true)
	@RequestMapping(value="/put/sexual/{sexual}"
			+ "/where/userId/{userId}",
			method={RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT}
			)
	public @ResponseBody Map<String,Object> changeSexual(
			UserOperaVO cUser){
		return iUserOper.mergeUserSexual(cUser);
	}
	
	//上传头像
	@VertifyAnnotation(vertifyUser = true)
    @RequestMapping(value = "/upload",method=RequestMethod.POST)  
    public Map<String,Object> upload(
    		@RequestParam(value = "file", required = false) MultipartFile file, 
    		CommonUser commonUser,
    		HttpServletRequest request) {  
  
    	return iUserOper.uploadPortraitWith(file,commonUser,request);
    } 
	
	
	
//	@RequestMapping(value="/model/userjump",method = {RequestMethod.GET} )
//	public ModelAndView visitPage(
//			HttpServletRequest req,
//			Map<String,Object> m1,Model m2,ModelMap m3){
//		PropertiesUtil pu = new PropertiesUtil(req);
//		//很棒的测试
//		m1.put("m1", pu.getProp("redis_conn_url"));
//		m2.addAttribute("m2", "m2 Model");
//		m3.put("m3", "m3 ModelMap");
//		ModelAndView modelAndView = new ModelAndView();  
//        modelAndView.addObject("m4", "m4 ModelAndView");  
//        modelAndView.setViewName("VTest");
//		return modelAndView;
//	}
	
	public IUserOper getiUserOper() {
		return iUserOper;
	}

	public void setiUserOper(IUserOper iUserOper) {
		this.iUserOper = iUserOper;
	}
	
	
	
	
}
