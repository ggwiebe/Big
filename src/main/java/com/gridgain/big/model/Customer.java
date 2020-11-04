package com.gridgain.big.model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Customer definition.
 * 
 **/
public class Customer implements Serializable {

    private static final long serialVersionUID = 0L;

    private String uuid;
    private Timestamp updateDT;
    private String name;
    private String description;
    private String customerType;
    private String password;

    /** Empty constructor. **/
    public Customer() {
        // No-op.
    }

    /** Full constructor. **/
    public Customer(
        String uuid,
        Timestamp updateDT,
        String name,
        String description,
        String customerType,
        String password) {
        this.uuid = uuid;
        this.updateDT = updateDT;
        this.name = name;
        this.description = description;
        this.customerType = customerType;
        this.password = password;
    }

    /**
     * Gets uuid
     * 
     * @return Value for uuid.
     **/
    public String getUuid() {
        return uuid;
    }

    /**
     * Sets uuid
     * 
     * @param uuid New value for uuid.
     **/
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     * Gets updateDT
     * 
     * @return Value for updateDT.
     **/
    public Timestamp getUpdateDT() {
        return updateDT;
    }

    /**
     * Sets updateDT
     * 
     * @param updateDT New value for updateDT.
     **/
    public void setUpdateDT(Timestamp updateDT) {
        this.updateDT = updateDT;
    }

    /**
     * Gets name
     * 
     * @return Value for name.
     **/
    public String getName() {
        return name;
    }

    /**
     * Sets name
     * 
     * @param name New value for name.
     **/
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets description
     * 
     * @return Value for description.
     **/
    public String getDescription() {
        return description;
    }

    /**
     * Sets description
     * 
     * @param description New value for description.
     **/
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets customerType
     * 
     * @return Value for customerType.
     **/
    public String getCustomerType() {
        return customerType;
    }

    /**
     * Sets customerType
     * 
     * @param customerType New value for customerType.
     **/
    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    /**
     * Gets password
     * 
     * @return Value for password.
     **/
    public String getPassword() {
        return password;
    }

    /**
     * Sets password
     * 
     * @param password New value for password.
     **/
    public void setPassword(String password) {
        this.password = password;
    }

    /** {@inheritDoc} **/
    @Override public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Customer))
            return false;
        
        Customer that = (Customer)o;
        if (uuid != null ? !uuid.equals(that.uuid) : that.uuid != null)
            return false;
        if (updateDT != null ? !updateDT.equals(that.updateDT) : that.updateDT != null)
            return false;
        if (name != null ? !name.equals(that.name) : that.name != null)
            return false;
        if (description != null ? !description.equals(that.description) : that.description != null)
            return false;
        if (customerType != null ? !customerType.equals(that.customerType) : that.customerType != null)
            return false;
        if (password != null ? !password.equals(that.password) : that.password != null)
            return false;
        return true;
    }

    /** {@inheritDoc} **/
    @Override public int hashCode() {
        int res = uuid != null ? uuid.hashCode() : 0;
        res = 31 * res + (updateDT != null ? updateDT.hashCode() : 0);
        res = 31 * res + (name != null ? name.hashCode() : 0);
        res = 31 * res + (description != null ? description.hashCode() : 0);
        res = 31 * res + (customerType != null ? customerType.hashCode() : 0);
        res = 31 * res + (password != null ? password.hashCode() : 0);

        return res;
    }

    /** {@inheritDoc} **/
    @Override public String toString() {
        return "Customer [" + 
            "uuid=" + uuid + ", " + 
            "updateDT=" + updateDT + ", " + 
            "name=" + name + ", " + 
            "description=" + description + ", " + 
            "customerType=" + customerType + ", " + 
            "password=" + password +
        "]";
    }
}