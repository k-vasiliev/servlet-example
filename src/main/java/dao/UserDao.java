package dao;

import entity.UserEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserDao {

  private final SessionFactory sessionFactory;

  public UserDao(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  public Optional<UserEntity> getUser(Integer userId) {
    Session session = getSessionFactory().getCurrentSession();
    try {
      UserEntity user = session.createQuery("SELECT u From UserEntity u WHERE u.id = :id", UserEntity.class)
          .setParameter("id", userId)
          .getSingleResult();
      return Optional.of(user);
    } catch (RuntimeException e) {
      return Optional.empty();
    }
  }

  public Integer save(UserEntity user) {
    Session session = getSessionFactory().getCurrentSession();
    session.saveOrUpdate(user);
    return user.getId();
  }

  private SessionFactory getSessionFactory() {
    return sessionFactory;
  }
}
