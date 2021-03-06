package net.dragberry.cloudstore.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "CATEGORY")
public class Category extends AbstractEntity {

    private static final long serialVersionUID = 1L;
    
    @Column(name = "title")
    private String title;
    
    @Column(name = "code")
    private String code;
    
    @Column(name = "description")
    private String description;
    
    @Column(name = "full_description")
    private String fullDescription;

    @ManyToMany(mappedBy = "categories", fetch = FetchType.LAZY)
    private Set<Product> products;

    @ManyToOne
    @JoinColumn(name = "PARENT_ID", referencedColumnName = "ID")
    private Category parentCategory;

    /**
     * Sub-categories
     */
    @OneToMany(mappedBy="parentCategory", cascade = CascadeType.PERSIST)
    private Set<Category> subCategories = new HashSet<Category>();

    /**
     * This method returns TRUE, if this category is a subcategory of another category.
     * @return true or false.
     */
    public boolean hasParent() {
    	return parentCategory != null;
    }
    
    /**
     * This method returns TRUE, if this category has subcategories.
     * @return true or false.
     */
    public boolean hasChildren() {
    	return !subCategories.isEmpty();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public Category getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }

    public Set<Category> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(Set<Category> subCategories) {
        this.subCategories = subCategories;
    }

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFullDescription() {
		return fullDescription;
	}

	public void setFullDescription(String fullDescription) {
		this.fullDescription = fullDescription;
	}

}
