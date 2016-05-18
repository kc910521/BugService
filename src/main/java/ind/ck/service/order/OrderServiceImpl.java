package ind.ck.service.order;

import ind.ck.model.bean.CommonUser;
import ind.ck.model.bean.EOrder;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;


@Service("orderServiceImpl")
@Scope("singleton")
public class OrderServiceImpl implements IOrderService{

	private SqlSession sqlSession = null;
	
	public OrderServiceImpl(){
/*		try {
			sqlSession = this.takeSqlSession();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
	
	@Override
	public List<EOrder> findOrderBy(CommonUser cuser) {
		// TODO Auto-generated method stub
		List<EOrder> resList = null;
		if (cuser != null && sqlSession != null){
			resList = sqlSession.selectList("touch.order.getOrderByUser", cuser);
		}
		return resList;
	}
	
	@Override
	public void takeOneFromTxt(String fPath) throws IOException{
		File file = new File(fPath);
		if (file.exists()){
			BufferedReader bread = new BufferedReader(new FileReader(file));
			StringBuffer sbuf = new StringBuffer();
			String ass = null;
			while ((ass = bread.readLine()) != null){
				System.out.println("ass:"+ass);
			}
			bread.close();
		}
		System.out.println("file disconnected.");
	}
	
	public byte[] readFileSt(String fPath) throws IOException{
		File file = new File(fPath);
		byte[] fbyte = null;
		if (file.exists()){
			long fLength = file.length();
			fbyte = new byte[(int) fLength];
			BufferedInputStream bfis = new BufferedInputStream(new FileInputStream(file));
			int cc = bfis.available();
			int r = bfis.read(fbyte,0,fbyte.length);
			if (r != cc){
				try {
					throw new Exception("ERROR READ FILE");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			bfis.close();
		}
		
		return fbyte;
	}
	
	public void outPutFile(String tPath) throws IOException{
		File file = new File(tPath);
		if (!file.exists() && !file.isDirectory()){
			file.createNewFile();
		}
		BufferedWriter bwriter = new BufferedWriter(new FileWriter(file));
		bwriter.write("111");
		bwriter.write("222");
		bwriter.flush();
		bwriter.close();
	}
	
	

	
	private final SqlSession takeSqlSession() throws IOException{
		  SqlSessionFactory sessionFactory;
		  sessionFactory = new SqlSessionFactoryBuilder()
		    .build(Resources.getResourceAsReader("Configuration.xml"));
		  return sessionFactory.openSession();
	}
}
