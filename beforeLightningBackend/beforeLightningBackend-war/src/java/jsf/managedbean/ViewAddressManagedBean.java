/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import entity.AddressEntity;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;

/**
 *
 * @author srinivas
 */
@Named(value = "viewAddressManagedBean")
@ViewScoped
public class ViewAddressManagedBean implements Serializable {
private AddressEntity addressToView;
    /**
     * Creates a new instance of ViewAddressManagedBean
     */
    public ViewAddressManagedBean() {
    }

    /**
     * @return the addressToView
     */
    public AddressEntity getAddressToView() {
        return addressToView;
    }

    /**
     * @param addressToView the addressToView to set
     */
    public void setAddressToView(AddressEntity addressToView) {
        this.addressToView = addressToView;
    }
    
}
