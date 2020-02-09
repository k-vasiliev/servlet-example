package entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "hh_user")
public class UserEntity {

  public UserEntity() {
  }

  @Id
  @GeneratedValue
  private Integer id;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "resume_archived_count", nullable = false)
  private int resumeArchivedCount;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getResumeArchivedCount() {
    return resumeArchivedCount;
  }

  public void setResumeArchivedCount(Integer resumeArchivedCount) {
    this.resumeArchivedCount = resumeArchivedCount;
  }
}
