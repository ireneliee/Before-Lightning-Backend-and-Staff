/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import util.enumeration.DeliveryStatusEnum;

/**
 *
 * @author irene
 */
@Entity
public class DeliverySlotEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deliverySlotId;

    @Column(nullable = false)
    private DeliveryStatusEnum deliveryStatus;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    private AddressEntity address;

    private LocalDateTime requestedTimeOfDelivery;
    private LocalDateTime timeOfArrival;

    public Long getDeliverySlotId() {
        return deliverySlotId;
    }

    public void setDeliverySlotId(Long deliverySlotId) {
        this.deliverySlotId = deliverySlotId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (deliverySlotId != null ? deliverySlotId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the deliverySlotId fields are not set
        if (!(object instanceof DeliverySlotEntity)) {
            return false;
        }
        DeliverySlotEntity other = (DeliverySlotEntity) object;
        if ((this.deliverySlotId == null && other.deliverySlotId != null) || (this.deliverySlotId != null && !this.deliverySlotId.equals(other.deliverySlotId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.DeliverySlotEntity[ id=" + deliverySlotId + " ]";
    }

    public DeliveryStatusEnum getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(DeliveryStatusEnum deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public LocalDateTime getRequestedTimeOfDelivery() {
        return requestedTimeOfDelivery;
    }

    public void setRequestedTimeOfDelivery(LocalDateTime requestedTimeOfDelivery) {
        this.requestedTimeOfDelivery = requestedTimeOfDelivery;
    }

    public LocalDateTime getTimeOfArrival() {
        return timeOfArrival;
    }

    public void setTimeOfArrival(LocalDateTime timeOfArrival) {
        this.timeOfArrival = timeOfArrival;
    }

    /**
     * @return the address
     */
    public AddressEntity getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(AddressEntity address) {
        this.address = address;
    }

}
