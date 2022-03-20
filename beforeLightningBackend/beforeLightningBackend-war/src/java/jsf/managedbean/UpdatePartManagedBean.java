/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.PartEntitySessionBeanLocal;
import entity.PartEntity;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import util.exception.InputDataValidationException;
import util.exception.PartEntityNotFoundException;
import util.exception.UpdatePartEntityException;

/**
 *
 * @author Koh Wen Jie
 */
@Named(value = "updatePartManagedBean")
@ViewScoped
public class UpdatePartManagedBean implements Serializable {

    @EJB(name = "PartEntitySessionBeanLocal")
    private PartEntitySessionBeanLocal partEntitySessionBeanLocal;

    @Inject
    private PartManagementManagedBean partManagementManagedBean;

    private PartEntity partEntityToUpdate;

    public UpdatePartManagedBean() {
    }

    @PostConstruct
    public void postConstruct() {
        initializeState();
    }

    public void initializeState() {
        partEntityToUpdate = null;
    }

    public void updatePart(ActionEvent event) {
        try {
            partEntitySessionBeanLocal.updatePartEntity(partEntityToUpdate);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Successfully Updated Part", null));

        } catch (PartEntityNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Part Not Found", null));
        } catch (UpdatePartEntityException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unable to Update Part", null));
        } catch (InputDataValidationException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Input Invalidation Error", null));
        }
        initializeState();
        partManagementManagedBean.initializeState();
    }

    public PartManagementManagedBean getPartManagementManagedBean() {
        return partManagementManagedBean;
    }

    public void setPartManagementManagedBean(PartManagementManagedBean partManagementManagedBean) {
        this.partManagementManagedBean = partManagementManagedBean;
    }

    public PartEntity getPartEntityToUpdate() {
        return partEntityToUpdate;
    }

    public void setPartEntityToUpdate(PartEntity partEntityToUpdate) {
        this.partEntityToUpdate = partEntityToUpdate;
    }

}
