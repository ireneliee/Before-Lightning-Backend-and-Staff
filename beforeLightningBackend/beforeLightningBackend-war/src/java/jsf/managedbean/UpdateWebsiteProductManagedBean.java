/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.PartChoiceEntitySessionBeanLocal;
import ejb.session.stateless.ProductEntitySessionBeanLocal;
import entity.PartChoiceEntity;
import entity.ProductEntity;
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
import util.exception.ProductEntityNotFoundException;
import util.exception.UpdatePartChoiceEntityException;
import util.exception.UpdateProductEntityException;

/**
 *
 * @author Koh Wen Jie
 */
@Named(value = "updateWebsiteProductManagedBean")
@ViewScoped
public class UpdateWebsiteProductManagedBean implements Serializable {

    @EJB(name = "PartChoiceEntitySessionBeanLocal")
    private PartChoiceEntitySessionBeanLocal partChoiceEntitySessionBeanLocal;

    @EJB(name = "ProductEntitySessionBeanLocal")
    private ProductEntitySessionBeanLocal productEntitySessionBeanLocal;

    @Inject
    private ProductManagementManagedBean productManagementManagedBean;

    private ProductEntity productEntityToUpdate;
    private PartChoiceEntity chassisPartChoiceToUpdate;
    private String imageLink;

    /**
     * Creates a new instance of UpdateWebsiteProductManagedBean
     */
    public UpdateWebsiteProductManagedBean() {
    }

    @PostConstruct
    public void postConstruct() {
        initializeState();
    }

    public void initializeState() {
        chassisPartChoiceToUpdate = new PartChoiceEntity();
        imageLink = "";
    }

    public void updateProduct(ActionEvent event) {
        try {
            if (!imageLink.equals("")) {
                productEntityToUpdate.setImageLink(imageLink);
                chassisPartChoiceToUpdate.setImageLink(imageLink);
            }
            chassisPartChoiceToUpdate.setPartOverview(productEntityToUpdate.getProductOverview());
            chassisPartChoiceToUpdate.setPartDescription(productEntityToUpdate.getDescription());
            chassisPartChoiceToUpdate.setPartChoiceName(productEntityToUpdate.getProductName() + " Chassis");

            productEntitySessionBeanLocal.updateProductEntity(productEntityToUpdate);
            partChoiceEntitySessionBeanLocal.updatePartChoiceEntity(chassisPartChoiceToUpdate);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Successfully Updated Product", null));
            initializeState();
            productManagementManagedBean.initializeState();

        } catch (ProductEntityNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Product Not Found Error ", null));
        } catch (UpdateProductEntityException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unable To Update Product ", null));
        } catch (InputDataValidationException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Input Validation Error ", null));
        } catch (PartChoiceEntityNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Part Choice (Chassis) Does Not Exist ", null));
        } catch (UpdatePartChoiceEntityException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unable To Update Part Choice (Chassis) ", null));
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
     * @return the productEntityToUpdate
     */
    public ProductEntity getProductEntityToUpdate() {
        return productEntityToUpdate;
    }

    /**
     * @param productEntityToUpdate the productEntityToUpdate to set
     */
    public void setProductEntityToUpdate(ProductEntity productEntityToUpdate) {
        this.productEntityToUpdate = productEntityToUpdate;
        try {
            chassisPartChoiceToUpdate = partChoiceEntitySessionBeanLocal.retrievePartChoiceEntityByPartChoiceName(productEntityToUpdate.getProductName() + " Chassis");
        } catch (PartChoiceEntityNotFoundException ex) {
            System.out.println("UNABLE TO GET PART CHOICE");
        }
        imageLink = productEntityToUpdate.getImageLink();
    }

    public PartChoiceEntity getChassisPartChoiceToUpdate() {
        return chassisPartChoiceToUpdate;
    }

    public void setChassisPartChoiceToUpdate(PartChoiceEntity chassisPartChoiceToUpdate) {
        this.chassisPartChoiceToUpdate = chassisPartChoiceToUpdate;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public ProductManagementManagedBean getProductManagementManagedBean() {
        return productManagementManagedBean;
    }

    public void setProductManagementManagedBean(ProductManagementManagedBean productManagementManagedBean) {
        this.productManagementManagedBean = productManagementManagedBean;
    }
}
