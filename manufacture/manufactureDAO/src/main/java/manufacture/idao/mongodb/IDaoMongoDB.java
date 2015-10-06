package manufacture.idao.mongodb;

import java.util.List;

import com.mongodb.BasicDBObject;

import manufacture.entity.cart.Cart;
import manufacture.entity.cart.CartProduct;
import manufacture.entity.mongodb.CategoryProduct;
import manufacture.entity.product.Product;

public interface IDaoMongoDB {

	BasicDBObject createProduct(CartProduct cartProduct);
	
	void createOrder(Cart cart);
	
	List<CategoryProduct> productsSellByCategoryAndMonth();
	
	List<CategoryProduct> productsSellByCategoryAndDay();
	
	List<CategoryProduct> productsPublishedByCategoryAndDay();
}