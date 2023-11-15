package com.tugbabingol.dto;

import java.io.Serializable;
import java.util.Date;

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
    //parametreli constructor
    public VKIDto(Long id, Date systemCreatedDate, Double uWeight, Double uHeight, Double VKI_value) {
        super(id, systemCreatedDate);
        this.uWeight = uWeight;
        this.uHeight = uHeight;
        this.VKI_value = VKI_value;
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
}//end class VKIDto

