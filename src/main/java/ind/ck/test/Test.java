package ind.ck.test;

import ind.ck.model.bean.CommonUser;

import java.util.ArrayList;
import java.util.List;


public class Test {

	static {
		System.out.println("static init ===");
	}
	public Test(){
		System.out.println("ddddd");
	}

	public static void openIt(){
		System.out.println("----");
	}
	
	public static void main(String[] args) {
		//Test.openIt();
/*		CommonUser uc1 = new CommonUser();
		uc1.setUsername("ck");
		uc1.setPassword("123456");
		test.addUser(uc1);
		
		System.out.println(test.uList.get(0).getPassword());
		uc1.setPassword("8");
		System.out.println(test.uList.get(0).getPassword());*/
/*		
		
		List<String> alist = new ArrayList<>();
		alist.add("1");
		alist.add("2");
		alist.add("3");
		test.hehe1(alist, alist.remove(0));*/
		
/*		Integer a = new Integer(10);
		Integer b = new Integer(10);
		Integer c = a;
		
		System.out.println(a+","+b+","+c);*/
		
	}
}
