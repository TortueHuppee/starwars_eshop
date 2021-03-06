package manufacture.dao.mongodb;

import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;

import manufacture.dao.cart.DaoCart;
import manufacture.entity.cart.Cart;
import manufacture.entity.cart.CartProduct;
import manufacture.entity.mongodb.CategoryProduct;
import manufacture.entity.mongodb.TypeProductProduct;
import manufacture.entity.product.Product;
import manufacture.idao.cart.IDaoCart;
import manufacture.idao.cart.IDaoProductCart;
import manufacture.idao.mongodb.IDaoMongoDB;
import manufacture.idao.product.IDaoProduct;

@Service
@Transactional
public class DaoMongoDB implements IDaoMongoDB {

	private Logger log = Logger.getLogger(DaoMongoDB.class);
	private DBCollection dbCollection;
	
	public DaoMongoDB() {
		log.info("DaoMongoDB.DaoMongoDB : Constructeur, nouvelle connexion MongoDB");
		MongoClient mongoClient = null;
		try {
			mongoClient = new MongoClient("localhost", 27017);
		} catch (UnknownHostException e){
			e.printStackTrace();
		}
		DB db = mongoClient.getDB("startwars");
		DBCollection coll = db.getCollection("products");
		setDbCollection(coll);
	}

	@Override
	public List<CategoryProduct> productsSellByCategoryAndMonth() {

		List<CategoryProduct> result = new ArrayList<>();

//		DBObject unwind = (DBObject) JSON.parse("{$unwind:'$produits'}");

		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.DATE,1);
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		DBObject match = new BasicDBObject("$match",
				new BasicDBObject("dateAchat",
				new BasicDBObject("$gte",calendar.getTime())));

		DBObject project = (DBObject) JSON.parse("{$project:{'quantite':1, "
				+ "'categorie':1,"
				+ "'_id':0}}");
		DBObject group = (DBObject) JSON.parse("{$group:{'_id':'$categorie',"
				+ "'somme':{'$sum':'$quantite'}}}");

		List<DBObject> operations = new ArrayList<DBObject>();
//		operations.add(unwind);
		operations.add(match);
		operations.add(project);
		operations.add(group);

		AggregationOutput output = dbCollection.aggregate(operations);
		Iterable<DBObject> cursor = output.results();

