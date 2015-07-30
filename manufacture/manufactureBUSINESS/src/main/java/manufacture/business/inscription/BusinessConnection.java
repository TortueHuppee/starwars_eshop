package manufacture.business.inscription;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import manufacture.entity.user.User;
import manufacture.ibusiness.user.IBusinessConnection;
import manufacture.idao.user.IDaoUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BusinessConnection implements IBusinessConnection {

	IDaoUser proxyUser;
	
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
			mDigest = MessageDigest.getInstance("SHA256");
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
	
	public IDaoUser getProxyUser() {
		return proxyUser;
	}
	
	@Autowired
	public void setProxyUser(IDaoUser proxyUser) {
		this.proxyUser = proxyUser;
	}
}
