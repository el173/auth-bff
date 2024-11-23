package el173.auth.bff.controller;

import el173.auth.bff.enums.AppName;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

/**
 * @author hashithkarunarathne
 * @project Auth-BFF
 * @created 23/11/2024 - 13:46
 */
@Controller
public class CustomLoginController {

    @Value("${app.auth.config.authorizationUrl}")
    private String authorizationUrl;

    @Value("${app.auth.config.app1.callback}")
    private String app1Callback;

    @Value("${app.auth.config.app2.callback}")
    private String app2Callback;

    @RequestMapping(value = "/app-login", method = RequestMethod.GET)
    public void customLoginPage(@RequestParam(required = true) String appName,
                                HttpSession session,
                                HttpServletRequest request,
                                HttpServletResponse response) throws IOException {

        if (appName == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "App name is required");
            return;
        }

        AppName appEnum = AppName.getAppEnum(appName);
        if (appEnum != null) {
            session.setAttribute("redirectUri", appEnum.getCallbackUrl());
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid app name");
            return;
        }

        response.sendRedirect(authorizationUrl);
    }

}
