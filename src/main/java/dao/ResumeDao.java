package dao;

import entity.ResumeEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ResumeDao {

  private final SessionFactory sessionFactory;

  public ResumeDao(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  public List<ResumeEntity> getResumes(Integer limit) {
    Session session = getSessionFactory().getCurrentSession();
    return session
        .createQuery("SELECT r FROM ResumeEntity r", ResumeEntity.class)
        .setMaxResults(limit)
        .list();
  }

  public Optional<ResumeEntity> getResume(Integer id) {
    Session session = getSessionFactory().getCurrentSession();
    try {
      ResumeEntity resume = session
          .createQuery("SELECT r FROM ResumeEntity r WHERE r.id = :id", ResumeEntity.class)
          .setParameter("id", id)
          .getSingleResult();

      return Optional.of(resume);
    } catch (RuntimeException e) {
      return Optional.empty();
    }
  }

  public Integer save(ResumeEntity resumeEntity) {
    Session session = getSessionFactory().getCurrentSession();
    session.save(resumeEntity);
    return resumeEntity.getId();
  }

  private SessionFactory getSessionFactory() {
    return sessionFactory;
  }
}
