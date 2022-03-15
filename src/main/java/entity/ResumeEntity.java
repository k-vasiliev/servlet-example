package entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "resume")
public class ResumeEntity {

  public ResumeEntity() {
  }

  public ResumeEntity(String title, String role) {
    this.title = title;
    this.role = role;
  }

  @Id
  @GeneratedValue
  private Integer id;

  @Column(name = "title", nullable = false)
  private String title;

  @Column(name = "role", nullable = false)
  private String role;

  @OneToOne
  @JoinColumn(name = "archived_by")
  private UserEntity archivedBy;

  public LocalDate getDateArchived() {
    return dateArchived;
  }

  public void setDateArchived(LocalDate dateArchived) {
    this.dateArchived = dateArchived;
  }

  @Column
  private LocalDate dateArchived;

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

  public UserEntity getArchivedBy() {
    return archivedBy;
  }

  public void setArchivedBy(UserEntity archivedBy) {
    this.archivedBy = archivedBy;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public void archive(UserEntity user) {
    archivedBy = user;
    dateArchived = LocalDate.now();
  }
}
