package manufacture.business.inscription;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import manufacture.entity.user.Address;
import manufacture.entity.user.User;
import manufacture.ibusiness.user.IBusinessConnection;
import manufacture.idao.user.IDaoAdress;
import manufacture.idao.user.IDaoUser;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BusinessConnection implements IBusinessConnection {

	private static Logger log = Logger.getLogger(BusinessConnection.class);
	
	IDaoUser proxyUser;
	
	IDaoAdress proxyAddress;
	
	@Override
	public User getSignInUser(String login, String password) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void getSignOutUser(User user) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean resertPassword(String login) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean getLogin(String email) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public User logUser(User user) {
		user.setPassword(sha256(user.getPassword()));
		return proxyUser.getUserLogin(user);
	}
	
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
		this.log = log;
	}
	
	public IDaoAdress getProxyAddress() {
		return proxyAddress;
	}
	
	@Autowired
	public void setProxyAddress(IDaoAdress proxyAddress) {
		this.proxyAddress = proxyAddress;
	}
}
