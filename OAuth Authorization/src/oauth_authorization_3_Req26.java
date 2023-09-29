public class oauth_authorization_3_Req26 {
    private static final String APPLICATION_NAME = "Social Networking Application";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final List<String> SCOPES = Arrays.asList("https://www.googleapis.com/auth/contacts.readonly");

    private static final String CLIENT_SECRET_FILE = "/path/to/your/client_secret.json";
    
    public static void main(String[] args) throws IOException, GeneralSecurityException { 
        // Build flow and trigger user authorization request
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new FileReader(CLIENT_SECRET_FILE));
        GoogleAuthorizationCodeFlow flow =
            new GoogleAuthorizationCodeFlow.Builder(
                    GoogleNetHttpTransport.newTrustedTransport(),
                    JSON_FACTORY,
                    clientSecrets,
                    SCOPES)
            .setDataStoreFactory(new FileDataStoreFactory(new java.io.File("tokens")))
            .setAccessType("offline")
            .build();
            
        // After this point, you would redirect the user to the authorization URL and handle the authorization code response.
        // Once authorization is complete, a token will be stored in the "tokens" directory for future use without needing user intervention
    }
}