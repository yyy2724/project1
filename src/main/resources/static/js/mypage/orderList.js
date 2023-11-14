function cancel(orderId) {
    // AJAX 호출을 생성합니다.
    var xhr = new XMLHttpRequest();

    // AJAX 요청이 완료되었을 때 호출될 콜백 함수를 정의합니다.
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                // 삭제 성공 시 실행할 코드를 여기에 작성합니다.
                alert('주문 취소 성공!');
            } else {
                // 삭제 실패 시 실행할 코드를 여기에 작성합니다.
                alert('실패: ' + xhr.status);
            }
        }
    };

    // 삭제할 상품의 ID를 URL 파라미터로 포함하여 전송합니다.
    var url = '/order/' + orderId + '/cancel' ;  // 여기에 컨트롤러의 URL을 넣어주세요

    // DELETE 메서드를 사용하여 AJAX 요청을 전송합니다.
    xhr.open('POST', url);

    // AJAX 요청을 전송합니다.
    xhr.send();
}
