package manufacture.ws.order;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.jws.WebService;
import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import manufacture.entity.cart.Cart;
import manufacture.entity.cart.CartProduct;
import manufacture.entity.cart.Delivery;
import manufacture.entity.cart.PaymentType;
import manufacture.entity.product.Product;
import manufacture.entity.user.User;
import manufacture.ifacade.cart.IGestionCart;
import manufacture.ifacade.cart.IPaiement;
import manufacture.ifacade.catalog.ICatalog;
import manufacture.ifacade.user.IConnection;
import manufacture.ws.devis.DevisResponseDTO;

@Transactional
@Component
@WebService(endpointInterface="manufacture.ws.order.IOrder")
public class OrderImpl implements IOrder {
	
	private static Logger log = Logger.getLogger(OrderImpl.class);
	
	private Cart cartForWS ;
	private IGestionCart proxyCart;
	private ICatalog proxyCatalog;
	private IConnection proxyConnection;
	private IPaiement proxyPaiement;

	private double getTotal() {
		return 0 ;	
	}
	
	private List<CartProduct> convertDevisResponseDTOToCartProduct(List<DevisResponseDTO> devisResponseProduct){
		
		List<CartProduct> listeCartProduct = new ArrayList<CartProduct>();
		
		for (DevisResponseDTO devisResponse : devisResponseProduct) {
			List<Product> listeProduct = proxyCatalog.getAllProductConstructorByProductRef(devisResponse.getIdProductRef());
		
		
				
		//Cas 1 : la reference produit demandee n'est pas enregistree dans la base de donnees
		if(!listeProduct.isEmpty())
		{			
			for (Product product : listeProduct) 
			{
				boolean isTheSame = true ;
				
				if (product.getColor().getIdColor() != devisResponse.getIdColor()) 
				{
					isTheSame = false ;
				}
				
				if (product.getMaterial().getIdMaterial() != devisResponse.getIdMaterial())
				{
					isTheSame = false ;
				}
				
				if (product.getConstructor().getIdConstructor() != devisResponse.getIdConstructor())
				{
					isTheSame = false ;
				}
				
				if (isTheSame)
				{
					CartProduct cartProduct = new CartProduct();
					cartProduct.setProduct(product);
					cartProduct.setQuantity(devisResponse.getQuantity());
					
					listeCartProduct.add(cartProduct);
				}
			}
		}
		}
		
		return listeCartProduct ;
	}
	
	private Cart convertOrderRequestToCart(OrderRequestDTO orderRequest) {
		
		Cart cart = new Cart();
		User user = proxyConnection.getUserByEmail(orderRequest.getEmailUser());
		List<CartProduct> listeCartProduct = new ArrayList<CartProduct>();
		//		User user = new User();
		Delivery delivery = new Delivery();
		PaymentType paymentType = new PaymentType();
		
		listeCartProduct = convertDevisResponseDTOToCartProduct(orderRequest.getListProductToOrder());
//		if (listeCartProduct.isEmpty()) {
//			
//		} else {
//			
//		}
		cart.setCartProducts(listeCartProduct);
		cart.setUser(user);
		cart.setDateCommande(new Date());
		cart.setIsValidated(true);
		cart.setAddressBilling(user.getAddresses().get(0));
		cart.setAddressDelivery(user.getAddresses().get(0));
		
		delivery.setIdDelivery(1);
		cart.setDelivery(delivery);
		
		// apres on ajoute un test pour savoir si le paiement est diff�r� ou pas
		
		paymentType.setIdPayment(2);
		cart.setPaymentType(paymentType);
		
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, +15);	
		cart.setDatePayment(calendar.getTime()); // a calculer une date differer
		
		return cart ;
	}
	
	@Override
	public OrderResponseDTO toOrder(OrderRequestDTO orderRequest) {
		
		OrderResponseDTO orderResponse = new OrderResponseDTO();
		Cart cart = convertOrderRequestToCart(orderRequest) ;
		proxyPaiement.processPaiement(cart);
		
		orderResponse.setPaymentType(orderRequest.getPaymentType());
		orderResponse.setDatePaiement(cart.getDatePayment());
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, +15);
		orderResponse.setDatePaiement(calendar.getTime());
		for (DevisResponseDTO produit : orderRequest.getListProductToOrder()) {
			log.info("********* idRef produit command� : "+produit.getIdProductRef() + "*********");
		}
		
		orderResponse.setListProductToOrder(orderRequest.getListProductToOrder());
		
		return orderResponse;
	}
	
	public Cart getCartForWS() {
		return cartForWS;
	}
	public void setCartForWS(Cart cartForWS) {
		this.cartForWS = cartForWS;
	}
	public IGestionCart getProxyCart() {
		return proxyCart;
	}
	
	@Autowired
	public void setProxyCart(IGestionCart proxyCart) {
		this.proxyCart = proxyCart;
	}

	public ICatalog getProxyCatalog() {
		return proxyCatalog;
	}

	@Autowired
	public void setProxyCatalog(ICatalog proxyCatalog) {
		this.proxyCatalog = proxyCatalog;
	}

	public IConnection getProxyConnection() {
		return proxyConnection;
	}

	@Autowired
	public void setProxyConnection(IConnection proxyConnection) {
		this.proxyConnection = proxyConnection;
	}

	public IPaiement getProxyPaiement() {
		return proxyPaiement;
	}

	@Autowired
	public void setProxyPaiement(IPaiement proxyPaiement) {
		this.proxyPaiement = proxyPaiement;
	}

}
