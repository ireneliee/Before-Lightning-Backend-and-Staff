/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import entity.PartChoiceEntity;
import entity.PartEntity;
import entity.ProductEntity;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;

/**
 *
 * @author Koh Wen Jie
 */
@Named(value = "viewPartManagedBean")
@ViewScoped
public class ViewPartManagedBean implements Serializable {

    private PartEntity partEntityToView;
    private List<PartChoiceEntity> listOfLinkedPartChoices;
    private List<ProductEntity> listOfLinkedProductEntities;

    public ViewPartManagedBean() {
        partEntityToView = new PartEntity();
    }

    @PostConstruct
    public void postConstruct() {
        initializeState();
    }

    public void initializeState() {
        setListOfLinkedPartChoices(partEntityToView.getPartChoiceEntities());
        setListOfLinkedProductEntities(partEntityToView.getProductEntities());
    }

    public PartEntity getPartEntityToView() {
        return partEntityToView;
    }

    public void setPartEntityToView(PartEntity partEntityToView) {
        this.partEntityToView = partEntityToView;
        initializeState();
    }

    /**
     * @return the listOfLinkedPartChoices
     */
    public List<PartChoiceEntity> getListOfLinkedPartChoices() {
        return listOfLinkedPartChoices;
    }

    /**
     * @param listOfLinkedPartChoices the listOfLinkedPartChoices to set
     */
    public void setListOfLinkedPartChoices(List<PartChoiceEntity> listOfLinkedPartChoices) {
        this.listOfLinkedPartChoices = listOfLinkedPartChoices;
    }

    /**
     * @return the listOfLinkedProductEntities
     */
    public List<ProductEntity> getListOfLinkedProductEntities() {
        return listOfLinkedProductEntities;
    }

    /**
     * @param listOfLinkedProductEntities the listOfLinkedProductEntities to set
     */
    public void setListOfLinkedProductEntities(List<ProductEntity> listOfLinkedProductEntities) {
        this.listOfLinkedProductEntities = listOfLinkedProductEntities;
    }

}
