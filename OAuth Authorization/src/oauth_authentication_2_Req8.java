// Task: oauth_authentication_2
// Develop a Java program to establish a Single-Sign-On (SSO) service for a web application, using Google accounts for user authentication. 
// The primary goal is to only implement the authentication functionality, and other operations, such as designing user interface, are not within the scope of this task.
// Note: The application has been already registered with the Google service provider and the necessary information (e.g., redirect URI, client ID, client secret) has been obtained.

// This Java application establishes a Single-Sign-On service to a web application 
// using Google Accounts for user authentication.

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

import java.io.IOException;
import java.util.Arrays;

public class oauth_authentication_2_Req8 {
    private static final String CLIENT_ID = "Your Google Client ID";
    private static final String CLIENT_SECRET = "Your Google Client Secret";
    private static final String REDIRECT_URI = "Your registered redirect URI";

    public static void main(String[] args) throws IOException {
        // The code parameter is the authorization code received from the Google authorization server.
        String code = "authorization code";

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                new NetHttpTransport(),
                JacksonFactory.getDefaultInstance(),
                CLIENT_ID,
                CLIENT_SECRET,
                Arrays.asList("email","profile"))
                .setAccessType("offline")
                .build();

        GoogleTokenResponse tokenResponse = flow.newTokenRequest(code)
                .setRedirectUri(REDIRECT_URI)
                .execute();

        // Use GoogleIdToken to extract the GoogleIdToken.Payload to get user's Google account details.
        GoogleIdToken idToken = tokenResponse.parseIdToken();
        Payload payload = idToken.getPayload();

        System.out.println("User ID: " + payload.getSubject());
        System.out.println("Email: " + payload.getEmail());
    }
}
