package com.corbado.springboot;

import com.corbado.entities.SessionValidationResult;
import com.corbado.exceptions.StandardException;
import com.corbado.generated.model.Identifier;
import com.corbado.sdk.Config;
import com.corbado.sdk.CorbadoSdk;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FrontendController {

  /** The project ID. */
  @Value("${projectID}")
  private String projectID;

  /** The api secret. */
  @Value("${apiSecret}")
  private String apiSecret;

  /** The sdk. */
  private final CorbadoSdk sdk;

  /**
   * Index.
   *
   * @param model the model
   * @return the string
   * @throws StandardException
   */
  @Autowired
  public FrontendController(
      @Value("${projectID}") final String projectID, @Value("${apiSecret}") final String apiSecret)
      throws StandardException {
	// Or use Config builder
    final Config config = new Config(projectID, apiSecret);
    this.sdk = new CorbadoSdk(config);
  }

  /**
   * Index.
   *
   * @param model the model
   * @return the string
   */
  @RequestMapping("/")
  public String index(final Model model) {
    model.addAttribute("PROJECT_ID", projectID);
    return "index";
  }

  /**
   * Profile.
   *
   * @param model the model
   * @param cboShortSession the cbo short session
   * @return the string
   */
  @RequestMapping("/profile")
  public String profile(
      final Model model, @CookieValue("cbo_short_session") final String cboShortSession) {
    try {
      // Validate user from token
      final SessionValidationResult validationResp =
          sdk.getSessions().getAndValidateCurrentUser(cboShortSession);

      // get list of emails from identifier service
      List<Identifier> emails;
      emails = sdk.getIdentifiers().listAllEmailsByUserId(validationResp.getUserID());
      model.addAttribute("PROJECT_ID", projectID);
      model.addAttribute("USER_ID", validationResp.getUserID());
      model.addAttribute("USER_NAME", validationResp.getFullName());
      // select email of your liking or list all emails
      model.addAttribute("USER_EMAIL", emails.get(0).getValue());

    } catch (final Exception e) {
	  //Handle verification errors here
      model.addAttribute("ERROR", e.getMessage());
      return "error";
    }
    return "profile";
  }
}
