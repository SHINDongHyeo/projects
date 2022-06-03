package mvc.model.domain;


import java.util.Objects;

import org.json.simple.JSONObject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Product {
	private String name;
	private String type;
	private String price;
	private String stock;
	
	

    @Override
	public boolean equals(Object object) {
		if(object == this) return true;
		if(!(object instanceof Product)) {
			return false;
		}
		Product product = (Product)object;
		return Objects.equals(name, product.name)
				&& Objects.equals(type, product.type)
				&& Objects.equals(price, product.price)
				&& Objects.equals(stock, product.stock);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(name, type, price, stock);
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		builder.append("이름 : ");
		builder.append(name);
		builder.append(", 종류 : ");
		builder.append(type);
		builder.append(", 가격 : ");
		builder.append(price);
		builder.append(", 재고 : ");
		builder.append(stock);
		
		return builder.toString();
	}
	public String toStringNumber() {
		StringBuilder builder = new StringBuilder();
		
		builder.append("이름 : ");
		builder.append(name);
		builder.append(", 종류 : ");
		builder.append(type);
		builder.append(", 가격 : ");
		builder.append(price);
		builder.append(", 수량 : ");
		builder.append(stock);
		
		return builder.toString();
	}
	@SuppressWarnings("unchecked")
	public JSONObject toJsonObject() {
		JSONObject jsonObject = new JSONObject();
		
		jsonObject.put("name", name);
		jsonObject.put("type", type);
		jsonObject.put("price", price);
		jsonObject.put("stock", stock);
		
		return jsonObject;
	}

   
}
