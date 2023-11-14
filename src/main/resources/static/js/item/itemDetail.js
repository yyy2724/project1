
    // 수량 입력 필드와 합계 표시 요소를 가져옵니다.
    var quantityInput = document.getElementById("quantity");
    var totalPriceElement = document.getElementById("totalPrice");
    var increaseButton = document.getElementById("increaseQuantity");
    var decreaseButton = document.getElementById("decreaseQuantity");
    var productId = document.getElementById("item_id").value;
    // var productId = parseInt(document.getElementById("item_id").innerText); // 실제 상품 ID로 변경하세요.

    // 수량 변경 이벤트를 감지하여 합계를 업데이트하는 함수를 정의합니다.
    function updateTotalPrice() {
        var quantity = parseInt(quantityInput.value); // 입력된 수량을 정수로 변환합니다.
        var itemPrice = parseInt(document.getElementById("itemPrice").innerText); // 상품 1개의 가격을 설정합니다. 필요에 따라 수정하세요.
        var totalPrice = quantity * itemPrice; // 합계를 계산합니다.

        // 합계를 천 단위 구분 기호(,)를 포함한 문자열로 표시합니다.
        totalPriceElement.innerText = totalPrice.toLocaleString() + "원";
    }

    // + 버튼을 클릭했을 때 수량을 증가시키고 합계를 업데이트합니다.
    increaseButton.addEventListener("click", function () {
        quantityInput.value = parseInt(quantityInput.value) + 1;
        updateTotalPrice();
    });

    // - 버튼을 클릭했을 때 수량을 감소시키고 합계를 업데이트합니다.
    decreaseButton.addEventListener("click", function () {
        var currentQuantity = parseInt(quantityInput.value);
        if (currentQuantity > 1) {
            quantityInput.value = currentQuantity - 1;
            updateTotalPrice();
        }
    });

    // // 장바구니에 담기 버튼 클릭 시 실행되는 함수
    // var cartButton = document.querySelector(".cart_btn");
    //
    // // 버튼에 클릭 이벤트 리스너를 추가합니다.
    // cartButton.addEventListener("click", addToCart);

    var isAddingToCart = false;

    function addToCart() {
        if (isAddingToCart) {
            return;
        }

        isAddingToCart = true;

        var quantity = parseInt(quantityInput.value);
        var totalPrice = parseInt(totalPriceElement.innerText.replace(/[^0-9]/g, '')); // "원" 및 천 단위 구분 기호(,) 제거

            if (quantity > 0 && !isNaN(totalPrice)) {

                // URL에 파라미터로 데이터를 포함하여 요청을 생성합니다.
                var url = '/cart/add?itemId=' + productId + '&count=' + quantity + '&total=' + totalPrice;

                // AJAX 요청을 생성합니다.
                var xhr = new XMLHttpRequest();
                xhr.open('POST', url, false);

                // 요청이 완료되었을 때 처리할 콜백 함수를 정의합니다.
                xhr.onreadystatechange = function () {
                    if (xhr.readyState === XMLHttpRequest.DONE) {
                        if (xhr.status === 200) {
                            isAddingToCart = false;
                            // 성공적으로 처리된 경우, 여기에서 필요한 동작을 수행합니다.
                            alert('장바구니에 상품이 추가되었습니다.');
                        } else {
                            // 오류가 발생한 경우에 대한 처리를 여기에 추가할 수 있습니다.
                            alert('로그인 후 상품을 담아주세요.');
                        }
                    }
                };

                // 요청을 보냅니다.
                xhr.send();

                window.location.href = "/"; // 실제 페이지 URL로 변경하세요.
            } else {
                alert("상품을 올바르게 선택하세요.");
            }
    }

    // 초기에도 합계를 계산하여 표시합니다.
    updateTotalPrice();
