/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.MemberEntitySessionBeanLocal;
import entity.MemberEntity;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpServletRequest;
import util.exception.InputDataValidationException;
import util.exception.MemberEntityNotFoundException;
import util.exception.UpdateMemberEntityException;

/**
 *
 * @author irene
 */
@Named(value = "viewCustomerManagedBean")
@ViewScoped
public class ViewCustomerManagedBean implements Serializable {

    @EJB
    private MemberEntitySessionBeanLocal memberEntitySessionBeanLocal;

    private MemberEntity memberEntityToView;
    
    private Boolean hasProfilePic;

    public ViewCustomerManagedBean() {
        memberEntityToView = new MemberEntity();
    }

    public void deactivateCustomer(ActionEvent event) throws IOException {
        memberEntityToView.setIsActive(false);
        System.out.println(memberEntityToView.getIsActive());
        try {
            memberEntitySessionBeanLocal.updateMemberEntity(memberEntityToView);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Customer account has been successfully deactivated", null));
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
        } catch (MemberEntityNotFoundException | UpdateMemberEntityException | InputDataValidationException ex) {
            Logger.getLogger(ViewCustomerManagedBean.class.getName()).log(Level.SEVERE, null, ex);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }

    }

    public void hasAProfilePicture() {
        System.out.println("MemberEntityToView image: " + memberEntityToView.getImageLink());
        if (memberEntityToView.getImageLink() != null && !memberEntityToView.getImageLink().equals("")) {
            System.out.println("==================Checking profile pic======================");
            System.out.println("Returning true");
            hasProfilePic = true;
        } else {
            System.out.println("Returning false");
            hasProfilePic = false;
        }
    }

public void activateCustomer(ActionEvent event) throws IOException {
        memberEntityToView.setIsActive(true);
        System.out.println(memberEntityToView.getIsActive());
        try {
            memberEntitySessionBeanLocal.updateMemberEntity(memberEntityToView);
             FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Customer account has been successfully activated", null));
             ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
    ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
        } catch (MemberEntityNotFoundException | UpdateMemberEntityException | InputDataValidationException ex) {
            Logger.getLogger(ViewCustomerManagedBean

.class
.getName()).log(Level.SEVERE, null, ex);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
        
    }

    public MemberEntity getMemberEntityToView() {
        return memberEntityToView;
    }

    public void setMemberEntityToView(MemberEntity memberEntityToView) {
        this.memberEntityToView = memberEntityToView;
        hasAProfilePicture();
    }

    public MemberEntitySessionBeanLocal getMemberEntitySessionBeanLocal() {
        return memberEntitySessionBeanLocal;
    }

    public void setMemberEntitySessionBeanLocal(MemberEntitySessionBeanLocal memberEntitySessionBeanLocal) {
        this.memberEntitySessionBeanLocal = memberEntitySessionBeanLocal;
    }

    public Boolean getHasProfilePic() {
        return hasProfilePic;
    }

    public void setHasProfilePic(Boolean hasProfilePic) {
        this.hasProfilePic = hasProfilePic;
    }
    
}
