/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.PromotionEntitySessionBeanLocal;
import entity.PromotionEntity;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import util.exception.InputDataValidationException;
import util.exception.PromotionDiscountTypeExclusiveOrException;
import util.exception.PromotionEntityNameExistsException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author kaiyu
 */
@Named(value = "createPromotionManagedBean")
@ViewScoped
public class CreatePromotionManagedBean implements Serializable {

	@EJB
	private PromotionEntitySessionBeanLocal promotionEntitySessionBean;

	@Inject
	private PromotionManagementManagedBean promotionManagementManagedBean;

	private String promotionName;
	private LocalDate promotionStartDate;
	private LocalDate promotionEndDate;
	private Double discount;
	private BigDecimal discountedPrice;

	public CreatePromotionManagedBean() {
	}

	@PostConstruct
	public void postConstruct() {
		initializeState();
	}

	public void initializeState() {

		promotionName = "";
		promotionStartDate = null;
		promotionEndDate = null;
		discount = 0.0;
		discountedPrice = new BigDecimal(0);

	}

	public void createNewPromotion(ActionEvent event) {

		try {
			Long promotionId;
			System.out.println("createpromomanagedbean :: createNewPromo()");
			PromotionEntity newPromotion = new PromotionEntity(promotionName, promotionStartDate, promotionEndDate, discount, discountedPrice);
			promotionId = promotionEntitySessionBean.createNewPromotionEntity(newPromotion);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Successfully created Promotion: " + promotionName, null));
			System.out.println("createpromomanagedbean :: just created promo with ID: " + promotionId);
			this.initializeState();
			promotionManagementManagedBean.initializeState();
		} catch (InputDataValidationException ex) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Input Validation Error", null));
		} catch (UnknownPersistenceException ex) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unknown Persistence Error", null));
		} catch (PromotionEntityNameExistsException ex) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Create New Promotion Error", null));
		} catch (PromotionDiscountTypeExclusiveOrException ex) {
			FacesContext.getCurrentInstance().addMessage("dialogCreatePromotion", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Either discount field or discounted price field must be 0!", null));
		}
	}

	public PromotionEntitySessionBeanLocal getPromotionEntitySessionBean() {
		return promotionEntitySessionBean;
	}

	public void setPromotionEntitySessionBean(PromotionEntitySessionBeanLocal promotionEntitySessionBean) {
		this.promotionEntitySessionBean = promotionEntitySessionBean;
	}

	public PromotionManagementManagedBean getPromotionManagementManagedBean() {
		return promotionManagementManagedBean;
	}

	public void setPromotionManagementManagedBean(PromotionManagementManagedBean promotionManagementManagedBean) {
		this.promotionManagementManagedBean = promotionManagementManagedBean;
	}

	public String getPromotionName() {
		return promotionName;
	}

	public void setPromotionName(String promotionName) {
		this.promotionName = promotionName;
	}

	public LocalDate getPromotionStartDate() {
		return promotionStartDate;
	}

	public void setPromotionStartDate(LocalDate promotionStartDate) {
		this.promotionStartDate = promotionStartDate;
	}

	public LocalDate getPromotionEndDate() {
		return promotionEndDate;
	}

	public void setPromotionEndDate(LocalDate promotionEndDate) {
		this.promotionEndDate = promotionEndDate;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public BigDecimal getDiscountedPrice() {
		return discountedPrice;
	}

	public void setDiscountedPrice(BigDecimal discountedPrice) {
		this.discountedPrice = discountedPrice;
	}

//	public PromotionEntity getNewPromotion() {
//		return newPromotion;
//	}
//
//	public void setNewPromotion(PromotionEntity newPromotion) {
//		this.newPromotion = newPromotion;
//	}
}
