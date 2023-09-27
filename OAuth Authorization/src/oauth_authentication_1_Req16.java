import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.SecureRandom;
import java.util.Collections;

public class oauth_authentication_1_Req16 extends HttpServlet {

    private static final String CLIENT_SECRET_FILE = "/path_to_your_client_secret_file.json";
    private static final JacksonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String REDIRECT_URI = "http://localhost:8080/oauth2callback";
    private static final Collection<String> SCOPES = Collections.singleton("https://www.googleapis.com/auth/userinfo.email");

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, 
                                  new InputStreamReader(oauth_authentication_1_Req16.class.getResourceAsStream(CLIENT_SECRET_FILE)));
        
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                new NetHttpTransport(), 
                JSON_FACTORY, 
                clientSecrets, 
                SCOPES)
                .build();
                
        String state = new BigInteger(130, new SecureRandom()).toString(32);
        req.getSession().setAttribute("state", state);
        
        resp.sendRedirect(flow.newAuthorizationUrl()
                .setRedirectUri(REDIRECT_URI)
                .setState(state)
                .setApprovalPrompt("auto")
                .setAccessType("offline")
                .build());
    }

    // handle the result from the authorization server
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // Read the state from the incoming request, and compare it to the
        // original protected state where we stored it. If it doesn't match
        // we have a CSRF attack.
        String state = req.getParameter("state");
        if (!state.equals(req.getSession().getAttribute("state"))) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        // exchange the authorization code
        Credential credential = flow.exchangeCodeForToken(req.getParameter("code"));

        // store the credentials and the user identity.
        req.getSession().setAttribute("google-credential", credential);
        req.getSession().setAttribute("user", credential.getUserInfo().getEmail());

        // return to the index
        resp.sendRedirect("/index.html");
    }
}