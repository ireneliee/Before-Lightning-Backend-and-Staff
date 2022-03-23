/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.StatisticSessionBeanLocal;
import entity.ProductEntity;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import org.primefaces.model.chart.PieChartModel;

/**
 *
 * @author irene
 */
@Named(value = "productPurchasedStatisticManagedBean")
@ViewScoped
public class ProductPurchasedStatisticManagedBean implements Serializable {

    @EJB
    private StatisticSessionBeanLocal statisticSessionBeanLocal;

    /**
     * Creates a new instance of ProductPurchasedStatisticManagedBean
     */
    private PieChartModel livePieModel;
    public ProductPurchasedStatisticManagedBean() {
    }
    
    @PostConstruct
    public void init() {
        createLivePieModel();
    }
    
    private void createLivePieModel() {
        Map<ProductEntity, Integer> productsPurchased = statisticSessionBeanLocal.retrieveProductPurchased();
        livePieModel = new PieChartModel();
        for(ProductEntity p: productsPurchased.keySet()) {
            livePieModel.set(p.getProductName(), productsPurchased.get(p));
        }

    }
    
    public PieChartModel getLivePieModel() {
        Map<ProductEntity, Integer> productsPurchased = statisticSessionBeanLocal.retrieveProductPurchased();
        for(ProductEntity p: productsPurchased.keySet()) {
            livePieModel.getData().put(p.getProductName(), productsPurchased.get(p));
        }
 
        livePieModel.setTitle("Products purchased");
        livePieModel.setLegendPosition("ne");
 
        return livePieModel;
    }
    

    public void setLivePieModel(PieChartModel livePieModel) {
        this.livePieModel = livePieModel;
    }
    
}
