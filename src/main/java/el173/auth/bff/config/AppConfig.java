package el173.auth.bff.config;

import el173.auth.bff.enums.AppName;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author hashithkarunarathne
 * @project Auth-BFF
 * @created 23/11/2024 - 15:00
 */
@Configuration
public class AppConfig {

    @Value("${app.auth.config.app1.callback}")
    private String app1Callback;

    @Value("${app.auth.config.app2.callback}")
    private String app2Callback;

    @PostConstruct
    public void initializeEnumValues() {
        AppName.APP1.setCallbackUrl(app1Callback);
        AppName.APP2.setCallbackUrl(app2Callback);
    }
}
