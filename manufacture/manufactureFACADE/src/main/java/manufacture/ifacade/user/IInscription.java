package manufacture.ifacade.user;

import manufacture.entity.user.User;

public interface IInscription {

	//Vérification des identifiants
	boolean loginAlreadyExisting(String login);
	
	boolean emailAlreadyExisting(String email);
	
	//Création
	User createAccount(User user);

}
