package service.notifier;

public class RabbitNotifier implements ResumeNotifier {

  @Override
  public void notifyResumeArchived() {
    System.out.println("Notify from rabbit");
  }
}
