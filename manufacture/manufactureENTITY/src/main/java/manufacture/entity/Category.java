package manufacture.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the category database table.
 * 
 */
@Entity
@NamedQuery(name="Category.findAll", query="SELECT c FROM Category c")
public class Category implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id_category")
	private int idCategory;

	@Column(name="category_name")
	private String categoryName;

	//bi-directional many-to-one association to ProductRef
	@OneToMany(mappedBy="category")
	private List<ProductRef> productRefs;

	public Category() {
	}

	public int getIdCategory() {
		return this.idCategory;
	}

	public void setIdCategory(int idCategory) {
		this.idCategory = idCategory;
	}

	public String getCategoryName() {
		return this.categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public List<ProductRef> getProductRefs() {
		return this.productRefs;
	}

	public void setProductRefs(List<ProductRef> productRefs) {
		this.productRefs = productRefs;
	}

	public ProductRef addProductRef(ProductRef productRef) {
		getProductRefs().add(productRef);
		productRef.setCategory(this);

		return productRef;
	}

	public ProductRef removeProductRef(ProductRef productRef) {
		getProductRefs().remove(productRef);
		productRef.setCategory(null);

		return productRef;
	}

}