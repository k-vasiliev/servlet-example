package service;

import dao.ResumeDao;
import dao.UserDao;
import entity.ResumeEntity;
import entity.UserEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.notifier.ResumeNotifier;

import javax.ws.rs.NotFoundException;
import java.util.List;

@Service
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

  @Transactional
  public List<ResumeEntity> getResumes(Integer limit) {
    return resumeDao.getResumes(limit);
  }

  @Transactional
  public void archiveResume(Integer resumeId) {
    ResumeEntity resume = resumeDao.getResume(resumeId).orElseThrow(NotFoundException::new);
    UserEntity user = userDao.getUser(ADMIN_ID).orElseThrow(NotFoundException::new);

    resume.archive(user);
    user.setResumeArchivedCount(user.getResumeArchivedCount() + 1);
    userDao.save(user);
    resumeDao.save(resume);
    resumeNotifier.notifyResumeArchived();
  }
}
