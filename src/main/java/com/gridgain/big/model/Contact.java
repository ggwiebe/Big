package com.gridgain.big.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * Contact definition.
 * 
 **/
public class Contact implements Serializable {

    private static final long serialVersionUID = 0L;

    private Timestamp updateDT;
    private String fullName;
    private String street;
    private String city;
    private String state;
    private String zip;
    private String phoneNum;
    private String comment;

    /** Empty constructor. **/
    public Contact() {
        // No-op.
    }

    /** Full constructor. **/
    public Contact(
        Timestamp updateDT, 
        String fullName, 
        String street, 
        String city, 
        String state, 
        String zip, 
        String phoneNum,
        String comment) {
        this.updateDT = updateDT;
        this.fullName = fullName;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.phoneNum = phoneNum;
        this.comment = comment;
    }

    public Timestamp getUpdateDT() {
        return this.updateDT;
    }
    public void setUpdateDT(Timestamp updateDT) {
        this.updateDT = updateDT;
    }

    public String getFullName() {
        return this.fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getStreet() {
        return this.street;
    }
    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return this.city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return this.state;
    }
    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return this.zip;
    }
    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getPhoneNum() {
        return this.phoneNum;
    }
    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getComment() {
        return this.comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Contact)) {
            return false;
        }
        Contact contact = (Contact) o;
        return Objects.equals(updateDT, contact.updateDT) && 
               Objects.equals(fullName, contact.fullName) && 
               Objects.equals(street, contact.street) && 
               Objects.equals(city, contact.city) && 
               Objects.equals(state, contact.state) && 
               Objects.equals(zip, contact.zip) && 
               Objects.equals(phoneNum, contact.phoneNum) &&
               Objects.equals(comment, contact.comment);
    }

    /** {@inheritDoc} **/
    @Override public int hashCode() {
        int res = updateDT != null ? updateDT.hashCode() : 0;
        res = 31 * res + (fullName != null ? fullName.hashCode() : 0);
        res = 31 * res + (street != null ? street.hashCode() : 0);
        res = 31 * res + (city != null ? city.hashCode() : 0);
        res = 31 * res + (state != null ? state.hashCode() : 0);
        res = 31 * res + (zip != null ? zip.hashCode() : 0);
        res = 31 * res + (phoneNum != null ? phoneNum.hashCode() : 0);
        res = 31 * res + (comment != null ? comment.hashCode() : 0);

        return res;
    }

    @Override
    public String toString() {
        return "{" +
            " updateDT='" + getUpdateDT() + "'" +
            ", fullName='" + getFullName() + "'" +
            ", street='" + getStreet() + "'" +
            ", city='" + getCity() + "'" +
            ", state='" + getState() + "'" +
            ", zip='" + getZip() + "'" +
            ", phoneNum='" + getPhoneNum() + "'" +
            ", comment='" + getComment() + "'" +
            "}";
    }

}