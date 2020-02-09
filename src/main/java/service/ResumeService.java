package service;

import dao.ResumeDao;
import dao.UserDao;
import entity.ResumeEntity;
import entity.UserEntity;
import service.notifier.RabbitNotifier;
import service.notifier.ResumeNotifier;

import javax.ws.rs.NotFoundException;

public class ResumeService {

  private static Integer ADMIN_ID = 1;

  public void archiveResume(Integer resumeId) {
    // transaction problem!

    ResumeDao resumeDao = new ResumeDao();
    UserDao userDao = new UserDao();
    ResumeNotifier resumeNotifier = new RabbitNotifier();

    ResumeEntity resume = resumeDao.getResume(resumeId).orElseThrow(NotFoundException::new);
    UserEntity user = userDao.getUser(ADMIN_ID).orElseThrow(NotFoundException::new);

    resume.archive(user);
    user.setResumeArchivedCount(user.getResumeArchivedCount() + 1);
    userDao.save(user);
    resumeDao.save(resume);
    resumeNotifier.notifyResumeArchived();
  }
}
