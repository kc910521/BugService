package ind.ck.controller.order;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import ind.ck.model.bean.CommonUser;
import ind.ck.model.bean.EOrder;
import ind.ck.service.order.IOrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class OrderController {

	@Autowired @Qualifier("orderServiceImpl")
	IOrderService iOrderService = null;
	
	@ResponseBody
	@RequestMapping(value="/order/getOrderByUser",method={RequestMethod.GET})
	public List<EOrder> takeOrderBy(CommonUser cuser,Integer id){
		//System.out.println(cuser.getId());
		try {
			iOrderService.takeOneFromTxt("C:\\Users\\KCSTATION\\Desktop\\ÐÂ½¨.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return iOrderService.findOrderBy(cuser);
	}

	public IOrderService getiOrderService() {
		return iOrderService;
	}

	public void setiOrderService(IOrderService iOrderService) {
		this.iOrderService = iOrderService;
	}

	
	
	
}
