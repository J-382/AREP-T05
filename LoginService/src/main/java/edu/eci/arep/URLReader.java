package edu.eci.arep;

import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

import java.net.URL;
import java.net.URLConnection;

import java.util.Map;
import java.util.Set;
import java.util.List;

public class URLReader {
    public static void main(String[] args){
        try {
            File trustStoreFile = new File("keystores/myTrustStore"); 
            char[] trustStorePassword = "123456".toCharArray();

            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(new FileInputStream(trustStoreFile), trustStorePassword);
            
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm()); 
            tmf.init(trustStore);

            SSLContext sslContext = SSLContext.getInstance("TLS"); 
            sslContext.init(null, tmf.getTrustManagers(), null); 
            SSLContext.setDefault(sslContext);
            
            readURL("https://localhost:4567/hello"); 
        } catch (KeyManagementException | NoSuchAlgorithmException | CertificateException | IOException | KeyStoreException e1) {
            e1.printStackTrace();
        }
    }

    private static void readURL(String siteToRead) {
        try {
            URL siteURL = new URL(siteToRead);
            URLConnection urlConnection = siteURL.openConnection();
            Map<String, List<String>> headers = urlConnection.getHeaderFields();
            Set<Map.Entry<String, List<String>>> entrySet = headers.entrySet();
            for (Map.Entry<String, List<String>> entry: entrySet){
                String headerName = entry.getKey();
                if (headerName != null){
                    System.out.println(headerName + ":");
                }
                List<String> headerValues = entry.getValue();
                for(String value: headerValues){
                    System.out.print(value);
                }
                System.out.println();
            }
            
            System.out.println("------------------- Message Body -------------------");
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String inputLine = null;
            while((inputLine = reader.readLine()) != null){
                System.out.println(inputLine);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}