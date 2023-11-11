package com.tugbabingol.dto;

import java.io.Serializable;
import java.util.Date;

// BaseDto
abstract public class BaseDto implements Serializable {

    // Serileştirme
    public static final long serialVersionUID = 1L;

    // Variable
    private Long id;
    private Date systemCreatedDate;

    // constructor (Parametresiz)
    public BaseDto() {
        System.out.println("HashCode " + BaseDto.class.hashCode());
        this.systemCreatedDate = new Date(System.currentTimeMillis());
    }

    // constructor (Parametreli)
    public BaseDto(Long id, Date systemCreatedDate) {
        this.id = id;
        this.systemCreatedDate = systemCreatedDate;
    }

    // toString
    @Override
    public String toString() {
        return "BaseDto{" +
                "id=" + id +
                ", systemCreatedDate=" + systemCreatedDate +
                '}';
    }//Bu çıktı, nesnenin id ve systemCreatedDate özelliklerini
    // içeren okunabilir bir formu temsil eder ve genellikle debugging veya loglama amaçları için kullanılır.

    //Metotlar
    //şu anki tarih ve saat bilgisi
    public String nowDate() {
        return String.valueOf(this.systemCreatedDate);
    }

    // Gövdesiz metot
    abstract public String nowDateAbstract();

    // Getter And Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getSystemCreatedDate() {
        return systemCreatedDate;
    }

    public void setSystemCreatedDate(Date systemCreatedDate) {
        this.systemCreatedDate = systemCreatedDate;
    }
} //end class BaseDto