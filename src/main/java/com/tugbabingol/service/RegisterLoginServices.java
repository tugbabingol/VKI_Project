package com.tugbabingol.service;


import com.tugbabingol.dao.RegisterDao;
import com.tugbabingol.controller.RegisterController;
import com.tugbabingol.dto.RegisterDto;
import com.tugbabingol.dto.VKIDto;
import com.tugbabingol.roles.ERoles;
import java.util.Scanner;
import com.tugbabingol.files.FilePathData;

public class RegisterLoginServices {

    // Injection
    private RegisterController registerController = new RegisterController();
    private FilePathData filePathData = new FilePathData();
    VKIDto vkiDto = new VKIDto();

    // REGISTER
    private RegisterDto register() {
        Scanner klavye = new Scanner(System.in);
        RegisterDto registerDto = new RegisterDto();
        String uName, uSurName,uEmailAddress, uPassword, rolles;
        Long remainingNumber;
        Boolean isPassive;
        System.out.println("\n###KAYIT OLMA SAYFASINA HOSGELDİNİZ");
        System.out.println("Adınızı giriniz");
        uSurName = klavye.nextLine();
        System.out.println("Soyadınızı giriniz");
        uName = klavye.nextLine();
        System.out.println("Emailinizi giriniz");
        uEmailAddress = klavye.nextLine();
        System.out.println("Sifrenizi giriniz");
        uPassword = klavye.nextLine();
        // default rol user olacak
        rolles = ERoles.USER.getValue().toString();
        remainingNumber = 5L;
        isPassive = true;
        ///////////////////
        registerDto.setuName(uName);
        registerDto.setuName(uSurName);
        registerDto.setuEmailAddress(uEmailAddress);
        registerDto.setuPassword(uPassword);
        registerDto.setRolles(rolles);
        registerDto.setRemainingNumber(remainingNumber);
        registerDto.setPassive(isPassive);
        // CREATE
        registerController.create(registerDto);
        filePathData.logFileWriter(uEmailAddress,uPassword);
        return registerDto;


    }

    // LOGIN
    public RegisterDto login() {
        Scanner klavye = new Scanner(System.in);
        RegisterDto registerDto = new RegisterDto();
        String uEmailAddress, uPassword;
        Long remaingNumber = 0L;
        System.out.println("\n###LOGIN SAYSASINA HOSGELDINIZ");
        System.out.println("Emailinizi giriniz");
        uEmailAddress = klavye.nextLine();
        System.out.println("Sifrenizi giriniz");
        uPassword = klavye.nextLine();

        // Email Find
        RegisterDto registerEmailFind = registerController.findByEmail(uEmailAddress);
        // Kullanıcı yoksa kayıt olsun ve login sayfasına ageri donsun.
        if (registerEmailFind == null) {
            // eğer kullanıcı yoksa kayıt olsun
            register();
            // Kayıt olduktan sonra Login sayfasına geri dön
            login();
        } else {
            // Eğer Kullanıcı Pasifse giris yapmasın
            if (registerEmailFind.getPassive() == false) {
                System.out.println("Üyeliğiniz Pasif edilmiştir sisteme giriş yapamazsınız");
                System.out.println("Lütfen admin'e başvurunuz.");
                System.exit(0);
            }

            // Database kaydedilmis decode edilmis sifre karsilastirmak
            RegisterDao registerDao=new RegisterDao();
            String firstValue=uPassword;
            String rawPassword=registerDao.generatebCryptPasswordEncoder(firstValue);
            boolean result=registerDao.matchbCryptPassword(firstValue,registerEmailFind.getuPassword());

            // Eğer kullanıcı varsa sisteme giriş yapsın    uPassword.equals(registerEmailFind.getuPassword()
            if (uEmailAddress.equals(registerEmailFind.getuEmailAddress()) && registerDao.matchbCryptPassword(firstValue,registerEmailFind.getuPassword()) ) {
               homePage();
            } else {
                // Kullanıcının kalan hakkı
                remaingNumber = registerEmailFind.getRemainingNumber();
                remaingNumber--;
                registerEmailFind.setRemainingNumber(remaingNumber);
                System.out.println("Kalan Hakkınız: " + registerEmailFind.getRemainingNumber());
                System.out.println("Sifreniz veya Emailiniz yanlış girdiniz");
                // Kalan Hak Database Eksilt
                registerController.updateRemaing(remaingNumber, registerEmailFind);
                //File loglama yapsın

                filePathData.logFileWriter(uEmailAddress,uPassword);

                // Sisteme giriş hakkım kalmazsa
                if (remaingNumber == 0) {
                    System.out.println("Giriş hakkınız kalmadı Hesanız Bloke oldu");
                    System.out.println("Admine Başvuru yapınız");
                    System.exit(0);
                } else if (remaingNumber >= 1) {
                    login();
                }
            } //end else
        }
        return registerDto;
    }

