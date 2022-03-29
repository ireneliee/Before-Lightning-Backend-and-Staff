package ws.datamodel;

import entity.AddressEntity;
import entity.MemberEntity;

public class CreateNewMemberReq {

    private AddressEntity address;
    private MemberEntity member;

    public CreateNewMemberReq() {
    }

    public CreateNewMemberReq(MemberEntity member, AddressEntity address) {
        this.member = member;
        this.address = address;
    }

    public AddressEntity getAddress() {
        return address;
    }

    public void setAddress(AddressEntity address) {
        this.address = address;
    }

    public MemberEntity getMember() {
        return member;
    }

    public void setMember(MemberEntity member) {
        this.member = member;
    }

   
}
