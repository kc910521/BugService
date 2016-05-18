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
	 * �޸��û�����
	 * by �ֻ���
	 */
	@Override
	public Map<String,Object> mergeUserPwdInfo(CommonUser cuser){
		int resVal = userDaoImpl.mergeUserPwd(cuser);
		if (resVal > 0){
			Tools.removeUserCookies();
			return PackageUtils.packageData(1, cuser, "�û������޸ĳɹ�,�����µ�¼");
		}else {
			return PackageUtils.packageData(0, cuser, "�û������޸�ʧ��");
		}
		//return ;
	}
	/**
	 * �޸��û���
	 * by �ֻ���
	 */
	@Override
	public Map<String,Object> mergeUserNameInfo(CommonUser cuser){
		int resVal = userDaoImpl.mergeUserName(cuser);
		if (resVal > 0){
			//Tools.removeUserCookies();
			return PackageUtils.packageData(1, cuser, "�û����޸ĳɹ�");
		}else {
			return PackageUtils.packageData(0, cuser, "�û����޸�ʧ��");
		}
	}
	
	@Override
	public Map<String,Object> mergeUserSexual(CommonUser cuser){
		cuser.setSexual(Tools.fixSexual(cuser.getSexual()));
		int resVal = userDaoImpl.mergeUserSexual(cuser);
		if (resVal > 0){
			//Tools.removeUserCookies();
			return PackageUtils.packageData(1, cuser, "�û��Ա��޸ĳɹ�");
		}else {
			return PackageUtils.packageData(0, cuser, "�û��Ա��޸�ʧ��");
		}
	}
	
	/**
	 * ǩ��
	 * ÿ��һ�Σ�redis�洢
	 * key �û�id value ���ǩ��ʱ��
	 * 
	 * @param cuser
	 * @return
	 */
	@Override
	public Map<String,Object> checkUserOnce(CommonUser cuser){
		if (redisDaoImpl != null){
			String tmValue = redisDaoImpl.get(cuser.getUserId());
			if (!StringUtils.isBlank(tmValue)){
				//�ж�ʱ��
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
					return PackageUtils.packageData(0, cuser, "��ǩ����");
				}*/
				if (this.isCheckedOd(tmValue)){
					return PackageUtils.packageData(0, cuser, "��ǩ����");
				}else{
					return this.checkIn(cuser);
				}
			}else{
				//ֱ��ǩ��
				return this.checkIn(cuser);
			}
		}else{
			return PackageUtils.packageData(0, cuser, "REDIS��������æ");
		}
	}
	
	/**
	 * �жϽ����Ƿ�ǩ����
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
	 * ǩ��
	 * @return
	 */
	private Map<String,Object> checkIn(CommonUser cuser){
		cuser.setBonus(15);
		if (userDaoImpl.addUserBonus(cuser) > 0){
			//redis active
			redisDaoImpl.set(cuser.getUserId(), spdf.format(new Date()), 3600*24);
			return PackageUtils.packageData(1, null, "ǩ���ɹ���");
		}else{
			return PackageUtils.packageData(0, cuser, "ǩ��ʧ�ܣ�");
		}
	}
	
	
	public Map<String,Object> uploadPortraitWith(MultipartFile file,CommonUser commonUser,HttpServletRequest request){
        System.out.println("��ʼ");  
        //String path = request.getSession().get.getRealPath("portrait");  
        //String fileName = file.getOriginalFilename();  
//        String fileName = new Date().getTime()+".jpg";  
        commonUser.setPortrait("pk"+UUID.randomUUID().toString().replaceAll("-", "")+".png");
        File targetFile = new File(ProperServlet.PORTRAIT_PATH, commonUser.getPortrait());  //""
        if(!targetFile.exists()){  
            targetFile.mkdirs();  
        }  
        //����  
        try {  
            file.transferTo(targetFile);  
        } catch (Exception e) {  
            e.printStackTrace();  
            return PackageUtils.packageData(0, null, "�ϴ�ͷ��ʧ��"); 
        }finally{
        	file = null;
        	targetFile = null;
        }
        
        if (this.mergeUserPortrait(commonUser)){
        	return PackageUtils.packageData(1, null, "Ū�����ϴ��ɹ���"); 
        }else{
        	return PackageUtils.packageData(0, null, "�ϴ�ͷ����Ϣʧ��"); 
        }
        
       
	}
	
	/**
	 * ����û�ͷ��·����db
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
	 * cuser��Ӧ��ֻ���ֻ�������
	 */
	@Override
	public Map<String,Object> vertifyMobile(CommonUser cuser){
		if (!Tools.isPhoneNumber(cuser.getPhoneNumber())){
			return PackageUtils.packageData(0, cuser, "�ֻ��Ų��Ϸ�");
		}
		//��֤�Ƿ��Ѿ�ע��
/*		List<CommonUser> uList = userDaoImpl.loadUserList(cuser);
		if (uList != null && !uList.isEmpty()){
			//�Ѿ�ע��
			uList.clear();
			uList = null;
			return PackageUtils.packageData(0, cuser, "�ֻ����Ѿ�ע��");
		}*/
		
		if (!this.continueMsgSend(cuser)){
			return PackageUtils.packageData(0, cuser, "��ʱ�޷�ע�ᣬ1���Ӻ����ԡ�");
		}
		
		String pdCode = Tools.takeVertifyCode();
		//���ö���API
		MsgSender msgSender = new MsgSender();
		try {
			msgSender.sendMsg(cuser.getPhoneNumber(), pdCode);
		} catch (ParseException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return PackageUtils.packageData(0, cuser, "�쳣��������");
		}finally{
			msgSender = null;
		}
		UserOperaVO uVo = (UserOperaVO) cuser;
		uVo.setVertifyCode(pdCode);
		//��redis����һ��30�������ڵļ�¼
		redisDaoImpl.set(cuser.getPhoneNumber(), spdf.format(new Date()), 30);
		return PackageUtils.packageData(1, uVo, "���ŷ��ͳɹ�");
	}
	
	/**
	 * �ж��Ƿ����msg��������
	 * redisδ����all ok�����
	 * @param cuser
	 * @return
	 */
	private boolean continueMsgSend(CommonUser cuser){
		if (redisDaoImpl != null){
			String lastTm = redisDaoImpl.get(cuser.getPhoneNumber());
			if (StringUtils.isBlank(lastTm)){
				//�գ�ֱ��ע��
				return true;
			}else{
				return false;
			}
		}
		return true;
		
	}

	/**
	 * �û���ע��
	 * �ɹ���д��cookie
	 * 
	 * WORKING
	 */
	@Override
	public Map<String,Object> registerUser(CommonUser cuser,HttpServletResponse resp) {
		// TODO Auto-generated method stub
		//�ж��û������ֻ��źϷ���
		if (!Tools.isPhoneNumber(cuser.getPhoneNumber())){
			return PackageUtils.packageData(0, cuser, "�û�������Ϊ�ֻ������ֻ��ű���Ϸ�");
		}
		if ( StringUtils.isBlank(cuser.getUname()) || cuser.getUname().matches(".*[,|��|/?|��].*")){
			cuser.setUname(UUID.randomUUID().toString().replaceAll("-",""));
			//return PackageUtils.packageData(0, cuser, "�û������Ϸ�");
		}
		
		//��ʱ�ó��û�������
		String pwd = cuser.getUpwd();
		cuser.setUpwd("");//ƴ��mybatis������
		CommonUser auser = this.userInDb(cuser);
		if (auser == null){
			//��ע��
			
			cuser.setUserId(UUID.randomUUID().toString().replaceAll("-",""));
			cuser.setUpwd(pwd);
			userDaoImpl.registerUser(cuser);

			resp.addCookie(Tools.feedCookies(Tools.takeUserToken(cuser.getUname(),cuser.getPhoneNumber())));
			
			return PackageUtils.packageData(1, cuser, "ע��ɹ���");
		}else{
			return PackageUtils.packageData(0, cuser, "�û��Ѵ���");
		}
	}
	
	/**
	 * �û��ĵ�½
	 * �ɹ���д��cookie
	 */
	@Override
	public Map<String,Object> takeOneUser(CommonUser cuser,HttpServletRequest req,HttpServletResponse resp) {
		// TODO Auto-generated method stub
		//ֱ�Ӵ�id����ȡ�û�ͨ��id
		if (!StringUtils.isBlank(cuser.getUserId())){
			//redisDaoImpl.set(cuser.getId(), cuser.toString(), 1000);
			UserOperaVO auserInn = this.userInDb(cuser);
			if (auserInn == null){
				return PackageUtils.packageData(0, auserInn, "��ȡ�û�ʧ��");
			}else{
				//�����û�ͷ���·��
				auserInn.setPortrait(this.takePortraitRootUrl(auserInn));
				//����ǩ��״̬��vo
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
				
				return PackageUtils.packageData(1, auserInn, "��ȡ�û��ɹ�");
			}
			
			
		}
		
		//����Ϊ�գ����cookie
		//or redis
		if ( StringUtils.isBlank(cuser.getUpwd()) ){
			/**
			 * С�ģ����ﵱ�û��������ֻ���½
			 * username���Ϊ�ֻ���
			 */
			if (this.isCookieLogined(req.getCookies(), 
					cuser.getUname())) {
				//resp.addCookie(PackageUtils.feedCookies(cuser.getUname()));
				return PackageUtils.packageData(1, null, "������û����ڡ�");
			}
			return PackageUtils.packageData(0, null, "������û������ڡ�");
			
		}else{
			//�õ��û�����
			CommonUser auser = this.userInDb(cuser);
			if (auser != null && auser.getUserId() != null && !"".equals(auser.getUserId())){
				resp.addCookie(Tools.feedCookies(Tools.takeUserToken(auser.getUname(),auser.getPhoneNumber())));
				//�����û�ͷ���·��
				auser.setPortrait(this.takePortraitRootUrl(auser));
				return PackageUtils.packageData(1, auser, "��ȡ�û��ɹ�");
			}else{
				return PackageUtils.packageData(0, cuser, "���������û�������");
			}
		}
	}
	
	/**
	 * ͨ���û���Ϣ�����ͷ�����ڷ�����λ��
	 * ��ʱδʵ��
	 * @param cuser
	 * @return
	 */
	private String takePortraitRootUrl(CommonUser cuser){
		
		return !StringUtils.isBlank(cuser.getPortrait())?
						"http://42.96.157.115:8080//portrait/"+cuser.getPortrait():null;
	}
	
	/**
	 * cookie �ᱻredisȡ��
	 * 
	 * @param cookieArrs
	 * @param userToken �ֽ׶�Ϊusername
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
