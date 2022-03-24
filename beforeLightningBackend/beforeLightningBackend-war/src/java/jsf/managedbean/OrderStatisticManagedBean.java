/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.PurchaseOrderEntitySessionBeanLocal;
import ejb.session.stateless.StatisticSessionBeanLocal;
import entity.PurchaseOrderEntity;
import entity.PurchaseOrderLineItemEntity;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.bar.BarChartDataSet;

/**
 *
 * @author irene
 */
@Named(value = "orderStatisticManagedBean")
@ViewScoped
public class OrderStatisticManagedBean implements Serializable {

    @EJB
    private StatisticSessionBeanLocal statisticSessionBeanLocal;

    @EJB
    private PurchaseOrderEntitySessionBeanLocal purchaseOrderEntitySessionBeanLocal;

    private BarChartModel orderStackedGroupModel;
    Map<String, List<PurchaseOrderEntity>> listOfPurchaseOrders;

    public OrderStatisticManagedBean() {
    }

    @PostConstruct
    public void postConstruct() {
        listOfPurchaseOrders = statisticSessionBeanLocal.retrievePurchaseOrdersBasedOnMonth();
        createOrderStackedGroupModel();
    }

    private void createOrderStackedGroupModel() {
        orderStackedGroupModel = new BarChartModel();
        orderStackedGroupModel.setTitle("Number of purchase order made VS number of products sold");

        ChartSeries ordersMadeDataSet = new ChartSeries();
        ChartSeries productsSoldDataSet = new ChartSeries();

        ordersMadeDataSet.setLabel("Monthly Orders Report Chart");

        productsSoldDataSet.setLabel("Products Sold");


        for (String month : listOfPurchaseOrders.keySet()) {
            int orderMade = 0;
            int productSold = 0;

            List<PurchaseOrderEntity> listOfPurchaseOrdersEachMonth = listOfPurchaseOrders.get(month);
            for (PurchaseOrderEntity p : listOfPurchaseOrdersEachMonth) {
                orderMade++;
                for (PurchaseOrderLineItemEntity pl : p.getPurchaseOrderLineItems()) {
                    productSold += pl.getQuantity();
                }

            }
            ordersMadeDataSet.set(month, orderMade);
            productsSoldDataSet.set(month, productSold);

        }

        orderStackedGroupModel.addSeries(ordersMadeDataSet);
        orderStackedGroupModel.addSeries(productsSoldDataSet);

    }

    public PurchaseOrderEntitySessionBeanLocal getPurchaseOrderEntitySessionBeanLocal() {
        return purchaseOrderEntitySessionBeanLocal;
    }

    public void setPurchaseOrderEntitySessionBeanLocal(PurchaseOrderEntitySessionBeanLocal purchaseOrderEntitySessionBeanLocal) {
        this.purchaseOrderEntitySessionBeanLocal = purchaseOrderEntitySessionBeanLocal;
    }

    public BarChartModel getOrderStackedGroupModel() {
        return orderStackedGroupModel;
    }

    public void setOrderStackedGroupModel(BarChartModel orderStackedGroupModel) {
        this.orderStackedGroupModel = orderStackedGroupModel;
    }

}
