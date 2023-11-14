$(function(){

	$("#joinCheckedBtn").click(function(){

		var id = $("#id").val();
		var password = $("#password").val();
		var name = $("#name").val();
		var phone = $("#phone").val();
		var address = $("#city").val();


		id = $.trim(id);
		password = $.trim(password);
		name = $.trim(name);
		phone = $.trim(phone);
		address = $.trim(address);

		if(id == ""){
			alert("아이디를 입력해 주세요.");
			$("#id").focus();
			return false;
		}
		if(password == ""){
			alert("패스워드를 입력해 주세요.");
			$("#password").focus();
			return false;
		}
		if(name == ""){
			alert("이름을 입력해 주세요.");
			$("#name").focus();
			return false;
			}
		if(phone == ""){
			alert("폰번호를 입력해 주세요.");
			$("#phone").focus();
			return false;
			}
		if(address == ""){
			alert("주소를 입력해 주세요.");
			$("#city").focus();
			return false;
		}

		$("#id").val(id);
		$("#password").val(password);
		$("#name").val(name);
		$("#phone").val(phone);
		$("#city").val(address);

		//serialize가 form요소하나하나를 읽어옴
		var formData = $("#joinForm").serialize();


		$.ajax({
			/* 전송 전 세팅 */
			type:"POST", //http메서드를 쓰면됨
			data:formData, //화면에 입력한 데이터 위에 변수 선언한거
			url:"member/join", //데이터를 전송하여 저장시키는 url
			dataType:text, //리턴타입, 성공여부를 text로 추출해줌

			/* 전송 후 세팅 */
			success: function(result) { //controller에서 return받은 message부분임
				if(result == "1"){
				    alert("가입 성공");
					location="/member/login"; //저장이 완료된 이후 이동하는 url
				}else{
					alert("가입 실패");
				}
			},
		    error: function() { //시스템에러
		    	alert("오류발생");
			}
		});


	});
});



//var idFlag = false;
//
//$("#id").blur(function(){   // .blur
//idFlag = false;     // ?
//checkId("first");   // ?
//});
//
//function checkId(event){
//if(idFlag)
//return true;
//
//var id = $("#id").val();
//var oMsg = $("#idMsg");
//var oInput = $("#id");
//
//        //============ [id 필수입력] ============
//        if (id == "") {
//            showErrorMsg(oMsg, "필수 정보입니다."); //에러메시지 출력
//            setFocusToInputObject(oInput); //전송 플래그 조절
//            return false;
//        }
//
//}
//
//        //============ [id 유효성검사] ============
//        var isID = /^[a-z0-9][a-z0-9_\-]{4,19}$/;
//        if (!isID.test(id)) {
//            showErrorMsg(oMsg, "5~20자의 영문 소문자, 숫자와 특수기호(_),(-)만 사용 가능합니다."); //에러메시지 출력
//            setFocusToInputObject(oInput); //전송 플래그 조절
//            return false;
//        }
//
//        //============ [id 중복체크] ============
//        // 추가) ajax 통신으로 DB 데이터 조회해서 중복확인하기!!!(post 방식)
//        idFlag = false; //검사 flag
//        let userId = $('input[name=id]').val(); // input_id에 입력되는 값
//        console.log("입력한 id값 : " + userId);
//
//        //id 중복체크를 위해 input에 입력한 id값을 가져와서 ajax data로 반드시 보내줘야한다.
//        $.ajax({
//            url: "member/emailChecked",
//            type: "post",
//            data: {
//                userId: userId
//            },
//            dataType: 'json',
//            success: function (result) {
//                //Action에서 받은 result값 : 1이면 사용가능, 0이면 중복됨
//                if (result == 0) { //돌려받은 결과가 중복이 존재한다는 0이면
//
//                    showErrorMsg(oMsg, "이미 사용중이거나 탈퇴한 아이디입니다."); //에러메시지 출력
//                    setFocusToInputObject(oInput); //전송 플래그 조절
//
//                } else { //돌려받은 결과가 중복이 없다는 false이면
//                    if (event == "first") { //그리고 #id라면
//                        showSuccessMsg(oMsg, "멋진 아이디네요!"); //에러메시지 출력
//                    } else {
//                        hideMsg(oMsg); //메시지 숨김
//                    }
//                    idFlag = true; //id 플래그 1로 변경. 더이상 id alert 발생 x
//                }
//            },
//            error: function () {
//                alert("서버요청실패");
//            }
//        })
//        return true;
//    } //function checkId(event) 끝


$('#emailCheckedBtn').on('click',emailCheckedFn);
function emailCheckedFn(){
const data={
  'email':$('#id').val()
};
  $.ajax({
    type:"GET",
    url:"/member/emailChecked",
    data:data,
    success:function(res){
        if(res==1){
          alert("중복이메일 입니다. 다시 이메일을 입력하세요")
          $('#email').focus();
          return false;
        }
         alert("등록가능한 이메일 입니다.")
     },
    error:function(){
        alert("Faill!!");
     }

  });
}