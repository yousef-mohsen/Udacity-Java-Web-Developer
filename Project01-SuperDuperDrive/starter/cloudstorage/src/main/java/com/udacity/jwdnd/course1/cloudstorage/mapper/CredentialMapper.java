package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {
    @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userid) VALUES(#{url}, #{username}, #{key}, #{password}, #{userId} )")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    int insert(Credential credential);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialId = #{credentialId}")
    int deleteCredentialByCredentialId(int credentialId);

    @Update("UPDATE CREDENTIALS SET " +
            "url=#{url}," +
            "password=#{password}," +
            "username=#{username}" +
            "WHERE credentialId=#{credentialId}")
    int updateCredentialByCredentialId(Credential credential);
    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userId} ")
    List<Credential> getAllCredentialByUser(int userId);

    @Select("SELECT * FROM CREDENTIALS WHERE credentialid = #{credentialId} ")
    Credential getCredentialByCredentialId(int credentialId);




}
