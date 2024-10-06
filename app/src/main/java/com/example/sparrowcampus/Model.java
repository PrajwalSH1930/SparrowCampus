package com.example.sparrowcampus;

public class Model {
    String address, des, email,exp, fullName, purl, qual, phone, special; // Change these to Long if they are stored as Long in Firebase
    boolean zAvail;

    public Model() {
        // Default constructor required for calls to DataSnapshot.getValue(Model.class)
    }

    public boolean iszAvail() {
        return zAvail;
    }

    public void setzAvail(boolean zAvail) {
        this.zAvail = zAvail;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSpecial() {
        return special;
    }

    public void setSpecial(String special) {
        this.special = special;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getQual() {
        return qual;
    }

    public void setQual(String qual) {
        this.qual = qual;
    }

    public String getPurl() {
        return purl;
    }

    public void setPurl(String purl) {
        this.purl = purl;
    }

    public Model(String address,String des, String email, String exp, String fullName, String phone, String purl, String qual, String special, boolean zAvail) {
        this.address=address;
        this.des = des;
        this.email = email;
        this.exp = exp;
        this.fullName = fullName;
        this.phone = phone;
        this.purl = purl;
        this.qual = qual;
        this.special=special;
        this.zAvail=zAvail;
    }
}