package ind.ck.service.user;

import ind.ck.common.util.MsgSender;
import ind.ck.common.util.PackageUtils;
import ind.ck.common.util.ProperServlet;
import ind.ck.common.util.Tools;
import ind.ck.dao.cacheDao.IRedisDao;
import ind.ck.dao.user.IUserCacheDao;
import ind.ck.dao.user.IUserDao;
import ind.ck.enums.StaticEnums;
import ind.ck.model.bean.CommonUser;
import ind.ck.model.user.CUserMapper;
import ind.ck.model.vo.UserOperaVO;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.http.ParseException;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public class UserOperImpl implements IUserOper{
	
	
	@Autowired @Qualifier("userDaoImpl")
	IUserDao userDaoImpl = null;
	
	@Autowired @Qualifier("userCacheDaoImpl")
	IUserCacheDao userCacheDao = null;
	
	@Autowired
	IRedisDao redisDaoImpl = null;
	
	private SimpleDateFormat spdf = null;
	private SimpleDateFormat dftm = null;
	
	public UserOperImpl(){
		spdf = new SimpleDateFormat( "yyyy-MM-dd" );
	}


	@Override
	public List<CommonUser> takeUserInf(String username) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * 修改用户密码
	 * by 手机号
	 */
	@Override
	public Map<String,Object> mergeUserPwdInfo(CommonUser cuser){
		int resVal = userDaoImpl.mergeUserPwd(cuser);
		if (resVal > 0){
			Tools.removeUserCookies();
			return PackageUtils.packageData(1, cuser, "用户密码修改成功,需重新登录");
		}else {
			return PackageUtils.packageData(0, cuser, "用户密码修改失败");
		}
		//return ;
	}
	/**
	 * 修改用户名
	 * by 手机号
	 */
	@Override
	public Map<String,Object> mergeUserNameInfo(CommonUser cuser){
		int resVal = userDaoImpl.mergeUserName(cuser);
		if (resVal > 0){
			//Tools.removeUserCookies();
			return PackageUtils.packageData(1, cuser, "用户名修改成功");
		}else {
			return PackageUtils.packageData(0, cuser, "用户名修改失败");
		}
	}
	
	@Override
	public Map<String,Object> mergeUserSexual(CommonUser cuser){
		cuser.setSexual(Tools.fixSexual(cuser.getSexual()));
		int resVal = userDaoImpl.mergeUserSexual(cuser);
		if (resVal > 0){
			//Tools.removeUserCookies();
			return PackageUtils.packageData(1, cuser, "用户性别修改成功");
		}else {
			return PackageUtils.packageData(0, cuser, "用户性别修改失败");
		}
	}
	
	/**
	 * 签到
	 * 每日一次，redis存储
	 * key 用户id value 最后签到时间
	 * 
	 * @param cuser
	 * @return
	 */
	@Override
	public Map<String,Object> checkUserOnce(CommonUser cuser){
		if (redisDaoImpl != null){
			String tmValue = redisDaoImpl.get(cuser.getUserId());
			if (!StringUtils.isBlank(tmValue)){
				//判断时间
/*				Date dat = null;
				try {
					dat = spdf.parse(tmValue);
				} catch (java.text.ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
/*				if (!Tools.isSameDate(dat, new Date())){
					return this.checkIn(cuser);
				}else{
					return PackageUtils.packageData(0, cuser, "已签到过");
				}*/
				if (this.isCheckedOd(tmValue)){
					return PackageUtils.packageData(0, cuser, "已签到过");
				}else{
					return this.checkIn(cuser);
				}
			}else{
				//直接签到
				return this.checkIn(cuser);
			}
		}else{
			return PackageUtils.packageData(0, cuser, "REDIS服务器繁忙");
		}
	}
	
	/**
	 * 判断今日是否签到过
	 * @return
	 */
	private boolean isCheckedOd(String tmValueInRd){
		Date dat = null;
		try {
			dat = spdf.parse(tmValueInRd);
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (!Tools.isSameDate(dat, new Date())){
			return false;
		}else{
			return true;
		}
	}
	
	
	/**
	 * 签到
	 * @return
	 */
	private Map<String,Object> checkIn(CommonUser cuser){
		cuser.setBonus(15);
		if (userDaoImpl.addUserBonus(cuser) > 0){
			//redis active
			redisDaoImpl.set(cuser.getUserId(), spdf.format(new Date()), 3600*24);
			return PackageUtils.packageData(1, null, "签到成功！");
		}else{
			return PackageUtils.packageData(0, cuser, "签到失败！");
		}
	}
	
	
	public Map<String,Object> uploadPortraitWith(MultipartFile file,CommonUser commonUser,HttpServletRequest request){
        System.out.println("开始");  
        //String path = request.getSession().get.getRealPath("portrait");  
        //String fileName = file.getOriginalFilename();  
//        String fileName = new Date().getTime()+".jpg";  
        commonUser.setPortrait("pk"+UUID.randomUUID().toString().replaceAll("-", "")+".png");
        File targetFile = new File(ProperServlet.PORTRAIT_PATH, commonUser.getPortrait());  //""
        if(!targetFile.exists()){  
            targetFile.mkdirs();  
        }  
        //保存  
        try {  
            file.transferTo(targetFile);  
        } catch (Exception e) {  
            e.printStackTrace();  
            return PackageUtils.packageData(0, null, "上传头像失败"); 
        }finally{
        	file = null;
        	targetFile = null;
        }
        
        if (this.mergeUserPortrait(commonUser)){
        	return PackageUtils.packageData(1, null, "弄不好上传成功了"); 
        }else{
        	return PackageUtils.packageData(0, null, "上传头像信息失败"); 
        }
        
       
	}
	
	/**
	 * 添加用户头像路径到db
	 * @param cuser
	 * @return
	 */
	private boolean mergeUserPortrait(CommonUser cuser){
		int resVal = userDaoImpl.mergeUserPortrait(cuser);
		if (resVal > 0){
			Tools.removeUserCookies();
			return true;
		}else {
			return false;
		}
		//return ;
	}
	
	/**
	 * cuser中应当只有手机号属性
	 */
	@Override
	public Map<String,Object> vertifyMobile(CommonUser cuser){
		if (!Tools.isPhoneNumber(cuser.getPhoneNumber())){
			return PackageUtils.packageData(0, cuser, "手机号不合法");
		}
		//验证是否已经注册
/*		List<CommonUser> uList = userDaoImpl.loadUserList(cuser);
		if (uList != null && !uList.isEmpty()){
			//已经注册
			uList.clear();
			uList = null;
			return PackageUtils.packageData(0, cuser, "手机号已经注册");
		}*/
		
		if (!this.continueMsgSend(cuser)){
			return PackageUtils.packageData(0, cuser, "暂时无法注册，1分钟后重试。");
		}
		
		String pdCode = Tools.takeVertifyCode();
		//调用短信API
		MsgSender msgSender = new MsgSender();
		try {
			msgSender.sendMsg(cuser.getPhoneNumber(), pdCode);
		} catch (ParseException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return PackageUtils.packageData(0, cuser, "异常的请求处理");
		}finally{
			msgSender = null;
		}
		UserOperaVO uVo = (UserOperaVO) cuser;
		uVo.setVertifyCode(pdCode);
		//向redis插入一个30秒生存期的记录
		redisDaoImpl.set(cuser.getPhoneNumber(), spdf.format(new Date()), 30);
		return PackageUtils.packageData(1, uVo, "短信发送成功");
	}
	
	/**
	 * 判断是否继续msg发送流程
	 * redis未开或all ok则继续
	 * @param cuser
	 * @return
	 */
	private boolean continueMsgSend(CommonUser cuser){
		if (redisDaoImpl != null){
			String lastTm = redisDaoImpl.get(cuser.getPhoneNumber());
			if (StringUtils.isBlank(lastTm)){
				//空，直接注册
				return true;
			}else{
				return false;
			}
		}
		return true;
		
	}

	/**
	 * 用户的注册
	 * 成功后写入cookie
	 * 
	 * WORKING
	 */
	@Override
	public Map<String,Object> registerUser(CommonUser cuser,HttpServletResponse resp) {
		// TODO Auto-generated method stub
		//判断用户名和手机号合法性
		if (!Tools.isPhoneNumber(cuser.getPhoneNumber())){
			return PackageUtils.packageData(0, cuser, "用户名不可为手机号且手机号必须合法");
		}
		if ( StringUtils.isBlank(cuser.getUname()) || cuser.getUname().matches(".*[,|，|/?|？].*")){
			cuser.setUname(UUID.randomUUID().toString().replaceAll("-",""));
			//return PackageUtils.packageData(0, cuser, "用户名不合法");
		}
		
		//暂时拿出用户的密码
		String pwd = cuser.getUpwd();
		cuser.setUpwd("");//拼接mybatis的条件
		CommonUser auser = this.userInDb(cuser);
		if (auser == null){
			//可注册
			
			cuser.setUserId(UUID.randomUUID().toString().replaceAll("-",""));
			cuser.setUpwd(pwd);
			userDaoImpl.registerUser(cuser);

			resp.addCookie(Tools.feedCookies(Tools.takeUserToken(cuser.getUname(),cuser.getPhoneNumber())));
			
			return PackageUtils.packageData(1, cuser, "注册成功！");
		}else{
			return PackageUtils.packageData(0, cuser, "用户已存在");
		}
	}
	
	/**
	 * 用户的登陆
	 * 成功后写入cookie
	 */
	@Override
	public Map<String,Object> takeOneUser(CommonUser cuser,HttpServletRequest req,HttpServletResponse resp) {
		// TODO Auto-generated method stub
		//直接传id，获取用户通过id
		if (!StringUtils.isBlank(cuser.getUserId())){
			//redisDaoImpl.set(cuser.getId(), cuser.toString(), 1000);
			UserOperaVO auserInn = this.userInDb(cuser);
			if (auserInn == null){
				return PackageUtils.packageData(0, auserInn, "获取用户失败");
			}else{
				//设置用户头像跟路径
				auserInn.setPortrait(this.takePortraitRootUrl(auserInn));
				//设置签到状态到vo
				String tmValue = redisDaoImpl.get(cuser.getUserId());
				if (!StringUtils.isBlank(tmValue)){
					if (this.isCheckedOd(tmValue)){
						auserInn.setCheckedOp(true);
					}else{
						auserInn.setCheckedOp(false);
					}
				}else{
					auserInn.setCheckedOp(false);
				}
				
				return PackageUtils.packageData(1, auserInn, "获取用户成功");
			}
			
			
		}
		
		//密码为空，检测cookie
		//or redis
		if ( StringUtils.isBlank(cuser.getUpwd()) ){
			/**
			 * 小心！这里当用户即便用手机登陆
			 * username会成为手机号
			 */
			if (this.isCookieLogined(req.getCookies(), 
					cuser.getUname())) {
				//resp.addCookie(PackageUtils.feedCookies(cuser.getUname()));
				return PackageUtils.packageData(1, null, "缓存的用户存在。");
			}
			return PackageUtils.packageData(0, null, "缓存的用户不存在。");
			
		}else{
			//拿到用户数据
			CommonUser auser = this.userInDb(cuser);
			if (auser != null && auser.getUserId() != null && !"".equals(auser.getUserId())){
				resp.addCookie(Tools.feedCookies(Tools.takeUserToken(auser.getUname(),auser.getPhoneNumber())));
				//设置用户头像跟路径
				auser.setPortrait(this.takePortraitRootUrl(auser));
				return PackageUtils.packageData(1, auser, "获取用户成功");
			}else{
				return PackageUtils.packageData(0, cuser, "密码错误或用户不存在");
			}
		}
	}
	
	/**
	 * 通过用户信息计算出头像所在服务器位置
	 * 暂时未实现
	 * @param cuser
	 * @return
	 */
	private String takePortraitRootUrl(CommonUser cuser){
		
		return !StringUtils.isBlank(cuser.getPortrait())?
						"http://42.96.157.115:8080//portrait/"+cuser.getPortrait():null;
	}
	
	/**
	 * cookie 会被redis取代
	 * 
	 * @param cookieArrs
	 * @param userToken 现阶段为username
	 * @return
	 */
	@Deprecated
	private boolean isCookieLogined(Cookie[] cookieArrs,String uname) {
		// TODO Auto-generated method stub
		String unameToken = uname!=null?uname.hashCode()+"":null;
		//String phoneNbToken = phoneNb!=null?phoneNb.hashCode()+"":null;
		
		
		if(cookieArrs != null){
			for(Cookie cok : cookieArrs){
				if((StaticEnums.COOKIE_USER+"").equals(cok.getName()) && cok.getValue() != null && !"".equals(cok.getValue())){
					String[] ckArrs = cok.getValue().split(",");
					if (ckArrs.length == 2 && (Arrays.binarySearch(ckArrs, unameToken) >= 0 )){
						return true;
					}
					return false;
				}
			}
		}
		return false;
	}

/*	@Override
	public boolean isExistInDb(String username) {
		// TODO Auto-generated method stub
		System.out.println("go sql user");
		CommonUser cuser = userDaoImpl.returnUser(username);
		if (cuser != null){
			return true;
		}else{
			//cookie lose user
			return false;
		}
	}*/
	
	/*	public static void main(String[] args) throws IOException {
	  SqlSessionFactory sessionFactory;
	  sessionFactory = new SqlSessionFactoryBuilder()
	    .build(Resources.getResourceAsReader("Configuration.xml"));
	  SqlSession sqlSession = sessionFactory.openSession();
	  CUserMapper userMapper = sqlSession.getMapper(CUserMapper.class);
	  System.out.println(userMapper.findAllUser(20));
	  CommonUser user1 = new CommonUser();
	  user1.setUsername("user77");
	  user1.setIbirthday(new Date());
	  userMapper.insertUser(user1);
	  sqlSession.commit();
	  sqlSession.close();*/
	  
	  
/*		  CommonUser cu1 = new CommonUser();
	  cu1.setUsername("1");
	  CommonUser cu2 = cu1;
	  
	  System.out.println(cu2.getUsername());
	  cu2.setUsername("2");
	  cu2.setId(222);
	  System.out.println(cu1.getId());
	  
}*/
	
	@Override
	public int addUserInRedis(CommonUser cuser) {
		// TODO Auto-generated method stub
		userCacheDao.putUserToCache(cuser);
		return 0;
	}
	
	@Override
	public UserOperaVO userInDb(CommonUser cuser) {
		// TODO Auto-generated method stub
		return userDaoImpl.returnUser(cuser);
	}
	
	/**
	 * getter setter= == = = = = = = = = =
	 */

	@Override
	public boolean isRedisLogined(String uid) {
		// TODO Auto-generated method stub
		
		return userCacheDao.userInCache(uid);
	}

	
	

	public void setUserCacheDao(IUserCacheDao userCacheDao) {
		this.userCacheDao = userCacheDao;
	}
	
	public IUserDao getUserDaoImpl() {
		return userDaoImpl;
	}

	public void setUserDaoImpl(IUserDao userDaoImpl) {
		this.userDaoImpl = userDaoImpl;
	}




	public static void main(String[] args) {
//		System.out.println(",121212111");
//		ResultSet re = null;
//		System.out.println(Arrays.binarySearch(new String[]{"3047872","11111111"}, "3047872"));
		UserOperaVO uv = (UserOperaVO) new CommonUser();
		//uv.setId("11111");
		
		
	}

	public void setRedisDaoImpl(IRedisDao redisDaoImpl) {
		this.redisDaoImpl = redisDaoImpl;
	}
	
	

}
