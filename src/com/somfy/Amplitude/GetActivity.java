package com.somfy.Amplitude;

import com.somfy.parameter.Parameter;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.net.ssl.*;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

public class GetActivity {

    public static void main(String[] args) throws Exception {
        Parameter p = new Parameter("testParameter");
        System.out.println(getUserActivity(p.getValue("Amplitude_User"),p.getValue("Amplitude_Activity_Limit")));
    }

    public static JSONObject getUserActivity(String user, String limit) throws Exception {
        try {
            TrustManager[] certs = trustAllCerts();
            OkHttpClient client = new OkHttpClient().newBuilder() .sslSocketFactory(SSLSocket(certs), (X509TrustManager) certs[0]).build();
            MediaType mediaType = MediaType.parse("text/plain");
            //RequestBody body = RequestBody.create(mediaType, "");
            Request request = new Request.Builder()
                    .url("https://analytics.eu.amplitude.com/api/2/useractivity?user="+user+"&limit="+limit)
                    .method("GET", null)
                    .addHeader("Authorization", "Basic Y2MxM2QxYmFmN2UwNWFiMTUwNjU5MzJmOTgyYWRkNjU6MGNlZTk0NTJjMTA2N2UxNzQ0YzE0NjQ2YTJkYTJjNGU=")
                    .build();
            Response response = client.newCall(request).execute();
            JSONObject Out = new JSONObject(response.body().string());
            response.close();
            return Out;
        }catch (Exception e) {
            System.out.println("Erreur lors de l'éxecution de la requete get useractivity.");
            e.fillInStackTrace();
        }
        return null;
    }

    public static TrustManager[] trustAllCerts() {
        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[] {
                new X509TrustManager() {
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
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
        } catch (GeneralSecurityException e) {
        }
        return trustAllCerts;
    }

    public static SSLSocketFactory SSLSocket(TrustManager[] certs) throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, certs, new SecureRandom());
        SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
        return  sslSocketFactory;
    }

}
