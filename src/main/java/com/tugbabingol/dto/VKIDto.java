package com.tugbabingol.dto;

import java.io.Serializable;

public class VKIDto extends BaseDto implements Serializable {
    @Override
    public String nowDateAbstract() {
        return null;
    }

    public static final Long serialVersionUID=1L;

    private String uName;
    private String uSurname;
    private Double uWeight;
    private Double uHeight;
    private Double VKI_value;

    //parametresiz constructor
    public VKIDto(){

    }

    @Override
    public String toString() {
        return "VKIDto{" + "uName='" + uName+ '\'' +
                ", uSurname='" + uSurname+ '\'' +
                ", uWeight='" + uWeight+ '\'' +
                ", uHeight='" + uHeight+ '\'' +
                ", VKI_value='" + VKI_value+ '\'' +
                "} "+ super.toString();
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName.trim();
    }

    public String getuSurname() {
        return uSurname;
    }

    public void setuSurname(String uSurname) {
        this.uSurname = uSurname.trim();
    }

    public Double getuWeight() {
        return uWeight;
    }

    public void setuWeight(Double uWeight) {
        this.uWeight = uWeight;
    }

    public Double getuHeight() {
        return uHeight;
    }

    public void setuHeight(Double uHeight) {
        this.uHeight = uHeight;
    }

    public Double getVKI_value() {
        return VKI_value;
    }

    public void setVKI_value(Double VKI_value) {
        this.VKI_value = VKI_value;
    }
}
