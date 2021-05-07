package com.example.laspost10h.Confidential_Details;

public class Java_API_URLs
{
                                                // GENERAL API URL

    public static final String CONTACT_US = "http://192.168.0.5:8080/Labspot_Backend_APIs/Labspot_Project_api/labspot/contact_us";

                                                // USER PART

    public static final String USER_REGISTRATION ="http://192.168.0.5:8080/Labspot_Backend_APIs/Labspot_Project_api/user/register";
    public static final String USER_LOGIN ="http://192.168.0.5:8080/Labspot_Backend_APIs/Labspot_Project_api/user/login";
    public static final String USER_ACCOUNT_VERIFICATION ="http://192.168.0.5:8080/Labspot_Backend_APIs/Labspot_Project_api/user/account_verify";
    public static final String USER_FORGOT_PASSWORD ="http://192.168.0.5:8080/Labspot_Backend_APIs/Labspot_Project_api/user/forgot_password_generate_otp";
    public static final String USER_FORGOT_PASSWORD_OTP_VERIFY ="http://192.168.0.5:8080/Labspot_Backend_APIs/Labspot_Project_api/user/forgot_password_otp_verify";
    public static final String USER_RESET_PASSWORD ="http://192.168.0.5:8080/Labspot_Backend_APIs/Labspot_Project_api/user/reset_password";

    // AFTER LOGIN
    public static final String USER_WELCOME_FETCH_DATA = "http://192.168.0.5:8080/Labspot_Backend_APIs/Labspot_Project_api/user/fetch_data";
    public static final String USER_CHANGE_PASSWORD = "http://192.168.0.5:8080/Labspot_Backend_APIs/Labspot_Project_api/user/change_password";
    public static final String USER_CHANGE_ADDRESS = "http://192.168.0.5:8080/Labspot_Backend_APIs/Labspot_Project_api/user/change_address";

                                                // LAB PART

    public static final String LAB_REGISTRATION ="http://192.168.0.5:8080/Labspot_Backend_APIs/Labspot_Project_api/lab/register";
    public static final String LAB_LOGIN ="http://192.168.0.5:8080/Labspot_Backend_APIs/Labspot_Project_api/lab/login";
    public static final String LAB_ACCOUNT_VERIFICATION ="http://192.168.0.5:8080/Labspot_Backend_APIs/Labspot_Project_api/lab/account_verify";
    public static final String LAB_FORGOT_PASSWORD ="http://192.168.0.5:8080/Labspot_Backend_APIs/Labspot_Project_api/lab/forgot_password_generate_otp";
    public static final String LAB_FORGOT_PASSWORD_OTP_VERIFY ="http://192.168.0.5:8080/Labspot_Backend_APIs/Labspot_Project_api/lab/forgot_password_otp_verify";
    public static final String LAB_RESET_PASSWORD ="http://192.168.0.5:8080/Labspot_Backend_APIs/Labspot_Project_api/lab/reset_password";

    // AFTER LOGIN
    public static final String LAB_WELCOME_FETCH_DATA = "http://192.168.0.5:8080/Labspot_Backend_APIs/Labspot_Project_api/lab/fetch_data";
    public static final String LAB_CHANGE_PASSWORD = "http://192.168.0.5:8080/Labspot_Backend_APIs/Labspot_Project_api/lab/change_password";
    public static final String LAB_CHANGE_ADDRESS = "http://192.168.0.5:8080/Labspot_Backend_APIs/Labspot_Project_api/lab/change_address";
    public static final String LAB_ADD_TEST_DETAILS = "http://192.168.0.5:8080/Labspot_Backend_APIs/Labspot_Project_api/lab/add_test_details";
    public static final String LAB_EDIT_TEST_DETAILS = "http://192.168.0.5:8080/Labspot_Backend_APIs/Labspot_Project_api/lab/edit_test_details";

                                                // DELIVERY PART

    public static final String DELIVERY_REGISTRATION ="http://192.168.0.5:8080/Labspot_Backend_APIs/Labspot_Project_api/delivery/register";
    public static final String DELIVERY_LOGIN ="http://192.168.0.5:8080/Labspot_Backend_APIs/Labspot_Project_api/delivery/login";
    public static final String DELIVERY_ACCOUNT_VERIFICATION ="http://192.168.0.5:8080/Labspot_Backend_APIs/Labspot_Project_api/delivery/account_verify";
    public static final String DELIVERY_FORGOT_PASSWORD ="http://192.168.0.5:8080/Labspot_Backend_APIs/Labspot_Project_api/delivery/forgot_password_generate_otp";
    public static final String DELIVERY_FORGOT_PASSWORD_OTP_VERIFY ="http://192.168.0.5:8080/Labspot_Backend_APIs/Labspot_Project_api/delivery/forgot_password_otp_verify";
    public static final String DELIVERY_RESET_PASSWORD ="http://192.168.0.5:8080/Labspot_Backend_APIs/Labspot_Project_api/delivery/reset_password";

    // AFTER LOGIN
    public static final String DELIVERY_WELCOME_FETCH_DATA = "http://192.168.0.5:8080/Labspot_Backend_APIs/Labspot_Project_api/delivery/fetch_data";
    public static final String DELIVERY_CHANGE_PASSWORD = "http://192.168.0.5:8080/Labspot_Backend_APIs/Labspot_Project_api/delivery/change_password";
    public static final String DELIVERY_CHANGE_ADDRESS = "http://192.168.0.5:8080/Labspot_Backend_APIs/Labspot_Project_api/delivery/change_address";

                                                // TEST TRANSACTION PART

