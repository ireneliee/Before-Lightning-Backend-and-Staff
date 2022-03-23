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
import javax.sql.DataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;

/**
 *
 * @author irene
 */
@Named(value = "salesReportManagedBean")
@ViewScoped
public class SalesReportManagedBean implements Serializable {

    @Resource(name = "beforeLightningBackendDatasource")
    private DataSource beforeLightningBackendDatasource;

    public SalesReportManagedBean() {
    }

    public void generateSalesReport(ActionEvent event) {
        try {
            HashMap parameters = new HashMap();
            InputStream reportStream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/jasperreport/salesReport.jasper");
            OutputStream outputStream = FacesContext.getCurrentInstance().getExternalContext().getResponseOutputStream();
            JasperRunManager.runReportToPdfStream(reportStream, outputStream, parameters, beforeLightningBackendDatasource.getConnection());
        } catch (IOException ex) {
            Logger.getLogger(SalesReportManagedBean.class.getName()).log(Level.SEVERE, null, ex);

        } catch (SQLException ex) {
            Logger.getLogger(SalesReportManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JRException ex) {
            Logger.getLogger(SalesReportManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
