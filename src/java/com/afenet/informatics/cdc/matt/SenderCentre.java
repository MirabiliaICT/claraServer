/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.afenet.informatics.cdc.matt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.apache.tomcat.util.codec.binary.Base64;

/**
 *
 * @author Mathew Official
 */
public class SenderCentre {
   public static void main(String[] args) {
    String pg_url = "https://phc.nphcdaserver.com/api/33/dataValueSets";
    String json = "";

    HttpURLConnection urlConnection = null;
    String name = "User11315022";
    String password = "User11315022.";
    StringBuilder sb = new StringBuilder();

    // sb.append("0");
    String authString = name + ":" + password;
    byte[] authEncBytes = Base64.encodeBase64(authString.getBytes());
    String authStringEnc = new String(authEncBytes);
   try {

        TrustManager[] trustAllCerts = new TrustManager[]{
            new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkClientTrusted(
                        java.security.cert.X509Certificate[] certs, String authType) {
                }

                public void checkServerTrusted(
                        java.security.cert.X509Certificate[] certs, String authType) {
                }
            }
        };

// Install the all-trusting trust manager
        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
        }

        // Now you can access an https URL without having the certificate in the truststore
        URL url = new URL(pg_url);
        urlConnection = (HttpURLConnection) url.openConnection();
      //  urlConnection.setRequestProperty("Authorization", "Basic " + authStringEnc);
        urlConnection.setDoOutput(true);
        
      //  urlConnection.setRequestMethod("GET");
        urlConnection.setUseCaches(true);
        urlConnection.setConnectTimeout(10000);
        urlConnection.setReadTimeout(10000);
        urlConnection.setRequestProperty("Accept", "application/json");
        urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        urlConnection.setRequestProperty("apiKey", "application/json");
        

        urlConnection.connect();

        String json_all = "username=sandbox&to=%2B2347031252904&from=%2B2347032305335";
           System.out.println(json_all);
        //  System.err.println("kk!");
        //   System.err.println(json_all);

        OutputStreamWriter out_ = new OutputStreamWriter(urlConnection.getOutputStream());
        out_.write(json_all);
        out_.close();

        int HttpResult = urlConnection.getResponseCode();

        if (HttpResult == 200) {
            BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "utf-8"));
            String line = null;
            sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            br.close();
            System.err.println(sb.toString());
            //debuSystem.err.println("STATUS: Success!");
            //  System.out.println("data sent to DHIS2" + sb.toString());
            if (sb.toString().indexOf("\"created\":1") >= 1) {
                //      response.setStatus(200);
                System.err.println("STATUS: Success!");

                String dd = sb.toString();
                String dx = dd.substring(dd.indexOf("uid"));
                String cl = dx.replaceAll("\"", "").replaceAll(",", "");
                String dw = cl.substring(cl.indexOf(":"));
                System.err.println(dw.replace("}]}]stats:{created:1updated:0deleted:0ignored:0total:1}}", ""));
                //    storeOut(dw.replace("}]}]stats:{created:1updated:0deleted:0ignored:0total:1}}", ""), dxs);

            }

        } else {
            BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getErrorStream(), "utf-8"));
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            br.close();

            System.out.println("Error from DHIS2" + sb.toString());
            // storeOut("Error", dxs);

        }

    } catch (IOException ex) {
        ex.getMessage();
    } finally {

    }
   }
    
     public static void putDemAll(String pg_url, String valu) {

        HttpURLConnection urlConnection = null;
        String name = "aefi_reporter_ROBOT";
        String password = "Passcode123!";
        StringBuilder sb = new StringBuilder();
        //System.out.println(pg_url);

        String authString = name + ":" + password;
        byte[] authEncBytes = Base64.encodeBase64(authString.getBytes());
        String authStringEnc = new String(authEncBytes);
        try {
            URL url = new URL(pg_url);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Authorization", "Basic " + authStringEnc);
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("PUT");
            urlConnection.setUseCaches(true);
            urlConnection.setConnectTimeout(2000);
            urlConnection.setReadTimeout(2000);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.connect();

            OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
            out.write(valu);
            out.close();

            int HttpResult = urlConnection.getResponseCode();
            //debug  
            //  System.out.println("Data session HTTP Return Code = " + HttpResult);

            if (HttpResult == 200) {
                BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "utf-8"));
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();

                //debug  ////System.out.println("#########AAA###" + sb.toString());
                if (sb.toString().indexOf("success") >= 1) {
                    //      response.setStatus(200);
                    System.err.println("DATA: Success!");
                    return;
                }
                if (sb.toString().indexOf("warning") >= 1) {
                    //        response.setStatus(300);
                    System.err.println("Warning!");
                    return;
                }
                if (sb.toString().indexOf("conflict") >= 1) {
                    //        response.setStatus(300);
                    System.err.println("Conflicts!");
                    return;
                }
                if ((sb.toString().indexOf("warning") >= 1) || (sb.toString().indexOf("success") >= 1)) {
                    //      response.setStatus(414);
                    System.err.println("Noticable Error:\n" + sb.toString());
                    return;
                }
            } else {
                //response.setStatus(502, "DHIS2 Not there!");
                ////System.out.println("####CCCCCCCCCCCCCC" + urlConnection.getInputStream().toString());
                BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "utf-8"));
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();

                ////System.out.println("#####XXX##" + sb.toString());
                ////System.out.println("OUT ERROR:>>>>" + urlConnection.getResponseMessage());
                return;
            }

        } catch (IOException ex) {
            Logger.getLogger(goAEFI.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

        }
}
