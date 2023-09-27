package com.example.google_sso_demo;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class oauth_authentication_1_Req3 {

    private static final String CLIENT_ID = "Your-Google-OAuth-Client-ID";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        // Obtain the token from client's Google Login
        String idTokenString = request.getParameter("id_token");

        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                .setAudience(Collections.singletonList(CLIENT_ID))
                .build();

        try {
            GoogleIdToken idToken = verifier.verify(idTokenString);
            if (idToken != null) {
                // User's ID is found in getId() of payload, verify it with your user system.
                GoogleIdToken.Payload payload = idToken.getPayload();
                String userId = payload.getSubject();

                // The user is authenticated, you can allow login now. Store the userId in a session variable.
                request.getSession().setAttribute("authenticatedUser", userId);

                // Redirect user to index page
                response.sendRedirect("index.jsp");
            } else {
                // Invalid token (could be expired or tampered), redirect back to login page.
                response.sendRedirect("login.jsp");
            }
        } catch (Exception e) {
            throw new RuntimeException("Problem verifying token", e);
        }
    }
}