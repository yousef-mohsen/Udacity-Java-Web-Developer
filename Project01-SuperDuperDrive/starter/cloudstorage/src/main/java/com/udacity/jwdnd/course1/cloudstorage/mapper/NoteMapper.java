package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper
public interface NoteMapper {

    @Insert("INSERT INTO Notes (noteTitle, noteDescription, userId) VALUES(#{noteTitle}, #{noteDescription}, #{userId} )")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    int insert(Note note);

    @Delete("DELETE FROM NOTES WHERE noteId = #{noteId}")
    int deleteNoteByNoteId(int noteId);

  //@Update("UPDATE NOTES SET (noteTitle, noteDescription, userId) VALUES(#{noteTitle}, #{noteDescription}, #{userId}) WHERE noteId = #{noteId} ")
  @Update("UPDATE NOTES SET " +
          "noteTitle=#{noteTitle}," +
          "noteDescription=#{noteDescription}" +
          "WHERE noteId=#{noteId}")
  int updateNoteByNoteId(Note note);
    @Select("SELECT * FROM NOTES WHERE userId = #{userId} ")
    List<Note> getAllNotesByUser(int userId);
}
