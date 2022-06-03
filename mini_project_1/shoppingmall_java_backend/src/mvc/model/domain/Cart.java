package mvc.model.domain;

import java.util.ArrayList;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class Cart {
    private ArrayList<Product> productList;

    public void addProduct(Product product) {
        productList.add(product);
    }

    public void deleteProduct(Product product) {
        for (Product pd : productList) {
            if (pd.getName().equals(product.getName())) {
                pd = null;
            }
        }
    }
}
