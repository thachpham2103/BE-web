package com.example.be.web.constant;

public class UrlConstant {

   public static class User{
       public static final String BASE="/users";
       public static final String CREATE_USER= BASE;
       public static final String DELETE_USER = BASE + "/{userId}";
   }

   public static class Auth{

//       private static final String PREFIX = "/auth";
       public static final String LOGIN =  "/login";
       public static final String refreshToken = "/refresh-token";
   }

}