    public static final String PLACE_TEST_REQUEST_TO_THE_LAB = "http://192.168.0.5:8080/Labspot_Backend_APIs/Labspot_Project_api/test_detail_transaction/place_test_request";
    public static final String LAB_REJECT_TEST_REQUEST = "http://192.168.0.5:8080/Labspot_Backend_APIs/Labspot_Project_api/test_detail_transaction/reject_test_request";
    public static final String LAB_ACCEPT_TEST_REQUEST = "http://192.168.0.5:8080/Labspot_Backend_APIs/Labspot_Project_api/test_detail_transaction/accept_test_request";
    public static final String USER_REQUEST_FOR_SAMPLE_COLLECT = "http://192.168.0.5:8080/Labspot_Backend_APIs/Labspot_Project_api/test_detail_transaction/request_for_sample_collect";
    public static final String REJECT_DELIVERY_REQUEST_FROM_USER = "http://192.168.0.5:8080/Labspot_Backend_APIs/Labspot_Project_api/test_detail_transaction/user_reject_delivery_request";
    public static final String ACCEPT_DELIVERY_REQUEST_FROM_USER = "http://192.168.0.5:8080/Labspot_Backend_APIs/Labspot_Project_api/test_detail_transaction/user_accept_delivery_request";
    public static final String REJECT_DELIVERY_REQUEST_FROM_LAB = "http://192.168.0.5:8080/Labspot_Backend_APIs/Labspot_Project_api/test_detail_transaction/lab_reject_delivery_request";
    public static final String ACCEPT_DELIVERY_REQUEST_FROM_LAB = "http://192.168.0.5:8080/Labspot_Backend_APIs/Labspot_Project_api/test_detail_transaction/lab_accept_delivery_request";

    //OTP SECTION from USER SIDE REQUEST
    public static final String USER_SIDE_OTP_GENERATION_TO_VERIFY_DELIVERY_BOY = "http://192.168.0.5:8080/Labspot_Backend_APIs/Labspot_Project_api/test_detail_transaction/user_delivery_request_person_verify_otp_generate";
    public static final String USER_SIDE_OTP_VERIFICATION_TO_VERIFY_DELIVERY_BOY = "http://192.168.0.5:8080/Labspot_Backend_APIs/Labspot_Project_api/test_detail_transaction/user_delivery_request_person_verify_otp_verify";

    public static final String USER_SIDE_OTP_GENERATION_TO_VERIFY_50_PERCENT_PAYMENT = "http://192.168.0.5:8080/Labspot_Backend_APIs/Labspot_Project_api/test_detail_transaction/user_confirm_50_percent_otp_generate";
    public static final String USER_SIDE_OTP_VERIFICATION_TO_VERIFY_50_PERCENT_PAYMENT = "http://192.168.0.5:8080/Labspot_Backend_APIs/Labspot_Project_api/test_detail_transaction/user_confirm_50_percent_payment_otp_verify";

    public static final String DELIVERY_BOY_AT_LAB_OTP_GENERATION_TO_VERIFY_DELIVERY_BOY = "http://192.168.0.5:8080/Labspot_Backend_APIs/Labspot_Project_api/test_detail_transaction/lab_delivery_request_person_verify_otp_generate";
    public static final String DELIVERY_BOY_AT_LAB_OTP_VERIFICATION_TO_VERIFY_DELIVERY_BOY = "http://192.168.0.5:8080/Labspot_Backend_APIs/Labspot_Project_api/test_detail_transaction/lab_delivery_request_person_verify_otp_verify";

    //OTP SECTION from LAB SIDE REQUEST
    public static final String DELIVERY_BOY_AT_LAB_OTP_GENERATION_TO_VERIFY_DELIVERY_BOY_PART_2 = "http://192.168.0.5:8080/Labspot_Backend_APIs/Labspot_Project_api/test_detail_transaction/lab_delivery_request_person_verify_otp_generate_part_2";
    public static final String DELIVERY_BOY_AT_LAB_OTP_VERIFICATION_TO_VERIFY_DELIVERY_BOY_PART_2 = "http://192.168.0.5:8080/Labspot_Backend_APIs/Labspot_Project_api/test_detail_transaction/lab_delivery_request_person_verify_otp_verify_part_2";

    public static final String USER_SIDE_OTP_GENERATION_TO_VERIFY_DELIVERY_BOY_PART_2 = "http://192.168.0.5:8080/Labspot_Backend_APIs/Labspot_Project_api/test_detail_transaction/user_delivery_request_person_verify_otp_generate_part_2";
    public static final String USER_SIDE_OTP_VERIFICATION_TO_VERIFY_DELIVERY_BOY_PART_2 = "http://192.168.0.5:8080/Labspot_Backend_APIs/Labspot_Project_api/test_detail_transaction/user_delivery_request_person_verify_otp_verify_part_2";

    public static final String USER_SIDE_OTP_GENERATION_TO_VERIFY_50_PERCENT_PAYMENT_PART_2 = "http://192.168.0.5:8080/Labspot_Backend_APIs/Labspot_Project_api/test_detail_transaction/user_confirm_50_percent_otp_generate_part_2";
    public static final String USER_SIDE_OTP_VERIFICATION_TO_VERIFY_50_PERCENT_PAYMENT_PART_2 = "http://192.168.0.5:8080/Labspot_Backend_APIs/Labspot_Project_api/test_detail_transaction/user_confirm_50_percent_payment_otp_verify_part_2";

    public static final String LAB_COMPLETE_TEST_PROCESS = "http://192.168.0.5:8080/Labspot_Backend_APIs/Labspot_Project_api/test_detail_transaction/complete_test_request";
    public static final String LAB_REQUEST_DELIVERY_BOY = "http://192.168.0.5:8080/Labspot_Backend_APIs/Labspot_Project_api/test_detail_transaction/request_for_report_collect";
}