package com.tugbabingol.dto;

import java.io.Serializable;

public class VKIDto extends BaseDto implements Serializable {
    @Override
    public String nowDateAbstract() {
        return null;
    }

    public static final Long serialVersionUID=1L;

    private Double uWeight;
    private Double uHeight;
    private Double VKI_value;

    //parametresiz constructor
    public VKIDto(){

    }

    @Override
    public String toString() {
        return "VKIDto{" +
                "uWeight=" + uWeight +
                ", uHeight=" + uHeight +
                ", VKI_value=" + VKI_value +
                '}';
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
