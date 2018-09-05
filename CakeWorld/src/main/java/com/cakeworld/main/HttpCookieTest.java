package com.cakeworld.main;

import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

public class HttpCookieTest {


    public static void main(String[] args) throws IOException {

         

        String urlString = "https://www.google.com/";

 

        //Create a default system-wide CookieManager

        CookieManager cookieManager = new CookieManager();

        CookieHandler.setDefault(cookieManager);


        //Open a connection for the given URL

        URL url = new URL(urlString);

        URLConnection urlConnection = url.openConnection();

        urlConnection.getContent();

 

        //Get CookieStore which is the default internal in-memory

        CookieStore cookieStore = cookieManager.getCookieStore();


        //Retrieve all stored HttpCookies from CookieStore

        List<HttpCookie> cookies = (List<HttpCookie>)cookieStore.getCookies();

         

        int cookieIdx = 0;

 
        System.out.println(cookies.size());
        //Iterate HttpCookie object

        for (HttpCookie ck : cookies) {

             

            System.out.println("------------------ Cookie." + ++cookieIdx  + " ------------------");


            System.out.println("Cookie name: " + ck.getName());

                         

            //Get the domain set for the cookie

            System.out.println("Domain: " + ck.getDomain());

 

            //Get the max age of the cookie

            System.out.println("Max age: " + ck.getMaxAge());

            System.out.println("Server path: " + ck.getPath());

            System.out.println("Is secured: " + ck.getSecure());

            System.out.println("Cookie value: " + ck.getValue());

            System.out.println("Cookie protocol version: " + ck.getVersion());

        }

    }

}
