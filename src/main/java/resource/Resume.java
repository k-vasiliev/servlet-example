package resource;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;

public class Resume {

  public Resume() {

  }

  public Resume(String title) {
    this.title = title;
    this.dateCreate = LocalDate.now();
  }

  private String title;

  private LocalDate dateCreate;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public LocalDate getDateCreate() {
    return dateCreate;
  }

  public void setDateCreate(LocalDate dateCreate) {
    this.dateCreate = dateCreate;
  }
}
