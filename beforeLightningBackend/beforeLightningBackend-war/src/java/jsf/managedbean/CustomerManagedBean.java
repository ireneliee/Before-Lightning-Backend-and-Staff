/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.MemberEntitySessionBeanLocal;
import entity.MemberEntity;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Inject;

/**
 *
 * @author irene
 */
@Named(value = "customerManagedBean")
@ViewScoped
public class CustomerManagedBean implements Serializable {

    @EJB
    private MemberEntitySessionBeanLocal memberEntitySessionBeanLocal;
    private List<MemberEntity> filteredMemberEntity;
    private List<MemberEntity> listOfMembers;
    @Inject
    private ViewCustomerManagedBean viewCustomerManagedBean;
    /**
     * Creates a new instance of CustomerManagedBean
     */
    @PostConstruct
    public void postConstruct() {
        listOfMembers = memberEntitySessionBeanLocal.retrieveAllMemberEntities();
    }
    public CustomerManagedBean() {
        
    }

    public MemberEntitySessionBeanLocal getMemberEntitySessionBeanLocal() {
        return memberEntitySessionBeanLocal;
    }

    public void setMemberEntitySessionBeanLocal(MemberEntitySessionBeanLocal memberEntitySessionBeanLocal) {
        this.memberEntitySessionBeanLocal = memberEntitySessionBeanLocal;
    }

    public List<MemberEntity> getFilteredMemberEntity() {
        return filteredMemberEntity;
    }

    public void setFilteredMemberEntity(List<MemberEntity> filteredMemberEntity) {
        this.filteredMemberEntity = filteredMemberEntity;
    }

    public List<MemberEntity> getListOfMembers() {
        return listOfMembers;
    }

    public void setListOfMembers(List<MemberEntity> listOfMembers) {
        this.listOfMembers = listOfMembers;
    }

    public ViewCustomerManagedBean getViewCustomerManagedBean() {
        return viewCustomerManagedBean;
    }

    public void setViewCustomerManagedBean(ViewCustomerManagedBean viewCustomerManagedBean) {
        this.viewCustomerManagedBean = viewCustomerManagedBean;
    }
    
}
