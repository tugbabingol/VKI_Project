package com.tugbabingol.dto;

import java.io.Serializable;

// Register
public class RegisterDto extends BaseDto implements Serializable {

    // Serileştirme
    public static final Long serialVersionUID=1L;

    //Variable
    private String uName;
    private String uSurName;
    private String uEmailAddress;
    private String uPassword;
    private Long remainingNumber; // Kullanıcı kalan hak
    private Boolean isPassive;
    private String rolles;

    // constructor(parametresiz)
    public RegisterDto() {
    }

    // constructor(parametreli)
    public RegisterDto(String uName,String uSurName, String uEmailAddress, String uPassword, Long remainingNumber, Boolean isPassive, String rolles) {
        this.uName = uName;
        this.uSurName = uSurName;
        this.uEmailAddress = uEmailAddress;
        this.uPassword = uPassword;
        this.remainingNumber = remainingNumber;
        this.isPassive = isPassive;
        this.rolles = rolles;
    }

    //toString
    @Override
    public String toString() {
        return "RegisterDto{" +
                "uName='" + uName + '\'' +
                ", uSurName='" + uSurName + '\'' +
                ", uEmailAddress='" + uEmailAddress + '\'' +
                ", uPassword='" + uPassword + '\'' +
                ", remainingNumber=" + remainingNumber +
                ", isPassive=" + isPassive +
                ", rolles='" + rolles + '\'' +
                "} " + super.toString();
    }

    @Override
    public String nowDateAbstract() {
        return null;
    }

    //GETTER AND SETTER
    public String getuName() {
        return uName;
    }

    // trim => Eğer Kullanıcının kelimelrde başında ve sonunda boşluklar varsa bunu engelememiz gerekiyor.
    public void setuName(String uName) {
        this.uName = uName.trim();
    }

    public String getuSurName() {
        return uSurName;
    }

    public void setuSurName(String uSurName) {
        this.uSurName = uSurName;
    }

    public String getuEmailAddress() {
        return uEmailAddress;
    }

    public void setuEmailAddress(String uEmailAddress) {
        this.uEmailAddress = uEmailAddress.trim();
    }

    public String getuPassword() {
        return uPassword;
    }

    public void setuPassword(String uPassword) {
        this.uPassword = uPassword.trim();
    }

    public Long getRemainingNumber() {
        return remainingNumber;
    }

    public void setRemainingNumber(Long remainingNumber) {
        this.remainingNumber = remainingNumber;
    }

    public Boolean getPassive() {
        return isPassive;
    }

    public void setPassive(Boolean passive) {
        isPassive = passive;
    }

    public String getRolles() {
        return rolles;
    }

    public void setRolles(String rolles) {
        this.rolles = rolles;
    }

} //end class RegisterDto