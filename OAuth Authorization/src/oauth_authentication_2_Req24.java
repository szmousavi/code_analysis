// Import required libraries
import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;

// oauth_authentication_2_Req24 class
public class oauth_authentication_2_Req24 {
  // Set up the Google OAuth 2.0 authorization code flow on application startup.
  private static final AuthorizationCodeFlow authorizationCodeFlow = initializeFlow();

  // Initialize the flow
  private static AuthorizationCodeFlow initializeFlow() {
    // Load client secrets
    InputStream in = oauth_authentication_2_Req24.class.getResourceAsStream("/client_secrets.json");
    GoogleClientSecrets clientSecrets = null;
    try {
      clientSecrets = GoogleClientSecrets.load(
              JacksonFactory.getDefaultInstance(), new InputStreamReader(in))
              .setRedirectUri("YOUR_REDIRECT_URI");
    } catch (IOException e) {
      e.printStackTrace();
    } 

    // Build flow
    return new GoogleAuthorizationCodeFlow.Builder(
            new NetHttpTransport(),
            JacksonFactory.getDefaultInstance(),
            clientSecrets,
            Collections.singleton("https://www.googleapis.com/auth/userinfo.email"))
            .setAccessType("offline") 
            .build();
  }

  // Get redirection URL
  public static String getRedirectURL() {
    return authorizationCodeFlow.newAuthorizationUrl()
            .setClientId("YOUR_CLIENT_ID")
            .set("access_type", "offline") 
            .set("prompt", "consent")
            .setRedirectUri("YOUR_REDIRECT_URI")
            .setState("state_parameter_passthrough_value") 
            .build();
  }

  // Get credentials
  public static Credential exchangeCode(String authorizationCode) {
    Credential credential = null;
    try {
      credential = new GoogleAuthorizationCodeTokenRequest(
                new NetHttpTransport(),
                JacksonFactory.getDefaultInstance(),
                "https://oauth2.googleapis.com/token",
                "YOUR_CLIENT_ID",
                "YOUR_CLIENT_SECRET",
                authorizationCode,
                "YOUR_REDIRECT_URI")
                .execute()
                .createScoped(Collections.singleton("https://www.googleapis.com/auth/userinfo.email"));
    } catch (IOException e) {
      e.printStackTrace();
    } 

    return credential;
  }
}