package manufacture.business.user;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import manufacture.entity.cart.Cart;
import manufacture.entity.cart.CartProduct;
import manufacture.entity.product.Product;
import manufacture.entity.user.Address;
import manufacture.entity.user.Civility;
import manufacture.entity.user.User;
import manufacture.ibusiness.user.IBusinessProfil;
import manufacture.idao.cart.IDaoCart;
import manufacture.idao.cart.IDaoProductCart;
import manufacture.idao.product.IDaoProduct;
import manufacture.idao.user.IDaoAdress;
import manufacture.idao.user.IDaoUser;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BusinessProfil implements IBusinessProfil {

	private static Logger log = Logger.getLogger(BusinessProfil.class);
	
	private IDaoUser proxyUser;
	
	private IDaoAdress proxyAddress;
	
	private IDaoProduct proxyProduct;
	
	private IDaoCart proxyCart;
	
	private IDaoProductCart proxyCartProduct;
	
	public static String sha256(String input) {
		MessageDigest mDigest;
		try {
			mDigest = MessageDigest.getInstance("SHA-256");
			byte[] result = mDigest.digest(input.getBytes());
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < result.length; i++) {
				sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
			}
			return sb.toString();	
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return input;
		}
	}
	
	@Override
	public User editUser(User user) {
		
		String newPassword = user.getPassword(); //cod� en SHA-256 si non chang�, sinon pas cod�
		String oldPassword = proxyUser.getPasswordByLogin(user.getLogin()); //cod� en SHA-256
		
		if (!newPassword.equals(oldPassword))
		{
			user.setPassword(sha256(newPassword));
		}
		
		return proxyUser.editUser(user);
	}
	
	@Override
	public List<Address> getAllAdressByUser(User user) {
		return proxyAddress.getAllAdressByUser(user);
	}
	
	@Override
	public void editAddress(Address addresse) {
		proxyAddress.updateAddress(addresse);
	}
	
	@Override
	public void saveAddress(Address nouvelleAdresse) {
		proxyAddress.createAddress(nouvelleAdresse);
	}
	
	@Override
	public List<Product> getProductSendByUser(User user) {
		return proxyProduct.getProductSendByUser(user);
	}

	@Override
	public List<Cart> getCartByUser(User user) {
		return proxyCart.getCartByUser(user);
	}
	
	@Override
	public List<CartProduct> getCartSendByUser(User user) {
		return proxyCartProduct.getCartSendByUser(user);
	}
	
	@Override
	public List<Product> getProductNotSendByUser(User user) {
		return proxyProduct.getProductNotSendByUser(user);
	}
	
	@Override
	public List<Address> getDeliveryAddressByUser(User user) {
		return proxyAddress.getDeliveryAddressByUser(user);
	}

	@Override
	public List<Address> getBillingAddressByUser(User user) {
		return proxyAddress.getBillingAddressByUser(user);
	}
	
    @Override
    public List<CartProduct> getCartProductByCart(Cart cart) {
        return proxyCartProduct.getCartProductByCart(cart);
    }
    
	@Override
	public List<Civility> getAllCivility() {
		return proxyUser.getAllCivility();
	}
	
	@Override
	public void removeAddress(Address address) {
		proxyAddress.deleteAddress(address);
	}
	
	//Proxy
	
	public IDaoUser getProxyUser() {
		return proxyUser;
	}
	
	@Autowired
	public void setProxyUser(IDaoUser proxyUser) {
		this.proxyUser = proxyUser;
	}
	
	public Logger getLog() {
		return log;
	}
	
	public void setLog(Logger log) {
		BusinessProfil.log = log;
	}
	
	public IDaoAdress getProxyAddress() {
		return proxyAddress;
	}
	
	@Autowired
	public void setProxyAddress(IDaoAdress proxyAddress) {
		this.proxyAddress = proxyAddress;
	}

	public IDaoProduct getProxyProduct() {
		return proxyProduct;
	}

	@Autowired
	public void setProxyProduct(IDaoProduct proxyProduct) {
		this.proxyProduct = proxyProduct;
	}

	public IDaoCart getProxyCart() {
		return proxyCart;
	}

	@Autowired
	public void setProxyCart(IDaoCart proxyCart) {
		this.proxyCart = proxyCart;
	}

	public IDaoProductCart getProxyCartProduct() {
		return proxyCartProduct;
	}

	@Autowired
	public void setProxyCartProduct(IDaoProductCart proxyCartProduct) {
		this.proxyCartProduct = proxyCartProduct;
	}
}
