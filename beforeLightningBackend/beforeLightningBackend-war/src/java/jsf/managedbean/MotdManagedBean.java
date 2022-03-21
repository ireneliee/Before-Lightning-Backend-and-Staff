/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.MessageOfTheDayEntitySessionBeanLocal;
import entity.MessageOfTheDayEntity;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.faces.view.ViewScoped;

/**
 *
 * @author kaiyu
 */
@Named(value = "motdManagedBean")
@ViewScoped
public class MotdManagedBean implements Serializable{

	/**
	 * Creates a new instance of MotdManagedBean
	 */
	public MotdManagedBean() {
	}
	
	
	
	
	@EJB
    private MessageOfTheDayEntitySessionBeanLocal messageOfTheDayEntitySessionBeanLocal;
    
    private List<MessageOfTheDayEntity> messageOfTheDayEntities;
    
    


    @PostConstruct
    public void postConstruct()
    {
        messageOfTheDayEntities = messageOfTheDayEntitySessionBeanLocal.retrieveAllMessagesOfTheDay();
    }

    
    
    public List<MessageOfTheDayEntity> getMessageOfTheDayEntities() {
        return messageOfTheDayEntities;
    }
	
	public String formatMotdHeader(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-Mm-dd");
		return format.format(date);
	}

    public void setMessageOfTheDayEntities(List<MessageOfTheDayEntity> messageOfTheDayEntities) {
        this.messageOfTheDayEntities = messageOfTheDayEntities;
    }
}