    ////////////////////////////////////////////////////////
    ///ANA SAYFA
    private void homePage(){
        Scanner klavye = new Scanner(System.in);
        VKIServices vkiServices = new VKIServices();
        while (true){
            System.out.println("\n ANA SAYFAYA HOSGELDINIZ");
            System.out.println("Lütfen Seçiminizi Yapınız");
            System.out.println("1-) Vucüt Kitle İndeksi Hesaplama \n2-) Admin İşlemleri \n");
            System.out.println("3-)Çıkış");
            int chooise = klavye.nextInt();
            switch (chooise) {
                case 1:
                    System.out.println("Vucüt Kitle İndeksi Hesaplama");
                    VKIDto vkiResult = vkiServices.hesapla();
                    vkiServices.vki_siniflandirma(vkiResult);
                    break;
                case 2:
                   adminLogin();
                    break;
                case 3:
                    logout();
                    break;
                default:
                    System.out.println("Lütfen belirtilen aralıkta sayı giriniz");
                    break;
            }//end switch
        }//end while
    }

    //////////////////////////////////////////////////////////////////////////////////
    //admin giriş ekranı
    private void adminLogin(){
        Scanner klavye = new Scanner(System.in);
        RegisterDto registerDto = new RegisterDto();
        RegisterDao registerDao = new RegisterDao();
        String uEmailAddress, uPassword;

        Long remaingNumber = 0L;
        System.out.println("\n###ADMİN SAYFASINA HOSGELDINIZ");
        System.out.println("Emailinizi giriniz");
        uEmailAddress = klavye.nextLine();
        System.out.println("Sifrenizi giriniz");
        uPassword = klavye.nextLine();
        // Email Find
        RegisterDto registerEmailFind = registerController.findByEmail(uEmailAddress);
        String firstValue=uPassword;
        //admin ise giriş yapsın
        if (uEmailAddress.equals(registerEmailFind.getuEmailAddress()) && registerDao.matchbCryptPassword(firstValue,registerEmailFind.getuPassword()) &&registerEmailFind.getRolles().equals(ERoles.ADMIN.getValue())){
            adminProcess(registerEmailFind);
        }else {//değilse yetkisi olmadığını söyleyip anasayfaya yönlendirsin
            System.out.println("Rolünüz: " + registerEmailFind.getRolles() + " Yetkiniz yoktur");
            homePage();
        }
    }
    ///////////////////////////////////////////////////////////////////////////////////////
    //Admin Process
    private void adminProcess(RegisterDto registerDto) {
        Scanner klavye = new Scanner(System.in);
        while (true) {
            System.out.println("\nADMIN SAYFASINA HOSGELDINIZ");
            System.out.println("Lütfen Seçiminizi Yapınız");
            System.out.println("0-) Ana sayfa\n1-) Üye Listele\n2-) Üye Ekle\n3-) Üye Bul(ID)\n4-) Üye Bul (Email)");
            System.out.println("5-) Üye Güncelle\n6-) Üye Sil\n7-) Giriş Logları\n8-) Rolünüz");
            System.out.println("9-) Dosya Ekle\n10-) Dosya Listele\n11-) Dosya Sil");
            System.out.println("12-) Dosya Bilgileri\n");
            int chooise = klavye.nextInt();
            switch (chooise) {
                case 0:
                    System.out.println("Home Page");
                    homePage();
                    break;
                case 1:
                    System.out.println("Listeleme");
                    memberList();
                    break;
                case 2:
                    if (registerDto.getRolles().equals(ERoles.ADMIN.getValue())) {
                        System.out.println("Oluşturma");
                        RegisterDto registerDtoCreate = memberCreate();
                        System.out.println(registerDtoCreate);
                    } else {
                        System.out.println("Rolünüz: " + registerDto.getRolles() + " Yetkiniz yoktur");
                        //throw new HamitMizrak0Exception("Yetkiniz Yoktur");
                    }
                    break;
                case 3:
                    if (registerDto.getRolles().equals(ERoles.ADMIN.getValue()) || registerDto.getRolles().equals(ERoles.WRITER.getValue())) {
                        memberList();
                        System.out.println("ID'e göre Bulma");
                        RegisterDto registerDtoFindId = memberFindById();
                        /*if(registerDto.getId()==registerDtoFindId.getId()){
                            System.out.println(registerController.findById(registerDto.getId()));
                        }
                        else{
                            System.out.println(registerDtoFindId);
                        }*/
                        System.out.println(registerDtoFindId);
                    } else {
                        System.out.println("Rolünüz: " + registerDto.getRolles() + " Yetkiniz yoktur");
                        //throw new HamitMizrak0Exception("Yetkiniz Yoktur");
                    }
                    break;
                case 4:
                    if (registerDto.getRolles().equals(ERoles.ADMIN.getValue()) || registerDto.getRolles().equals(ERoles.WRITER.getValue())) {
                        memberList();
                        System.out.println("Email'e göre bulma");
                        RegisterDto registerDtoFindEmail = memberfindEmail();
                        System.out.println(registerDtoFindEmail);
                    } else {
                        System.out.println("Rolünüz: " + registerDto.getRolles() + " Yetkiniz yoktur");
                        //throw new HamitMizrak0Exception("Yetkiniz Yoktur");
                    }
                    break;
                case 5:
                    if (registerDto.getRolles().equals(ERoles.ADMIN.getValue())) {
                        memberList();
                        System.out.println("Güncelleme");
                        RegisterDto registerDtoUpdate = memberUpdate();
                        System.out.println(registerDtoUpdate);
                    } else {
                        System.out.println("Rolünüz: " + registerDto.getRolles() + " Yetkiniz yoktur");
                        //throw new HamitMizrak0Exception("Yetkiniz Yoktur");
                    }
                    break;
                case 6:
                    if (registerDto.getRolles().equals(ERoles.ADMIN.getValue())) {
                        memberList();
                        System.out.println("Silme");
                        RegisterDto registerDtoDelete = memberDelete();
                        System.out.println(registerDtoDelete);
                    } else {
                        System.out.println("Rolünüz: " + registerDto.getRolles() + " Yetkiniz yoktur");
                        //throw new HamitMizrak0Exception("Yetkiniz Yoktur");
                    }
                    break;
                case 7:
                    logFile();
                    break;
                case 8:
                    System.out.println("Rolünüz: " + userRoles(registerDto.getRolles()));
                    break;
                case 9:
                    System.out.println("Dosya Ekleme");
                    if (registerDto.getRolles().equals(ERoles.ADMIN.getValue()) || registerDto.getRolles().equals(ERoles.WRITER.getValue())) {
                        specialFileCreateData();
                    } else {
                        System.out.println("Rolünüz: " + registerDto.getRolles() + " Yetkiniz yoktur");
                        //throw new HamitMizrak0Exception("Yetkiniz Yoktur");
                    }
                    break;
                case 10:
                    System.out.println("Dosya Listeleme");
                    if (registerDto.getRolles().equals(ERoles.ADMIN.getValue()) || registerDto.getRolles().equals(ERoles.WRITER.getValue())) {
                        fileListData();
                    } else {
                        System.out.println("Rolünüz: " + registerDto.getRolles() + " Yetkiniz yoktur");
                        //throw new HamitMizrak0Exception("Yetkiniz Yoktur");
                    }
                    break;
                case 11:
                    System.out.println("Dosya Silme");
                    if (registerDto.getRolles().equals(ERoles.ADMIN.getValue()) ) {
                        fileDeleteData();
                    } else {
                        System.out.println("Rolünüz: " + registerDto.getRolles() + " Yetkiniz yoktur");
                        //throw new HamitMizrak0Exception("Yetkiniz Yoktur");
                    }
                    break;
                case 12:
                    System.out.println("Dosya Bilgileri");
                    if (registerDto.getRolles().equals(ERoles.ADMIN.getValue()) || registerDto.getRolles().equals(ERoles.WRITER.getValue())) {
                        fileInformation();
                    } else {
                        System.out.println("Rolünüz: " + registerDto.getRolles() + " Yetkiniz yoktur");
                        //throw new HamitMizrak0Exception("Yetkiniz Yoktur");
                    }
                    break;

                default:
                    System.out.println("Lütfen belirtilen aralıkta sayı giriniz");
                    break;
            } //end switch
        } //end while
    } //end method adminProcess

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // METHOD
    // just member login
    private void specialHomePage() {
        System.out.println("Sadece Üyeler Bu sayfayı görebilir.");
    }

