/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.datamodel;

/**
 *
 * @author srinivas
 */
public class DeleteCreditCardReq {
    private String creditCardId;
    private String memberId;

    public DeleteCreditCardReq() {
    }

    /**
     * @return the creditCardId
     */
    public String getCreditCardId() {
        return creditCardId;
    }

    /**
     * @param creditCardId the creditCardId to set
     */
    public void setCreditCardId(String creditCardId) {
        this.creditCardId = creditCardId;
    }

    /**
     * @return the memberId
     */
    public String getMemberId() {
        return memberId;
    }

    /**
     * @param memberId the memberId to set
     */
    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }
    
    
    
}
