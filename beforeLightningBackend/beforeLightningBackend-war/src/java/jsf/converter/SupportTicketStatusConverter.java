/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.converter;

/**
 *
 * @author irene
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.faces.convert.EnumConverter;
import javax.faces.convert.FacesConverter;
import util.enumeration.SupportTicketStatusEnum;

@FacesConverter(value="accessRightEnumConverter", forClass = SupportTicketStatusEnum.class)
public class SupportTicketStatusConverter extends EnumConverter
{

    public SupportTicketStatusConverter()
    {
        super(SupportTicketStatusEnum.class);
    }
}
