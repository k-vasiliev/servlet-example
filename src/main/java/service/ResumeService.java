package service;

import dao.ResumeDao;
import dao.UserDao;
import entity.ResumeEntity;
import entity.UserEntity;
import service.notifier.ResumeNotifier;

import javax.ws.rs.NotFoundException;

public class ResumeService {

  private final ResumeDao resumeDao;
  private final UserDao userDao;
  private final ResumeNotifier resumeNotifier;

  private static Integer ADMIN_ID = 1;

  public ResumeService(ResumeDao resumeDao, UserDao userDao, ResumeNotifier resumeNotifier) {
    this.resumeDao = resumeDao;
    this.userDao = userDao;
    this.resumeNotifier = resumeNotifier;
  }

  public void archiveResume(Integer resumeId) {
    // transaction problem!

    ResumeEntity resume = resumeDao.getResume(resumeId).orElseThrow(NotFoundException::new);
    UserEntity user = userDao.getUser(ADMIN_ID).orElseThrow(NotFoundException::new);

    resume.archive(user);
    user.setResumeArchivedCount(user.getResumeArchivedCount() + 1);
    userDao.save(user);
    resumeDao.save(resume);
    resumeNotifier.notifyResumeArchived();
  }
}
