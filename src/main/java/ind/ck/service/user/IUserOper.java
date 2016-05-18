package ind.ck.service.user;

import ind.ck.model.bean.CommonUser;

import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

public interface IUserOper {

	//public boolean isLogined(String username,Cookie[] cookieArrs);
	
	@Deprecated
	//public boolean isCookieLogined(Cookie[] cookieArrs);
	
	public boolean isRedisLogined(String uid);
	
	public List<CommonUser> takeUserInf(String username);
	
	public Map<String, Object> registerUser(CommonUser cuser,HttpServletResponse resp);
	
	public CommonUser userInDb(CommonUser cuser);
	
	public int addUserInRedis(CommonUser cuser);
	
	Map<String,Object> takeOneUser(CommonUser cuser,HttpServletRequest req,HttpServletResponse resp);
	
	/**
	 * 修改用户密码 通过手机
	 * @param cuser
	 * @return
	 */
	Map<String,Object> mergeUserPwdInfo(CommonUser cuser);
	
	/**
	 * 发送并返回手机验证码
	 * @param cuser
	 * @return
	 */
	public Map<String,Object> vertifyMobile(CommonUser cuser);
	
	/**
	 * 上传用户头像
	 * @param file
	 * @param commonUser
	 * @param request
	 * @return
	 */
	public Map<String,Object> uploadPortraitWith(MultipartFile file,CommonUser commonUser,HttpServletRequest request);

	Map<String,Object> mergeUserNameInfo(CommonUser cuser);

	Map<String, Object> mergeUserSexual(CommonUser cuser);

	Map<String, Object> checkUserOnce(CommonUser cuser);
	
}
