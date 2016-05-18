package ind.ck.controller.news;

import java.util.Map;

import ind.ck.common.anno.VertifyAnnotation;
import ind.ck.common.util.VelocityUtils;
import ind.ck.dao.util.PropertiesUtil;
import ind.ck.enums.StaticEnums;
import ind.ck.service.news.INewsService;
import ind.ck.service.news.NewsServiceImpl;
import ind.ck.service.user.IUserOper;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.PathParam;

import org.apache.catalina.tribes.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Scope("singleton")
public class NewsController {
	
	@Autowired @Qualifier("newsServiceImpl")
	INewsService newsServiceImpl;
	
	@Autowired
	IUserOper iUserOper = null;
	

	//@VertifyAnnotation
	@RequestMapping(value="/news/{newsId}/page/{pageNo}",method={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView takeMVFromNews(
			@PathVariable String newsId,
			@PathVariable int pageNo,
			HttpServletRequest request,
			ModelAndView mdv
			){
		System.out.println(Arrays.toString(request.getCookies()) );
		
		
		if (VelocityUtils.fileIsExist(newsId)){
			
			mdv.setViewName("template/"+VelocityUtils.HTML_FILE_HEAD+newsId+".html");
		}else{
			//mdv.addAllObjects();
			Map<String,String> dtMap = newsServiceImpl.takeHtmlContent(newsId, pageNo);
			mdv.addAllObjects(dtMap);
			mdv.setViewName("page/NewsTplet.html");
			VelocityUtils.htmlStaticlize(newsId, dtMap);
		}

		
		
/*		if ( iUserOper.isCacheLogined(StaticEnums.COOKIE_USER+"", request.getCookies()) ){
			
		}else{
			mdv.setViewName("wcnm.html");
		}*/
		return mdv;
	}
	


	

	public INewsService getNewsServiceImpl() {
		return newsServiceImpl;
	}

	public void setNewsServiceImpl(INewsService newsServiceImpl) {
		this.newsServiceImpl = newsServiceImpl;
	}

	public IUserOper getiUserOper() {
		return iUserOper;
	}

	public void setiUserOper(IUserOper iUserOper) {
		this.iUserOper = iUserOper;
	}

	
	
	
}
