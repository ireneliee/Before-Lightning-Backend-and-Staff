/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.AccessoryItemEntitySessionBeanLocal;
import ejb.session.stateless.PartChoiceEntitySessionBeanLocal;
import ejb.session.stateless.PromotionEntitySessionBeanLocal;
import entity.AccessoryItemEntity;
import entity.PartChoiceEntity;
import entity.PromotionEntity;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import util.exception.AccessoryAlreadyExistsInPromotionException;
import util.exception.AccessoryItemEntityNotFoundException;
import util.exception.InputDataValidationException;
import util.exception.PartChoiceAlreadyExistsInPromotionException;
import util.exception.PartChoiceEntityNotFoundException;
import util.exception.PromotionDiscountTypeExclusiveOrException;
import util.exception.PromotionEntityNotFoundException;
import util.exception.UpdatePromotionException;

/**
 *
 * @author kaiyu
 */
@Named(value = "updatePromotionManagedBean")
@ViewScoped
public class UpdatePromotionManagedBean implements Serializable {

	@EJB
	private AccessoryItemEntitySessionBeanLocal accessoryItemEntitySessionBean;

	@EJB
	private PartChoiceEntitySessionBeanLocal partChoiceEntitySessionBean;

	@EJB
	private PromotionEntitySessionBeanLocal promotionEntitySessionBean;

	private PromotionEntity promotionEntityToUpdate;

	private PromotionEntity tempPromotionEntity;

	private List<AccessoryItemEntity> accessoryItemToAdd;
	private List<AccessoryItemEntity> accessoryItemToRemove;
	private List<PartChoiceEntity> partChoiceToAdd;
	private List<PartChoiceEntity> partChoiceToRemove;

	private List<AccessoryItemEntity> listOfNonPromotionalAccessoryItems;
	private List<PartChoiceEntity> listOfNonPromotionalPartChoices;

	private List<AccessoryItemEntity> listOfPromotionalAccessoryItems;
	private List<PartChoiceEntity> listOfPromotionalPartChoices;

	@Inject
	private PromotionManagementManagedBean promotionmanagementManagedBean;
	@Inject
	private ViewPromotionManagedBean viewPromotionManagedBean;

	public UpdatePromotionManagedBean() {
	}

	@PostConstruct
	public void postConstruct() {
		initializeState();
	}

	public void initializeState() {
		promotionEntityToUpdate = new PromotionEntity();
		tempPromotionEntity = new PromotionEntity();

		accessoryItemToAdd = new ArrayList<>();
		accessoryItemToRemove = new ArrayList<>();
		partChoiceToAdd = new ArrayList<>();
		partChoiceToRemove = new ArrayList<>();

		listOfNonPromotionalAccessoryItems = accessoryItemEntitySessionBean.retrieveAllAccessoryItemEntities();
		listOfNonPromotionalAccessoryItems.removeIf(pc -> pc.getPromotionEntities().size() > 0);
		listOfNonPromotionalPartChoices = partChoiceEntitySessionBean.retrieveAllPartChoiceEntities();
		listOfNonPromotionalPartChoices.removeIf(pc -> pc.getPromotionEntities().size() > 0);

		listOfPromotionalAccessoryItems = promotionEntitySessionBean.retrieveAccessoryItemsWithSpecificPromotion(promotionEntityToUpdate.getPromotionEntityId());
		listOfPromotionalPartChoices = promotionEntitySessionBean.retrievePartChoicesWithSpecificPromotion(promotionEntityToUpdate.getPromotionEntityId());
	}

