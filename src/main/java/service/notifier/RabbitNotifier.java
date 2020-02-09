package service.notifier;

import org.springframework.stereotype.Service;

@Service
public class RabbitNotifier implements ResumeNotifier {

  @Override
  public void notifyResumeArchived() {
    System.out.println("Notify from rabbit");
  }
}
