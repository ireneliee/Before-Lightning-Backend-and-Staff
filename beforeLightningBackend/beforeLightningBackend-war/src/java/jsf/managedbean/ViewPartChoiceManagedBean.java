/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import entity.PartChoiceEntity;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import javax.annotation.PostConstruct;

/**
 *
 * @author Koh Wen Jie
 */
@Named(value = "viewPartChoiceManagedBean")
@ViewScoped
public class ViewPartChoiceManagedBean implements Serializable {

    private PartChoiceEntity partChoiceEntityToView;
    
    
    public ViewPartChoiceManagedBean() {
    }

    @PostConstruct
    public void postConstruct() {
        initializeState();
    }

    public void initializeState() {
        
    }

    public PartChoiceEntity getPartChoiceEntityToView() {
        return partChoiceEntityToView;
    }

    public void setPartChoiceEntityToView(PartChoiceEntity partChoiceEntityToView) {
        this.partChoiceEntityToView = partChoiceEntityToView;
    }
}
