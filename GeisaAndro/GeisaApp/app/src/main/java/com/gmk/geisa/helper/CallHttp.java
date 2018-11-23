package com.gmk.geisa.helper;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by kenjin on 2/4/2016.
 */
public class CallHttp {
    public String readHttp(String url,String Authorization) {
        HttpURLConnection urlConnection = null;
        StringBuilder builder = new StringBuilder();
        try {

            URL urlLink = new URL(url);
            urlConnection = (HttpURLConnection) urlLink.openConnection();
            urlConnection.setRequestMethod("GET");
            if(Authorization!=null) {
                urlConnection.setRequestProperty("Authorization", Authorization);
            }
            urlConnection.setDoOutput(true);
            urlConnection.connect();
            Log.e("isi response Get","code :"+ urlConnection.getResponseCode());

            InputStream content = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(content));
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.e("error cui Mal",e.toString());
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("error cui IO",e.toString());
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("error cui EX",e.toString());
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return builder.toString();
    }
    public String postHttp(String url, Map<String, String> params,String Authorization) {
        HttpURLConnection urlConnection = null;
        StringBuilder builder = new StringBuilder();
        try {

            URL urlLink = new URL(url);
            urlConnection = (HttpURLConnection) urlLink.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoInput(true);
            if(Authorization!=null) {
               urlConnection.setRequestProperty("Authorization","Intermediate "+ Authorization);
            }
            //urlConnection.setDoOutput(true);
           // urlConnection.setRequestProperty("Content-Type", "application/json");

            StringBuffer requestParams = new StringBuffer();
           // Log.e("isi url", "sampe sini "+ url);
            if (params != null && (params.size() > 0)) {
                Log.e("isi post", params.toString());
                urlConnection.setDoOutput(true); // true indicates POST request

                // creates the params string, encode them using URLEncoder
                Iterator<String> paramIterator = params.keySet().iterator();
                while (paramIterator.hasNext()) {
                    String key = paramIterator.next();
                    String value = params.get(key);
                   // Log.e("isi param ", key + ","+ value);
                    requestParams.append(URLEncoder.encode(key, "UTF-8"));
                    requestParams.append("=").append(
                            URLEncoder.encode(value, "UTF-8"));
                    requestParams.append("&");
                }
                //Log.e("isi post param",requestParams.toString());
                // sends POST data
                OutputStreamWriter writer = new OutputStreamWriter(
                        urlConnection.getOutputStream());
                writer.write(requestParams.toString());
                writer.flush();
            }

            Log.e("isi response Post","code :"+ urlConnection.getResponseCode());

            InputStream content = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(content));

            String line;
            while ((line = reader.readLine()) != null) {
                Log.e("isi post",line + " ");
                builder.append(line);
            }

        } catch (MalformedURLException e) {
            Log.e("error cui Mal",e.toString());
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("error cui IO",e.toString());
            e.printStackTrace();
        } catch (Exception e) {
            Log.e("error cui EX",e.toString());
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return builder.toString();
    }
}
