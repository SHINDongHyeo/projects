package mvc.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import mvc.exception.DataNotFoundException;
import mvc.model.domain.Customer;
import mvc.model.domain.Order;
import mvc.model.domain.Product;

public class Model {
	
	private static String productListFileName = "products.json";
	private static String orderListFileName = "orders.json";
	
	private Model() {}
	
	private static Model instance = new Model();
	
	/**
	 * Return Model instance
	 * 
	 * @return Model
	 */
	public static Model getInstance() {
		return instance;
	}
	
	
	/**
	 * Read JSON file named "product.json"
	 * 
	 * @return ArrayList<Product> 상품 리스트
	 * @throws IOException 상품 리스트에 데이터가 없는 경우 예외 발생
	 */
	public static ArrayList<Product> readProductList() throws DataNotFoundException {
		BufferedReader bufferedReader = null;
		ArrayList<Product> productList = null;
		JSONParser parser = new JSONParser();
		
		try {
			bufferedReader = new BufferedReader(new FileReader(productListFileName));
			
			JSONObject jsonObject = (JSONObject) parser.parse(bufferedReader);
			JSONArray jsonArray = (JSONArray) jsonObject.get("products");
			
			productList = toProductList(jsonArray);
			return productList;
		}
		// catch IOException, ParseException
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				bufferedReader.close();
			}
			catch(IOException e) {
				e.printStackTrace();
			}
		}
		if(productList == null) {
			throw new DataNotFoundException("상품 리스트 데이터가 없습니다.");
		}
		return productList;
	}
	
	/**
	 * Write JSON file named "products.json"
	 * 
	 * @param productList 상품 리스트
	 */
	@SuppressWarnings("unchecked")
	public static void writeProductList(ArrayList<Product> productList) {
		BufferedWriter bufferedWriter = null;
		JSONObject jsonObject = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		
		for(Product product : productList) {
			jsonArray.add(product.toJsonObject());
		}
		
		jsonObject.put("products", jsonArray);
		
		try {
			bufferedWriter = new BufferedWriter(new FileWriter(productListFileName));
			bufferedWriter.write(jsonObject.toJSONString());
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				bufferedWriter.close();
			}
			catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Read JSON file named "orders.json"
	 * 
	 * @return ArrayList<Order> 주문 리스트
	 * @throws DataNotFoundException 주문 리스트에 데이터가 없는 경우 예외 발생
	 */
	public static ArrayList<Order> readOrderList() throws DataNotFoundException {
		BufferedReader bufferedReader = null;
		ArrayList<Order> orderList = null;
		JSONParser parser = new JSONParser();
		
		try {
			bufferedReader = new BufferedReader(new FileReader(orderListFileName));
			JSONObject jsonObject = (JSONObject) parser.parse(bufferedReader);
			JSONArray jsonArray = (JSONArray) jsonObject.get("orders");
			
			orderList = toOrderList(jsonArray);
			
			return orderList;
		}
		// catch IOException, ParseException
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				bufferedReader.close();
			}
			catch(IOException e) {
				e.printStackTrace();
			}
		}
		if(orderList == null) {
			throw new DataNotFoundException("Data Not Found Exception Occured");
		}
		return orderList;
	}
	
	/**
	 * Write JSON file named "orders.json"
	 * 
	 * @param orderList 주문 리스트
	 */
	@SuppressWarnings("unchecked")
	public static void writeOrderList(ArrayList<Order> orderList) {
		BufferedWriter bufferedWriter = null;
		JSONObject jsonObject = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		
		for(Order order : orderList) {
			jsonArray.add(order.toJsonObject());
		}
		
		jsonObject.put("orders", jsonArray);
		
		try {
			bufferedWriter = new BufferedWriter(new FileWriter(orderListFileName));
			bufferedWriter.write(jsonObject.toJSONString());
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				bufferedWriter.close();
			}
			catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Convert JSONObject to Product
	 * 
	 * @param jsonObject
	 * @return Product
	 */
	private static Product toProduct(JSONObject jsonObject) {
		return new Product(
				(String)jsonObject.get("name"), 
				(String)jsonObject.get("type"), 
				(String)jsonObject.get("price"), 
				(String)jsonObject.get("stock"));
	}
	
	/**
	 * Convert JSONArray to Product list
	 * 
	 * @param jsonArray
	 * @return ArrayList<Product>
	 */
	private static ArrayList<Product> toProductList(JSONArray jsonArray) {
		ArrayList<Product> productList = new ArrayList<Product>();
		for(Object object : jsonArray) {
			productList.add(toProduct((JSONObject)object));
		}
		return productList;
	}
	
	/**
	 * Convert JSONObject to Customer
	 * 
	 * @param jsonObject
	 * @return Customer
	 */
	private static Customer toCustomer(JSONObject jsonObject) {
		return new Customer(
				(String)jsonObject.get("name"),
				(String)jsonObject.get("address"),
				(String)jsonObject.get("phoneNumber"),
				(String)jsonObject.get("point"));
	}
	
	/**
	 * Convert JSONObject to Order
	 * 
	 * @param jsonObject
	 * @return Order
	 */
	private static Order toOrder(JSONObject jsonObject) {
		return new Order(
				(String)jsonObject.get("state"),
				(String)jsonObject.get("payment"),
				(String)jsonObject.get("total"),
				(String)jsonObject.get("date"),
				toCustomer((JSONObject)jsonObject.get("customer")),
				toProductList((JSONArray)jsonObject.get("productList")));
	}
	
	/**
	 * Convert JSONArray to Order list
	 * 
	 * @param jsonArray
	 * @return ArrayList<Order>
	 */
	private static ArrayList<Order> toOrderList(JSONArray jsonArray) {
		ArrayList<Order> orderList = new ArrayList<Order>();
		for(Object object : jsonArray) {
			orderList.add(toOrder((JSONObject)object));
		}
		return orderList;
	}
}
