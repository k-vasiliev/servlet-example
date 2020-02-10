import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import service.notifier.KafkaNotifier;
import service.notifier.ResumeNotifier;

@Configuration
public class TestConfig {

  @Bean
  ResumeNotifier resumeNotifier() {
    return new KafkaNotifier();
  }

}
