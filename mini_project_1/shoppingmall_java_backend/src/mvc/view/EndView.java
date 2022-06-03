package mvc.view;

import java.util.ArrayList;

import mvc.model.domain.Order;
import mvc.model.domain.Product;

public class EndView {
	
	public static void printMsg(String msg) {
		System.out.println(msg);
	}
	
	public static void printProduct(Product product) {
		System.out.println(product);
	}
	
	public static void printProductList(ArrayList<Product> productList) {
		for(Product product : productList) {
			System.out.println(product);
		}
	}
	
	public static void printOrder(Order order) {
		System.out.println(order.toStringNumber());
	}
	
	public static void printOrderList(ArrayList<Order> orderList) {
		for(Order order : orderList) {
			System.out.println(order);
		}
	}

}
