package com.ffta.shops;

public class ReferCodes {
    public ReferCodes()
    {}

    public ReferCodes(String refer, String phone,String bname, String name) {
        Refer = refer;
        Phone = phone;
        Bname=bname;
        Name=name;
    }

    public String getRefer() {
        return Refer;
    }

    public void setRefer(String refer) {
        Refer = refer;
    }

    public String getPhone() {
        return Phone;
    }

    public String getBname() {
        return Bname;
    }

    public void setBname(String bname) {
        Bname = bname;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    String Refer,Phone,Bname,Name;

}
