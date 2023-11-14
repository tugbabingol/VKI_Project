package com.tugbabingol.service;

import com.tugbabingol.controller.VKIController;
import com.tugbabingol.dto.VKIDto;

import java.util.Scanner;

public class VKIServices {
    VKIController vkiController= new VKIController();
    // VKI HESAPLAMAK
    public VKIDto hesapla() {
        VKIDto vkiDto = new VKIDto();
        Scanner klavye = new Scanner(System.in);
        String uName, uSurname;
        Double uWeight, uHeight, vki_result;
        System.out.println("\n VKI HESAPLAMA ");
        System.out.println("\n LÜTFEN BİLGİLERİNİZİ GİRİNİZ");
        System.out.println("Kilonuzu giriniz. ");
        uWeight = Double.valueOf(klavye.nextLine());
        System.out.println("Boyunuzu giriniz. ");
        uHeight = Double.valueOf(klavye.nextLine());
        if (uWeight <= 0 || uHeight <= 0) {
            System.out.println("Lütfen geçerli bir kilo ya da boy miktarı giriniz!!");
            hesapla();
        } else {
            vki_result = uWeight / (uHeight * uHeight);
            vkiDto.setuWeight(uWeight);
            vkiDto.setuHeight(uHeight);
            vkiDto.setVKI_value(vki_result);
            // veritabanına kayıt edildi.
            vkiController.create(vkiDto);
        }
        return vkiDto;
    }

    // Sonuçlandırma
    protected static void vki_siniflandirma(VKIDto vkiDto) {
        Double sonuc = vkiDto.getVKI_value();
        if (sonuc < 18.5) {
            System.out.println(sonuc + " Kilonuz ideal kilonun altındadır.");
        } else if ((18.5 <= sonuc) && (sonuc <= 24.99)) {
            System.out.println(sonuc + " Kilonuz ideal kilodadır.");
        } else if ((25 <= sonuc) && (sonuc <= 29.99)) {
            System.out.println(sonuc + " Kilonuz ideal kilonun üstündedir.");
        } else {
            System.out.println(sonuc + " Kilonuz ideal kilonun üstündedir.");
        }
    }

    // VKI DENEME
    public static void main(String[] args) {
        VKIServices vkiServices = new VKIServices();
        VKIDto vkiResult = vkiServices.hesapla();
        vkiServices.vki_siniflandirma(vkiResult);
    }
}
