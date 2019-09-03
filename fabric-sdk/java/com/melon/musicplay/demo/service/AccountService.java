package com.melon.musicplay.demo.service;


import com.melon.musicplay.demo.model.UserContext;
import static com.melon.musicplay.demo.utils.Util.defaultCryptoSuite;
import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.security.CryptoSuite;
import org.hyperledger.fabric_ca.sdk.HFCAClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;


@Slf4j
@Service
public class AccountService {

    @Value("${hyperledger.org.admin.name}")
    private String adminName;
    @Value("${hyperledger.org.admin.password}")
    private String adminPassword;
    @Value("${hyperledger.org.admin.account}")
    private String adminAccount;
    @Value("${hyperledger.org.affiliation}")
    private String affiliation;
    @Value("${hyperledger.org.mspId}")
    private String mspId;
    @Value("${hyperledger.org.caServerUrl}")
    private String caServerUrl;

    private UserContext adminContext;

    @PostConstruct
    public void init() {
        try {
            createAdminContextIfNeccessary();
            writeAdminContextToFileSystem(adminContext);
        } catch (Exception e) {
            throw new RuntimeException("Exception occured while trying to init admin context", e);
        }
    }

    private void createAdminContextIfNeccessary() throws Exception {
        UserContext fileSystemAdminContext = readAdminContextFromFileSystem();

        if (fileSystemAdminContext != null) {
            adminContext = fileSystemAdminContext;
            return;
        }
        
        log.debug("Admin context does not exist in file system, so create new admin context");

        createAdminContext();
    }

    private UserContext readAdminContextFromFileSystem() throws Exception {
        return null;
    }

    private void createAdminContext() throws Exception{
        this.adminContext = new UserContext();
        adminContext.setName(adminName);
        adminContext.setAccount(adminAccount);
        adminContext.setMspId(mspId);
        adminContext.setAffiliation(affiliation);
        adminContext.setRoles(getAdminRoles());
        adminContext.setEnrollment(getAdminEnrollment());
    }

    private Enrollment getAdminEnrollment() throws Exception {
        HFCAClient caClient = createCAClient();
        Enrollment adminEnrollment = caClient.enroll(adminName, adminPassword);

        return adminEnrollment;
    }

    private HFCAClient createCAClient() throws Exception {
        CryptoSuite cryptoSuite = defaultCryptoSuite();

        HFCAClient caClient = HFCAClient.createNewInstance(caServerUrl, null);
        caClient.setCryptoSuite(cryptoSuite);

        return caClient;
    }

    private Set<String> getAdminRoles() {
        Set<String> adminRoles = new HashSet<>();
        adminRoles.add("USER");
        adminRoles.add("ADMIN");
        return adminRoles;
    }

    private void writeAdminContextToFileSystem(UserContext adminContext) {

    }

    public UserContext getAdminContext() {
        return adminContext;
    }

}
