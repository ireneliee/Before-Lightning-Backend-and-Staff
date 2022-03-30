package ws.datamodel;

import entity.AddressEntity;

public class CreateNewMemberReq {

    private AddressEntity address;
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String email;
    private String contact;
    private String imageLink;

    public CreateNewMemberReq() {
    }

    public CreateNewMemberReq(AddressEntity address, String username, String password, String firstname, String lastname, String email, String contact, String imageLink) {
        this.address = address;
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.contact = contact;
        this.imageLink = imageLink;
    }

    public AddressEntity getAddress() {
        return address;
    }

    public void setAddress(AddressEntity address) {
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;

    }
}
