package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService {
    private FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public File getFile(int fileId){
        return fileMapper.getFile(fileId);
    }

    public void addFile(File file)
    {
        fileMapper.insert(file);
    }

    public List<String> getAllFilesNameByUser(int userId)
    {
        return fileMapper.getAllFilesNameByUser(userId);
    }


    public File getFileByFileNameAndUserId(String filename, int userId)
    {
        return fileMapper.getFileByFileNameAndUserId(filename, userId);
    }


   public int checkCountOfFileName(String filename, int userId)
    {
        return fileMapper.checkCountOfFileName(filename, userId);
    }

    public void deleteFileByFilenameAndUserId(String filename, int userId)
    {
        fileMapper.deleteFileByFilenameAndUserId(filename,  userId);
    }

}
