package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.*;

import java.sql.Blob;
import java.util.List;

@Mapper
public interface FileMapper {

    @Insert("INSERT INTO FILES (filename, contenttype, fileSize, userid, fileData) VALUES(#{filename}, #{contentType}, #{fileSize}, #{userid}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int insert(File file);

    @Select("SELECT * FROM FILES WHERE fileid = #{fileId}")
    File getFile(int fileId);

    @Select("SELECT filename FROM FILES  WHERE userid = #{userId} ")
    List<String> getAllFilesNameByUser(int userId);

    @Select("SELECT * FROM FILES  WHERE filename = #{filename} AND userid = #{userId} ")
    File getFileByFileNameAndUserId(String filename, int userId);

    @Select("SELECT count(*) FROM FILES  WHERE filename = #{filename} AND userid = #{userId} ")
    int checkCountOfFileName(String filename, int userId);

    @Delete("DELETE FROM FILES WHERE filename = #{filename} AND userid = #{userId}")
    int deleteFileByFilenameAndUserId(String filename, int userId);



}
