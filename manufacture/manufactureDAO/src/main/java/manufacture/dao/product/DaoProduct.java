package manufacture.dao.product;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import manufacture.entity.cart.Cart;
import manufacture.entity.product.Category;
import manufacture.entity.product.Color;
import manufacture.entity.product.Product;
import manufacture.idao.product.IDaoColor;
import manufacture.idao.product.IDaoProduct;

@Service
@Transactional
public class DaoProduct implements IDaoProduct {

	private Logger log = Logger.getLogger(DaoProduct.class);
	private SessionFactory sf;
	

	@Override
	public Product getProductByIdProduct(int idProduct) {
		Session session = sf.getCurrentSession();
		String requete = "SELECT p FROM Product p WHERE p.idProduct = :idProduct";
		Query hql = session.createQuery(requete);
		hql.setParameter("idProduct", idProduct);
		return (Product) hql.list().get(0);
	}
	
	@Override
	public List<Product> getAllProductByProductRef(int idProducRef) {
		Session session = sf.getCurrentSession();
		String requete = "SELECT p FROM Product p WHERE p.productRef.idProductRef = :paramId";
		Query hql = session.createQuery(requete);
		hql.setParameter("paramId", idProducRef);
		List<Product> resultat = hql.list();
		return resultat;
	}

	@Override
	public void updateProductStock(int idProduct, int quantitySend) {
		//IMEN
		Session session = sf.getCurrentSession();
		Product product = getProductByIdProduct(idProduct);
		product.setStock(product.getStock() - quantitySend);
		session.save(product);
	}

	@Override
	public void checkProductStock(int idProduct) {
		// TODO Auto-generated method stub
		//IMEN
		Session session = sf.getCurrentSession();
		Product product = getProductByIdProduct(idProduct);
		product.getStock();
	}

	@Override
	public void addProduct(Product product) {
		Session session = sf.getCurrentSession();
		session.save(product);
	}

	@Override
	public void deleteProduct(Product product) {
		Session session = sf.getCurrentSession();
		session.delete(product);
	}

	@Override
	public void updateProduct(Product product) {
		Session session = sf.getCurrentSession();
		session.update(product);
	}
	
	@Override
	public List<Product> getAllProduct() {
		Session session = sf.getCurrentSession();
		String requete = "FROM Product a";
		Query hql = session.createQuery(requete);
		List<Product> resultat = hql.list();
		return resultat;
	}

	//Getters et Setters
	public SessionFactory getSf() {
		return sf;
	}

	@Autowired
	public void setSf(SessionFactory sf) {
		this.sf = sf;
	}

}
