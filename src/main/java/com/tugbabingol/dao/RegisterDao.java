package com.tugbabingol.dao;

import com.tugbabingol.dto.RegisterDto;
import com.tugbabingol.roles.ERoles;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

/*
Transaction:  Create, Delete, Update
connection.setAutoCommit(false)  => Default: true
connection.commit();   ==> Başarılı
connection.rollback(); ==> Başarısız
 */

// RegisterDao
public class RegisterDao implements IDaoGenerics<RegisterDto>, Serializable {

    // SPEED DATA
    @Override
    public String speedData(Long id) {
        for (int i = 1; i <= id; i++) {
            try (Connection connection = getInterfaceConnection()) {
                // Manipulation: executeUpdate() [create, delete, update]
                // Sorgularda  : executeQuery [list, find]
                // Transaction:
                connection.setAutoCommit(false); //default:true
                String sql = "INSERT INTO `register` (`name`,`surname`,`email_address`,`password`,`roles`,`remaining_number`,`is_passive`) \n" +
                        " VALUES (?,?, ?, ?,?,?,?)";
                String rnd = UUID.randomUUID().toString();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, "name() " + rnd);
                preparedStatement.setString(2, "surname() " + rnd);
                preparedStatement.setString(3, "emailAddress()" + rnd);
                preparedStatement.setString(4, "password()" + rnd);
                preparedStatement.setString(5, ERoles.USER.getValue());
                preparedStatement.setInt(6, 5);
                preparedStatement.setBoolean(7, true);
                // executeUpdate: create, delete, update için kullanılır.
                int rowsEffected = preparedStatement.executeUpdate();
                // eğer ekleme yapılmamışsa -1 değerini döner
                if (rowsEffected > 0) {
                    System.out.println(RegisterDao.class + " Başarılı Ekleme Tamamdır");
                    connection.commit(); // başarılı
                } else {
                    System.err.println(RegisterDao.class + " !!! Başarısız Ekleme Tamamdır");
                    connection.rollback(); // başarısız
                }
            } catch (SQLException sql) {
                sql.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println(id + "tane veri eklendi");
        return id + "tane veri eklendi";
    }

    // ALL DELETE
    @Override
    public String allDelete() {
        try (Connection connection = getInterfaceConnection()) {
            // Manipulation: executeUpdate() [create, delete, update]
            // Sorgularda  : executeQuery [list, find]
            // Transaction:
            connection.setAutoCommit(false); //default:true
            String sql = "DELETE FROM `register`";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            // executeUpdate: create, delete, update için kullanılır.
            int rowsEffected = preparedStatement.executeUpdate();
            // eğer silme yapılmamışsa -1 değerini döner
            if (rowsEffected > 0) {
                System.out.println(RegisterDao.class + " Başarılı Bütün Veriler Silme Tamamdır");
                connection.commit(); // başarılı
            } else {
                System.err.println(RegisterDao.class + " !!! Başarısız Bütün Silme Tamamdır");
                connection.rollback(); // başarısız
            }
        } catch (SQLException sql) {
            sql.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list().size() + " tane veri silindi";
    }

    ////////////////////////////////////////////////////////

    // Sifreleme olustur (Encoder)
    public String generatebCryptPasswordEncoder(String value){
        // Sifrelemeyi olusturmak
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String rawPassword = bCryptPasswordEncoder.encode(value);
        return rawPassword;
    }

    // Sifre karsilastir (Match)
    public Boolean matchbCryptPassword(String fistValue, String rawPassword){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        boolean isMatch=bCryptPasswordEncoder.matches(fistValue,rawPassword);
        return isMatch;
    }

    public static void main(String[] args) {
        RegisterDao registerDao=new RegisterDao();
        String fistValue="123456";
        String rawPassword=registerDao.generatebCryptPasswordEncoder(fistValue);
        boolean result=registerDao.matchbCryptPassword(fistValue,rawPassword);
        System.out.println(result);
    }

    ////////////////////////////////////////////////////////
    // CREATE
    // INSERT INTO `register` (`name`,`email_address`,`password`,`roles`,`remaining_number`,`is_passive`)
    // VALUES ('computer', 'hamitmizrak@gmail.com', '123456','admin','5','1');
    @Override
    public RegisterDto create(RegisterDto registerDto) {
        try (Connection connection = getInterfaceConnection()) {
            // Manipulation: executeUpdate() [create, delete, update]
            // Sorgularda  : executeQuery [list, find]
            // Transaction:
            connection.setAutoCommit(false); //default:true
            String sql = "INSERT INTO `register` (`name`,`surname`,`email_address`,`password`,`roles`,`remaining_number`,`is_passive`) \n" +
                    " VALUES (?,?, ?, ?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, registerDto.getuName());
            preparedStatement.setString(2, registerDto.getuSurName());
            preparedStatement.setString(3, registerDto.getuEmailAddress());

            //registerDto.setuPassword(resultSet.getString("password"));
            preparedStatement.setString(4, generatebCryptPasswordEncoder(registerDto.getuPassword()));

            preparedStatement.setString(5, registerDto.getRolles());
            preparedStatement.setLong(6, registerDto.getRemainingNumber());
            preparedStatement.setBoolean(7, registerDto.getPassive());
            // executeUpdate: create, delete, update için kullanılır.
            int rowsEffected = preparedStatement.executeUpdate();
            // eğer ekleme yapılmamışsa -1 değerini döner
            if (rowsEffected > 0) {
                System.out.println(RegisterDao.class + " Başarılı Ekleme Tamamdır");
                connection.commit(); // başarılı
            } else {
                System.err.println(RegisterDao.class + " !!! Başarısız Ekleme Tamamdır");
                connection.rollback(); // başarısız
            }
            return registerDto; // eğer başarılı ise return registerDto
        } catch (SQLException sql) {
            sql.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // FIND ID
    @Override
    public RegisterDto findById(Long id) {
        RegisterDto registerDto = null;
        try (Connection connection = getInterfaceConnection()) {
            // Dikkat: id Long olduğu için tırnak içinde yazmıyoruz  örneğin: id=1
            String sql = "SELECT * FROM register where id=" + id;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            // executeUpdate() [create, delete, update]
            // Sorgularda  : executeQuery [list, find]
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                // name, email_address, password, roles, remaining_number, is_passive
                registerDto = new RegisterDto();
                registerDto.setId(resultSet.getLong("id"));
                registerDto.setuName(resultSet.getString("name"));
                registerDto.setuSurName(resultSet.getString("surname"));
                registerDto.setuEmailAddress(resultSet.getString("email_address"));
                registerDto.setRolles(resultSet.getString("roles"));
                registerDto.setRemainingNumber(resultSet.getLong("remaining_number"));
                registerDto.setPassive(resultSet.getBoolean("is_passive"));
                registerDto.setSystemCreatedDate(resultSet.getDate("system_created_date"));
            }
            return registerDto; // eğer başarılı ise return registerDto
        } catch (SQLException sql) {
            sql.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // FIND EMAIL
    @Override
    public RegisterDto findByEmail(String email) {
        RegisterDto registerDto = null;
        try (Connection connection = getInterfaceConnection()) {
            // Dikkat: email_address String olduğu için tırnak içinde yazıyoruz örneğin: email="hamitmizrak@gmail.com"
            String sql = "SELECT * FROM register where email_address='" + email + "\'";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            // executeUpdate() [create, delete, update]
            // Sorgularda  : executeQuery [list, find]
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                // name, email_address, password, roles, remaining_number, is_passive
                registerDto = new RegisterDto();
                registerDto.setId(resultSet.getLong("id"));
                registerDto.setuName(resultSet.getString("name"));
                registerDto.setuSurName(resultSet.getString("surname"));
                registerDto.setuEmailAddress(resultSet.getString("email_address"));
                registerDto.setuPassword(resultSet.getString("password"));
                registerDto.setRolles(resultSet.getString("roles"));
                registerDto.setRemainingNumber(resultSet.getLong("remaining_number"));
                registerDto.setPassive(resultSet.getBoolean("is_passive"));
            }
            return registerDto; // eğer başarılı ise return registerDto
        } catch (SQLException sql) {
            sql.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    // LIST
    @Override
    public ArrayList<RegisterDto> list() {
        ArrayList<RegisterDto> list = new ArrayList<>();
        RegisterDto registerDto;
        try (Connection connection = getInterfaceConnection()) {
            // Dikkat: email_address String olduğu için tırnak içinde yazıyoruz örneğin: email="hamitmizrak@gmail.com"
            String sql = "SELECT * FROM libraries.register";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            // executeUpdate() [create, delete, update]
            // Sorgularda  : executeQuery [list, find]
            ResultSet resultSet = preparedStatement.  executeQuery();
            while (resultSet.next()) {
                // name, email_address, password, roles, remaining_number, is_passive
                registerDto = new RegisterDto();
                registerDto.setId(resultSet.getLong("id"));
                registerDto.setuName(resultSet.getString("name"));
                registerDto.setuSurName(resultSet.getString("surname"));
                registerDto.setuEmailAddress(resultSet.getString("email_address"));
                registerDto.setuPassword(resultSet.getString("password"));
                registerDto.setRolles(resultSet.getString("roles"));
                registerDto.setRemainingNumber(resultSet.getLong("remaining_number"));
                registerDto.setPassive(resultSet.getBoolean("is_passive"));
                registerDto.setSystemCreatedDate(resultSet.getDate("system_created_date"));
                list.add(registerDto);
            }
            return list; // eğer başarılı ise return registerDto
        } catch (SQLException sql) {
            sql.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // UPDATE
    // Transaction: Create, Delete, Update
    @Override
    public RegisterDto update(Long id, RegisterDto registerDto) {
        // Bu ID ile ilgili kullanıcı varmı ?
        RegisterDto find = findById(id);
        if (find != null) {
            try (Connection connection = getInterfaceConnection()) {
                // Manipulation: executeUpdate() [create, delete, update]
                // Sorgularda  : executeQuery [list, find]
                // Transaction:
                connection.setAutoCommit(false); //default:true
                String sql = "UPDATE `register` SET `name`=?,`surname`=?, `email_address`=?, `password`=?, `roles`=?, " +
                        "`remaining_number`=?, `is_passive`=?" +
                        " WHERE `id` =?;";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, registerDto.getuName());
                preparedStatement.setString(2, registerDto.getuSurName());
                preparedStatement.setString(3, registerDto.getuEmailAddress());
                preparedStatement.setString(4, registerDto.getuPassword());
                preparedStatement.setString(5, registerDto.getRolles());
                preparedStatement.setLong(6, registerDto.getRemainingNumber());
                preparedStatement.setBoolean(7, registerDto.getPassive());
                preparedStatement.setLong(8, registerDto.getId());
                // executeUpdate: create, delete, update için kullanılır.
                int rowsEffected = preparedStatement.executeUpdate();
                // eğer güncelle yapılmamışsa -1 değerini döner
                if (rowsEffected > 0) {
                    System.out.println(RegisterDao.class + " Başarılı Güncelleme Tamamdır");
                    connection.commit(); // başarılı
                } else {
                    System.err.println(RegisterDao.class + " !!! Başarısız Güncelleme Tamamdır");
                    connection.rollback(); // başarısız
                }
                return registerDto; // eğer başarılı ise return registerDto
            } catch (SQLException sql) {
                sql.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("Böyle Bir kullanıcı yoktur ve güncelleme yapılamaz...");
        }
        return null;
    }

    // UPDATE (REMAING NUMBER)
    @Override
    public RegisterDto updateRemaing(Long id, RegisterDto registerDto) {
        // Bu ID ile ilgili kullanıcı varmı ?
        RegisterDto find = findById(id);
        if (find != null) {
            try (Connection connection = getInterfaceConnection()) {
                // Manipulation: executeUpdate() [create, delete, update]
                // Sorgularda  : executeQuery [list, find]
                // Transaction:
                connection.setAutoCommit(false); //default:true
                String sql = "UPDATE `register` SET  `remaining_number`=?" +
                        " WHERE `id` =?;";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setLong(1, registerDto.getRemainingNumber());
                preparedStatement.setLong(2, registerDto.getId());
                // executeUpdate: create, delete, update için kullanılır.
                int rowsEffected = preparedStatement.executeUpdate();
                // eğer güncelle yapılmamışsa -1 değerini döner
                if (rowsEffected > 0) {
                    System.out.println(RegisterDao.class + " Başarılı Kalan Hak Güncelleme Tamamdır");
                    connection.commit(); // başarılı
                } else {
                    System.err.println(RegisterDao.class + " !!! Başarısız Kalan Hak Güncelleme Tamamdır");
                    connection.rollback(); // başarısız
                }
                return registerDto; // eğer başarılı ise return registerDto
            } catch (SQLException sql) {
                sql.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("Böyle bir kullanıcı yoktur");
        }
        return null;
    }

    // DELETE
    @Override
    public RegisterDto deleteById(RegisterDto registerDto) {
        // Bu ID ile ilgili kullanıcı varmı ?
        RegisterDto find = findById(registerDto.getId());
        if (find != null) {
            try (Connection connection = getInterfaceConnection()) {
                // Manipulation: executeUpdate() [create, delete, update]
                // Sorgularda  : executeQuery [list, find]
                // Transaction:
                connection.setAutoCommit(false); //default:true
                String sql = "DELETE FROM `register` WHERE `id` = ?;";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setLong(1, registerDto.getId());
                // executeUpdate: create, delete, update için kullanılır.
                int rowsEffected = preparedStatement.executeUpdate();
                // eğer silme yapılmamışsa -1 değerini döner
                if (rowsEffected > 0) {
                    System.out.println(RegisterDao.class + " Başarılı Silme Tamamdır");
                    connection.commit(); // başarılı
                } else {
                    System.err.println(RegisterDao.class + " !!! Başarısız Silme Tamamdır");
                    connection.rollback(); // başarısız
                }
                return registerDto; // eğer başarılı ise return registerDto
            } catch (SQLException sql) {
                sql.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("Böyle bir kullanıcı yoktur Silme yapılamaz");
        }
        return null;
    }

} //end class RegisterDao