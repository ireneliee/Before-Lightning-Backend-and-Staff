/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import entity.ProductEntity;
import entity.ReviewEntity;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;

/**
 *
 * @author Koh Wen Jie
 */
@Named(value = "viewProductManagedBean")
@ViewScoped
public class ViewProductManagedBean implements Serializable {

    private ProductEntity productEntityToView;
    private Integer rating;

    public ViewProductManagedBean() {
        productEntityToView = new ProductEntity();
    }

    @PostConstruct
    public void postConstruct() {
        List<ReviewEntity> listReviews = productEntityToView.getReviewEntities();
        int total = 0;
        int count = 0;
        for (ReviewEntity rev : listReviews) {
            total += rev.getRating();
            count += 1;
        }
        if (count == 0) {
            rating = 0;
        } else {
            rating = total / count;
        }
    }

    /**
     * @return the productEntityToView
     */
    public ProductEntity getProductEntityToView() {
        return productEntityToView;
    }

    /**
     * @param productEntityToView the productEntityToView to set
     */
    public void setProductEntityToView(ProductEntity productEntityToView) {
        this.productEntityToView = productEntityToView;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}
