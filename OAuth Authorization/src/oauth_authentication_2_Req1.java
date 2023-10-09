/* 
 * A Java Application to establish a Single Sign On (SSO) service 
 * for a web application using Google accounts for User authentication.
 * 
 * The main goal is to implement the authentication functionality.
 */
 
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.oauth2.Oauth2;
import com.google.api.services.oauth2.model.Userinfo;

import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;

public class oauth_authentication_2_Req1 {
    private static final String clientId = "YOUR_GOOGLE_CLIENT_ID";
    private static final String clientSecret = "YOUR_GOOGLE_CLIENT_SECRET";
    private static final String redirectUri = "YOUR_REDIRECT_URI";

    private static final String APPLICATION_NAME = "Google SSO Java Client";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    public static void main(String[] args) throws IOException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

        GoogleClientSecrets.Details web = new GoogleClientSecrets.Details();
        web.setClientId(clientId);
        web.setClientSecret(clientSecret);
        web.setRedirectUris(Collections.singletonList(redirectUri));

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
            HTTP_TRANSPORT, JSON_FACTORY, web, Collections.singleton("https://www.googleapis.com/auth/userinfo.profile"))
            .setAccessType("offline").setApprovalPrompt("auto").build();

        String url = flow.newAuthorizationUrl().setRedirectUri(redirectUri).build();
        System.out.println("Please open the following URL in your browser then type the authorization code:");
        System.out.println("  " + url);

        GoogleTokenResponse response = null; // Get response from user input
        /* Code to get token response from user input goes here */

        Oauth2 oauth2 = new Oauth2.Builder(HTTP_TRANSPORT, JSON_FACTORY, null)
            .setApplicationName(APPLICATION_NAME)
            .build();

        Userinfo userinfo = oauth2.userinfo().get().execute();

        System.out.println("User ID: " + userinfo.getId());
        System.out.println("User Email: " + userinfo.getEmail());
    }
}