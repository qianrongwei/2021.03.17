/**
 * 提交回复(对问题进行回复)
 */
function post() {
    var questionId = $("#question_id").val();
    var comment = $("#comment_content").val();
    comment2Target(questionId,1,comment);

}

function comment2Target(targetId,type,comment) {
    if(!comment){
        alert("不能输入空内容.....");
        return;
    }
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: "application/json",
        data: JSON.stringify({
            "parentId": targetId,
            "comment": comment,
            "type": type
        }),
        success: function (response) {
            if(response.code == 200){
                //$("#comment_section").hide();
                window.location.reload();
            }else{
                if(response.code == 2003){
                    var isAccepted = confirm(response.message);
                    if(isAccepted == true){
                        window.open("https://github.com/login/oauth/authorize?client_id=c9804c645a2454bbad9e&redirect_uri=http://localhost:8080/callback&scope=user&state=1");
                        window.localStorage.setItem("closable",true);
                    }
                }else{
                    alert(response.message);
                }
            }
        },
        dataType: "json"
    });

}

function comment(e) {
    var commentId = e.getAttribute("data-id");
    var comment = $("#input-"+commentId).val();
    comment2Target(commentId,2,comment);
}

/*
    展开二级评论
 */
function collapseComments(e) {
    var id = e.getAttribute("data-id");
    var comments = $("#comment-" + id);

    //获取二级评论的展开状态
    var collapse = e.getAttribute('data-collapse');
    if(collapse) {
        //折叠二级评论
        comments.removeClass("in");
        e.removeAttribute("data-collapse");
        e.classList.remove("active");
    }else{
        var subCommentContainer = $("#comment-"+id);
        if(subCommentContainer.children().length != 1){
            //之前已经加载过了
            //展开二级评论
            comments.addClass("in");
            //标记二级评论展开状态
            e.setAttribute("data-collapse","in");
            e.classList.add("active");;
        }else{
            $.get( "/comment/"+id, function( data ) {
                $.each(data.data.reverse(),function (index,comment) {

                    var mediaLeftElement = $("<div/>",{
                        "class": "media-left"
                    }).append($("<img/>",{
                        "class": "media-object img-rounded",
                        "src": comment.user.avatarUrl
                    }));

                    var mediaBody = $("<div/>",{
                        "class": "media-body"
                    }).append($("<h5/>",{
                        "class": "media-heading",
                        html: comment.user.name
                    })).append($("<div/>",{
                        html: comment.comment
                    })).append($("<div/>",{
                        "class": "menu",
                    }).append($("<span/>",{
                        "class": "pull-right",
                        html: moment(comment.gmtCreate).format('YYYY-MM-DD'),
                    })));


                    var mediaElement = $("<div/>",{
                        "class": "media"
                    }).append(mediaLeftElement)
                        .append(mediaBody);

                    var commentElement = $("<div/>",{
                        "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 comments" ,
                    }).append(mediaElement);
                    subCommentContainer.prepend(commentElement);
                });
                //展开二级评论
                comments.addClass("in");
                //标记二级评论展开状态
                e.setAttribute("data-collapse","in");
                e.classList.add("active");;
            });
        }
    }
}

function selectTag(e) {

    var value = e.getAttribute("data-tag");
    var preVious = $("#tag").val();
    if(preVious.indexOf(value) == -1){
        if(preVious){
            $("#tag").val(preVious + ',' + value);
        }else{
            $("#tag").val(value);
        }
    }
}

function showSelectTag() {
    $("#select-tag").show();
}








