package edu.eci.arep;

import static spark.Spark.*;

import org.json.JSONObject;

public class App { 

    public static void main(String[] args) {
        secure("keystores/ecikeystore.p12", "123456", null, null); 
        port(getPort());
        get("/service", "application/json", ((req, res) -> {
            res.type("application/json");
            JSONObject json = new JSONObject();
            json.put("0", "This is a JSON File, that means... this sh*t is working! :D");
            return json;
        }));
    }

    public static int getPort(){
        if (System.getenv("PORT") != null){
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567;
    }
} 
