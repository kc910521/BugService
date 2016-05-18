package ind.ck.service.order;

import ind.ck.model.bean.CommonUser;
import ind.ck.model.bean.EOrder;

import java.io.IOException;
import java.util.List;

public interface IOrderService {

	List<EOrder> findOrderBy(CommonUser cuser);
	
	public void takeOneFromTxt(String fPath) throws IOException;
}