    // CRUD
    // USER LIST
    private void memberList() {
        registerController.list().forEach(System.out::println);
    }

    // USER CREATE
    private RegisterDto memberCreate() {
        return register();
    }

    // USER Find Id
    private RegisterDto memberFindById() {
        System.out.println("Lütfen Bulmak istediğiniz ID giriniz");
        return registerController.findById(new Scanner(System.in).nextLong());
    }

    // USER Find Email
    private RegisterDto memberfindEmail() {
        System.out.println("Lütfen Bulmak istediğiniz email giriniz");
        return registerController.findByEmail(new Scanner(System.in).nextLine());
    }

    // USER Update
    private RegisterDto memberUpdate() {
        Scanner klavye = new Scanner(System.in);
        RegisterDto registerDto = new RegisterDto();
        String uName,uSurname, uEmailAddress, uPassword, rolles;
        Long remainingNumber, id;
        Boolean isPassive;
        System.out.println("Güncellemek istediğiniz ID  giriniz");
        id = klavye.nextLong();
        // NOT: Scanner'da tam sayıdan sonra String Gelirse bir alt satıra geçiyor
        // bunu engellemenin yolu klavye.nextLine()
        klavye.nextLine();

        System.out.println("Güncellemek istediğiniz adınızı giriniz");
        uName = klavye.nextLine();
        System.out.println("Güncellemek istediğiniz adınızı giriniz");
        uSurname = klavye.nextLine();
        System.out.println("Güncellemek istediğiniz Emailinizi giriniz");
        uEmailAddress = klavye.nextLine();
        System.out.println("Güncellemek istediğiniz Sifrenizi giriniz");
        uPassword = klavye.nextLine();
        // default rol user olacak
        rolles = ERoles.USER.getValue().toString();
        System.out.println("Güncellemek istediğiniz hak sayısını giriniz");
        remainingNumber = klavye.nextLong();
        System.out.println("Güncellemek istediğiniz kullanıcı aktif/pasif");
        isPassive = true;
        ////////////////////////////////////////////////////////////////////
        registerDto.setId(id);
        registerDto.setuName(uName);
        registerDto.setuSurName(uName);
        registerDto.setuEmailAddress(uEmailAddress);
        registerDto.setuPassword(uPassword);
        registerDto.setRolles(rolles);
        registerDto.setRemainingNumber(remainingNumber);
        registerDto.setPassive(isPassive);
        return registerController.update(id, registerDto);
    }

