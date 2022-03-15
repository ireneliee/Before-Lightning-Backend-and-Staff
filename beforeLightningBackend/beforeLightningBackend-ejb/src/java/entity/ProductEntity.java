/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Entity
public class ProductEntity extends ProductTypeEntity implements Serializable {

    @Column(nullable = false, length = 128)
    @NotNull
    @Size(max = 128)
    private String productName;
    @Column(nullable = false, unique = true, length = 7)
    @NotNull
    @Size(min = 7, max = 7)
    private String skuCode;
    @Column(nullable = false)
    @NotNull
    private String description;
    @Column(nullable = false)
    @NotNull
    private String productOverview;
    @Column(nullable = false)
    @NotNull
    @Positive
    @Min(1)
    @Max(5)
    private Integer productRating;
    @Column(nullable = true, length = 256)
    @Size(max = 256)
    private String imageLink;

    @ManyToMany(mappedBy = "products")
    private List<PartEntity> parts;

    public ProductEntity(String superclassName) {
        super(superclassName);
        this.parts = new ArrayList<>();
    }

    public ProductEntity() {
        this.parts = new ArrayList<>();
        this.imageLink = "";
    }

    public ProductEntity(String superclassName, String productName, String skuCode, String description, String productOverview, Integer productRating) {
        this(superclassName);
        this.productName = productName;
        this.skuCode = skuCode;
        this.description = description;
        this.productOverview = productOverview;
        this.productRating = productRating;
        this.imageLink = "";
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProductOverview() {
        return productOverview;
    }

    public void setProductOverview(String productOverview) {
        this.productOverview = productOverview;
    }

    public Integer getProductRating() {
        return productRating;
    }

    public void setProductRating(Integer productRating) {
        this.productRating = productRating;
    }

    public List<PartEntity> getParts() {
        return parts;
    }

    public void setParts(List<PartEntity> parts) {
        this.parts = parts;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

}
