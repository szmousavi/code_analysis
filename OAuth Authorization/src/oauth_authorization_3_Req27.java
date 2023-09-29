public class oauth_authorization_3_Req27 {
    private static String REDIRECT_URI = "urn:ietf:wg:oauth:2.0:oob";
    private static JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    public static void main(String[] args) {
        try {
            GoogleClientSecrets clientSecrets = 
                GoogleClientSecrets.load(
                    JSON_FACTORY, 
                    new InputStreamReader(oauth_authorization_3_Req27.class.getResourceAsStream("/client_secrets.json")) );

            GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                new NetHttpTransport(), 
                JSON_FACTORY, 
                clientSecrets, 
                Collections.singletonList("https://www.googleapis.com/auth/contacts.readonly"))
                .setAccessType("offline").setApprovalPrompt("force").build();

            String url = flow.newAuthorizationUrl().setRedirectUri(REDIRECT_URI).build();
            System.out.println("Please open the following URL in a web browser then type the authorization code:\n" + url);

            // Read code typed by user.
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Enter code: ");
            String code = reader.readLine();

            // Generate Credential using retrieved code.
            GoogleTokenResponse response = flow.newTokenRequest(code).setRedirectUri(REDIRECT_URI).execute();
            GoogleCredential credential = new GoogleCredential().setFromTokenResponse(response);

            // Use a credential to access the API.
            // Example: Use the credential to access the Google People API
            // The specific code to call the API is out of scope for this implementation.

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}