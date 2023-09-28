package com.ikolay.constant;

public class EndPoints {

    public static final String VERSION = "api/v1";
    public static final String COMPANY = VERSION + "/company";
    public static final String TRANSACTION = VERSION + "/transaction";
    public static final String LEAVE = VERSION + "/leave";
    public static final String COMMENT = VERSION + "/comment";
    public static final String ADDCOMMENT = "/addcomment";
    public static final String REGISTER = "/register";
    public static final String UPDATE = "/update";
    public static final String FINDCOMPANY = "/findcompany";
    public static final String ADD = "/add";
    public static final String CREATE = "/create";
    public static final String DELETE = "/delete";
    public static final String GETCOMPANYINFORMATION = "/companyinformation";
    public static final String COMPANYINFOFORCONFIRMATION = "/getcompanynameandtaxno";
    public static final String FINDBYCOMPANYNAMETOP5 = "/findbycompanynametopfive";
    public static final String FINDALL = "/findall";
    public static final String INCOMINGPAYMENTS = "/payments";
    public static final String PROFITLOSS = "/annualprofitloss";
    public static final String ALLEXPENSES = "/allexpenses";
    public static final String FINDALLCOMMENTFORADMIN = "/findallcommentforadmin";
    public static final String FINDALLCOMMENTFORGUEST = "/findallcommentforguest";
    public static final String FINDIDBYEMAIL = "/finduseridbyemail/{email}";
    public static final String ACCEPTCOMMENT = "/acceptcomment/{id}";
    public static final String REJECTCOMMENT  = "/rejectcomment/{id}";
    public static final String GETUSERSCOMMENT  = "/finduserscomment/{userId}";
    public static final String FINDCOMPANYLEAVES  = "/getcompanyleaves";


}