		for (DBObject moObj : cursor)
		{
			CategoryProduct cp = new CategoryProduct();
			cp.setCategory(moObj.get("_id").toString());
			int quantity = Integer.parseInt(moObj.get("somme").toString());
			cp.setQuantity(quantity);
			result.add(cp);
		}
		return result;
	}
	
	@Override
	public List<CategoryProduct> productsSellByCategoryAndOneMonthAgo() {

		List<CategoryProduct> result = new ArrayList<>();

//		DBObject unwind = (DBObject) JSON.parse("{$unwind:'$produits'}");

		Calendar calendarStart = Calendar.getInstance();
		Calendar calendarEnd = Calendar.getInstance();
		calendarStart.set(calendarStart.DATE,1);
		calendarStart.add(calendarStart.MONTH, -1);
		calendarStart.set(calendarStart.HOUR, 0);
		calendarStart.set(calendarStart.MINUTE, 0);
		calendarStart.set(calendarStart.SECOND, 0);
		calendarStart.set(calendarStart.MILLISECOND, 0);
		
		calendarEnd.set(calendarEnd.DATE,1);
		calendarEnd.set(calendarEnd.HOUR, 0);
		calendarEnd.set(calendarEnd.MINUTE, 0);
		calendarEnd.set(calendarEnd.SECOND, 0);
		calendarEnd.set(calendarEnd.MILLISECOND, 0);

		DBObject match = new BasicDBObject("$match",
				new BasicDBObject("dateAchat",
				new BasicDBObject("$gte",calendarStart.getTime())
				.append("$lte", calendarEnd.getTime())));

		DBObject project = (DBObject) JSON.parse("{$project:{'quantite':1, "
				+ "'categorie':1,"
				+ "'_id':0}}");
		DBObject group = (DBObject) JSON.parse("{$group:{'_id':'$categorie',"
				+ "'somme':{'$sum':'$quantite'}}}");

		List<DBObject> operations = new ArrayList<DBObject>();
//		operations.add(unwind);
		operations.add(match);
		operations.add(project);
		operations.add(group);

		AggregationOutput output = dbCollection.aggregate(operations);
		Iterable<DBObject> cursor = output.results();

		for (DBObject moObj : cursor)
		{
			CategoryProduct cp = new CategoryProduct();
			cp.setCategory(moObj.get("_id").toString());
			int quantity = Integer.parseInt(moObj.get("somme").toString());
			cp.setQuantity(quantity);
			result.add(cp);
		}
		return result;
	}
	
	@Override
	public List<CategoryProduct> productsSellByCategoryAndTwoMonthAgo() {

		List<CategoryProduct> result = new ArrayList<>();

//		DBObject unwind = (DBObject) JSON.parse("{$unwind:'$produits'}");

		Calendar calendarStart = Calendar.getInstance();
		Calendar calendarEnd = Calendar.getInstance();
		calendarStart.set(calendarStart.DATE,1);
		calendarStart.add(calendarStart.MONTH, -2);
		calendarStart.set(calendarStart.HOUR, 0);
		calendarStart.set(calendarStart.MINUTE, 0);
		calendarStart.set(calendarStart.SECOND, 0);
		calendarStart.set(calendarStart.MILLISECOND, 0);
		
		calendarEnd.set(calendarEnd.DATE,1);
		calendarStart.add(calendarStart.MONTH, -1);
		calendarEnd.set(calendarEnd.HOUR, 0);
		calendarEnd.set(calendarEnd.MINUTE, 0);
		calendarEnd.set(calendarEnd.SECOND, 0);
		calendarEnd.set(calendarEnd.MILLISECOND, 0);

		DBObject match = new BasicDBObject("$match",
				new BasicDBObject("dateAchat",
				new BasicDBObject("$gte",calendarStart.getTime())
				.append("$lte", calendarEnd.getTime())));

		DBObject project = (DBObject) JSON.parse("{$project:{'quantite':1, "
				+ "'categorie':1,"
				+ "'_id':0}}");
		DBObject group = (DBObject) JSON.parse("{$group:{'_id':'$categorie',"
				+ "'somme':{'$sum':'$quantite'}}}");

		List<DBObject> operations = new ArrayList<DBObject>();
//		operations.add(unwind);
		operations.add(match);
		operations.add(project);
		operations.add(group);

		AggregationOutput output = dbCollection.aggregate(operations);
		Iterable<DBObject> cursor = output.results();

		for (DBObject moObj : cursor)
		{
			CategoryProduct cp = new CategoryProduct();
			cp.setCategory(moObj.get("_id").toString());
			int quantity = Integer.parseInt(moObj.get("somme").toString());
			cp.setQuantity(quantity);
			result.add(cp);
		}
		return result;
	}
	
	@Override
	public List<CategoryProduct> productsSellByCategoryAndThreeMonthAgo() {

		List<CategoryProduct> result = new ArrayList<>();

//		DBObject unwind = (DBObject) JSON.parse("{$unwind:'$produits'}");

		Calendar calendarStart = Calendar.getInstance();
		Calendar calendarEnd = Calendar.getInstance();
		calendarStart.set(calendarStart.DATE,1);
		calendarStart.add(calendarStart.MONTH, -3);
		calendarStart.set(calendarStart.HOUR, 0);
		calendarStart.set(calendarStart.MINUTE, 0);
		calendarStart.set(calendarStart.SECOND, 0);
		calendarStart.set(calendarStart.MILLISECOND, 0);
		
		calendarEnd.set(calendarEnd.DATE,1);
		calendarStart.add(calendarStart.MONTH, -2);
		calendarEnd.set(calendarEnd.HOUR, 0);
		calendarEnd.set(calendarEnd.MINUTE, 0);
		calendarEnd.set(calendarEnd.SECOND, 0);
		calendarEnd.set(calendarEnd.MILLISECOND, 0);

		DBObject match = new BasicDBObject("$match",
				new BasicDBObject("dateAchat",
				new BasicDBObject("$gte",calendarStart.getTime())
				.append("$lte", calendarEnd.getTime())));

		DBObject project = (DBObject) JSON.parse("{$project:{'quantite':1, "
				+ "'categorie':1,"
				+ "'_id':0}}");
		DBObject group = (DBObject) JSON.parse("{$group:{'_id':'$categorie',"
				+ "'somme':{'$sum':'$quantite'}}}");

		List<DBObject> operations = new ArrayList<DBObject>();
//		operations.add(unwind);
		operations.add(match);
		operations.add(project);
		operations.add(group);

		AggregationOutput output = dbCollection.aggregate(operations);
		Iterable<DBObject> cursor = output.results();

		for (DBObject moObj : cursor)
		{
			CategoryProduct cp = new CategoryProduct();
			cp.setCategory(moObj.get("_id").toString());
			int quantity = Integer.parseInt(moObj.get("somme").toString());
			cp.setQuantity(quantity);
			result.add(cp);
		}
		return result;
	}

	@Override
	public List<CategoryProduct> productsSellByCategoryAndDay() {

		List<CategoryProduct> result = new ArrayList<>();

//		DBObject unwind = (DBObject) JSON.parse("{$unwind:'$produits'}");

		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		DBObject match = new BasicDBObject("$match",
				new BasicDBObject("dateAchat",
				new BasicDBObject("$gte",calendar.getTime())));

		DBObject project = (DBObject) JSON.parse("{$project:{'quantite':1, "
				+ "'categorie':1,"
				+ "'_id':0}}");
		DBObject group = (DBObject) JSON.parse("{$group:{'_id':'$categorie',"
				+ "'somme':{'$sum':'$quantite'}}}");

		List<DBObject> operations = new ArrayList<DBObject>();
//		operations.add(unwind);
		operations.add(match);
		operations.add(project);
		operations.add(group);

		AggregationOutput output = dbCollection.aggregate(operations);
		Iterable<DBObject> cursor = output.results();

		for (DBObject moObj : cursor)
		{
			CategoryProduct cp = new CategoryProduct();
			cp.setCategory(moObj.get("_id").toString());
			int quantity = Integer.parseInt(moObj.get("somme").toString());
			cp.setQuantity(quantity);
			result.add(cp);
		}
		return result;
	}

	@Override
	public List<CategoryProduct> productsPublishedByCategoryAndDay() {
		List<CategoryProduct> result = new ArrayList<>();

//		DBObject unwind = (DBObject) JSON.parse("{$unwind:'$produits'}");

		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		DBObject match = new BasicDBObject("$match",
				new BasicDBObject("dateAchat",
				new BasicDBObject("$gte",calendar.getTime())));

		DBObject project = (DBObject) JSON.parse("{$project:{'quantite':1, "
				+ "'categorie':1,"
				+ "'_id':0}}");
		DBObject group = (DBObject) JSON.parse("{$group:{'_id':'$categorie',"
				+ "'somme':{'$sum':'$quantite'}}}");

		List<DBObject> operations = new ArrayList<DBObject>();
//		operations.add(unwind);
		operations.add(match);
		operations.add(project);
		operations.add(group);

		AggregationOutput output = dbCollection.aggregate(operations);
		Iterable<DBObject> cursor = output.results();

		for (DBObject moObj : cursor)
		{
			CategoryProduct cp = new CategoryProduct();
			cp.setCategory(moObj.get("_id").toString());
			int quantity = Integer.parseInt(moObj.get("somme").toString());
			cp.setQuantity(quantity);
			result.add(cp);
		}
		return result;
	}
	
	@Override
	public List<TypeProductProduct> productsSellByTypeProductAndMonth() {
		List<TypeProductProduct> result = new ArrayList<>();

//		DBObject unwind = (DBObject) JSON.parse("{$unwind:'$produits'}");

		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.DATE,1);
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		DBObject match = new BasicDBObject("$match",
				new BasicDBObject("dateAchat",
				new BasicDBObject("$gte",calendar.getTime())));

		DBObject project = (DBObject) JSON.parse("{$project:{'quantite':1, "
				+ "'type':1,"
				+ "'_id':0}}");
		DBObject group = (DBObject) JSON.parse("{$group:{'_id':'$type',"
				+ "'somme':{'$sum':'$quantite'}}}");

		List<DBObject> operations = new ArrayList<DBObject>();
