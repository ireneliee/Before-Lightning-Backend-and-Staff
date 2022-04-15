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
import util.exception.PartEntityExistException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author Koh Wen Jie
 */
@Named(value = "createPartManagedBean")
@ViewScoped
public class CreatePartManagedBean implements Serializable {

    @EJB(name = "PartEntitySessionBeanLocal")
    private PartEntitySessionBeanLocal partEntitySessionBeanLocal;

    @Inject
    private PartManagementManagedBean partManagementManagedBean;

    private PartEntity newPartEntity;

    public CreatePartManagedBean() {
    }

    @PostConstruct
    public void postConstruct() {
        initializeState();
    }

    public void initializeState() {
        newPartEntity = new PartEntity();
    }

    public void createNewPart(ActionEvent event) {
        try {
            Long partId = partEntitySessionBeanLocal.createNewPartEntity(newPartEntity);

            if (!partId.equals(null)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Successfully created Part " + newPartEntity.getPartName(), null));
                initializeState();
                partManagementManagedBean.initializeState();
            }
        } catch (UnknownPersistenceException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unknown Persistence Error", null));
        } catch (PartEntityExistException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Part Already Exists!", null));
        } catch (InputDataValidationException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Input Validation Error", null));
        }
    }

    public PartManagementManagedBean getProductManagementManagedBean() {
        return partManagementManagedBean;
    }

    public void setProductManagementManagedBean(PartManagementManagedBean partManagementManagedBean) {
        this.partManagementManagedBean = partManagementManagedBean;
    }

    public PartEntity getNewPartEntity() {
        return newPartEntity;
    }

    public void setNewPartEntity(PartEntity newPartEntity) {
        this.newPartEntity = newPartEntity;
    }

}
