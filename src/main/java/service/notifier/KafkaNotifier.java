package service.notifier;

public class KafkaNotifier implements ResumeNotifier {

  @Override
  public void notifyResumeArchived() {
    System.out.println("Notify from kafka");
  }
}
