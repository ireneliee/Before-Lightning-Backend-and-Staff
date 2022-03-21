/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.PartChoiceEntitySessionBeanLocal;
import ejb.session.stateless.PartEntitySessionBeanLocal;
import entity.PartChoiceEntity;
import entity.PartEntity;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import org.primefaces.event.FileUploadEvent;
import util.exception.InputDataValidationException;
import util.exception.PartChoiceEntityExistException;
import util.exception.PartNameNotFoundException;
import util.exception.UnableToAddPartChoiceToPartChoiceException;
import util.exception.UnableToAddPartChoiceToPartException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author Koh Wen Jie
 */
@Named(value = "createPartChoiceManagedBean")
@ViewScoped
public class CreatePartChoiceManagedBean implements Serializable {

    @EJB(name = "PartEntitySessionBeanLocal")
    private PartEntitySessionBeanLocal partEntitySessionBeanLocal;

    @EJB(name = "PartChoiceEntitySessionBeanLocal")
    private PartChoiceEntitySessionBeanLocal partChoiceEntitySessionBeanLocal;

    @Inject
    private PartChoiceManagementManagedBean partChoiceManagementManagedBean;

    private PartChoiceEntity newPartChoiceEntity;
    private String imageLink;
    private List<PartEntity> listOfAvailablePartEntities;
    private List<PartEntity> listOfSelectedPartEntities;
    private List<PartChoiceEntity> listOfAvailableChassisPartChoiceEntities;
    private List<PartChoiceEntity> listOfSelectedChassisPartChoiceEntities;

    public CreatePartChoiceManagedBean() {
    }

    @PostConstruct
    public void postConstruct() {
        initializeState();
    }

    public void initializeState() {
        setNewPartChoiceEntity(new PartChoiceEntity());
        setImageLink("");
        listOfAvailablePartEntities = partEntitySessionBeanLocal.retrieveAllPartEntities();
        listOfAvailablePartEntities.removeIf(p -> p.getPartName().equals("Chassis"));
        try {
            listOfAvailableChassisPartChoiceEntities = partEntitySessionBeanLocal.retrievePartEntityByPartName("Chassis").getPartChoiceEntities();
        } catch (PartNameNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "CHASSIS PART NOT FOUND", null));
        }
    }

    public void createNewPartChoice(ActionEvent event) {
        if (listOfSelectedPartEntities.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please Select Parts that This Part Choice is Under", null));
        } else {
            try {
                if (!getImageLink().equals("")) {
                    newPartChoiceEntity.setImageLink(imageLink);
                }
                Long partChoiceId = partChoiceEntitySessionBeanLocal.createNewPartChoiceEntity(newPartChoiceEntity);
                if (!partChoiceId.equals(null)) {
                    //Add to Part
                    partEntitySessionBeanLocal.addPartChoiceToListOfParts(partChoiceId, listOfSelectedPartEntities);
                    partChoiceEntitySessionBeanLocal.addPartChoiceToListOfChassisChoice(partChoiceId, listOfSelectedChassisPartChoiceEntities);
                }

                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Successfully created Part Choice " + getNewPartChoiceEntity().getPartChoiceName(), null));
                initializeState();
                partChoiceManagementManagedBean.initializeState();

            } catch (UnknownPersistenceException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unknown Persistence Error", null));
            } catch (PartChoiceEntityExistException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Part Choice Exists!", null));
            } catch (InputDataValidationException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Input Validation Error", null));
            } catch (UnableToAddPartChoiceToPartException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unable To Add Part Choice to Part", null));
            } catch (UnableToAddPartChoiceToPartChoiceException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unable To Add Part Choice to Chassis", null));
            }
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

    /**
     * @return the imageLink
     */
    public String getImageLink() {
        return imageLink;
    }

    /**
     * @param imageLink the imageLink to set
     */
    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public PartChoiceEntitySessionBeanLocal getPartChoiceEntitySessionBeanLocal() {
        return partChoiceEntitySessionBeanLocal;
    }

    public void setPartChoiceEntitySessionBeanLocal(PartChoiceEntitySessionBeanLocal partChoiceEntitySessionBeanLocal) {
        this.partChoiceEntitySessionBeanLocal = partChoiceEntitySessionBeanLocal;
    }

    public PartChoiceEntity getNewPartChoiceEntity() {
        return newPartChoiceEntity;
    }

    public void setNewPartChoiceEntity(PartChoiceEntity newPartChoiceEntity) {
        this.newPartChoiceEntity = newPartChoiceEntity;
    }

    public PartEntitySessionBeanLocal getPartEntitySessionBeanLocal() {
        return partEntitySessionBeanLocal;
    }

    public void setPartEntitySessionBeanLocal(PartEntitySessionBeanLocal partEntitySessionBeanLocal) {
        this.partEntitySessionBeanLocal = partEntitySessionBeanLocal;
    }

    public List<PartEntity> getListOfAvailablePartEntities() {
        return listOfAvailablePartEntities;
    }

    public void setListOfAvailablePartEntities(List<PartEntity> listOfAvailablePartEntities) {
        this.listOfAvailablePartEntities = listOfAvailablePartEntities;
    }

    public List<PartEntity> getListOfSelectedPartEntities() {
        return listOfSelectedPartEntities;
    }

    public void setListOfSelectedPartEntities(List<PartEntity> listOfSelectedPartEntities) {
        this.listOfSelectedPartEntities = listOfSelectedPartEntities;
    }

    /**
     * @return the listOfSelectedChassisPartChoiceEntities
     */
    public List<PartChoiceEntity> getListOfSelectedChassisPartChoiceEntities() {
        return listOfSelectedChassisPartChoiceEntities;
    }

    /**
     * @param listOfSelectedChassisPartChoiceEntities the
     * listOfSelectedChassisPartChoiceEntities to set
     */
    public void setListOfSelectedChassisPartChoiceEntities(List<PartChoiceEntity> listOfSelectedChassisPartChoiceEntities) {
        this.listOfSelectedChassisPartChoiceEntities = listOfSelectedChassisPartChoiceEntities;
    }

    public List<PartChoiceEntity> getListOfAvailableChassisPartChoiceEntities() {
        return listOfAvailableChassisPartChoiceEntities;
    }

    public void setListOfAvailableChassisPartChoiceEntities(List<PartChoiceEntity> listOfAvailableChassisPartChoiceEntities) {
        this.listOfAvailableChassisPartChoiceEntities = listOfAvailableChassisPartChoiceEntities;
    }

}
