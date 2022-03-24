/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.AccessoryEntitySessionBeanLocal;
import entity.AccessoryEntity;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Inject;

/**
 *
 * @author Koh Wen Jie
 */
@Named(value = "accessoryManagementManagedBean")
@ViewScoped
public class AccessoryManagementManagedBean implements Serializable {

    @EJB(name = "AccessoryEntitySessionBeanLocal")
    private AccessoryEntitySessionBeanLocal accessoryEntitySessionBeanLocal;

    private List<AccessoryEntity> listOfAccessoryEntities;
    private List<AccessoryEntity> filteredListOfAccessoryEntities;

    @Inject
    private ViewAccessoryManagedBean viewAccessoryManagedBean;
    @Inject
    private CreateAccessoryManagedBean createAccessoryManagedBean;
    @Inject
    private UpdateAccessoryManagedBean updateAccessoryManagedBean;
    @Inject
    private DeleteAccessoryManagedBean deleteAccessoryManagedBean;
    @Inject
    private DisableAccessoryManagedBean disableAccessoryManagedBean;

    @Inject
    private UpdateWebsiteAccessoryManagedBean updateWebsiteAccessoryManagedBean;

    public AccessoryManagementManagedBean() {
    }

    @PostConstruct
    public void postConstruct() {
        initializeState();
    }

    public void initializeState() {
        listOfAccessoryEntities = accessoryEntitySessionBeanLocal.retrieveAllAccessoryEntities();
    }

    public List<AccessoryEntity> getListOfAccessoryEntities() {
        return listOfAccessoryEntities;
    }

    public void setListOfAccessoryEntities(List<AccessoryEntity> listOfAccessoryEntities) {
        this.listOfAccessoryEntities = listOfAccessoryEntities;
    }

    public List<AccessoryEntity> getFilteredListOfAccessoryEntities() {
        return filteredListOfAccessoryEntities;
    }

    public void setFilteredListOfAccessoryEntities(List<AccessoryEntity> filteredListOfAccessoryEntities) {
        this.filteredListOfAccessoryEntities = filteredListOfAccessoryEntities;
    }

    public ViewAccessoryManagedBean getViewAccessoryManagedBean() {
        return viewAccessoryManagedBean;
    }

    public void setViewAccessoryManagedBean(ViewAccessoryManagedBean viewAccessoryManagedBean) {
        this.viewAccessoryManagedBean = viewAccessoryManagedBean;
    }

    public CreateAccessoryManagedBean getCreateAccessoryManagedBean() {
        return createAccessoryManagedBean;
    }

    public void setCreateAccessoryManagedBean(CreateAccessoryManagedBean createAccessoryManagedBean) {
        this.createAccessoryManagedBean = createAccessoryManagedBean;
    }

    public UpdateAccessoryManagedBean getUpdateAccessoryManagedBean() {
        return updateAccessoryManagedBean;
    }

    public void setUpdateAccessoryManagedBean(UpdateAccessoryManagedBean updateAccessoryManagedBean) {
        this.updateAccessoryManagedBean = updateAccessoryManagedBean;
    }

    public DeleteAccessoryManagedBean getDeleteAccessoryManagedBean() {
        return deleteAccessoryManagedBean;
    }

    public void setDeleteAccessoryManagedBean(DeleteAccessoryManagedBean deleteAccessoryManagedBean) {
        this.deleteAccessoryManagedBean = deleteAccessoryManagedBean;
    }

    public DisableAccessoryManagedBean getDisableAccessoryManagedBean() {
        return disableAccessoryManagedBean;
    }

    public void setDisableAccessoryManagedBean(DisableAccessoryManagedBean disableAccessoryManagedBean) {
        this.disableAccessoryManagedBean = disableAccessoryManagedBean;
    }

    public UpdateWebsiteAccessoryManagedBean getUpdateWebsiteAccessoryManagedBean() {
        return updateWebsiteAccessoryManagedBean;
    }

    public void setUpdateWebsiteAccessoryManagedBean(UpdateWebsiteAccessoryManagedBean updateWebsiteAccessoryManagedBean) {
        this.updateWebsiteAccessoryManagedBean = updateWebsiteAccessoryManagedBean;
    }

}
