package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredentialService {
    private CredentialMapper credentialMapper;

    public CredentialService(CredentialMapper credentialMapper) {
        this.credentialMapper = credentialMapper;
    }

    public void deleteCredentialByCredentialId(int credentialId)
    {
        credentialMapper.deleteCredentialByCredentialId(credentialId);
    }
    public void addCredential(Credential credential)
    {
        credentialMapper.insert(credential);
    }

    public void updateCredential(Credential credential)
    {
        credentialMapper.updateCredentialByCredentialId(credential);
    }

    public List<Credential> getAllCredentialByUser(int userId) {
        return credentialMapper.getAllCredentialByUser(userId);
    }

public Credential getCredentialByCredentialId(int credentialId)
{
    return credentialMapper.getCredentialByCredentialId(credentialId);
}

}
