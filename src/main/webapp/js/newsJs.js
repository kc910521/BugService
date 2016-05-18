/**
 * 
 */
	
	var commentLoaded = false;
	/**
	 * 刷新评论
	 */
    function regFlushComment() {
        $(window).scroll(function(){
            if (!commentLoaded){
                var scrollTop = $(this).scrollTop();
                var scrollHeight = $(document).height();
                var windowHeight = $(this).height();
                if(scrollTop + windowHeight >= scrollHeight){
                    //alert("you are in the bottom");
                    commentLoaded = true;
                    takeComment();
                }
            }

        });
    }
        
        
        
    function takeComment(){
    	var newsId = $("#sp_newsId").text();
    	if (newsId != undefined){
    		//alert("newsId:"+newsId);
        	$.ajax({
        		url : '/BugService/comment/get/newsId/'+newsId+'/pgNow/1.mk',
        		type : 'GET',
        		dataType:'JSON',
        		success : function (dt){
/*	        			console.dir("11:"+dt);
	        			alert(dt);*/
        			var resultHtml = "";
        			for (var tp = 0; tp < dt.DATA.length; tp++){
        				//console.dir(tp);
        				resultHtml += "" +
                        	"<li class=\"list-group-item\">"+
               					"<span class=\"badge\">"+dt.DATA[tp].timeStamp+"</span>";
               					
               			if (dt.DATA[tp].quote_content != undefined && dt.DATA[tp].quote_content != ''){
               				resultHtml += "                    <\/p><\/article>";
               				resultHtml += "";
               				resultHtml += "                 <article class=\"quote_artical\"><p>";
               			}
               			resultHtml +="<br/>"+dt.DATA[tp].username+" 说："+dt.DATA[tp].content+"&nbsp;";
        				resultHtml += "<\/li>";
        			}
        			if ("" != resultHtml){
        				$("#comment_ul").html("");
        			}
        			$("#comment_ul").append(resultHtml);
        		},
        		error : function(){
        			alert("获取评论数据失败！");
        		}
        	});
    	}
    
    }