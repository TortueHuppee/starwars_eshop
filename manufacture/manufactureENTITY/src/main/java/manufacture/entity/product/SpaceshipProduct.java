package manufacture.entity.product;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the spaceship_product database table.
 * 
 */
@Entity
@Table(name="spaceship_product")
public class SpaceshipProduct implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id_spaceship_product")
	private Integer idSpaceshipProduct;

	//bi-directional many-to-one association to ProductRef
	@ManyToOne
	@JoinColumn(name="id_product_ref")
	private ProductRef productRef;

	//bi-directional many-to-one association to SpaceshipRef
	@ManyToOne
	@JoinColumn(name="id_spaceship_ref")
	private SpaceshipRef spaceshipRef;

	public SpaceshipProduct() {
	}

	public Integer getIdSpaceshipProduct() {
		return this.idSpaceshipProduct;
	}

	public void setIdSpaceshipProduct(Integer idSpaceshipProduct) {
		this.idSpaceshipProduct = idSpaceshipProduct;
	}

	public ProductRef getProductRef() {
		return this.productRef;
	}

	public void setProductRef(ProductRef productRef) {
		this.productRef = productRef;
	}

	public SpaceshipRef getSpaceshipRef() {
		return this.spaceshipRef;
	}

	public void setSpaceshipRef(SpaceshipRef spaceshipRef) {
		this.spaceshipRef = spaceshipRef;
	}

}