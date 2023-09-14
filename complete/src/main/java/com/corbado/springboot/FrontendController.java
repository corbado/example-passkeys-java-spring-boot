package com.corbado.springboot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

@RestController
public class FrontendController {

	@Autowired
	private Environment environment;

	@GetMapping("/")
	public String index() {
		String projectID = environment.getProperty("environment.projectid");
		return "<!DOCTYPE html>" +
				"<html>" +
				"" +
				"<head>" +
				"    <title>Corbado</title>" +
				"</head>" +
				"" +
				"<body>" +
				"<script src=\"https://" + projectID + ".frontendapi.corbado.io/auth.js\"></script>" +
				"<corbado-auth-provider project-id=" + projectID + ">" +
				"    <div slot=\"unauthed\">" +
				"        <corbado-auth project-id=" + projectID + " conditional=\"yes\">" +
				"            <input name=\"username\" id=\"corbado-username\" data-input=\"username\" required autocomplete=\"webauthn\" />" +
				"        </corbado-auth>" +
				"    </div>" +
				"" +
				"    <div slot=\"authed\">" +
				"        You're logged in. Click <a href=\"/profile\">here</a> to visit the profile page." +
				"    </div>" +
				"" +
				"</corbado-auth-provider>" +
				"</body>" +
				"" +
				"</html>";
	}


	@GetMapping("/profile")
	public String profile() {
		String projectID = environment.getProperty("environment.projectid");
		return "<!DOCTYPE html>" +
				"<html>" +
				"" +
				"<head>" +
				"    <title>Corbado</title>" +
				"</head>" +
				"<body>" +
				"    <!-- Import Corbado SDK -->" +
				"    <script src=\"https://" + projectID + ".frontendapi.corbado.io/auth.js\"></script>" +
				"    <script src=\"https://" + projectID + ".frontendapi.corbado.io/utility.js\"></script>" +
				"    <script>" +
				"        const session = new Corbado.Session('" + projectID + "');" +
				"        session.refresh(user => {" +
				"            if (user === null) {" +
				"            } else {" +
				"                document.getElementById('info').innerHTML = `" +
				"                    User-ID: ${user.userID}" +
				"                    <br />" +
				"                    Email: ${user.email}" +
				"                `;" +
				"                console.log(" +
				"                    user.userID," +
				"                    user.userIdentifier," +
				"                    user.userFullName," +
				"                    user.email," +
				"                    user.phoneNumber" +
				"                )" +
				"            }" +
				"        })" +
				"    </script>" +
				"    <corbado-auth-provider project-id=" + projectID + ">" +
				"        <div slot=\"unauthed\">" +
				"            This content is only available to logged in users. You can log in <a href=\"/\">here</a>." +
				"        </div>" +
				"" +
				"        <div slot=\"authed\">" +
				"            <div>" +
				"                <h1>Profile Page</h1>" +
				"                <div>" +
				"                    <p id=\"info\"></p>" +
				"                </div>" +
				"            </div>" +
				"            <corbado-logout-handler project-id=" + projectID + " redirect-url=\"/\">" +
				"                <button>Logout</button>" +
				"            </corbado-logout-handler>" +
				"        </div>" +
				"" +
				"    </corbado-auth-provider>" +
				"</body>" +
				"" +
				"</html>";
	}

}
