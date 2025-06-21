package am.martirosyan.userbot.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter
@Getter
@ConfigurationProperties(prefix = "telegram")
public class BotProperties {
    private String username;
    private String token;
    private long creatorId;
}
