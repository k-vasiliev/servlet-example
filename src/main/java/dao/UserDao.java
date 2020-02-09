package dao;

import entity.UserEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.DatabaseUtil;

import java.util.Optional;

public class UserDao {

  public Optional<UserEntity> getUser(Integer userId) {
    Session session = getSessionFactory().getCurrentSession();
    session.beginTransaction();
    try {
      UserEntity user = session.createQuery("SELECT u From UserEntity u WHERE u.id = :id", UserEntity.class)
          .setParameter("id", userId)
          .getSingleResult();
      session.getTransaction().commit();
      return Optional.of(user);
    } catch (RuntimeException e) {
      return Optional.empty();
    }
  }

  public Integer save(UserEntity user) {
    Session session = getSessionFactory().getCurrentSession();
    session.beginTransaction();
    session.saveOrUpdate(user);
    session.getTransaction().commit();
    return user.getId();
  }

  private SessionFactory getSessionFactory() {
    return DatabaseUtil.getSessionFactory();
  }
}
