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
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import util.exception.PromotionEntityNotFoundException;

/**
 *
 * @author kaiyu
 */
@Named(value = "deletePromotionManagedBean")
@ViewScoped
public class DeletePromotionManagedBean implements Serializable {

	@EJB
	private PromotionEntitySessionBeanLocal promotionEntitySessionBean;

	@Inject
	private PromotionManagementManagedBean promotionManagementManagedBean;

	private PromotionEntity promotionEntityToDelete;

	public DeletePromotionManagedBean() {
	}

	@PostConstruct
	public void postConstruct() {
		initializeState();
	}

	public void initializeState() {
		promotionEntityToDelete = null;

	}

	public void deletePromotion(ActionEvent event) {
		try {
			promotionEntitySessionBean.removePromotionEntity(promotionEntityToDelete.getPromotionEntityId());
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Successfully Deleted Promotion!", null));

		} catch (PromotionEntityNotFoundException ex) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Product Cannot Be Found", null));
		} finally {
			promotionManagementManagedBean.initializeState();
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

	public PromotionEntity getPromotionEntityToDelete() {
		return promotionEntityToDelete;
	}

	public void setPromotionEntityToDelete(PromotionEntity promotionEntityToDelete) {
		this.promotionEntityToDelete = promotionEntityToDelete;
	}

}
