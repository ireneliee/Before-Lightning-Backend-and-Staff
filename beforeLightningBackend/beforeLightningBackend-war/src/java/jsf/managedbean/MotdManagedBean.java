/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.MessageOfTheDayEntitySessionBeanLocal;
import entity.EmployeeEntity;
import entity.MessageOfTheDayEntity;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import util.exception.EmployeeEntityNotFoundException;
import util.exception.InputDataValidationException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author kaiyu
 */
@Named(value = "motdManagedBean")
@ViewScoped
public class MotdManagedBean implements Serializable {

	/**
	 * Creates a new instance of MotdManagedBean
	 */
	public MotdManagedBean() {
	}

	@EJB
	private MessageOfTheDayEntitySessionBeanLocal messageOfTheDayEntitySessionBeanLocal;
	
	

	private List<MessageOfTheDayEntity> messageOfTheDayEntities;

	private MessageOfTheDayEntity newMessageOfTheDay;
	private String messageTitle;
	private String messageBody;

	private EmployeeEntity currentEmployee;

	@PostConstruct
	public void postConstruct() {
		messageOfTheDayEntities = messageOfTheDayEntitySessionBeanLocal.retrieveAllMessagesOfTheDay();
		messageTitle = "";
		messageBody = "";
	}

	public MessageOfTheDayEntity getNewMessageOfTheDay() {
		return newMessageOfTheDay;
	}

	public void setNewMessageOfTheDay(MessageOfTheDayEntity newMessageOfTheDay) {
		this.newMessageOfTheDay = newMessageOfTheDay;
	}

	public List<MessageOfTheDayEntity> getMessageOfTheDayEntities() {
		return messageOfTheDayEntities;
	}

	public void createMessageOfTheDay(ActionEvent event) {
		try {
			EmployeeEntity currentEmployee = (EmployeeEntity) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentEmployeeEntity");
			MessageOfTheDayEntity temp = new MessageOfTheDayEntity(messageTitle, messageBody, Calendar.getInstance().getTime());
			newMessageOfTheDay = messageOfTheDayEntitySessionBeanLocal.createNewMessageOfTheDay(temp, currentEmployee);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Successfully created Message of the Day " + newMessageOfTheDay.getMotdId(), null));
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Employee deleted successfully", null));

		} catch (InputDataValidationException ex) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Input Validation Error!", null));
		} catch (EmployeeEntityNotFoundException ex) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Employee not found Error!", null));
		} catch (UnknownPersistenceException ex) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unknown Persistence Exception Error!", null));

		}
	}

	public EmployeeEntity getCurrentEmployee() {
		return currentEmployee;
	}

	public void setCurrentEmployee(EmployeeEntity currentEmployee) {
		this.currentEmployee = currentEmployee;
	}

	public String getMessageTitle() {
		return messageTitle;
	}

	public void setMessageTitle(String messageTitle) {
		this.messageTitle = messageTitle;
	}

	public String getMessageBody() {
		return messageBody;
	}

	public void setMessageBody(String messageBody) {
		this.messageBody = messageBody;
	}

	public String formatMotdHeader(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-Mm-dd");
		return format.format(date);
	}

	public void setMessageOfTheDayEntities(List<MessageOfTheDayEntity> messageOfTheDayEntities) {
		this.messageOfTheDayEntities = messageOfTheDayEntities;
	}

	public MessageOfTheDayEntitySessionBeanLocal getMessageOfTheDayEntitySessionBeanLocal() {
		return messageOfTheDayEntitySessionBeanLocal;
	}

	public void setMessageOfTheDayEntitySessionBeanLocal(MessageOfTheDayEntitySessionBeanLocal messageOfTheDayEntitySessionBeanLocal) {
		this.messageOfTheDayEntitySessionBeanLocal = messageOfTheDayEntitySessionBeanLocal;
	}

}
