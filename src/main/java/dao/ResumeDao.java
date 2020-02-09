package dao;

import entity.ResumeEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import util.DatabaseUtil;

import java.util.List;
import java.util.Optional;

@Repository
public class ResumeDao {

  public List<ResumeEntity> getResumes(Integer limit) {
    Session session = getSessionFactory().getCurrentSession();
    session.beginTransaction();
    List<ResumeEntity> resumes = session
        .createQuery("SELECT r FROM ResumeEntity r", ResumeEntity.class)
        .setMaxResults(limit)
        .list();
    session.getTransaction().commit();
    return resumes;
  }

  public Optional<ResumeEntity> getResume(Integer id) {
    Session session = getSessionFactory().getCurrentSession();
    session.beginTransaction();
    try {
      ResumeEntity resume = session
          .createQuery("SELECT r FROM ResumeEntity r WHERE r.id = :id", ResumeEntity.class)
          .setParameter("id", id)
          .getSingleResult();

      session.getTransaction().commit();
      return Optional.of(resume);
    } catch (RuntimeException e) {
      return Optional.empty();
    }
  }

  public Integer save(ResumeEntity resumeEntity) {
    Session session = getSessionFactory().getCurrentSession();
    session.beginTransaction();
    session.save(resumeEntity);
    session.getTransaction().commit();
    return resumeEntity.getId();
  }

  private SessionFactory getSessionFactory() {
    return DatabaseUtil.getSessionFactory();
  }
}
