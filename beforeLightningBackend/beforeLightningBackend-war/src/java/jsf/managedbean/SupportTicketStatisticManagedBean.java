/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.SupportTicketEntitySessionBeanLocal;
import entity.SupportTicketEntity;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import org.primefaces.model.chart.PieChartModel;
import util.enumeration.SupportTicketStatusEnum;

/**
 *
 * @author irene
 */
@Named(value = "supportTicketStatisticManagedBean")
@ViewScoped
public class SupportTicketStatisticManagedBean implements Serializable {

    @EJB
    private SupportTicketEntitySessionBeanLocal supportTicketEntitySessionBeanLocal;
    private PieChartModel supportTicketFulfillmentChart;

    /**
     * Creates a new instance of SupportTicketStatisticManagedBean
     */
    public SupportTicketStatisticManagedBean() {
    }

    @PostConstruct
    public void init() {
        createSupportTicketFulfillmentChart();
    }

    public void createSupportTicketFulfillmentChart() {
        List<SupportTicketEntity> listOfSupportTickets = supportTicketEntitySessionBeanLocal.retrieveAllSupportTickets();
        int notProcessed = 0;
        int processed = 0;
        int closed = 0;
        SupportTicketStatusEnum status;
        for (SupportTicketEntity supportTicket : listOfSupportTickets) {
            status = supportTicket.getSupportTicketStatus();
            if (status == SupportTicketStatusEnum.OPEN) {
                notProcessed++;
            } else if (status == SupportTicketStatusEnum.PROCESSING) {
                processed++;
            } else if (status == SupportTicketStatusEnum.CLOSED) {
                closed++;
            }
        }

        supportTicketFulfillmentChart = new PieChartModel();

        supportTicketFulfillmentChart.set("Not processed", notProcessed);
        supportTicketFulfillmentChart.set("Processing", processed);
        supportTicketFulfillmentChart.set("Closed", closed);

        supportTicketFulfillmentChart.setTitle("Support Ticket Fulfillment Chart");
        supportTicketFulfillmentChart.setLegendPosition("w");
        supportTicketFulfillmentChart.setShadow(false);

    }

    public SupportTicketEntitySessionBeanLocal getSupportTicketEntitySessionBeanLocal() {
        return supportTicketEntitySessionBeanLocal;
    }

    public void setSupportTicketEntitySessionBeanLocal(SupportTicketEntitySessionBeanLocal supportTicketEntitySessionBeanLocal) {
        this.supportTicketEntitySessionBeanLocal = supportTicketEntitySessionBeanLocal;
    }

    public PieChartModel getSupportTicketFulfillmentChart() {
        return supportTicketFulfillmentChart;
    }

    public void setSupportTicketFulfillmentChart(PieChartModel supportTicketFulfillmentChart) {
        this.supportTicketFulfillmentChart = supportTicketFulfillmentChart;
    }

}
