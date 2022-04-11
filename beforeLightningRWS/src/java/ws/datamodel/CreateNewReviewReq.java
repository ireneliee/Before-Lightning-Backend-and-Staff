/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.datamodel;

/**
 *
 * @author irene
 */
public class CreateNewReviewReq {

    private String customerUsername;
    private Integer rating;
    private String description;
    private Integer itemId;

    public CreateNewReviewReq() {
    }

    public CreateNewReviewReq(String customerUsername, Integer rating, String description, Integer accId) {
        this.customerUsername = customerUsername;
        this.rating = rating;
        this.description = description;
        this.itemId = accId;
    }

    public String getCustomerUsername() {
        return customerUsername;
    }

    public void setCustomerUsername(String customerUsername) {
        this.customerUsername = customerUsername;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

}
