<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/_common/tagInclude.jsp" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
<style>
._42fu, ._42gx:focus, ._42gx:hover{background-image:url(https://fbstatic-a.akamaihd.net/rsrc.php/v2/yq/r/EvPprLxGs_q.png);background-repeat:no-repeat;background-size:auto;background-position:-352px -152px;background-color:#eee;border:1px solid #999;border-bottom-color:#888;-webkit-box-shadow:0 1px 0 rgba(0, 0, 0, .1)}
._42fu:before{content:'';display:inline-block;height:20px;vertical-align:middle}
._42fu:active, ._42fu._42fs{background:#ddd;border-bottom-color:#999;-webkit-box-shadow:inset 0 1px 1px rgba(0, 0, 0, .2)}
._42fu .img{bottom:1px;position:relative;vertical-align:middle}
._42ft{cursor:pointer;display:inline-block;text-decoration:none;white-space:nowrap}
._42ft:hover{text-decoration:none}
._42ft + ._42ft{margin-left:4px}
</style>
<title>::: welcome :::</title>
<script type="text/javascript" src="/resources/js/jquery-1.9.0.min.js" ></script>
<script type="text/javascript" src="/resources/js/jquery.form.js" ></script>
<script type="text/javascript">
$(document).ready(function(){ 
	
	$('#postBtn').click(function(){
		if($('#message').val() == ''){
			return false;
		}
		
		$.post('/post', {post:$('#message').val()});
		$('#message').val('');
		$('#message').focus();
	});
	
});

function commentRegist(postId){
	$.post('/comment', {comText:$('#comText'+postId).val(), postId:postId});
	$('#comText'+postId).val('');
	$('#comText'+postId).focus();
}

function postList(){	
	 $.ajax({
		url : '/post',
		type : "GET", 
		cache: false, 
		success : function(messages){
			$.each(messages, function(){
				$('#posting').prepend('<input type="text" id="comText'+this.postId+'" placeholder="댓글 남기기" size="50" onKeyUp="javascript:onEnterKeyUp(event, '+this.postId+', this)" /> <br />')
				.prepend('<input type="hidden" id="postId" name="postId" value="'+this.postId+'"/>')
				.prepend('<div id="comment'+this.postId+'"></div>')
				.prepend('<a id="postLike'+this.postId+'" href="#" onClick=javascript:postLike("'+this.postId+'","'+this.ownerId+'");><font size="1">좋아요</font></a><br />')
				.prepend('<div id = "likeInfo'+this.postId+'">')
				.prepend('<strong>'+this.ownerId+'</strong><br />'+this.post+'<br />')
				.prepend('<img src="/resources/upload/${loginUser.userPhoto}" width="30" height="30"/>');
				//.append('<button type="button" id="commentBtn'+this.postId+'" onClick="javascript:commentRegist('+this.postId+');">댓글</button> <br />');
			});
		},
		error : function(xhr){
			if (xhr.statusText != "abort" && xhr.status != 503) {
				console.error("Unable to retrieve chat messages. Chat ended.");
			}
		},
		complete : postList
	});
}

function commentList(){
	 $.ajax({
		url : '/comment',
		type : "GET",
		cache: false,
		success : function(messages){
			$.each(messages, function(){
				$('#comment'+this.postId).append('<font size="2">'+this.ownerId+' '+this.comText+'</font><br />');
			});
		},
		error : function(xhr){
			if (xhr.statusText != "abort" && xhr.status != 503) {
				console.error("Unable to retrieve chat messages. Chat ended.");
			}
		},
		complete : commentList
	});
}

function onEnterKeyUp(ev, postId, obj){
    var evKeyup = null;

    if(ev){ //firefox
        evKeyup = ev;    
    }else{ //explorer
        evKeyup = window.event;
    }
    if(obj.value == ''){
    	return false;
    }
    if(evKeyup.keyCode == 13){ // enter key code:13
    	commentRegist(postId);
    }
}

function postLike(postId, ownerId){
	 $.getJSON('likeit.json', {postId:postId, ownerId:ownerId}, function(data){
		$('#likeInfo'+postId).prepend('<a id="yourLike'+postId+'"><font size="1">회원님이 좋아합니다</font><br /></a>');
		 
		$('#postLike'+postId).attr({'onClick': 'javascript:postLikeCancel("'+postId+'", "'+ownerId+'")'});
		$('#postLike'+postId).html('<font size=1>좋아요 취소</font>');
	}); 
}

function postLikeCancel(postId, ownerId){
	 $.getJSON('likeitcancel.json', {postId:postId, ownerId:ownerId}, function(data){
		 $('#yourLike'+postId).remove();
		$('#postLike'+postId).attr({'onClick': 'javascript:postLike("'+postId+'", "'+ownerId+'")'});
		$('#postLike'+postId).html('<font size=1>좋아요</font>');
	}); 
}

</script>
</head>
<body onLoad="postList();commentList();">
<div>
	<c:url value="./secLogout" var="logoutUrl" />
	<img src="/resources/upload/${loginUser.userPhoto}" width="30" height="30"/>
	<strong><sec:authentication property="principal.username" /></strong>
	<a href="${logoutUrl}">LogOUT</a> 
	<br />팔로잉 <b>${followingCount}</b> 팔로워 <b>${followerCount}</b> <br />
</div>

<p /> 

	<input type="text" placeholder="지금 무슨 생각을 하고 계신가요?" size="100" id="message" name="message" />
	<button type="button" id="postBtn" class="_42fu _42ft">게시</button>

	<div id="posting">
		<c:forEach items="${postList}" var="post" varStatus="status"> 
			<img src="/resources/upload/${post.userPhoto}" width="30" height="30"/>
			<strong>${post.ownerId}</strong> <br />${post.post}<br />
			
			<div id = "likeInfo${post.postId}">
				<font size="1">
					<c:set var="isLike" value="${status.current}" />						
					<c:forEach items="${post.postLikeList}" var="postLikeList">
						<c:choose>
							<c:when test="${loginUser.username eq postLikeList.ownerId}">
								<c:set target="${isLike}" property="like" value="true" />
								<a id="yourLike${post.postId}">
									<font size="1">회원님이 좋아합니다</font><br />
								</a>
							</c:when>
							<c:otherwise>
								<font size="1">${postLikeList.ownerId}님이 좋아합니다</font><br />							
							</c:otherwise>
						</c:choose>
					</c:forEach>
					<c:choose>
						<c:when test="${isLike.like eq true}">
							<a id="postLike${post.postId}" href="#" onClick="javascript:postLikeCancel('${post.postId}','${post.ownerId}')">좋아요 취소</a>
						</c:when>
	 					<c:otherwise>
	 						<a id="postLike${post.postId}" href="#" onClick="javascript:postLike('${post.postId}','${post.ownerId}')">좋아요</a>
	 					</c:otherwise>
					</c:choose>
				</font>
			</div>
			<div id="comment${post.postId}">
				<c:forEach items="${post.commentList}" var="comment">
					<font size="2">${comment.ownerId} ${comment.comText}</font><br />
				</c:forEach>
			</div>
			<input type="hidden" id="postId" name="postId" value="${post.postId}"/>
			<input type="text" id="comText${post.postId}" placeholder="댓글 남기기" size="50" onKeyUp="javascript:onEnterKeyUp(event, ${post.postId}, this)" /> <br />
		</c:forEach>
	</div>
	<p />
	<a href="/">메인으로</a>
</body>
</html>