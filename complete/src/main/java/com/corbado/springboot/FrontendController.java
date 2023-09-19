package com.corbado.springboot;

import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.SignedJWT;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Controller
public class FrontendController {

    @Value("${projectid}")
    private String projectID;

    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("PROJECT_ID", projectID);
        return "index";
    }


    @RequestMapping("/home")
    public String home(Model model, @CookieValue("cbo_short_session") String cboShortSession) {
        String issuer = "https://" + projectID + ".frontendapi.corbado.io";
        String jwks_uri = "https://" + projectID + ".frontendapi.corbado.io/.well-known/jwks";


        // Get the json file from the jwks_uri
        try {
            JSONObject json = JsonReader.readJsonFromUrl(jwks_uri);
     //       System.out.println(json.toString());
     //       System.out.println(json.getJSONArray("keys").getJSONObject(0));
     //       System.out.println(json.getJSONArray("keys").getJSONObject(0).getString("n"));
            System.out.println("Checkp 1");
            JSONObject publicKey = json.getJSONArray("keys").getJSONObject(0);

            System.out.println("publicKey: " + publicKey.toString());

            System.out.println("Checkp 2");
            SignedJWT signedJWT = SignedJWT.parse(cboShortSession);

            System.out.println("Checkp 3");
            RSAKey rsaKey = RSAKey.parse(publicKey.toString());


            System.out.println("Checkp 4");
            JWSVerifier verifier = new RSASSAVerifier(rsaKey);
            boolean isValid = signedJWT.verify(verifier);
            System.out.println("isValid: " + isValid);


            JSONObject payloadJSON = new JSONObject(signedJWT.getPayload().toJSONObject());
            String kid = payloadJSON.getString("iss");
            if (!kid.equals(issuer)) {
                model.addAttribute("ERROR", "JWT token issuer does not match!");
                return "error";
                //	throw new Exception("JWT token issuer does not match!");
            }

            model.addAttribute("PROJECT_ID", projectID);
            model.addAttribute("USER_ID", json);
            model.addAttribute("USER_NAME", publicKey);
            model.addAttribute("USER_EMAIL", "payload");
            return "home";
        } catch (IOException e) {
            System.out.println(e.getMessage());
            model.addAttribute("ERROR", e.getMessage());
            return "error";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            model.addAttribute("ERROR", "Could not verify JWT signature! " + e.getMessage());
            return "error";
        }
    }
}
