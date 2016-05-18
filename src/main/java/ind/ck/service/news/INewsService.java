package ind.ck.service.news;

import java.util.Map;

public interface INewsService {

	public Map<String,String> takeHtmlContent(String titleId,int pageNo);
	
}
