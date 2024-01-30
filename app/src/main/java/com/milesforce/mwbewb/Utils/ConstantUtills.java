package com.milesforce.mwbewb.Utils;

import com.milesforce.mwbewb.R;

import java.net.URL;

import retrofit2.http.Url;

public abstract class ConstantUtills {
    public static String File_Name;
    public static String FILE_SAVE_TIME;
    public static boolean IS_INCOMMING_EVENT = false;
    public static boolean IS_OUTGOING_EVENT = false;
    public static boolean IS_OUTGOING_EVENT_NEW = false;
    public static boolean IS_ONOUTGOING_CALL_EVENT = false;
    public static boolean IS_SUCCESS_CALL = false;
    public static final String AccessToken = "accessToken";
    public static final String SaveToken = "saveToken";
    public static String LastRecordTimeCheck = "time_check";
    public static String UpdatedTimeRecord = "lastUpdatedRecordTime";
    public static String PUSHER_STATUS = "CONNECTING";
    public static String Bde_user_id = "bdeid";

    public static String fragment_token = "token";

    public static String USER_NAME = "user_name";
    public static String BATTERY_PERCENTAGE = " ";
    public static String BATTERY_STATUS = " ";
    public static String VERSION_NUMBER = "0.1.7";
   // public static String Escalation_Url = "getAcadsEscalations";
    public static String SecondLevel_Url = "getAcadsSecondLevel";
    //  public static String NET_ENQUIRY_URL = "getNetEnquiriesPagination";



    /*For Normal Apis*/


    public static String Delays_url = "getDelays";
    public static String Today_url = "getToday";
    public static String Waiting_url = "getWaiting";
    public static String UnTapped_url = "getUntapped";
    public static final int TAB_CHANGE_CODE = 0;
    public static String _Url = "getAcadsEscalations";
    public static String Escalation_Url = "getAllEscalationsPagination";
    public static String NET_ENQUIRY_URL = "getNetEnquiriesMobileWithoutPagination";
    public static final int IIML_TAB_CHANGE_CODE = 0;
    public static String MHP_URL= "getMHPPaginationMobile";
    public static String ZOOM_INVITATION = "sendZoomInvite";



    /*Accades Urls*/

    /*public static String Delays_url = "getAcadsDelaysMobile";
    public static String Today_url = "getAcadsTodayMobile";
    public static String Waiting_url = "getAcadsWaitingMobile";
    public static final int TAB_CHANGE_CODE = 1;
    public static String Escalation_Url = "getAcadsEscalations";
    public static String SecondLevel_Url = "getAcadsSecondLevel";
*/
    //For IIMl-FA





//    public static String Delays_url = "getIIMLDelaysPagination";
//    public static String Today_url = "getIIMLTodayPagination";
//    public static String Waiting_url = "getIIMLWaitingPagination";
//    public static String UnTapped_url = "getIIMLUntappedPagination";
//    public static final int TAB_CHANGE_CODE = 2;
//    public static String _Url = "getAcadsEscalations";
//    public static String Escalation_Url = "getAllEscalationsPagination";
//    public static String NET_ENQUIRY_URL = "getNetEnquiriesMobileWithoutPagination";
//    public static final int IIML_TAB_CHANGE_CODE = 1;
//    public static String MHP_URL= "getIIMLMHPPagination";
//    public static String ZOOM_INVITATION = "sendZoomInviteBA";







}