	public void updatePromotion(ActionEvent event) {
		try {
			promotionEntitySessionBean.updatePromotionEntity(tempPromotionEntity);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Successfully Updated Promotion", null));
		} catch (PromotionEntityNotFoundException ex) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Promotion Not Found Error ", null));
		} catch (UpdatePromotionException ex) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unable To Update Promotion ", null));
		} catch (InputDataValidationException ex) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Input Validation Error ", null));
		} catch (PromotionDiscountTypeExclusiveOrException ex) {
			System.out.println("updatepromo managedbean, either discount field must be 0");
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Either one of the Discount field must be 0!", null));
		} finally {
			this.initializeState();
			promotionmanagementManagedBean.initializeState();
		}
	}

	public void addAccessoryItemToPromotion(ActionEvent event) {
		try {
			promotionEntitySessionBean.addAccessoryItemsToPromotion(promotionEntityToUpdate.getPromotionEntityId(), accessoryItemToAdd);
			viewPromotionManagedBean.initialiseState();
		} catch (AccessoryAlreadyExistsInPromotionException ex) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Existing accessory items under this promotion cannot be added!", null));
		} catch (AccessoryItemEntityNotFoundException ex) {
			System.out.println("accessory not found...");
		} catch (PromotionEntityNotFoundException ex) {
			System.out.println("promo not found...");
		}
	}

	public void removeAccessoryItemToPromotion(ActionEvent event) {
		try {
			promotionEntitySessionBean.removeAccessoryItemsFromPromotion(promotionEntityToUpdate.getPromotionEntityId(), accessoryItemToRemove);
			viewPromotionManagedBean.initialiseState();

		} catch (AccessoryItemEntityNotFoundException ex) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Part choice not under promotion!", null));
		} catch (PromotionEntityNotFoundException ex) {
			System.out.println("promo not found...");
		}
	}

	public void addPartChoiceToPromotion(ActionEvent event) {
		try {
			promotionEntitySessionBean.addPartChoicesToPromotion(promotionEntityToUpdate.getPromotionEntityId(), partChoiceToAdd);
			viewPromotionManagedBean.initialiseState();

		} catch (PartChoiceAlreadyExistsInPromotionException ex) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Existing part choices under this promotion cannot be added!", null));
		} catch (PartChoiceEntityNotFoundException ex) {
			System.out.println("part choice not found...");
		} catch (PromotionEntityNotFoundException ex) {
			System.out.println("promo not found...");
		}
	}

	public void removePartChoiceToPromotion(ActionEvent event) {
		try {
			promotionEntitySessionBean.removePartChoicesFromPromotion(promotionEntityToUpdate.getPromotionEntityId(), partChoiceToRemove);
			viewPromotionManagedBean.initialiseState();

		} catch (PartChoiceEntityNotFoundException ex) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Part choice not under promotion!", null));
		} catch (PromotionEntityNotFoundException ex) {
			System.out.println("promo not found...");
		}

	}

	public PromotionEntitySessionBeanLocal getPromotionEntitySessionBean() {
		return promotionEntitySessionBean;
	}

	public void setPromotionEntitySessionBean(PromotionEntitySessionBeanLocal promotionEntitySessionBean) {
		this.promotionEntitySessionBean = promotionEntitySessionBean;
	}

	public PromotionEntity getPromotionEntityToUpdate() {
		return promotionEntityToUpdate;
	}

	public void setPromotionEntityToUpdate(PromotionEntity promotionEntityToUpdate) {
		this.tempPromotionEntity.setPromotionEntityId(promotionEntityToUpdate.getPromotionEntityId());
		this.tempPromotionEntity.setPromotionName(promotionEntityToUpdate.getPromotionName());
		this.tempPromotionEntity.setStartDate(promotionEntityToUpdate.getStartDate());
		this.tempPromotionEntity.setEndDate(promotionEntityToUpdate.getEndDate());
		this.tempPromotionEntity.setDiscount(promotionEntityToUpdate.getDiscount());
		this.tempPromotionEntity.setDiscountedPrice(promotionEntityToUpdate.getDiscountedPrice());

		this.promotionEntityToUpdate = promotionEntityToUpdate;
	}

	public List<AccessoryItemEntity> getAccessoryItemToAdd() {
		return accessoryItemToAdd;
	}

	public void setAccessoryItemToAdd(List<AccessoryItemEntity> accessoryItemToAdd) {
		this.accessoryItemToAdd = accessoryItemToAdd;
	}

	public List<AccessoryItemEntity> getAccessoryItemToRemove() {
		return accessoryItemToRemove;
	}

	public void setAccessoryItemToRemove(List<AccessoryItemEntity> accessoryItemToRemove) {
		this.accessoryItemToRemove = accessoryItemToRemove;
	}

	public List<PartChoiceEntity> getPartChoiceToAdd() {
		return partChoiceToAdd;
	}

	public void setPartChoiceToAdd(List<PartChoiceEntity> partChoiceToAdd) {
		this.partChoiceToAdd = partChoiceToAdd;
	}

	public List<PartChoiceEntity> getPartChoiceToRemove() {
		return partChoiceToRemove;
	}

	public void setPartChoiceToRemove(List<PartChoiceEntity> partChoiceToRemove) {
		this.partChoiceToRemove = partChoiceToRemove;
	}

	public AccessoryItemEntitySessionBeanLocal getAccessoryItemEntitySessionBean() {
		return accessoryItemEntitySessionBean;
	}

	public void setAccessoryItemEntitySessionBean(AccessoryItemEntitySessionBeanLocal accessoryItemEntitySessionBean) {
		this.accessoryItemEntitySessionBean = accessoryItemEntitySessionBean;
	}

	public PartChoiceEntitySessionBeanLocal getPartChoiceEntitySessionBean() {
		return partChoiceEntitySessionBean;
	}

	public void setPartChoiceEntitySessionBean(PartChoiceEntitySessionBeanLocal partChoiceEntitySessionBean) {
		this.partChoiceEntitySessionBean = partChoiceEntitySessionBean;
	}

	public List<AccessoryItemEntity> getListOfNonPromotionalAccessoryItems() {

		listOfNonPromotionalAccessoryItems = accessoryItemEntitySessionBean.retrieveAllAccessoryItemEntities();
		listOfNonPromotionalAccessoryItems.removeIf(pc -> pc.getPromotionEntities().size() > 0);
		return listOfNonPromotionalAccessoryItems;
	}

	public void setListOfNonPromotionalAccessoryItems(List<AccessoryItemEntity> listOfNonPromotionalAccessoryItems) {
		this.listOfNonPromotionalAccessoryItems = listOfNonPromotionalAccessoryItems;
	}

	public List<PartChoiceEntity> getListOfNonPromotionalPartChoices() {
		listOfNonPromotionalPartChoices = partChoiceEntitySessionBean.retrieveAllPartChoiceEntities();
		listOfNonPromotionalPartChoices.removeIf(pc -> pc.getPromotionEntities().size() > 0);

		return listOfNonPromotionalPartChoices;
	}

	public void setListOfNonPromotionalPartChoices(List<PartChoiceEntity> listOfNonPromotionalPartChoices) {
		this.listOfNonPromotionalPartChoices = listOfNonPromotionalPartChoices;
	}

	public List<AccessoryItemEntity> getListOfPromotionalAccessoryItems() {
		listOfPromotionalAccessoryItems = promotionEntitySessionBean.retrieveAccessoryItemsWithSpecificPromotion(promotionEntityToUpdate.getPromotionEntityId());
		return listOfPromotionalAccessoryItems;
	}

	public void setListOfPromotionalAccessoryItems(List<AccessoryItemEntity> listOfPromotionalAccessoryItems) {
		this.listOfPromotionalAccessoryItems = listOfPromotionalAccessoryItems;
	}

	public List<PartChoiceEntity> getListOfPromotionalPartChoices() {
		listOfPromotionalPartChoices = promotionEntitySessionBean.retrievePartChoicesWithSpecificPromotion(promotionEntityToUpdate.getPromotionEntityId());
		return listOfPromotionalPartChoices;
	}

	public void setListOfPromotionalPartChoices(List<PartChoiceEntity> listOfPromotionalPartChoices) {
		this.listOfPromotionalPartChoices = listOfPromotionalPartChoices;
	}

	public PromotionManagementManagedBean getPromotionmanagementManagedBean() {
		return promotionmanagementManagedBean;
	}

	public void setPromotionmanagementManagedBean(PromotionManagementManagedBean promotionmanagementManagedBean) {
		this.promotionmanagementManagedBean = promotionmanagementManagedBean;
	}

	public ViewPromotionManagedBean getViewPromotionManagedBean() {
		return viewPromotionManagedBean;
	}

	public void setViewPromotionManagedBean(ViewPromotionManagedBean viewPromotionManagedBean) {
		this.viewPromotionManagedBean = viewPromotionManagedBean;
	}

	public PromotionEntity getTempPromotionEntity() {
		return tempPromotionEntity;
	}

	public void setTempPromotionEntity(PromotionEntity tempPromotionEntity) {
		this.tempPromotionEntity = tempPromotionEntity;
	}

}
