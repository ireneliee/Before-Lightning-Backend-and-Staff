/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperRunManager;

/**
 *
 * @author irene
 */
@Named(value = "reportManagedBean")
@ViewScoped
public class ReportManagedBean implements Serializable {

    @Resource(name = "beforeLightningBackendDatasource")
    private DataSource beforeLightningBackendDatasource;

    public ReportManagedBean() {
    }

    public void generateSalesReport(ActionEvent event) {
        try {
            HashMap parameters = new HashMap();
            InputStream reportStream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/jasperreport/salesReport.jasper");
            JasperPrint jasperPrint = JasperFillManager.fillReport(reportStream, parameters, beforeLightningBackendDatasource.getConnection());
            
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            response.reset();
            ServletOutputStream stream = response.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, stream);
            FacesContext.getCurrentInstance().responseComplete();
        } catch (IOException | SQLException | JRException ex) {
            Logger.getLogger(ReportManagedBean.class.getName()).log(Level.SEVERE, null, ex);

        }
    }
    
    public void generateCustomerReport(ActionEvent event) {
        try {
            HashMap parameters = new HashMap();
            InputStream reportStream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/jasperreport/customer.jasper");
            JasperPrint jasperPrint = JasperFillManager.fillReport(reportStream, parameters, beforeLightningBackendDatasource.getConnection());
            
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            response.reset();
            ServletOutputStream stream = response.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, stream);
            FacesContext.getCurrentInstance().responseComplete();
        } catch (IOException | SQLException | JRException ex) {
            Logger.getLogger(ReportManagedBean.class.getName()).log(Level.SEVERE, null, ex);

        }
    }

    public void generateAccessoryReport(ActionEvent event) {
        
        try {
            HashMap parameters = new HashMap();
            InputStream reportStream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/jasperreport/AccessoryReport.jasper");
            JasperPrint jasperPrint = JasperFillManager.fillReport(reportStream, parameters, beforeLightningBackendDatasource.getConnection());
            
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            response.reset();
            ServletOutputStream stream = response.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, stream);
            FacesContext.getCurrentInstance().responseComplete();
        } catch (IOException | SQLException | JRException ex) {
            Logger.getLogger(ReportManagedBean.class.getName()).log(Level.SEVERE, null, ex);

        }
    }

    public void generateMemberReport(ActionEvent event) {
        try {
            HashMap parameters = new HashMap();
            InputStream reportStream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/jasperreport/MemberReport.jasper");
            JasperPrint jasperPrint = JasperFillManager.fillReport(reportStream, parameters, beforeLightningBackendDatasource.getConnection());
            
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            response.reset();
            ServletOutputStream stream = response.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, stream);
            FacesContext.getCurrentInstance().responseComplete();
        } catch (IOException | SQLException | JRException ex) {
            Logger.getLogger(ReportManagedBean.class.getName()).log(Level.SEVERE, null, ex);

        }
        
    }

    public void generatePartChoiceReport(ActionEvent event) {
        try {
            HashMap parameters = new HashMap();
            InputStream reportStream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/jasperreport/PartChoiceReport.jasper");
            JasperPrint jasperPrint = JasperFillManager.fillReport(reportStream, parameters, beforeLightningBackendDatasource.getConnection());
            
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            response.reset();
            ServletOutputStream stream = response.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, stream);
            FacesContext.getCurrentInstance().responseComplete();
        } catch (IOException | SQLException | JRException ex) {
            Logger.getLogger(ReportManagedBean.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

    public void generatePartReport(ActionEvent event) {
         try {
            HashMap parameters = new HashMap();
            InputStream reportStream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/jasperreport/PartReport.jasper");
            JasperPrint jasperPrint = JasperFillManager.fillReport(reportStream, parameters, beforeLightningBackendDatasource.getConnection());
            
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            response.reset();
            ServletOutputStream stream = response.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, stream);
            FacesContext.getCurrentInstance().responseComplete();
        } catch (IOException | SQLException | JRException ex) {
            Logger.getLogger(ReportManagedBean.class.getName()).log(Level.SEVERE, null, ex);

        }
      
    }

    public void generateProductReport(ActionEvent event) {
         try {
            HashMap parameters = new HashMap();
            InputStream reportStream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/jasperreport/ProductReport.jasper");
            JasperPrint jasperPrint = JasperFillManager.fillReport(reportStream, parameters, beforeLightningBackendDatasource.getConnection());
            
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            response.reset();
            ServletOutputStream stream = response.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, stream);
            FacesContext.getCurrentInstance().responseComplete();
        } catch (IOException | SQLException | JRException ex) {
            Logger.getLogger(ReportManagedBean.class.getName()).log(Level.SEVERE, null, ex);

        }
      
    }

    public void generateSupportTicketReport(ActionEvent event) {
        try {
            HashMap parameters = new HashMap();
            InputStream reportStream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/jasperreport/SupportTicketReport.jasper");
            JasperPrint jasperPrint = JasperFillManager.fillReport(reportStream, parameters, beforeLightningBackendDatasource.getConnection());
            
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            response.reset();
            ServletOutputStream stream = response.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, stream);
            FacesContext.getCurrentInstance().responseComplete();
        } catch (IOException | SQLException | JRException ex) {
            Logger.getLogger(ReportManagedBean.class.getName()).log(Level.SEVERE, null, ex);

        }
       
    }
}
