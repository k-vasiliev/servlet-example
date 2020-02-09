package entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "resume")
public class ResumeEntity {

  public ResumeEntity() {
  }

  public ResumeEntity(String title) {
    this.title = title;
  }

  @Id
  @GeneratedValue
  private Integer id;

  @Column(name = "title", nullable = false)
  private String title;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }
}