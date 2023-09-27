    public class oauth_authentication_1_Req4 {
      private static final String CLIENT_ID = "your-client-id";
      private static final String CLIENT_SECRET = "your-client-secret";
      private static final String REDIRECT_URI = "http://localhost:8080/callback";

      // ...

      public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Redirect to Google for user authentication
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(NetHttpTransport(), JacksonFactory())
          .setAudience(Collections.singletonList(CLIENT_ID))
          .build();

        String idToken = request.getParameter("id_token");

        GoogleIdToken idToken = verifier.verify(idToken);
        if (idToken != null) {
          Payload payload = idToken.getPayload();

          // Get profile information from payload
          String email = payload.getEmail();
          
          // Check if email is verified
          boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
          if(emailVerified){
            // Redirect to your index page
            response.sendRedirect("index.html");
          }
          else{
            // Redirect back to login page
            response.sendRedirect("login.html");
          }
        } else {
          System.out.println("Invalid ID token.");
        }
      }
    }