import com.google.auth.oauth2.TokenVerifier;
import com.google.auth.oauth2.GoogleIdTokenVerifier;
import com.google.auth.oauth2.GoogleIdToken;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class oauth_authentication_1_Req24 extends HttpServlet {

  public void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {
    // get token from request
    String idTokenString = req.getAttribute("id_token");
    
    // Initialize verifier
    GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
        .setAudience(Collections.singletonList(CLIENT_ID))
        .build();

    GoogleIdToken idToken = verifier.verify(idTokenString);
    if (idToken != null) {
      // successful login
      resp.sendRedirect("index.html");
    } else {
      // invalid or expired token
      resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }
  }
}