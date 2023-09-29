package com.ikolay.constant;

public class EndPoints {
    public static final String VERSION = "api/v1";
    public static final String USER = VERSION + "/user";
    public static final String SHIFT = VERSION + "/shift";
    public static final String REGISTER = "/register";
    public static final String ADD = "/add";
    public static final String UPDATE = "/update";
    public static final String FINDSHIFTBYID = "/findshift/{id}";
    public static final String DELETEFROMCONFIRMATION = "/deletewithauthid";
    public static final String CONFIRMATION = "/confirmation";
    public static final String GETUSERINFORMATION = "/loggeduser";
    public static final String PERSONELLIST = "/getallpersonelwithcompanyid";
    public static final String FINDIDBYEMAIL = "/finduseridbyemail/{email}";
    public static final String FINDALLPENDINGMANAGERS = "/pendingmanagers";
    public static final String SETSHIFTTOUSER = "/setshift";
    public static final String COMPANYINFOFORCONFIRMATION = "/getcompanynameandtaxno";
    public static final String GETFIRSTANDLASTNAMEWITHID = "/getusersfirstandlastname/{id}";


}
