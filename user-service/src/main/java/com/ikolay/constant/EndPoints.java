package com.ikolay.constant;

public class EndPoints {

    public static final String VERSION = "api/v1";
    public static final String USER = VERSION + "/user";
    public static final String SHIFT = VERSION + "/shift";
    public static final String REGISTER = "/register";
    public static final String ADD = "/add";

    public static final String LOGIN = "/login";
    public static final String UPDATE = "/update";
    public static final String FINDCOMPANY = "/findcompany";
    public static final String DELETEBYID = "/delete/{id}";
    public static final String FINDALL = "/findall";
    public static final String ACTIVATION = "/activation";

    public static final String FINDSHIFTBYID = "/findshift/{id}";
    public static final String DELETEFROMCONFIRMATION = "/deletewithauthid";
    public static final String CONFIRMATION = "/confirmuser";
    public static final String GETUSERINFORMATION = "/loggeduser";

    public static final String PERSONELLIST = "/getallpersonelwithcompanyid";
    public static final String FINDIDBYEMAIL = "/findIdByEmail";
    public static final String FINDALLPENDINGMANAGERS = "/findAllPendingManagers";
    public static final String SETSHIFTTOUSER = "/setShiftToUser";
    public static final String COMPANYINFOFORCONFIRMATION = "/getcompanynameandtaxno";
    public static final String GETFIRSTANDLASTNAMEWITHID = "/getusersfirstandlastname/{id}";


}
