import com.google.api.client.googleapis.auth.oauth2.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import javax.servlet.http.*;
import java.io.IOException;

public class oauth_authentication_1_Req14 extends HttpServlet {
    private static final String CLIENT_ID = "Your-Client-ID";
    private static final String CLIENT_SECRET = "Your-Client-Secret";
    private static final String CALLBACK_URI = "http://localhost:8080/oauth2callback";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
            new NetHttpTransport(),
            JacksonFactory.getDefaultInstance(),
            CLIENT_ID, 
            CLIENT_SECRET,
            Arrays.asList("https://www.googleapis.com/auth/userinfo.profile", "https://www.googleapis.com/auth/userinfo.email"))
            .setAccessType("online")
            .setApprovalPrompt("auto").build();

        String url = flow.newAuthorizationUrl().setRedirectUri(CALLBACK_URI).build();
        res.setStatus(HttpServletResponse.SC_FOUND);
        res.setHeader("Location", url);
    }
}