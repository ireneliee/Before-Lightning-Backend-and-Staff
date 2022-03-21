/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.PartChoiceEntitySessionBeanLocal;
import entity.PartChoiceEntity;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import org.primefaces.event.FileUploadEvent;
import util.exception.InputDataValidationException;
import util.exception.PartChoiceEntityNotFoundException;
import util.exception.UpdatePartChoiceEntityException;

/**
 *
 * @author Koh Wen Jie
 */
@Named(value = "updatePartChoiceManagedBean")
@ViewScoped
public class UpdatePartChoiceManagedBean implements Serializable {

    @EJB(name = "PartChoiceEntitySessionBeanLocal")
    private PartChoiceEntitySessionBeanLocal partChoiceEntitySessionBeanLocal;

    @Inject
    private PartChoiceManagementManagedBean partChoiceManagementManagedBean;

    private PartChoiceEntity partChoiceEntityToUpdate;
    private String imageLink;

    /**
     * Creates a new instance of UpdatePartChoicecManagedBean
     */
    public UpdatePartChoiceManagedBean() {
    }

    @PostConstruct
    public void postConstruct() {
        initializeState();
    }

    public void initializeState() {
        setImageLink("");
    }

    public void updatePartChoice(ActionEvent event) {
        if (!imageLink.equals("")) {
            partChoiceEntityToUpdate.setImageLink(imageLink);
        }
        try {
            partChoiceEntitySessionBeanLocal.updatePartChoiceEntity(partChoiceEntityToUpdate);
        } catch (PartChoiceEntityNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unable To Retrieve Part Choice" , null));
        } catch (UpdatePartChoiceEntityException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unable To Update Part Choice: ", null));
        } catch (InputDataValidationException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Input Validation Error: " , null));
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Successfully Updated Part Choice", null));
        partChoiceManagementManagedBean.initializeState();
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
     * @return the partChoiceManagementManagedBean
     */
    public PartChoiceManagementManagedBean getPartChoiceManagementManagedBean() {
        return partChoiceManagementManagedBean;
    }

    /**
     * @param partChoiceManagementManagedBean the
     * partChoiceManagementManagedBean to set
     */
    public void setPartChoiceManagementManagedBean(PartChoiceManagementManagedBean partChoiceManagementManagedBean) {
        this.partChoiceManagementManagedBean = partChoiceManagementManagedBean;
    }

    /**
     * @return the partChoiceEntityToUpdate
     */
    public PartChoiceEntity getPartChoiceEntityToUpdate() {
        return partChoiceEntityToUpdate;
    }

    /**
     * @param partChoiceEntityToUpdate the partChoiceEntityToUpdate to set
     */
    public void setPartChoiceEntityToUpdate(PartChoiceEntity partChoiceEntityToUpdate) {
        this.partChoiceEntityToUpdate = partChoiceEntityToUpdate;
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

}
