package mvc.view;

import mvc.controller.Controller;
import mvc.model.domain.Customer;
import mvc.model.domain.Order;

public class StartView {

    public static void main(String[] args) {

        System
            .out
            .println("--- 상품 리스트 ---");
        Controller.getProductList();

        while (true) {
            System
                .out
                .println("--- 장바구니 추가 ---");
            System
                .out
                .println("원하는 상품의 이름을 입력해주세요");
            String wantProductName = Controller.inputString();
            System
                .out
                .println("원하는 수량을 입력해주세요(숫자만입력)");
            String wantProductVolume = Controller.inputString();
            Controller.addCart(wantProductName, wantProductVolume);

            String wantMore;
            while (true) {
                System
                    .out
                    .println("장바구니에 계속 추가하시겠습니까?(Y/N)");
                wantMore = Controller.inputString();
                if (wantMore.equals("Y")) {
                    break;
                } else if (wantMore.equals("N")) {
                    break;
                } else {
                    System
                        .out
                        .println("Y 나 N 을 입력해주세요");
                    continue;
                }
            }
            if (wantMore.equals("N")) {
                break;
            }
        }

        // 주문 추가
        System
            .out
            .println("--- 주문 추가 ---");
        System
            .out
            .println("고객 정보를 입력해 주세요");
        System
            .out
            .println("이름을 입력해주세요");
        String customerName = Controller.inputString();
        System
            .out
            .println("주소를 입력해주세요");
        String customerAddress = Controller.inputString();
        System
            .out
            .println("전화번호를 입력해주세요");
        String customerPhoneNumber = Controller.inputString();
        System
            .out
            .println("포인트를 입력해주세요");
        String customerPoint = Controller.inputString();
        Customer customer = new Customer(
            customerName,
            customerAddress,
            customerPhoneNumber,
            customerPoint
        );
        Controller.insertOrder(new Order(
            "결제 완료",
            "신용카드",
            Controller.getCartTotal(),
            "2022-05-24",
            customer,
            Controller.getCart()
        ));

        // 주문 검색
        System
            .out
            .println("--- 주문 검색 ---");
        String searchOrderPhoneNumber = Controller.inputString();
        Controller.getOrder(searchOrderPhoneNumber);

        // 주문 삭제
        System
            .out
            .println("----주문삭제-----");
        String deletehOrderPhoneNumber = Controller.inputString();
        Controller.deleteOrder(deletehOrderPhoneNumber);
        Controller.printOrderList();
    }
}
