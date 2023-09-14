package com.corbado.springboot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

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
		model.addAttribute("PROJECT_ID", projectID);
		model.addAttribute("USER_ID", projectID);
		model.addAttribute("USER_NAME", cboShortSession);
		model.addAttribute("USER_EMAIL", cboShortSession);
		return "home";
	}
}
