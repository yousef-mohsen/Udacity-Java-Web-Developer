package com.udacity.jwdnd.course1.cloudstorage.services;


import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    private NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public void deleteNoteByNoteId(int noteId)
    {
        noteMapper.deleteNoteByNoteId(noteId);
    }
    public void addNote(Note note)
    {
        noteMapper.insert(note);
    }

    public void updateNote(Note note)
    {
        noteMapper.updateNoteByNoteId(note);
    }

    public List<Note> getAllNotesByUser(int userId) {
        return noteMapper.getAllNotesByUser(userId);
    }
}
