/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.PartChoiceEntitySessionBeanLocal;
import entity.PartChoiceEntity;
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
import util.exception.DeletePartChoiceEntityException;
import util.exception.PartChoiceEntityNotFoundException;

/**
 *
 * @author Koh Wen Jie
 */
@Named(value = "deletePartChoiceManagedBean")
@ViewScoped
public class DeletePartChoiceManagedBean implements Serializable {

    @EJB(name = "PartChoiceEntitySessionBeanLocal")
    private PartChoiceEntitySessionBeanLocal partChoiceEntitySessionBeanLocal;

    @Inject
    private PartChoiceManagementManagedBean partChoiceManagementManagedBean;
    /**
     * Creates a new instance of DeletePartChoiceManagedBean
     */
    private PartChoiceEntity partChoiceEntityToDelete;

    public DeletePartChoiceManagedBean() {
    }

    @PostConstruct
    public void postConstruct() {
        initializeState();
    }

    public void initializeState() {
    }

    public void deletePartChoice(ActionEvent event) {
        try {
            partChoiceEntitySessionBeanLocal.deletePartChoiceEntity(partChoiceEntityToDelete.getPartChoiceEntityId());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Part Choice Has Been Successfully Deleted!", null));
            initializeState();
        } catch (PartChoiceEntityNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Part Choice Does Not Exist!", null));
        } catch (DeletePartChoiceEntityException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unable To Delete Part Choice !", null));
        }
        partChoiceManagementManagedBean.initializeState();
    }

    /**
     * @return the partChoiceManagementManagedBean
     */
    public PartChoiceManagementManagedBean getPartChoiceManagementManagedBean() {
        return partChoiceManagementManagedBean;
    }

    /**
     * @param partChoiceManagementManagedBean the
     * partChoiceManagementManagedBean to set
     */
    public void setPartChoiceManagementManagedBean(PartChoiceManagementManagedBean partChoiceManagementManagedBean) {
        this.partChoiceManagementManagedBean = partChoiceManagementManagedBean;
    }

    /**
     * @return the partChoiceEntityToDelete
     */
    public PartChoiceEntity getPartChoiceEntityToDelete() {
        return partChoiceEntityToDelete;
    }

    /**
     * @param partChoiceEntityToDelete the partChoiceEntityToDelete to set
     */
    public void setPartChoiceEntityToDelete(PartChoiceEntity partChoiceEntityToDelete) {
        this.partChoiceEntityToDelete = partChoiceEntityToDelete;
    }

}
