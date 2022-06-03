package mvc.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;

import mvc.exception.DataNotFoundException;
import mvc.exception.InvalidInputException;
import mvc.exception.OutOfStockException;
import mvc.model.Model;
import mvc.model.domain.Order;
import mvc.model.domain.Product;
import mvc.view.EndView;

public class Controller {
	
	private static ArrayList<Product> productList = null;
	private static ArrayList<Order> orderList = null;
	private static ArrayList<Product> cart = new ArrayList<Product>();
	
	// String null check
	private static String validateString(String string) throws InvalidInputException {
		if(string!=null) {
			return string;
		}
		throw new InvalidInputException("유효하지 않은 입력입니다.");
	}
	
	// 존재하는 product에 접근하는지 검증
	private static Product validateProduct(String name) throws DataNotFoundException {
		try {
			productList = Model.readProductList();
			for(Product product : productList) {
				if(product.getName().equals(validateString(name))) {
					return product;
				}
			}
		}
		catch(Exception e) {
			EndView.printMsg(e.getMessage());
		}
		throw new DataNotFoundException("해당 상품은 없습니다.");
	}
	
	// 존재하는 order에 접근하는지 검증
	private static Order validateOrder(String phone) throws DataNotFoundException {
		try {
			for(Order order : getOrderList()) {
				if(order.getCustomer().getPhoneNumber().equals(validateString(phone))) {
					return order;
				}
			}
		}
		catch(InvalidInputException e) {
			EndView.printMsg(e.getMessage());
		}
		throw new DataNotFoundException("해당 주문은 없습니다.");
	}
	
	// 상품 추가
	public static void insertProduct(Product product) {
		try {
			productList = Model.readProductList();
			productList.add(product);
			Model.writeProductList(productList);
			EndView.printMsg("상품 추가 완료.");
		}
		catch(DataNotFoundException e) {
			EndView.printMsg(e.getMessage());
		}
	}
	
	
	// 상품 리스트 검색
	public static void getProductList() {
		try {
			productList = Model.readProductList();
			EndView.printProductList(productList);
		}
		catch(DataNotFoundException e) {
			EndView.printMsg(e.getMessage());
		}
	}
	
	// 상품 재고 수정(주문 시 상품 재고 수정 필요)
	private static void updateProductStock(String name, String stock) throws OutOfStockException {
		try {
			Product product = validateProduct(name);
			Integer number = Integer.parseInt(product.getStock()) - Integer.parseInt(stock);
			if(number >= 0) {
				product.setStock(number.toString());
				Model.writeProductList(productList);
			}
			else {
				throw new OutOfStockException("재고가 없습니다.");
			}
		}
		catch(DataNotFoundException e) {
			EndView.printMsg(e.getMessage());
		}
	}
	
	// 장바구니에 상품 추가
	public static void addCart(String name, String stock) {
		try {
			Product product = validateProduct(name);
			Integer number = Integer.parseInt(product.getStock()) - Integer.parseInt(stock);
			if(number >= 0) {
				cart.add(new Product(product.getName(), product.getType(), product.getPrice(), stock));
				EndView.printMsg("장바구니 추가 완료.");
			}
			else {
				EndView.printMsg("재고가 없습니다.");
			}
		}
		catch(DataNotFoundException e) {
			EndView.printMsg(e.getMessage());
		}
	}
	
	// 장바구니 검색
	public static ArrayList<Product> getCart() {
		EndView.printProductList(cart);
		return cart;
	}
	
	// 장바구니 상품들의 총액 검색
	public static String getCartTotal() {
		Integer total = 0;
		for(Product product : cart) {
			total += Integer.parseInt(product.getPrice());
		}
		return total.toString();
	}
	
	// 장바구니의 상품 삭제
	public static void deleteProductInCart(String name) {
		int index = 0;
		try {
			for(Product product : cart) {
				if(product.getName().equals(validateString(name))) {
					index = cart.indexOf(product);
				}
			}
			cart.remove(index);
		}
		catch(InvalidInputException e) {
			EndView.printMsg(e.getMessage());
		}
	}
	
	// 주문 추가
	public static void insertOrder(Order order) {
		try {
			for(Product product : order.getProductList()) {
				updateProductStock(product.getName(), product.getStock());
			}
			getOrderList().add(order);
			Model.writeOrderList(orderList);
		}
		catch(OutOfStockException e) {
			EndView.printMsg(e.getMessage());
		}
		cart.clear();
	}
	
	// 주문 검색
	public static void getOrder(String phone) {
		try {
			EndView.printOrder(validateOrder(phone));
		}
		catch(DataNotFoundException e) {
			EndView.printMsg(e.getMessage());
		}
	}
	
	// 주문 리스트 검색
	private static ArrayList<Order> getOrderList() {
		try {
			orderList = Model.readOrderList();
		}
		catch(DataNotFoundException e) {
			orderList = new ArrayList<Order>();
		}
		return orderList;
	}
	
	public static void printOrderList() {
		EndView.printOrderList(getOrderList());
	}
	
	// 주문 삭제
	public static void deleteOrder(String phone) {
		try {
			ArrayList<Order> tempOrderList = new ArrayList<Order>();
			Order order = validateOrder(phone);
			for(Order orderInList : getOrderList()) {
				if(orderInList.getCustomer().getPhoneNumber().equals(order.getCustomer().getPhoneNumber())) {
					continue;
				}
				else {
					tempOrderList.add(orderInList);
				}
			}
			for(Product product:order.getProductList()) {
				try {
					updateProductStock(product.getName(), Integer.toString((-1)*Integer.parseInt(product.getStock())));
				} catch (OutOfStockException e) {
					e.printStackTrace();
					EndView.printMsg(e.getMessage());
				}
			}
			Model.writeOrderList(tempOrderList);
			EndView.printMsg("주문 취소 완료.");
		}
		catch(DataNotFoundException e) {
			EndView.printMsg(e.getMessage());
		}
	}
	
	public static String inputString() {
		while (true) {
			BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
			try {
				String in = input.readLine();
				return in;
			} catch (IOException e) {
				EndView.printMsg(e.getMessage());
			}
			System.out.println("잘못된 입력입니다. 다시 입력해주세요");
		}
	}
}
