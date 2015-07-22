package manufacture.web.user;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import manufacture.entity.user.User;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author ocalik
 *	Bean g�rant la connection et le maintien de la connexion de l'utilisateur sur le site.
 */
@Component
@Scope(value="session")
public class UserBean {
	private static Logger LOGGER = Logger.getLogger(UserBean.class);
	private User user;
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}