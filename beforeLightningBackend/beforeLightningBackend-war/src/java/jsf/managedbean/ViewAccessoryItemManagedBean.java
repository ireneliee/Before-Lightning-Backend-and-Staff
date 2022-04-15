/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import entity.AccessoryItemEntity;
import entity.ReviewEntity;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 *
 * @author Koh Wen Jie
 */
@Named(value = "viewAccessoryItemManagedBean")
@ViewScoped
public class ViewAccessoryItemManagedBean implements Serializable {

    private AccessoryItemEntity accessoryItemEntityToView;
    private Integer rating;

    @Inject
    private AccessoryItemManagementManagedBean accessoryItemManagementManagedBean;

    /**
     * Creates a new instance of ViewAccessoryItemManagedBean
     */
    public ViewAccessoryItemManagedBean() {
    }

    @PostConstruct
    public void postConstruct() {
        initializeState();
    }

    public void initializeState() {

    }

    public AccessoryItemManagementManagedBean getAccessoryItemManagementManagedBean() {
        return accessoryItemManagementManagedBean;
    }

    public void setAccessoryItemManagementManagedBean(AccessoryItemManagementManagedBean accessoryItemManagementManagedBean) {
        this.accessoryItemManagementManagedBean = accessoryItemManagementManagedBean;

    }

    public AccessoryItemEntity getAccessoryItemEntityToView() {
        return accessoryItemEntityToView;
    }

    public void setAccessoryItemEntityToView(AccessoryItemEntity accessoryItemEntityToView) {
        this.accessoryItemEntityToView = accessoryItemEntityToView;
        System.out.println(accessoryItemEntityToView.getAccessoryItemName());
        List<ReviewEntity> listReviews = accessoryItemEntityToView.getReviewEntities();
        System.out.println("Size: " + accessoryItemEntityToView.getReviewEntities().size());
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
