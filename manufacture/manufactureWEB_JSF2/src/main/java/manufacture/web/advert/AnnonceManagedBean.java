package manufacture.web.advert;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import manufacture.entity.product.Color;
import manufacture.entity.product.Material;
import manufacture.entity.product.Product;
import manufacture.entity.product.ProductRef;
import manufacture.entity.product.TypeProduct;
import manufacture.ifacade.advert.IAdvert;
import manufacture.ifacade.catalog.ICatalog;
import manufacture.web.catalogBean.CatalogManagedBean;
import manufacture.web.user.UserBean;

import org.apache.log4j.Logger;

@ManagedBean(name = "mbAnnonce")
@SessionScoped
public class AnnonceManagedBean {

    private static Logger log = Logger.getLogger(AnnonceManagedBean.class);

    /**
     * Constantes
     */

    private static final Integer PRODUCT_ARTISAN_TYPE_ID = 2;
    private static final Integer PRODUCT_OCCASION_TYPE_ID = 3;
    
    private static final Integer USER_ARTISAN_ROLE_ID = 3;
    private static final Integer USER_PARTICULIER_ROLE_ID = 1;

    @ManagedProperty(value="#{userBean}")
    private UserBean userBean;

    @ManagedProperty(value="#{catalog}")
    private ICatalog proxyCatalog;
    
    @ManagedProperty(value="#{advert}")
    private IAdvert proxyAdvert;
    
    @ManagedProperty(value="#{mbCatalog}")
    private CatalogManagedBean catalogBean;

    private List<ProductRef> listeProduitRef;
    private int idCategorieSelected;

    private Product newProduct;

    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private Date date = new Date();

    /**
     * Messages d'erreurs :
     */
    private String messageErreur = "";
    
    private boolean donneesInitialisees = false;

    @PostConstruct
    public void init()
    {
        if(userBean.isLogged()){
            initialiseDonnees();
            donneesInitialisees = true;
        }
    }

    // Methodes
    public void listeProductRef()
    {
        listeProduitRef = proxyCatalog.getProductRefByCategory(idCategorieSelected);
    }
    
    public void initialiseDonnees()
    {
        idCategorieSelected = 1;
        listeProduitRef = proxyCatalog.getProductRefByCategory(idCategorieSelected);
        
        newProduct = new Product();
        
        newProduct.setUser(userBean.getUser());
        newProduct.setTypeProduct(new TypeProduct());
        if (userBean.getUser().getUserRole().getIdUserRole() == USER_PARTICULIER_ROLE_ID)
        {
            newProduct.getTypeProduct().setIdTypeProduct(PRODUCT_OCCASION_TYPE_ID);
        }
        else if (userBean.getUser().getUserRole().getIdUserRole() == USER_ARTISAN_ROLE_ID)
        {
            newProduct.getTypeProduct().setIdTypeProduct(PRODUCT_ARTISAN_TYPE_ID);
        }
        
        newProduct.setProductRef(new ProductRef());
        
        Color color = new Color();
        color.setIdColor(1);        
        newProduct.setColor(color);
        
        Material material = new Material();
        material.setIdMaterial(1);
        newProduct.setMaterial(material);
        
        newProduct.setSellerComment("");
                
        newProduct.setDatePublication(date);
        newProduct.setOnLine(true);
    }

    public String validationFormulaire()
    {
        if (newProduct.getSellerComment().equals(""))
        {
            messageErreur = "Veuillez rentrer un commentaire avant de publier l'annonce.";
        }
        else
        {
            messageErreur = "";
            return createAdvert();
        }
        return null;
    }

    public String createAdvert()
    {
    proxyAdvert.createAdvert(newProduct);
        catalogBean.init();
        
        return "annonceEnregistree.xhtml?faces-redirect=true";
    }

    // Getters et Setters

    public UserBean getUserBean() {
        return userBean;
    }

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }

    public ICatalog getProxyCatalog() {
        return proxyCatalog;
    }

    public void setProxyCatalog(ICatalog proxyCatalog) {
        this.proxyCatalog = proxyCatalog;
    }

    public List<ProductRef> getListeProduitRef() {
        return listeProduitRef;
    }

    public void setListeProduitRef(List<ProductRef> listeProduitRef) {
        this.listeProduitRef = listeProduitRef;
    }

    public Product getNewProduct() {
        return newProduct;
    }

    public void setNewProduct(Product newProduct) {
        this.newProduct = newProduct;
    }

    public SimpleDateFormat getSdf() {
        return sdf;
    }

    public void setSdf(SimpleDateFormat sdf) {
        this.sdf = sdf;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMessageErreur() {
        return messageErreur;
    }

    public void setMessageErreur(String messageErreur) {
        this.messageErreur = messageErreur;
    }

    public IAdvert getProxyAdvert() {
        return proxyAdvert;
    }

    public void setProxyAdvert(IAdvert proxyAdvert) {
        this.proxyAdvert = proxyAdvert;
    }

    public CatalogManagedBean getCatalogBean() {
        return catalogBean;
    }

    public void setCatalogBean(CatalogManagedBean catalogBean) {
        this.catalogBean = catalogBean;
    }

    public static Logger getLog() {
        return log;
    }

    public static void setLog(Logger paramLog) {
        log = paramLog;
    }

    public static Integer getProductArtisanTypeId() {
        return PRODUCT_ARTISAN_TYPE_ID;
    }

    public static Integer getProductOccasionTypeId() {
        return PRODUCT_OCCASION_TYPE_ID;
    }

    public static Integer getUserArtisanRoleId() {
        return USER_ARTISAN_ROLE_ID;
    }

    public static Integer getUserParticulierRoleId() {
        return USER_PARTICULIER_ROLE_ID;
    }

    public boolean isDonneesInitialisees() {
        return donneesInitialisees;
    }

    public void setDonneesInitialisees(boolean paramDonneesInitialisees) {
        donneesInitialisees = paramDonneesInitialisees;
    }

    public int getIdCategorieSelected() {
        return idCategorieSelected;
    }

    public void setIdCategorieSelected(int paramIdCategorieSelected) {
        idCategorieSelected = paramIdCategorieSelected;
    }
}