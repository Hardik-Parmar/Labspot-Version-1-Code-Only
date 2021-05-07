package com.example.laspost10h.Confidential_Details;

public class Image_URLs
{
    public static String getUserImageURL(String image_name)
    {
        String userURL = "http://192.168.0.5/labspot_image_server/user_profile_picture/" + image_name;

        return userURL;
    }

    public static String getDeliveryImageURL(String image_name)
    {
        String deliveryURL = "http://192.168.0.5/labspot_image_server/delivery_profile_picture/" + image_name;

        return deliveryURL;
    }

    public static String getLabLogoURL(String logo_name)
    {
        String labURL = "http://192.168.0.5/labspot_image_server/lab_logo/" + logo_name;

        return  labURL;
    }
}