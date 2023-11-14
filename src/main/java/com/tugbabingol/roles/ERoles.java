package com.tugbabingol.roles;

public enum ERoles {
    ADMIN(1L,"admin"),USER(2L,"user"),WRITER(2L,"writer");
    private final Long key;
    private final String value;

    // Constructor (Parametreli constructor)
    // Constructor'a private verirsek;
    // Bu Enum'ın instance(new) oluşturulmasına izin vermiyor.

    private ERoles(Long key, String value) {
        this.key = key;
        this.value = value;
    }

    // GETTER
    public Long getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
//end ENUM EROLES
