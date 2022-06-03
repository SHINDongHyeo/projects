package mvc.model.domain;

import java.util.Objects;

import org.json.simple.JSONObject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Customer {
	private String name;
	private String address;
	private String phoneNumber;
	private String point;
	
	

    @Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		builder.append("이름 : ");
		builder.append(name);
		builder.append(", 주소 : ");
		builder.append(address);
		builder.append(", 전화 번호 : ");
		builder.append(phoneNumber);
		builder.append(", 포인트 : ");
		builder.append(point);
		
		return builder.toString();
	}
	
	@Override
	public boolean equals(Object object) {
		if(object == this) return true;
		if(!(object instanceof Customer)) {
			return false;
		}
		Customer customer = (Customer)object;
		return Objects.equals(name, customer.name)
				&& Objects.equals(address, customer.address)
				&& Objects.equals(phoneNumber, customer.phoneNumber)
				&& Objects.equals(point, customer.point);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(name, address, phoneNumber, point);
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject toJsonObject() {
		JSONObject jsonObject = new JSONObject();
		
		jsonObject.put("name", name);
		jsonObject.put("address", address);
		jsonObject.put("phoneNumber", phoneNumber);
		jsonObject.put("point", point);
		
		return jsonObject;
	}
}
