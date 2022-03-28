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
import javax.inject.Inject;

/**
 *
 * @author kaiyu
 */
@Named(value = "promotionManagementManagedBean")
@ViewScoped
public class PromotionManagementManagedBean implements Serializable {

	@EJB
	private PromotionEntitySessionBeanLocal promotionEntitySessionBean;

	@Inject
	private ViewPromotionManagedBean viewPromotionManagedBean;
	@Inject
	private UpdatePromotionManagedBean updatePromotionManagedBean;
	@Inject
	private DeletePromotionManagedBean deletePromotionManagedBean;

	/**
	 * Creates a new instance of PromotionManagementManagedBean
	 */
	private List<PromotionEntity> listOfAllPromotions;

	public PromotionManagementManagedBean() {
	}

	@PostConstruct
	public void PostConstruct() {
		initializeState();
	}

	public void initializeState() {
		listOfAllPromotions = promotionEntitySessionBean.retrieveAllPromotions();
	}

	public List<PromotionEntity> getListOfAllPromotions() {
		return listOfAllPromotions;
	}

	public void setListOfAllPromotions(List<PromotionEntity> listOfAllPromotions) {
		this.listOfAllPromotions = listOfAllPromotions;
	}

	public PromotionEntitySessionBeanLocal getPromotionEntitySessionBean() {
		return promotionEntitySessionBean;
	}

	public void setPromotionEntitySessionBean(PromotionEntitySessionBeanLocal promotionEntitySessionBean) {
		this.promotionEntitySessionBean = promotionEntitySessionBean;
	}

	public ViewPromotionManagedBean getViewPromotionManagedBean() {
		return viewPromotionManagedBean;
	}

	public void setViewPromotionManagedBean(ViewPromotionManagedBean viewPromotionManagedBean) {
		this.viewPromotionManagedBean = viewPromotionManagedBean;
	}

	public UpdatePromotionManagedBean getUpdatePromotionManagedBean() {
		return updatePromotionManagedBean;
	}

	public void setUpdatePromotionManagedBean(UpdatePromotionManagedBean updatePromotionManagedBean) {
		this.updatePromotionManagedBean = updatePromotionManagedBean;
	}

	public DeletePromotionManagedBean getDeletePromotionManagedBean() {
		return deletePromotionManagedBean;
	}

	public void setDeletePromotionManagedBean(DeletePromotionManagedBean deletePromotionManagedBean) {
		this.deletePromotionManagedBean = deletePromotionManagedBean;
	}

}
