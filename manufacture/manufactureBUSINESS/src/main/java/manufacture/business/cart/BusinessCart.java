package manufacture.business.cart;

import java.util.Date;
import java.util.List;

import manufacture.entity.cart.Cart;
import manufacture.entity.cart.CartProduct;
import manufacture.entity.cart.Delivery;
import manufacture.entity.cart.PaymentType;
import manufacture.entity.product.Product;
import manufacture.ibusiness.cart.IBusinessCart;
import manufacture.idao.cart.IDaoCart;
import manufacture.idao.cart.IDaoPaymentAndDelivery;
import manufacture.idao.cart.IDaoProductCart;
import manufacture.idao.product.IDaoProduct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BusinessCart implements IBusinessCart {
			
    private Logger log = Logger.getLogger(BusinessCart.class);
    
    private IDaoCart proxyCart;
    private IDaoProductCart proxyProductCart;
    private IDaoPaymentAndDelivery proxyPaymentAndDelivery;
    private IDaoProduct proxyProduct;

	@Override
	public void updateOptionsProduct(int idCartProduct, Product newProduct) {
		proxyProductCart.updateOptionsProduct(idCartProduct, newProduct);
	}

	@Override
	public void updateQuantityProduct(int idCartProduct, int newQuantity) {
		proxyProductCart.updateQuantityProduct(idCartProduct, newQuantity);
	}

	@Override
	public List<Product> getAllProductByCart(int idCart) {
		return proxyProductCart.getAllProductByCart(idCart);
	}

	@Override
	public double getTotalPrice(int idCart) {
		List<CartProduct> listCartProduct = proxyCart.getAllCartProductByCart(idCart);
		double total = 0;
		for (CartProduct cartProduct : listCartProduct) {
			total += proxyProductCart.getSubTotalPrice(cartProduct.getIdCartProduct());
		}
		return total;
	}
	

	@Override
	public double getSubTotalPrice(int idCartProduct) {
		return proxyProductCart.getSubTotalPrice(idCartProduct);
	}

	/**
	 * M�thode permettant de valider la panier du professionnel sans payer la commande.
	 * @param idCart l'identifiant du panier.
	 */
	@Override
	public void orderProfessionalCommande(Cart commande) {

	    commande.setDateCommande(new Date());
        commande.setIsValidated(true);
        
        if(commande.getIdCart( ) == null)
        {
            proxyCart.addCart(commande);
            for(CartProduct cp : commande.getCartProducts())
            {
                cp.setCart(commande);
                proxyProductCart.addProductToCart(cp);
                proxyProduct.updateProductStock(cp.getProduct().getIdProduct() , cp.getQuantity());
            }
        }
        
        if (commande.getPaymentType().getName().equals("Paiement imm�diat"))
        {
            proxyCart.validatePayment(commande);
        }
	}

	/**
	 * M�thode permettant au particulier de valider son panier et de payer la commande.
	 * @param idCart l'identifiant du panier.
	 */
	@Override
	public void orderSpecificCommande(Cart commande) {

		commande.setDateCommande(new Date());
		commande.setIsValidated(true);
		
		if(commande.getIdCart( ) == null)
		{
		    proxyCart.addCart(commande);
		    for(CartProduct cp : commande.getCartProducts())
		    {
		        cp.setCart(commande);
		        proxyProductCart.addProductToCart(cp);
		        proxyProduct.updateProductStock(cp.getProduct().getIdProduct() , cp.getQuantity());
		    }
		}

		proxyCart.validatePayment(commande);
	}

	/**
	 * M�thode pour payer une commande dans le cas des clients professionnels.
	 * Et si l'option Paiement diff�r� a �t� choisie.
	 */
	@Override
	public Cart validatePayment(Cart cart) {
		return proxyCart.validatePayment(cart);
	}

//	@Override
//	public void createNewCart(int idUser) {
//		proxyCart.createNewCart(idUser);
//	}

	@Override
	public void sendRecall(int idUser) {
		proxyCart.sendRecall(idUser);
	}

	@Override
	public List<PaymentType> getAllPaymentType() {
		return proxyPaymentAndDelivery.getAllPaymentType();
	}

	@Override
	public List<Delivery> getAllDeliveryType() {
		return proxyPaymentAndDelivery.getAllDeliveryType();
	}

	@Override
	public void checkProductStock(int idProduct) {
		proxyProduct.checkProductStock(idProduct);
	}

	public IDaoCart getProxyCart() {
		return proxyCart;
	}

	@Autowired
	public void setProxyCart(IDaoCart proxyCart) {
		this.proxyCart = proxyCart;
	}

	public IDaoProductCart getProxyProductCart() {
		return proxyProductCart;
	}
	
	@Autowired
	public void setProxyProductCart(IDaoProductCart proxyProductCart) {
		this.proxyProductCart = proxyProductCart;
	}

	public IDaoPaymentAndDelivery getProxyPaymentAndDelivery() {
		return proxyPaymentAndDelivery;
	}

	@Autowired
	public void setProxyPaymentAndDelivery(
			IDaoPaymentAndDelivery proxyPaymentAndDelivery) {
		this.proxyPaymentAndDelivery = proxyPaymentAndDelivery;
	}

	public IDaoProduct getProxyProduct() {
		return proxyProduct;
	}

	@Autowired
	public void setProxyProduct(IDaoProduct proxyProduct) {
		this.proxyProduct = proxyProduct;
	}

}
