/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.AccessoryEntitySessionBeanLocal;
import ejb.session.stateless.AccessoryItemEntitySessionBeanLocal;
import entity.AccessoryEntity;
import entity.AccessoryItemEntity;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import org.primefaces.event.FileUploadEvent;
import util.exception.AccessoryEntityNotFoundException;
import util.exception.AccessoryItemNameExists;
import util.exception.AccessoryNameExistsException;
import util.exception.InputDataValidationException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author Koh Wen Jie
 */
@Named(value = "createAccessoryItemManagedBean")
@ViewScoped
public class CreateAccessoryItemManagedBean implements Serializable {

    @EJB(name = "AccessoryItemEntitySessionBeanLocal")
    private AccessoryItemEntitySessionBeanLocal accessoryItemEntitySessionBeanLocal;

    @EJB(name = "AccessoryEntitySessionBeanLocal")
    private AccessoryEntitySessionBeanLocal accessoryEntitySessionBeanLocal;

    @Inject
    private AccessoryItemManagementManagedBean accessoryItemManagementManagedBean;

    private AccessoryItemEntity newAccessoryItemEntity;
    private String imageLink;
//    private List<String> listOfAccessoryEntities;
//    private String accessoryEntityToLink;    
    private List<AccessoryEntity> listOfAccessoryEntities;
    private AccessoryEntity accessoryEntityToLink;

    /**
     * Creates a new instance of CreateAccessoryItemManagedBean
     */
    public CreateAccessoryItemManagedBean() {
    }

    @PostConstruct
    public void postConstruct() {
        initializeState();
    }

    public void initializeState() {
        newAccessoryItemEntity = new AccessoryItemEntity();
        accessoryEntityToLink = new AccessoryEntity();
        imageLink = "";
        listOfAccessoryEntities = new ArrayList<>();
        listOfAccessoryEntities = accessoryEntitySessionBeanLocal.retrieveAllAccessoryEntitiesNotDisabled();
//        List< AccessoryEntity> list = accessoryEntitySessionBeanLocal.retrieveAllAccessoryEntitiesNotDisabled();
//        for (AccessoryEntity acc : list) {
//            listOfAccessoryEntities.add(acc.getAccessoryName());
//        }

    }

    public void createNewAccessoryItem(ActionEvent event) throws AccessoryEntityNotFoundException {

        System.out.println("WORKING");
        if (accessoryEntityToLink == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please Select Accessory Type! ", null));
        } else {
            if (!imageLink.equals("")) {
                newAccessoryItemEntity.setImageLink(imageLink);
            }
            try {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, accessoryEntityToLink.getAccessoryName() + "HAS BEEN SELECTED ", null));
                accessoryItemEntitySessionBeanLocal.createNewAccessoryItemEntity(newAccessoryItemEntity, accessoryEntityToLink.getAccessoryEntityId());
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Successfully created Accessory Item! ", null));
                initializeState();
            } catch (UnknownPersistenceException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unknown Persistence Error ", null));
            } catch (InputDataValidationException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Input Validation Error " + ex.getMessage(), null));
            } catch (AccessoryItemNameExists ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Accessory Item Name Exists! ", null));
            }

            accessoryItemManagementManagedBean.initializeState();
        }
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

    public AccessoryEntitySessionBeanLocal getAccessoryEntitySessionBeanLocal() {
        return accessoryEntitySessionBeanLocal;
    }

    public void setAccessoryEntitySessionBeanLocal(AccessoryEntitySessionBeanLocal accessoryEntitySessionBeanLocal) {
        this.accessoryEntitySessionBeanLocal = accessoryEntitySessionBeanLocal;
    }

    public AccessoryItemEntity getNewAccessoryItemEntity() {
        return newAccessoryItemEntity;
    }

    public void setNewAccessoryItemEntity(AccessoryItemEntity newAccessoryItemEntity) {
        this.newAccessoryItemEntity = newAccessoryItemEntity;
    }

    public AccessoryItemManagementManagedBean getAccessoryItemManagementManagedBean() {
        return accessoryItemManagementManagedBean;
    }

    public void setAccessoryItemManagementManagedBean(AccessoryItemManagementManagedBean accessoryItemManagementManagedBean) {
        this.accessoryItemManagementManagedBean = accessoryItemManagementManagedBean;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

//    public String getAccessoryEntityToLink() {
//        return accessoryEntityToLink;
//    }
//
//    public void setAccessoryEntityToLink(String accessoryEntityToLink) {
//        this.accessoryEntityToLink = accessoryEntityToLink;
//    }
//
//    public List<String> getListOfAccessoryEntities() {
//        return listOfAccessoryEntities;
//    }
//
//    public void setListOfAccessoryEntities(List<String> listOfAccessoryEntities) {
//        this.listOfAccessoryEntities = listOfAccessoryEntities;
//    }
    public List<AccessoryEntity> getListOfAccessoryEntities() {
        return listOfAccessoryEntities;
    }

    public void setListOfAccessoryEntities(List<AccessoryEntity> listOfAccessoryEntities) {
        this.listOfAccessoryEntities = listOfAccessoryEntities;
    }

    public AccessoryEntity getAccessoryEntityToLink() {
        System.out.println("Gettinmg ===========");

        return accessoryEntityToLink;
    }

    public void setAccessoryEntityToLink(AccessoryEntity accessoryEntityToLink) {
        System.out.println("SETTING ===========");
        this.accessoryEntityToLink = accessoryEntityToLink;
    }

}
