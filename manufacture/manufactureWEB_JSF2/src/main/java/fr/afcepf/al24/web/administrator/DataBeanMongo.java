package fr.afcepf.al24.web.administrator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

import manufacture.entity.mongodb.CategoryProduct;
import manufacture.entity.mongodb.TypeProductProduct;
import manufacture.ifacade.mongodb.IMongoDB;

import org.apache.log4j.Logger;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.PieChartModel;

@ManagedBean(name="mbMongo")
@SessionScoped
public class DataBeanMongo {

private static Logger log = Logger.getLogger(DataBeanMongo.class);
	
	private List<CategoryProduct> listeProduitsVendusSurLeJour = new ArrayList<>();

	private SelectItemGroup articlesVendus;

	private final int MAX_WIDTH_CIRCLE = 100;
	private final int MIN_WIDTH_CIRCLE = 20;
	private int sommeArticlesVendus;

	@PostConstruct
	public void init()
	{
		log.info("Init()");
		Chrono chrono = new Chrono();
		chrono.start();
		sommeArticlesVendus = 0;

//		listeProduitsVendusSurLeJour = proxyMongo.productsSellByCategoryAndDay();
		listeProduitsVendusSurLeJour = new ArrayList<>();
		articlesVendus = new SelectItemGroup();
		SelectItem[] tableauArticleMisEnLigne = new SelectItem[4];
		for (int i = 0; i < 4; i++)
		{
			Random rand = new Random();
			int nombreAleatoire = rand.nextInt(200 - 10 + 1) + 10;
			tableauArticleMisEnLigne[i] = new SelectItem(nombreAleatoire, "test", "descrition" + i);
			sommeArticlesVendus += nombreAleatoire;
			log.info("Liste articlesVendus : " + nombreAleatoire);
		}
		
		for (int i = 0; i < listeProduitsVendusSurLeJour.size(); i++)
		{
			CategoryProduct cp = listeProduitsVendusSurLeJour.get(i);
			tableauArticleMisEnLigne[i] = new SelectItem(cp.getQuantity(), cp.getCategory());
			sommeArticlesVendus += cp.getQuantity();
			
		}
		articlesVendus.setSelectItems(tableauArticleMisEnLigne);
		chrono.stop();
		log.info(chrono.tempsEcoule() + "ms");
	}

	//M�thodes
	//small (20px), smaller (35px), medium (50px), mediumLarge (75px), large (100px)
	public void updateData()
	{
		init();
	}

	public String getClassForCircle(int width)
	{
		if (width == MIN_WIDTH_CIRCLE)
		{
			return "small";
		} else if (width > MIN_WIDTH_CIRCLE && width <= ((MAX_WIDTH_CIRCLE + MIN_WIDTH_CIRCLE)/4))
		{
			return "smaller";
		} else if (width > ((MAX_WIDTH_CIRCLE + MIN_WIDTH_CIRCLE)/4) && width <= (MAX_WIDTH_CIRCLE/2))
		{
			return "medium";
		} else if (width > (MAX_WIDTH_CIRCLE/2) && width <= (MAX_WIDTH_CIRCLE*3/4))
		{
			return "mediumLarge";
		} else if (width > (MAX_WIDTH_CIRCLE*3/4) && width <= MAX_WIDTH_CIRCLE)
		{
			return "large";
		}
		return "";
	}

	public int articleVendusCalculateWidthCircle(int nombreArticles)
	{
		int width = nombreArticles * MAX_WIDTH_CIRCLE / sommeArticlesVendus;
		return (width < MIN_WIDTH_CIRCLE) ? MIN_WIDTH_CIRCLE : width;
	}
	
	//Getters et Setters

	public int getSommeArticlesVendus() {
		return sommeArticlesVendus;
	}

	public void setSommeArticlesVendus(int sommeArticlesVendus) {
		this.sommeArticlesVendus = sommeArticlesVendus;
	}

	public int getMAX_WIDTH_CIRCLE() {
		return MAX_WIDTH_CIRCLE;
	}

	public int getMIN_WIDTH_CIRCLE() {
		return MIN_WIDTH_CIRCLE;
	}

	public SelectItemGroup getArticlesVendus() {
		return articlesVendus;
	}

	public void setArticlesVendus(SelectItemGroup articlesVendus) {
		this.articlesVendus = articlesVendus;
	}

	public List<CategoryProduct> getListeProduitsVendusSurLeJour() {
		return listeProduitsVendusSurLeJour;
	}

	public void setListeProduitsVendusSurLeJour(
			List<CategoryProduct> listeProduitsVendusSurLeJour) {
		this.listeProduitsVendusSurLeJour = listeProduitsVendusSurLeJour;
	}

	public static Logger getLog() {
		return log;
	}

	public static void setLog(Logger log) {
		DataBeanMongo.log = log;
	}
}
