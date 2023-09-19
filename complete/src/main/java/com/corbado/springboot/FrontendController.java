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

        try {
            JSONObject json = JsonReader.readJsonFromUrl(jwks_uri);
            JSONObject publicKey = json.getJSONArray("keys").getJSONObject(0);
            SignedJWT signedJWT = SignedJWT.parse(cboShortSession);
            RSAKey rsaKey = RSAKey.parse(publicKey.toString());
            JWSVerifier verifier = new RSASSAVerifier(rsaKey);
            boolean isValid = signedJWT.verify(verifier);
            if (!isValid) {
                model.addAttribute("ERROR", "JWT token is not valid!");
                return "error";
            }

            JSONObject payloadJSON = new JSONObject(signedJWT.getPayload().toJSONObject());
            String kid = payloadJSON.getString("iss");
            if (!kid.equals(issuer)) {
                model.addAttribute("ERROR", "JWT token issuer does not match!");
                return "error";
            }

            model.addAttribute("PROJECT_ID", projectID);
            model.addAttribute("USER_ID", payloadJSON.get("sub"));
            model.addAttribute("USER_NAME", payloadJSON.get("name"));
            model.addAttribute("USER_EMAIL", payloadJSON.get("email"));
            return "home";

        } catch (Exception e) {
            System.out.println(e.getMessage());
            model.addAttribute("ERROR", e.getMessage());
            return "error";
        }
    }
}
