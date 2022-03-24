/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.MemberEntitySessionBeanLocal;
import ejb.session.stateless.SupportTicketEntitySessionBeanLocal;
import entity.MemberEntity;
import entity.SupportTicketEntity;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import org.primefaces.PrimeFaces;
import util.enumeration.SupportTicketStatusEnum;
import util.exception.MemberEntityNotFoundException;
import util.exception.UpdateSupportTicketEntityException;

/**
 *
 * @author irene
 */
@Named(value = "supportTicketManagedBean")
@ViewScoped
public class SupportTicketManagedBean implements Serializable {

    @EJB
    private MemberEntitySessionBeanLocal memberEntitySessionBeanLocal;

    @EJB
    private SupportTicketEntitySessionBeanLocal supportTicketEntitySessionBeanLocal;

    @Inject
    private ViewCustomerManagedBean viewCustomerManagedBean;

    private List<SupportTicketEntity> listOfSupportTicket;
    private List<SupportTicketEntity> filteredListOfSupportTicket;
    private SupportTicketStatusEnum[] supportTicketStatusEnums;
    private SupportTicketEntity selectedTicketToUpdate;
    private SupportTicketStatusEnum openStatus;
    private SupportTicketStatusEnum processingStatus;
    private SupportTicketStatusEnum closeStatus;

    public SupportTicketManagedBean() {
        openStatus = SupportTicketStatusEnum.OPEN;
        processingStatus = SupportTicketStatusEnum.PROCESSING;
        closeStatus = SupportTicketStatusEnum.CLOSED;
    }

    @PostConstruct
    public void postConstruct() {
        listOfSupportTicket = supportTicketEntitySessionBeanLocal.retrieveAllSupportTickets();
        supportTicketStatusEnums = SupportTicketStatusEnum.values();
        selectedTicketToUpdate = new SupportTicketEntity();

    }

    public void viewCustomer(ActionEvent event) {
        try {
            String email = (String) event.getComponent().getAttributes().get("emailOfCreator");
            MemberEntity m = memberEntitySessionBeanLocal.retrieveMemberEntityByEmail(email);
            System.out.println("Member is found" + m.getFirstname());
            viewCustomerManagedBean.setMemberEntityToView(m);
            /*
             PrimeFaces.current().dialog().openDynamic("dialogViewCustomer");
            */
        } catch (MemberEntityNotFoundException ex) {
            viewCustomerManagedBean.setMemberEntityToView(new MemberEntity());
            FacesContext.getCurrentInstance().addMessage("Error", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ticket creator is not one of our registered members", null));
        }
    }

    public void processTicket(ActionEvent event) {
        selectedTicketToUpdate = (SupportTicketEntity) event.getComponent().getAttributes().get("supportTicketEntityToUpdate");
        selectedTicketToUpdate.setSupportTicketStatus(SupportTicketStatusEnum.PROCESSING);
        try {
            supportTicketEntitySessionBeanLocal.updateSupportTicketEntity(selectedTicketToUpdate);
            FacesContext.getCurrentInstance().addMessage("Success", new FacesMessage(FacesMessage.SEVERITY_INFO, "Support ticket is being processed.", null));
            selectedTicketToUpdate = new SupportTicketEntity();
        } catch (UpdateSupportTicketEntityException ex) {
            FacesContext.getCurrentInstance().addMessage("Error", new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while trying to process the support ticket" + ex.getMessage(), null));
        }

    }

    public void closeTicket(ActionEvent event) {
        selectedTicketToUpdate = (SupportTicketEntity) event.getComponent().getAttributes().get("supportTicketEntityToUpdate");
        selectedTicketToUpdate.setSupportTicketStatus(SupportTicketStatusEnum.CLOSED);
        try {
            supportTicketEntitySessionBeanLocal.updateSupportTicketEntity(selectedTicketToUpdate);
            FacesContext.getCurrentInstance().addMessage("Success", new FacesMessage(FacesMessage.SEVERITY_INFO, "Support ticket is closed.", null));
            selectedTicketToUpdate = new SupportTicketEntity();
        } catch (UpdateSupportTicketEntityException ex) {
            FacesContext.getCurrentInstance().addMessage("Error", new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while trying to close the support ticket" + ex.getMessage(), null));
        }

    }

    public void reopenTicket(ActionEvent event) {
        selectedTicketToUpdate = (SupportTicketEntity) event.getComponent().getAttributes().get("supportTicketEntityToUpdate");
        selectedTicketToUpdate.setSupportTicketStatus(SupportTicketStatusEnum.OPEN);
        try {
            supportTicketEntitySessionBeanLocal.updateSupportTicketEntity(selectedTicketToUpdate);
            FacesContext.getCurrentInstance().addMessage("Success", new FacesMessage(FacesMessage.SEVERITY_INFO, "Support ticket is reopened.", null));
            selectedTicketToUpdate = new SupportTicketEntity();
        } catch (UpdateSupportTicketEntityException ex) {
            FacesContext.getCurrentInstance().addMessage("Error", new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while trying to reopen the support ticket" + ex.getMessage(), null));
        }

    }

    public SupportTicketEntitySessionBeanLocal getSupportTicketEntitySessionBeanLocal() {
        return supportTicketEntitySessionBeanLocal;
    }

    public void setSupportTicketEntitySessionBeanLocal(SupportTicketEntitySessionBeanLocal supportTicketEntitySessionBeanLocal) {
        this.supportTicketEntitySessionBeanLocal = supportTicketEntitySessionBeanLocal;
    }

    public List<SupportTicketEntity> getListOfSupportTicket() {
        return listOfSupportTicket;
    }

    public void setListOfSupportTicket(List<SupportTicketEntity> listOfSupportTicket) {
        this.listOfSupportTicket = listOfSupportTicket;
    }

    public List<SupportTicketEntity> getFilteredListOfSupportTicket() {
        return filteredListOfSupportTicket;
    }

    public void setFilteredListOfSupportTicket(List<SupportTicketEntity> filteredListOfSupportTicket) {
        this.filteredListOfSupportTicket = filteredListOfSupportTicket;
    }

    public SupportTicketStatusEnum[] getSupportTicketStatusEnums() {
        return supportTicketStatusEnums;
    }

    public void setSupportTicketStatusEnums(SupportTicketStatusEnum[] supportTicketStatusEnums) {
        this.supportTicketStatusEnums = supportTicketStatusEnums;
    }

    public SupportTicketEntity getSelectedTicketToUpdate() {
        return selectedTicketToUpdate;
    }

    public void setSelectedTicketToUpdate(SupportTicketEntity selectedTicketToUpdate) {
        this.selectedTicketToUpdate = selectedTicketToUpdate;
    }

    public SupportTicketStatusEnum getOpenStatus() {
        return openStatus;
    }

    public void setOpenStatus(SupportTicketStatusEnum openStatus) {
        this.openStatus = openStatus;
    }

    public SupportTicketStatusEnum getProcessingStatus() {
        return processingStatus;
    }

    public void setProcessingStatus(SupportTicketStatusEnum processingStatus) {
        this.processingStatus = processingStatus;
    }

    public SupportTicketStatusEnum getCloseStatus() {
        return closeStatus;
    }

    public void setCloseStatus(SupportTicketStatusEnum closeStatus) {
        this.closeStatus = closeStatus;
    }

    public ViewCustomerManagedBean getViewCustomerManagedBean() {
        return viewCustomerManagedBean;
    }

    public void setViewCustomerManagedBean(ViewCustomerManagedBean viewCustomerManagedBean) {
        this.viewCustomerManagedBean = viewCustomerManagedBean;
    }

}
