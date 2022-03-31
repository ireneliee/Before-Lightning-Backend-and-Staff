/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.EmployeeEntitySessionBeanLocal;
import entity.EmployeeEntity;
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
import util.exception.EmployeeEntityNotFoundException;
import util.exception.InputDataValidationException;
import util.exception.UpdateEmployeeEntityException;

/**
 *
 * @author Koh Wen Jie
 */
@Named(value = "settingsManagementManagedBean")
@ViewScoped
public class SettingsManagementManagedBean implements Serializable {

    @EJB(name = "EmployeeEntitySessionBeanLocal")
    private EmployeeEntitySessionBeanLocal employeeEntitySessionBeanLocal;

    @Inject
    private UpdateEmployeeManagedBean updateEmployeeManagedBean;

    private EmployeeEntity currentEmployeeEntity;
    private String imageLink;
    
    /**
     * Creates a new instance of SettingsManagementManagedBean
     */
    public SettingsManagementManagedBean() {
    }

    @PostConstruct
    public void postConstruct() {
        currentEmployeeEntity = (EmployeeEntity) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentEmployeeEntity");
    }

    public void handleFileUpload(FileUploadEvent event) {
        try {
            String newFilePath = FacesContext.getCurrentInstance().getExternalContext().getInitParameter("alternatedocroot_1") + System.getProperty("file.separator") + event.getFile().getFileName();
            System.out.println(newFilePath);
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

            imageLink = (event.getFile().getFileName());

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "File uploaded successfully", ""));
        } catch (IOException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "File upload error: " + ex.getMessage(), ""));
        }
    }

    public void updateProfileImage(ActionEvent event) {
        currentEmployeeEntity.setImageLink(imageLink);
        try {
            employeeEntitySessionBeanLocal.updateEmployeeEntity(currentEmployeeEntity);
        } catch (EmployeeEntityNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Employee Not Found", ""));
        } catch (UpdateEmployeeEntityException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unable to Update Image", ""));
        } catch (InputDataValidationException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Input Validation Error", ""));
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Profile Image updated successfully", ""));
        imageLink = "";
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("currentEmployeeEntity", currentEmployeeEntity);
    }

    public EmployeeEntity getCurrentEmployeeEntity() {
        return currentEmployeeEntity;
    }

    public void setCurrentEmployeeEntity(EmployeeEntity currentEmployeeEntity) {
        this.currentEmployeeEntity = currentEmployeeEntity;
    }

    public UpdateEmployeeManagedBean getUpdateEmployeeManagedBean() {
        return updateEmployeeManagedBean;
    }

    public void setUpdateEmployeeManagedBean(UpdateEmployeeManagedBean updateEmployeeManagedBean) {
        this.updateEmployeeManagedBean = updateEmployeeManagedBean;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public EmployeeEntitySessionBeanLocal getEmployeeEntitySessionBeanLocal() {
        return employeeEntitySessionBeanLocal;
    }

    public void setEmployeeEntitySessionBeanLocal(EmployeeEntitySessionBeanLocal employeeEntitySessionBeanLocal) {
        this.employeeEntitySessionBeanLocal = employeeEntitySessionBeanLocal;
    }

}
