package ind.ck.controller.common;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ind.ck.common.util.PackageUtils;
import ind.ck.model.bean.Comment;
import ind.ck.model.vo.CommentVO;
import ind.ck.service.cmt.ICommentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/common")
public class CommonController {

	@ResponseBody
	@RequestMapping(value="/exception",method={RequestMethod.GET,RequestMethod.POST})
	public Map<String,Object> exceptionGet(){
		return PackageUtils.packageData(0, "", "服务器或网络异常");
	}
	
	@ResponseBody
	@RequestMapping(value="/noFound",method={RequestMethod.GET,RequestMethod.POST})
	public Map<String,Object> noFoundGet(){
		return PackageUtils.packageData(0, "", "资源请求URL不合法");
	}
	
	
	
}
