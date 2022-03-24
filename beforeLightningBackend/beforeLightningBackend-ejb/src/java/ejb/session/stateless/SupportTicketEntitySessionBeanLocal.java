/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.SupportTicketEntity;
import java.util.List;
import javax.ejb.Local;
import util.exception.CreateNewSupportTicketEntityException;
import util.exception.DeleteSupportTicketEntityException;
import util.exception.InputDataValidationException;
import util.exception.SupportTicketEntityNotFoundException;
import util.exception.UpdateSupportTicketEntityException;

/**
 *
 * @author srinivas
 */
@Local
public interface SupportTicketEntitySessionBeanLocal {

    public SupportTicketEntity retrieveSupportTicketBySupportTicketId(Long supportTicketId) throws SupportTicketEntityNotFoundException;

    public SupportTicketEntity createNewSupportTicketEntity(SupportTicketEntity newSupportTicketEntity) throws InputDataValidationException, CreateNewSupportTicketEntityException;

    public List<SupportTicketEntity> retrieveAllSupportTickets();

    public void deleteSupportTicket(Long supportTicketId) throws SupportTicketEntityNotFoundException, DeleteSupportTicketEntityException;

    public void updateSupportTicketEntity(SupportTicketEntity updatedSupportTicket) throws UpdateSupportTicketEntityException;
    
}
