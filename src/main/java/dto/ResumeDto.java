package dto;

public class ResumeDto {

  private Integer id;
  private String title;
  private String role;
  private boolean isArchived;

  public ResumeDto() {
  }

  public ResumeDto(Integer id, String title, boolean isArchived) {
    this.id = id;
    this.title = title;
    this.isArchived = isArchived;
  }

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

  public boolean isArchived() {
    return isArchived;
  }

  public void setArchived(boolean archived) {
    isArchived = archived;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }
}
