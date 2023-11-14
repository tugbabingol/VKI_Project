package com.tugbabingol.dao;

import com.tugbabingol.dto.RegisterDto;
import com.tugbabingol.dto.VKIDto;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class VKIDao implements IDaoGenerics<VKIDto>, Serializable {
    @Override
    public String speedData(Long id) {
        return null;
    }


    ////////////////////////////////////////////////////////////////////////
    //ALL DELETE
    @Override
    public String allDelete() {
        try (Connection connection = getInterfaceConnection()) {
            // Manipulation: executeUpdate() [create, delete, update]
            // Sorgularda  : executeQuery [list, find]
            // Transaction:
            connection.setAutoCommit(false); //default:true
            String sql = "DELETE FROM `data`";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            // executeUpdate: create, delete, update için kullanılır.
            int rowsEffected = preparedStatement.executeUpdate();
            // eğer silme yapılmamışsa -1 değerini döner
            if (rowsEffected > 0) {
                System.out.println(VKIDao.class + " Başarılı Bütün Veriler Silme Tamamdır");
                connection.commit(); // başarılı
            } else {
                System.err.println(VKIDao.class + " !!! Başarısız Bütün Silme Tamamdır");
                connection.rollback(); // başarısız
            }
        } catch (SQLException sql) {
            sql.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list().size() + " tane veri silindi";
    }
    //////////////////////////////////////////////////////////////////////////////////////
    //CREATE
    @Override
    public VKIDto create(VKIDto vkiDto) {
        try (Connection connection=getInterfaceConnection()){
            connection.setAutoCommit(false);
            String sql = "INSERT INTO `data` (`weight`, `height`, `vki_value`) \n "+
                    "VALUES(?,?,?)";
            PreparedStatement preparedStatement =connection.prepareStatement(sql);
            preparedStatement.setDouble(1,vkiDto.getuWeight());
            preparedStatement.setDouble(2,vkiDto.getuHeight());
            preparedStatement.setDouble(3,vkiDto.getVKI_value());

            int rowsEffected = preparedStatement.executeUpdate();

            if (rowsEffected>0){
                System.out.println(RegisterDao.class + " Başarılı Ekleme Tamamdır");
                connection.commit(); // başarılı
            } else {
                System.err.println(RegisterDao.class + " !!! Başarısız Ekleme Tamamdır");
                connection.rollback(); // başarısız
            }
        }catch (SQLException sql) {
            sql.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    ///////////////////////////////////////////////////////////////////////////
    //FIND BY ID
    @Override
    public VKIDto findById(Long id) {
        VKIDto vkiDto=null;
        try (Connection connection=getInterfaceConnection()){
            String sql = "SELECT * FROM register where id=" + id;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                //name,surname, weight,height, vki_value
                vkiDto =new VKIDto();
                vkiDto.setId(resultSet.getLong("vki_id"));
                vkiDto.setuWeight(resultSet.getDouble("weight"));
                vkiDto.setuHeight(resultSet.getDouble("height"));
                vkiDto.setVKI_value(resultSet.getDouble("vki_value"));
                vkiDto.setSystemCreatedDate(resultSet.getDate("systemCreatedDate"));
            }
            return  vkiDto;
        } catch (SQLException sql) {
            sql.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public VKIDto findByEmail(String email) {
        return null;
    }



    @Override
    public ArrayList<VKIDto> list() {
        ArrayList<VKIDto> list = new ArrayList<>();
        VKIDto vkiDto;
        try (Connection connection = getInterfaceConnection()) {
            // Dikkat: email_address String olduğu için tırnak içinde yazıyoruz örneğin: email="hamitmizrak@gmail.com"
            String sql = "SELECT * FROM libraries.data";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            // executeUpdate() [create, delete, update]
            // Sorgularda  : executeQuery [list, find]
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                // nick_name, email_address, password, roles, remaining_number, is_passive
                vkiDto =new VKIDto();
                vkiDto.setId(resultSet.getLong("vki_id"));
                vkiDto.setuWeight(resultSet.getDouble("weight"));
                vkiDto.setuHeight(resultSet.getDouble("height"));
                vkiDto.setVKI_value(resultSet.getDouble("vki_value"));
                vkiDto.setSystemCreatedDate(resultSet.getDate("systemCreatedDate"));
                vkiDto.setSystemCreatedDate(resultSet.getDate("system_created_date"));
                list.add(vkiDto);
            }
            return list; // eğer başarılı ise return registerDto
        } catch (SQLException sql) {
            sql.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public VKIDto update(Long id, VKIDto vkiDto) {
        // Bu ID ile ilgili kullanıcı varmı ?
        VKIDto find = findById(id);
        if (find != null) {
            try (Connection connection = getInterfaceConnection()) {
                // Manipulation: executeUpdate() [create, delete, update]
                // Sorgularda  : executeQuery [list, find]
                // Transaction:
                connection.setAutoCommit(false); //default:true
                String sql = "UPDATE `register` SET  `weight`=?, `height`=?, " +
                        "`vki_value`=?" +
                        " WHERE `id` =?;";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setDouble(1, vkiDto.getuWeight());
                preparedStatement.setDouble(2, vkiDto.getuHeight());
                preparedStatement.setDouble(3, vkiDto.getVKI_value());
                preparedStatement.setLong(4, vkiDto.getId());
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
                return vkiDto; // eğer başarılı ise return registerDto
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

    @Override
    public RegisterDto updateRemaing(Long id, VKIDto vkiDto) {
        return null;
    }

    @Override
    public VKIDto deleteById(VKIDto vkiDto) {
        // Bu ID ile ilgili kullanıcı varmı ?
        VKIDto find = findById(vkiDto.getId());
        if (find != null) {
            try (Connection connection = getInterfaceConnection()) {
                // Manipulation: executeUpdate() [create, delete, update]
                // Sorgularda  : executeQuery [list, find]
                // Transaction:
                connection.setAutoCommit(false); //default:true
                String sql = "DELETE FROM `register` WHERE `id` = ?;";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setLong(1, vkiDto.getId());
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
                return vkiDto; // eğer başarılı ise return registerDto
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
}
