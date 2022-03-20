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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import util.exception.DeletePartEntityException;
import util.exception.PartEntityNotFoundException;

/**
 *
 * @author Koh Wen Jie
 */
@Named(value = "deletePartManagedBean")
@ViewScoped
public class DeletePartManagedBean implements Serializable {

    @EJB(name = "PartEntitySessionBeanLocal")
    private PartEntitySessionBeanLocal partEntitySessionBeanLocal;

    @Inject
    private PartManagementManagedBean partManagementManagedBean;

    private PartEntity partEntityToDelete;

    /**
     * Creates a new instance of deletePartManagedBean
     */
    public DeletePartManagedBean() {
    }

    @PostConstruct
    public void postConstruct() {
        initializeState();
    }

    public void initializeState() {
    }

    public void deletePart(ActionEvent event) {
        try {
            partEntitySessionBeanLocal.deletePartEntity(partEntityToDelete.getPartEntityId());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Part Has Been Successfully Deleted!", null));
            initializeState();
        } catch (PartEntityNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Part Cannot Be Found!", null));
        } catch (DeletePartEntityException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unable To Delete Part", null));
        }
        partManagementManagedBean.initializeState();
    }

    public PartEntity getPartEntityToDelete() {
        return partEntityToDelete;
    }

    public void setPartEntityToDelete(PartEntity partEntityToDelete) {
        this.partEntityToDelete = partEntityToDelete;
    }

    public PartManagementManagedBean getPartManagementManagedBean() {
        return partManagementManagedBean;
    }

    public void setPartManagementManagedBean(PartManagementManagedBean partManagementManagedBean) {
        this.partManagementManagedBean = partManagementManagedBean;
    }

}
