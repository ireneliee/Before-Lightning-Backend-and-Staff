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
        rating = 0;
    }

    @PostConstruct
    public void postConstruct() {

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
        System.out.println(productEntityToView.getProductName());
        List<ReviewEntity> listReviews = productEntityToView.getReviewEntities();
        System.out.println("Size: " + productEntityToView.getReviewEntities().size());
        int total = 0;
        int count = 0;
        for (ReviewEntity rev : listReviews) {
            System.out.println("Rating: " + rev.getDescription());
            total += rev.getRating();
            count += 1;
        }
        if (count == 0) {
            rating = 0;
        } else {
            rating = Math.round(total / count);
        }
        System.out.println("RATING IS: " + this.rating);
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}
