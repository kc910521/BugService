package mb;

import ind.ck.model.bean.CommonUser;
import ind.ck.model.bean.EOrder;
import ind.ck.model.user.CUserMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.web.servlet.mvc.SimpleControllerHandlerAdapter;

public class MbTest {

	private int aaa = 0;
	
	public void queryT(){
		//SimpleControllerHandlerAdapter;
		//RequestMappingHandlerAdapter
		//RequestParamMethodArgumentResolver
		//AnnotatedElementUtils.findMergedAnnotation(element, annotationType)
	}
	
	public static void main(String[] args) throws IOException {
		  SqlSessionFactory sessionFactory;
		  sessionFactory = new SqlSessionFactoryBuilder()
		    .build(Resources.getResourceAsReader("Configuration.xml"));
		  SqlSession sqlSession = sessionFactory.openSession();
		  
		  //EOrder eorder = (EOrder)sqlSession.selectOne("", 1);
		  Map<String,Object> pmap = new HashMap<>();
		  pmap.put("tid", 1);
		  pmap.put("user_id", 1111);
		  //pmap.put("order_id", 12);
		  //List<EOrder> resList = sqlSession.selectList("touch.order.getOrderCondition", pmap);
		  //System.out.println(resList.get(1).getUser_id());
		  
		  List<String> inCondition = new ArrayList<>();
		  inCondition.add("3");
		  inCondition.add("4");
		  int a = sqlSession.delete("touch.order.deleteOrderByIds", inCondition);
		  System.out.println("a:"+a);
		  //sqlSession.insert("touch.order.insertOrder", pmap);
		  
/*		  CUserMapper userMapper = sqlSession.getMapper(CUserMapper.class);
//		  sqlSession.commit();
		  System.out.println(userMapper.findAllUser(20));
		  CommonUser user1 = new CommonUser();
		  user1.setUsername("user77");
		  user1.setIbirthday(new Date());
		  userMapper.insertUser(user1);*/
		  sqlSession.commit();
		  sqlSession.close();
	}
}
