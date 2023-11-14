/*javascript ajax*/
const updateBtn=document.querySelector('#updateBtn');
updateBtn.addEventListener('click',updateFn);


function updateFn(e){
  e.preventDefault();
  const data={
      id: document.querySelector('#member_id').value,
      email: document.querySelector('#member_email').value,
      pw: document.querySelector('#member_pw').value,
      phone: document.querySelector('#member_phone').value,
      address: document.querySelector('#member_address').value
  };
  const url="/mypage/myUpdate";
  const method="POST";

  fetch(url, {
    method: method, // POST
    headers: { // 헤더 조작
      "Content-Type": "application/json",
    }, body: JSON.stringify(data),
    }).then((response) => response.json())
    .then((data) =>{
            alert("회원수정 Success!!")
            document.querySelector('#member_id').value=data.id;
            document.querySelector('#member_email').value=data.email;
            document.querySelector('#member_pw').value=data.pw;
            document.querySelector('#member_phone').value=data.nickName;
            document.querySelector('#member_address').value=data.role;
          }
     );
}