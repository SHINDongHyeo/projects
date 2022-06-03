package mvc.model.domain;

import java.util.ArrayList;
import java.util.Objects;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Order {
	private String state;
	private String payment;
	private String total;
	private String date;
	private Customer customer;
	private ArrayList<Product> productList;
	
	
    @Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		builder.append("상태 : ");
		builder.append(state);
		builder.append(", 결제 수단 : ");
		builder.append(payment);
		builder.append(", 결제 금액 : ");
		builder.append(total);
		builder.append(", 주문 날짜 : ");
		builder.append(date);
		builder.append(", 주문 고객 : ");
		builder.append(customer.toString());
		builder.append(", 주문 내역 : ");
		
		for(Product product : productList) {
			builder.append(product.toString());
		}
		
		return builder.toString();
	}
	public String toStringNumber() {
		StringBuilder builder = new StringBuilder();
		
		builder.append("상태 : ");
		builder.append(state);
		builder.append(", 결제 수단 : ");
		builder.append(payment);
		builder.append(", 결제 금액 : ");
		builder.append(total);
		builder.append(", 주문 날짜 : ");
		builder.append(date);
		builder.append(", 주문 고객 : ");
		builder.append(customer.toString());
		builder.append(", 주문 내역 : ");
		
		for(Product product : productList) {
			builder.append(product.toStringNumber());
		}
		
		return builder.toString();
	}
	
	@Override
	public boolean equals(Object object) {
		if(object == this) return true;
		if(!(object instanceof Order)) {
			return false;
		}
		Order order = (Order)object;
		return Objects.equals(state, order.state)
				&& Objects.equals(payment, order.payment)
				&& Objects.equals(total, order.total)
				&& Objects.equals(date, order.date)
				&& Objects.equals(state, order.state)
				&& Objects.equals(customer, order.customer)
				&& Objects.equals(productList, order.productList);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(state, payment, total, date, customer, productList);
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject toJsonObject() {
		JSONObject jsonObject = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		
		for(Product product : productList) {
			jsonArray.add(product.toJsonObject());
		}
		
		
		jsonObject.put("state", state);
		jsonObject.put("payment", payment);
		jsonObject.put("total", total);
		jsonObject.put("date", date);
		jsonObject.put("customer", customer.toJsonObject());
		jsonObject.put("productList", jsonArray);
		
		return jsonObject;
	}
}
