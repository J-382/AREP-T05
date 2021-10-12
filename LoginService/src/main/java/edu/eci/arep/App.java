package edu.eci.arep;

import static spark.Spark.*;

import spark.Request;
import spark.Response;

public class App {

    public static void main(String[] args) {
        secure("keystores/ecikeystore.p12", "123456", null, null); 
        port(getPort());
        get("/hello", (req, res) -> "Hello World"); 
        post("/login", (req, res) -> login(req, res));
    } 

    public static boolean login(Request req, Response res){
        SecureContext sc = SecureContext.initSecureContext();
        String user = "", passwd = "";
        return sc.validate(user, sc.createHash(passwd));
    }

    public static int getPort(){
        if (System.getenv("PORT") != null){
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567;
    }
} 
