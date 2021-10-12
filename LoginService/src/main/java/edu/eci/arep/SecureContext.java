package edu.eci.arep;

import java.util.Map;
import java.util.HashMap;

import org.apache.commons.codec.digest.DigestUtils;

public class SecureContext {

    private Map<String, String> users;

    public SecureContext(){
        users = new HashMap<String, String>();
        users.put("root", createHash("root"));
        users.put("kali", createHash("kali"));
        users.put("admin", createHash("admin"));
    }

    public static SecureContext initSecureContext(){
        return new SecureContext();
    }

    public boolean validate(String user, String password){
        return users.containsKey(user) && users.get(user).equals(password);
    }

    public String createHash(String passwd){
        byte[] hashed = DigestUtils.sha256(passwd);
        return hashed.toString();
    }

}
