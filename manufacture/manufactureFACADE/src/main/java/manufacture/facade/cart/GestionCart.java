package manufacture.facade.cart;

import java.util.List;

import manufacture.entity.cart.Cart;
import manufacture.entity.cart.Delivery;
import manufacture.entity.cart.RelayPoint;
import manufacture.entity.product.Product;
import manufacture.ibusiness.cart.IBusinessCart;
import manufacture.ifacade.cart.IGestionCart;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GestionCart implements IGestionCart {
   
    private static Logger log = Logger.getLogger(GestionCart.class);
    
    private IBusinessCart businessCart;

    @Override
    public List<Product> getAllProductByCart(int paramIdCart) {
        return businessCart.getAllProductByCart(paramIdCart);
    }

    @Override
    public double getTotalPrice(int paramIdCart) {
        return businessCart.getTotalPrice(paramIdCart);
    }

    @Override
    public void validateCommande(Cart paramCart) {
        
        if (paramCart.getUser().getUserRole().getRole().equals("Particulier"))
        {
            businessCart.orderSpecificCommande(paramCart);
        }
        else if (paramCart.getUser().getUserRole().getRole().equals("Professionnel"))
        {
            businessCart.orderProfessionalCommande(paramCart);
        }
    }

    @Override
    public void validatePayment(Cart paramCart) {
        businessCart.validatePayment(paramCart);
    }
    
	@Override
	public List<Delivery> getAllDelivery() {
		return businessCart.getAllDeliveryType();
	}
	
	@Override
	public List<RelayPoint> getAllRelayPoints() {
		return businessCart.getAllRelayPoints();
	}

    public static Logger getLog() {
        return log;
    }

    public static void setLog(Logger paramLog) {
        log = paramLog;
    }

    public IBusinessCart getBusinessCart() {
        return businessCart;
    }

    @Autowired
    public void setBusinessCart(IBusinessCart paramBusinessCart) {
        businessCart = paramBusinessCart;
    }
}
