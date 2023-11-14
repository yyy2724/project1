//아이디가 ajaxBtn 클릭하면 ajaxFn함수 실행
//강사님 예제에서 발췌
$('ajaxBtn').on('click',ajaxFn)

function ajaxFn(){
           //변수명 data
    const data={
        'item':${'itemId'}.val().
        'replyContent':$('#replyContent').val().
        'replyWriter':${"#replyWriter"}.val()
    }
    console.log(data);

    $.ajax({
    type:'POST',
    url:"/reply/ajaxWrite",

    //예약어 data
    data:data,
    success:function(res){
    alert("댓글달기완료")
    console.log(res);

    let reData="<tr>";
        reData+="<td>"+res.itemId+"</td>";
        reData+="<td>"+res.id+"</td>";
        reData+="<td>"+res.replyWriter+"</td>";
        reData+="<td>"+res.replyContent+"</td>";
        reData+="<td>"+res.createTime+"</td>";
        reData+="</tr>";
        $('#tData').append(reData);
    },
    error:()=>{
        alert("댓글달기 실패")
    }


    })
}