package com.pgmacdesign.smsverification.myfiles;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by pmacdowell on 8/13/2015.
 */
public class ServerRequest {

    public static final int POST = 1;
    public static final int GET = 0;

    public static String makeRequest(String uri, String json, int request_type) {
        if(request_type == POST) {
            try {
                HttpPost httpPost = new HttpPost(uri);
                httpPost.setEntity(new StringEntity(json));
                httpPost.setHeader("Accept", "application/json");
                httpPost.setHeader("Content-type", "application/json");

                String returnMe = convertHTTPToString(new DefaultHttpClient().execute(httpPost));
                return returnMe;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return null;
            } catch (ClientProtocolException e) {
                e.printStackTrace();
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        } else if(request_type == GET){
            try {
                HttpGet httpGet = new HttpGet(uri);

                String returnMe = convertHTTPToString(new DefaultHttpClient().execute(httpGet));
                return returnMe;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return null;
            } catch (ClientProtocolException e) {
                e.printStackTrace();
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }

    private static String convertHTTPToString(HttpResponse response){

        HttpEntity httpEntity = null;
        httpEntity = response.getEntity();
        String myResponse = null;
        try {
            myResponse = EntityUtils.toString(httpEntity);
        } catch (Exception e){
            e.printStackTrace();
        }
        return myResponse;
    }
}
