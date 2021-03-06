package manufacture.entity.report;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the reporting_decision database table.
 * 
 */
@Entity
@Table(name="reporting_decision")
public class ReportingDecision implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id_reporting_decision")
	private Integer idReportingDecision;

	private String decision;

	//bi-directional many-to-one association to Reporting
	@OneToMany(mappedBy="reportingDecision")
	private List<Reporting> reportings;

	public ReportingDecision() {
	}

	public Integer getIdReportingDecision() {
		return this.idReportingDecision;
	}

	public void setIdReportingDecision(Integer idReportingDecision) {
		this.idReportingDecision = idReportingDecision;
	}

	public String getDecision() {
		return this.decision;
	}

	public void setDecision(String decision) {
		this.decision = decision;
	}

	public List<Reporting> getReportings() {
		return this.reportings;
	}

	public void setReportings(List<Reporting> reportings) {
		this.reportings = reportings;
	}

	public Reporting addReporting(Reporting reporting) {
		getReportings().add(reporting);
		reporting.setReportingDecision(this);

		return reporting;
	}

	public Reporting removeReporting(Reporting reporting) {
		getReportings().remove(reporting);
		reporting.setReportingDecision(null);

		return reporting;
	}

}