/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import entity.AccessoryEntity;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;

/**
 *
 * @author Koh Wen Jie
 */
@Named(value = "viewAccessoryManagedBean")
@ViewScoped
public class ViewAccessoryManagedBean implements Serializable {

    private AccessoryEntity accessoryEntityToView;
    
    /**
     * Creates a new instance of ViewAccessoryManagedBean
     */
    public ViewAccessoryManagedBean() {
    }

    public AccessoryEntity getAccessoryEntityToView() {
        return accessoryEntityToView;
    }

    public void setAccessoryEntityToView(AccessoryEntity accessoryEntityToView) {
        this.accessoryEntityToView = accessoryEntityToView;
    }
    
}