    // USER Delete
    private RegisterDto memberDelete() {
        Scanner klavye = new Scanner(System.in);
        RegisterDto registerDto = new RegisterDto();
        Long id;
        System.out.println("Silmek istediğiniz ID'i giriniz");
        id = klavye.nextLong();
        registerDto.setId(id);
        return registerController.deleteById(registerDto);
    }

    // LOGLAMA
    private void logFile() {
        filePathData.logFileReader();
    }

    // ROLES
    private String userRoles(String roles) {
        return roles;
    }

    // Logout
    private void logout() {
        //JOptionPane.
        System.out.println("Sistemden Çıkmak mı istiyor sunuz ? E/H");
        Scanner klavye = new Scanner(System.in);
        char result = klavye.nextLine().toLowerCase().charAt(0);
        if (result == 'e') {
            System.out.println("Sistemden Çıkış yapılıyor iyi günler dileriz.");
            System.exit(0);
        } else {
            System.out.println("Sistemden Çıkış yapılmadı");
        }
    } //end logout()

    ///anasayfaya gitmek
    private void toHomePage(){
        System.out.println("Anasayfaya dönmek mi istiyorsunuz ? E/H");
        Scanner klavye = new Scanner(System.in);
        char result = klavye.nextLine().toLowerCase().charAt(0);
        if (result == 'e') {
            homePage();
        } else {
            System.out.println("Anasayfaya gidilmedi");
        }
    }
    // CREATE FILE
    private void specialFileCreateData() {
        Scanner klavye = new Scanner(System.in);
        System.out.println("Oluşturmak istediğiniz dosya adını giriniz");
        String fileName = klavye.nextLine();
        filePathData.specialFileCreate(fileName);
    }

    // File List , Information
    private void fileListData() {
        filePathData.fileList();
    }

    // File Delete
    private void fileDeleteData() {
        filePathData.fileIsDelete();
    }

    // File Information
    private void fileInformation() {
        filePathData.fileProperties();
    }
} //end class

