package net.dragberry.cloudstore.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;

import net.dragberry.cloudstore.business.CategoryServiceLocal;
import net.dragberry.cloudstore.business.ProductServiceLocal;
import net.dragberry.cloudstore.domain.Category;
import net.dragberry.cloudstore.model.ProductListModelBean;
import net.dragberry.cloudstore.query.ProductListQuery;
import net.dragberry.cloudstore.result.ProductList;

@Named("productController")
@RequestScoped
public class ProductController implements Serializable {

	private static final long serialVersionUID = 1982692042270260487L;
	
	@Inject
	private ProductListModelBean productListModelBean;
	@Inject
	private CategoryServiceLocal categoryService;
	@Inject
	private ProductServiceLocal productService;
	
	public void init() {
		if (!productListModelBean.isInitialized()) {
			List<Category> categoryList = categoryService.fetchCategories();
			productListModelBean.setCategoryList(categoryList);
			productListModelBean.setInitialized(true);
		}
		String categoryCode = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("category");
		if (StringUtils.isNotBlank(categoryCode)) {
			Category category = categoryService.fetchCategoryByCode(categoryCode);
			List<Long> categoryIds = new ArrayList<Long>();
			categoryIds.add(category.getId());
			productListModelBean.getProductListQuery().setCategoryIdList(categoryIds);
		}
		doSearch();
	}
	
	public void doSearch() {
		ProductListQuery listQuery =  productListModelBean.getProductListQuery();
		if (listQuery.getMaxCost() != null && listQuery.getMaxCost().equals(BigDecimal.ZERO)) {
			listQuery.setMaxCost(null);
		}
		ProductList productList = productService.fetchProducts(productListModelBean.getProductListQuery());
		productListModelBean.setProductList(productList.getList());
	}
	
	public void doReset() {
		productListModelBean.setProductListQuery(new ProductListQuery());
		doSearch();
	}

	public ProductListModelBean getProductListModelBean() {
		return productListModelBean;
	}

	public void setProductListModelBean(ProductListModelBean productListModelBean) {
		this.productListModelBean = productListModelBean;
	}

}
