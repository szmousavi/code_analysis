import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet(
  name = "oauth_authentication_1_Req5",
  urlPatterns = {"/tokensignin"}
)
public class oauth_authentication_1_Req5 extends HttpServlet {

  @Override
  protected void service(javax.servlet.http.HttpServletRequest req, javax.servlet.http.HttpServletResponse resp) {
    // Code to initialize your application, load configurations, etc.
    // This method redirects the request and response objects to your TokenServlet class.
    new TokenServlet().service(req, resp);
  }
}