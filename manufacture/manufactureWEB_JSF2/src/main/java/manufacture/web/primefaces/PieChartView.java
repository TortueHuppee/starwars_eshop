package manufacture.web.primefaces;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

import org.primefaces.model.chart.PieChartModel;

@ManagedBean(name="pieView")
public class PieChartView {

	private PieChartModel pieModel1;
	private PieChartModel pieModel2;

	@PostConstruct
	public void init() {
		createPieModels();
	}

	public PieChartModel getPieModel1() {
		return pieModel1;
	}

	public PieChartModel getPieModel2() {
		return pieModel2;
	}

	private void createPieModels() {
		createPieModel1();
		createPieModel2();
	}

	private void createPieModel1() {
		pieModel1 = new PieChartModel();

		pieModel1.set("Armes", 540);
		pieModel1.set("Pi�ces d�tach�es", 325);
		pieModel1.set("Accessoires", 702);
		pieModel1.set("Technologie", 421);

		pieModel1.setTitle("Nombre d'articles par cat�gorie");
		pieModel1.setLegendPosition("w");
	}

	private void createPieModel2() {
		pieModel2 = new PieChartModel();

		pieModel2.set("Brand 1", 540);
		pieModel2.set("Brand 2", 325);
		pieModel2.set("Brand 3", 702);
		pieModel2.set("Brand 4", 421);

		pieModel2.setTitle("Custom Pie");
		pieModel2.setLegendPosition("e");
		pieModel2.setFill(false);
		pieModel2.setShowDataLabels(true);
		pieModel2.setDiameter(150);
	}
}
