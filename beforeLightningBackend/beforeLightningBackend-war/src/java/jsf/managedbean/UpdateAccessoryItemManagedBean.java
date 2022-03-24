/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.AccessoryItemEntitySessionBeanLocal;
import entity.AccessoryItemEntity;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
import org.primefaces.event.FileUploadEvent;
import util.exception.AccessoryItemEntityNotFoundException;
import util.exception.InputDataValidationException;
import util.exception.UpdateAccessoryItemEntityException;

/**
 *
 * @author Koh Wen Jie
 */
@Named(value = "updateAccessoryItemManagedBean")
@ViewScoped
public class UpdateAccessoryItemManagedBean implements Serializable {

    @EJB(name = "AccessoryItemEntitySessionBeanLocal")
    private AccessoryItemEntitySessionBeanLocal accessoryItemEntitySessionBeanLocal;

    @Inject
    private AccessoryItemManagementManagedBean accessoryItemManagementManagedBean;

    private AccessoryItemEntity accessoryItemEntityToUpdate;
    private String imageLink;

    /**
     * Creates a new instance of UpdateAccessoryItemManagedBean
     */
    public UpdateAccessoryItemManagedBean() {
    }

    @PostConstruct
    public void postConstruct() {
        initializeState();
    }

    public void initializeState() {
        setImageLink("");
    }

    public void updateAccessoryItem(ActionEvent event) {
        if (!imageLink.equals("")) {
            accessoryItemEntityToUpdate.setImageLink(imageLink);
        }
        try {
            accessoryItemEntitySessionBeanLocal.updateAccessoryItem(accessoryItemEntityToUpdate);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Successfully Updated Part Choice", null));
            initializeState();
        } catch (UpdateAccessoryItemEntityException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Update Accessory Item Error", null));
        } catch (InputDataValidationException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Input Validation Error", null));
        } catch (AccessoryItemEntityNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Accessory Item Not Found", null));
        }
        accessoryItemManagementManagedBean.initializeState();
    }

    public void handleFileUpload(FileUploadEvent event) {
        try {
            String newFilePath = FacesContext.getCurrentInstance().getExternalContext().getInitParameter("alternatedocroot_1") + System.getProperty("file.separator") + event.getFile().getFileName();

            File file = new File(newFilePath);
            FileOutputStream fileOutputStream = new FileOutputStream(file);

            int a;
            int BUFFER_SIZE = 8192;
            byte[] buffer = new byte[BUFFER_SIZE];

            InputStream inputStream = event.getFile().getInputStream();

            while (true) {
                a = inputStream.read(buffer);

                if (a < 0) {
                    break;
                }

                fileOutputStream.write(buffer, 0, a);
                fileOutputStream.flush();
            }

            fileOutputStream.close();
            inputStream.close();

            setImageLink(event.getFile().getFileName());

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "File uploaded successfully", ""));
        } catch (IOException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "File upload error: " + ex.getMessage(), ""));
        }
    }

    public AccessoryItemEntitySessionBeanLocal getAccessoryItemEntitySessionBeanLocal() {
        return accessoryItemEntitySessionBeanLocal;
    }

    public void setAccessoryItemEntitySessionBeanLocal(AccessoryItemEntitySessionBeanLocal accessoryItemEntitySessionBeanLocal) {
        this.accessoryItemEntitySessionBeanLocal = accessoryItemEntitySessionBeanLocal;
    }

    public AccessoryItemManagementManagedBean getAccessoryItemManagementManagedBean() {
        return accessoryItemManagementManagedBean;
    }

    public void setAccessoryItemManagementManagedBean(AccessoryItemManagementManagedBean accessoryItemManagementManagedBean) {
        this.accessoryItemManagementManagedBean = accessoryItemManagementManagedBean;
    }

    public AccessoryItemEntity getAccessoryItemEntityToUpdate() {
        return accessoryItemEntityToUpdate;
    }

    public void setAccessoryItemEntityToUpdate(AccessoryItemEntity accessoryItemEntityToUpdate) {
        this.accessoryItemEntityToUpdate = accessoryItemEntityToUpdate;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

}
