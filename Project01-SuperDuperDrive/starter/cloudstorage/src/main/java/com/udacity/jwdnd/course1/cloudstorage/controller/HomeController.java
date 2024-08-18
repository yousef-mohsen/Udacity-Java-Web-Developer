package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;
import java.util.Base64;

import org.apache.commons.io.IOUtils;

import javax.annotation.Resource;


@Controller
@RequestMapping("/home")
public class HomeController {

    private NoteService noteService;
    private UserService userService;

    private CredentialService credentialService;

    private EncryptionService encryptionService;
    private FileService fileService;

    public HomeController(NoteService noteService, UserService userService, CredentialService credentialService, EncryptionService encryptionService, FileService fileService) {
        this.noteService = noteService;
        this.userService = userService;
        this.credentialService = credentialService;
        this.encryptionService = encryptionService;
        this.fileService = fileService;
    }

    @GetMapping
    public String getHomePage(Authentication authentication, @ModelAttribute Note note, @ModelAttribute Credential credential, Model model) {
        int userId = userService.getUser(authentication.getName()).getUserId();
        model.addAttribute("notes", this.noteService.getAllNotesByUser(userId));
        model.addAttribute("credentials", this.credentialService.getAllCredentialByUser(userId));
        model.addAttribute("fileNames", this.fileService.getAllFilesNameByUser(userId));

        model.addAttribute("encryptionService", this.encryptionService);
        return "home";
    }

    @GetMapping("/notes/delete/{id}")
    public String delete(@PathVariable int id, Authentication authentication, @ModelAttribute Note note, Model model) {
        noteService.deleteNoteByNoteId(id);
        int userId = userService.getUser(authentication.getName()).getUserId();
        model.addAttribute("notes", this.noteService.getAllNotesByUser(userId));
        return "result";
    }

    @GetMapping("/files/delete/{filename}")
    public String delete(@PathVariable String filename, Authentication authentication, @ModelAttribute Note note, Model model) {
        int userId = userService.getUser(authentication.getName()).getUserId();
        fileService.deleteFileByFilenameAndUserId(filename, userId);
        model.addAttribute("fileNames", this.fileService.getAllFilesNameByUser(userId));
        return "result";
    }




    @GetMapping("/credentials/delete/{id}")
    public String delete(@PathVariable int id, Authentication authentication, @ModelAttribute Credential credential, Model model) {
        credentialService.deleteCredentialByCredentialId(id);
        int userId = userService.getUser(authentication.getName()).getUserId();
        model.addAttribute("credentials", this.credentialService.getAllCredentialByUser(userId));
        return "result";
    }





    @PostMapping("/nav-credentials")
    public String postNote(Authentication authentication, @ModelAttribute Credential credential, Model model)
    {


        int userId = userService.getUser(authentication.getName()).getUserId();
        if(credential.getCredentialId() == 0)
        {
            credential.setUserId(userId);
            String encodedKey = encryptionService.getKey();
            credential.setKey(encodedKey);
            credential.setPassword(encryptionService.encryptValue(credential.getPassword(), credential.getKey()));
            credentialService.addCredential(credential);

        }
        else{
            Credential tempCredential = credentialService.getCredentialByCredentialId(credential.getCredentialId());
            credential.setPassword(encryptionService.encryptValue(credential.getPassword(), tempCredential.getKey()));
            credentialService.updateCredential(credential);
        }
        model.addAttribute("credentials", this.credentialService.getAllCredentialByUser(userId));

        return "result";
    }






    @PostMapping("/nav-notes")
    public String postNote(Authentication authentication, @ModelAttribute Note note, Model model)
    {

        System.out.println(note.getNoteId());
        if(note.getNoteId() == 0)
        {
            int userId = userService.getUser(authentication.getName()).getUserId();
            note.setUserId(userId);
            noteService.addNote(note);
            model.addAttribute("notes", this.noteService.getAllNotesByUser(userId));
        }
        else{
            int userId = userService.getUser(authentication.getName()).getUserId();
            noteService.updateNote(note);
            model.addAttribute("notes", this.noteService.getAllNotesByUser(userId));
        }
        return "result";
    }


    //from lesson slides
    @PostMapping("/file-upload")
    public String handleFileUpload(@RequestParam("fileUpload") MultipartFile fileUpload, Model model, @ModelAttribute File file, Authentication authentication )
    {
        int userId = userService.getUser(authentication.getName()).getUserId();
        try
        {
            InputStream fis = fileUpload.getInputStream();

            int count = fileService.checkCountOfFileName(fileUpload.getOriginalFilename(), userId);
            if (count != 0)
            {
                model.addAttribute("Error", "Filename already exists, please choose another filename");
                return "result";
            }


            file.setFileData(IOUtils.toByteArray(fis));
        }
            catch(Exception e)
            {
                model.addAttribute("Error", "Error while reading file");
                return "result";
            }


        file.setUserid(userId);
        file.setFileSize(Long.toString(fileUpload.getSize()));
        file.setFilename(fileUpload.getOriginalFilename());
        file.setContentType(fileUpload.getContentType());
        fileService.addFile(file);
        return "result";
    }

    @GetMapping("/file/download/{filename}")
    public ResponseEntity<ByteArrayResource> download(@PathVariable String filename, Model model, @ModelAttribute File file, Authentication authentication) throws IOException {

        int userId = userService.getUser(authentication.getName()).getUserId();
        File tempfile = fileService.getFileByFileNameAndUserId(filename, userId);
        ByteArrayResource resource =  new ByteArrayResource(tempfile.getFileData());



        return ResponseEntity.ok().header("Content-Disposition", String.format("attachment; filename=\"" + tempfile.getFilename() + "\""))
                .contentLength(Long.parseLong(tempfile.getFileSize()))
                .contentType(MediaType.valueOf(tempfile.getContentType()))
                .body(resource);


    }








}
