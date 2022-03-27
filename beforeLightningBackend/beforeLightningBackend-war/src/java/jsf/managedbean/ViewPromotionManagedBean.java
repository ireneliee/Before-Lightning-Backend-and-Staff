/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.PromotionEntitySessionBeanLocal;
import entity.AccessoryItemEntity;
import entity.PartChoiceEntity;
import entity.PromotionEntity;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;

/**
 *
 * @author kaiyu
 */
@Named(value = "viewPromotionManagedBean")
@ViewScoped
public class ViewPromotionManagedBean implements Serializable {

	@EJB
	private PromotionEntitySessionBeanLocal promotionEntitySessionBean;

	private PromotionEntity promotionEntityToView;
	private List<AccessoryItemEntity> listOfPromotionalAccessories;
	private List<PartChoiceEntity> listOfPromotionalPartChoices;

	public ViewPromotionManagedBean() {
		promotionEntityToView = new PromotionEntity();
	}

	@PostConstruct
	public void postConstruct() {
		initialiseState();
	}

	public void initialiseState() {
		listOfPromotionalAccessories = promotionEntitySessionBean.retrieveAccessoryItemsWithSpecificPromotion(promotionEntityToView.getPromotionEntityId());
		listOfPromotionalPartChoices = promotionEntitySessionBean.retrievePartChoicesWithSpecificPromotion(promotionEntityToView.getPromotionEntityId());
	}

	public List<AccessoryItemEntity> getListOfPromotionalAccessories() {
		return listOfPromotionalAccessories;
	}

	public void setListOfPromotionalAccessories(List<AccessoryItemEntity> listOfPromotionalAccessories) {
		this.listOfPromotionalAccessories = listOfPromotionalAccessories;
	}

	public List<PartChoiceEntity> getListOfPromotionalPartChoices() {
		listOfPromotionalPartChoices = promotionEntitySessionBean.retrievePartChoicesWithSpecificPromotion(promotionEntityToView.getPromotionEntityId());
		return listOfPromotionalPartChoices;
	}

	public void setListOfPromotionalPartChoices(List<PartChoiceEntity> listOfPromotionalPartChoices) {
		this.listOfPromotionalPartChoices = listOfPromotionalPartChoices;
	}

	public PromotionEntitySessionBeanLocal getPromotionEntitySessionBean() {
		return promotionEntitySessionBean;
	}

	public void setPromotionEntitySessionBean(PromotionEntitySessionBeanLocal promotionEntitySessionBean) {
		this.promotionEntitySessionBean = promotionEntitySessionBean;
	}

	public PromotionEntity getPromotionEntityToView() {
		return promotionEntityToView;
	}

	public void setPromotionEntityToView(PromotionEntity promotionEntityToView) {
		this.promotionEntityToView = promotionEntityToView;
	}

}
