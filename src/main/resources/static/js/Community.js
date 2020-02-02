function post() {
    var questionId=$("#questionId").val();
    var content=$("#commentContent").val();
    commentTarget(1,questionId,content);
}

function commentTarget(type,questionId,content) {
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType:'application/json',
        data:JSON.stringify( {
            "parentId":questionId,
            "content":content,
            "type":type
        }),
        success: function (response) {
            if (response.message=="成功"){
                window.location.reload();
            }else if (response.message=="未登录，请先登录"){
                var isAccepted=confirm(response.message);
                if (isAccepted) {
                    window.open("https://github.com/login/oauth/authorize?client_id=Iv1.e8bdf4793d3e9a65&redirect_uri=http://localhost:8887/callback&scope=user&state=1");
                    window.localStorage.setItem("closable",true);
                }
            } else {
                alert(response.message);
            }
            console.log(response);
        },
        dataType: "json"
    });

    console.log(content);
    console.log(questionId);
}
function comment(e) {
    var commentId=e.getAttribute("data-id");
    var content=$("#input-"+commentId).val();
    commentTarget(2,commentId,content);
}



//展开二级目录
function collapseComments(e) {
    var id=e.getAttribute("data-id");
    var comments=$("#commentCreateDTO-"+id);
    //获取一下二级评论的展开状态
    var collapse=e.getAttribute("data-collapse");
    if (collapse){
        //关闭二级评论
        comments.removeClass("in");
        e.removeAttribute("data-collapse");
    }else {

        //展开二级评论
        comments.addClass("in");
        //标记二级评论展开状态
        e.setAttribute("data-collapse","in");
    }
}
function selectTags(value) {
    var previous = $("#tag").val();
    if (previous.indexOf(value) != -1) {

    } else {
        if (previous) {
            $("#tag").val(previous + ',' + value);
        } else {
            $("#tag").val(value);
        }
    }
}

//标签展示
function showSelectTag() {
    $("#select-tag").show();
}