//		operations.add(unwind);
		operations.add(match);
		operations.add(project);
		operations.add(group);

		AggregationOutput output = dbCollection.aggregate(operations);
		Iterable<DBObject> cursor = output.results();

		for (DBObject moObj : cursor)
		{
			TypeProductProduct cp = new TypeProductProduct();
			cp.setTypeProduct(moObj.get("_id").toString());
			int quantity = Integer.parseInt(moObj.get("somme").toString());
			cp.setQuantity(quantity);
			result.add(cp);
		}
		return result;
	}

	@Override
	public void createOrder(Cart cart) {
		//cart g�n�rer al�atoirement : date commande (depuis le 01/01/2015 jusqu'� today
		BasicDBObject panier = new BasicDBObject("_id", cart.getIdCart())
		.append("dateAchat", cart.getDateCommande());

		List<BasicDBObject> listeProduits = new ArrayList<>();
		//g�n�rer al�atoirement :
		//* nombre de cartProduct
		//* quantit� dans chaque cartproduct
		//* produit du cartproduct
		List<CartProduct> listeCartProduct = cart.getCartProducts();

		for (CartProduct cartProduct : listeCartProduct)
		{
			listeProduits.add(createProduct(cartProduct));
		}

		panier.put("produits", listeProduits);
		dbCollection.insert(panier);
	}

	@Override
	public BasicDBObject createProduct(CartProduct cartProduct) {
		Product product = cartProduct.getProduct();
		BasicDBObject doc = new BasicDBObject("prix", product.getPrice())
		.append("type", product.getTypeProduct().getTypeProduct())
		.append("quantite", cartProduct.getQuantity())
		.append("nom", product.getProductRef().getProductName())
		.append("categorie", product.getProductRef().getCategory().getCategoryName())
		.append("dateAchat", cartProduct.getCart().getDateCommande());
		dbCollection.insert(doc);
		return doc;
	}

	public DBCollection getDbCollection() {
		return dbCollection;
	}

	public void setDbCollection(DBCollection dbCollection) {
		this.dbCollection = dbCollection;
	}
}

