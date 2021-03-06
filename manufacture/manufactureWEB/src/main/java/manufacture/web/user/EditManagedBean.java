package manufacture.web.user;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import manufacture.entity.user.Address;
import manufacture.entity.user.City;
import manufacture.entity.user.User;
import manufacture.ifacade.user.IProfil;

@ManagedBean(name="editBean")
@ViewScoped
public class EditManagedBean {

    private Logger log = Logger.getLogger(EditManagedBean.class);
    
	private boolean editModePersonnel;
	private boolean editModeAdresse;

	@ManagedProperty(value="#{profil}")
	private IProfil proxyProfil;
	
	@ManagedProperty(value="#{userBean}")
	private UserBean userBean;

	@ManagedProperty(value="#{profilBean}")
	private ProfilBean profilBean;
	
	private Address nouvelleAdresse = new Address();
	private Address adresseAModifier = new Address();
	private City ville;
	private Integer idCityNouvelleAdresse;
	private Integer idCityAdresseAModifier;
	
	@PostConstruct
	public void init()
	{
	    nouvelleAdresse.setUser(userBean.getUser());
	    ville = new City();
	    ville.setIdCity(1);
	    
	    idCityAdresseAModifier = 1;
	    idCityNouvelleAdresse = 1;
	    
        nouvelleAdresse.setCity(ville);
        editModeAdresse = false;
        editModePersonnel = false;
	}
	
	//Methodes
	
	public void saveNewAddress()
	{
	    ville.setIdCity(idCityNouvelleAdresse);
	    nouvelleAdresse.setCity(ville);
	    log.info(ville.getIdCity());
	    
	    nouvelleAdresse.setUser(userBean.getUser());
		proxyProfil.saveAddress(nouvelleAdresse);

		if (nouvelleAdresse.getIsBillingaddress())
		{
			profilBean.getAdressesFacturation().add(nouvelleAdresse);
		}
		
		if (nouvelleAdresse.getIsDeliveryaddress())
		{
			profilBean.getAdressesLivraison().add(nouvelleAdresse);
		}
			
		nouvelleAdresse = new Address();
		profilBean.initialiserAdresses();
	}
	
	public void editionModePersonnel() {
		editModePersonnel = true;
	}
	
	public void cancelModePersonnel() {
		editModePersonnel = false;
	}
	
	public void editionModeAdresse(Address address) {
		log.info(address.getIdAddress());
	    editModeAdresse = true;
		adresseAModifier = address;
	}
	
	public void cancelModeAdresse() {
		editModeAdresse = false;
	}

	public void saveUser() {
	   User newuser = proxyProfil.editUser(userBean.getUser());
	   userBean.setUser(newuser);
	   editModePersonnel = false;
	}
	
	public void saveAdresse() {
	    ville.setIdCity(idCityAdresseAModifier);
	    adresseAModifier.setCity(ville);
		proxyProfil.editAddress(adresseAModifier);
		editModeAdresse = false;
	}

	//Getters et Setters
	
	public UserBean getUserBean() {
		return userBean;
	}

	public void setUserBean(UserBean userBean) {
		this.userBean = userBean;
	}

	public boolean isEditModePersonnel() {
		return editModePersonnel;
	}

	public void setEditModePersonnel(boolean editModePersonnel) {
		this.editModePersonnel = editModePersonnel;
	}

	public boolean isEditModeAdresse() {
		return editModeAdresse;
	}

	public void setEditModeAdresse(boolean editModeAdresse) {
		this.editModeAdresse = editModeAdresse;
	}

	public Address getNouvelleAdresse() {
		return nouvelleAdresse;
	}

	public void setNouvelleAdresse(Address nouvelleAdresse) {
		this.nouvelleAdresse = nouvelleAdresse;
	}

	public City getVille() {
		return ville;
	}

	public void setVille(City ville) {
		this.ville = ville;
	}

	public IProfil getProxyProfil() {
		return proxyProfil;
	}

	public void setProxyProfil(IProfil proxyProfil) {
		this.proxyProfil = proxyProfil;
	}

	public ProfilBean getProfilBean() {
		return profilBean;
	}

	public void setProfilBean(ProfilBean profilBean) {
		this.profilBean = profilBean;
	}

    public Address getAdresseAModifier() {
        return adresseAModifier;
    }

    public void setAdresseAModifier(Address paramAdresseAModifier) {
        adresseAModifier = paramAdresseAModifier;
    }

    public Logger getLog() {
        return log;
    }

    public void setLog(Logger paramLog) {
        log = paramLog;
    }

    public Integer getIdCityNouvelleAdresse() {
        return idCityNouvelleAdresse;
    }

    public void setIdCityNouvelleAdresse(Integer paramIdCityNouvelleAdresse) {
        idCityNouvelleAdresse = paramIdCityNouvelleAdresse;
    }

    public Integer getIdCityAdresseAModifier() {
        return idCityAdresseAModifier;
    }

    public void setIdCityAdresseAModifier(Integer paramIdCityAdresseAModifier) {
        idCityAdresseAModifier = paramIdCityAdresseAModifier;
    }
}
