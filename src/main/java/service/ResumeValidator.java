package service;

import dto.ResumeDto;

public class ResumeValidator {

  public void validate(ResumeDto resumeDto) {
    String title = resumeDto.getTitle();
    String role = resumeDto.getRole();

    if (title == null || title.isEmpty()) {
      throw new IllegalArgumentException("Title is not present");
    }
    if (role == null || role.isEmpty()) {
      throw new IllegalArgumentException("Role is not present");
    }
  }
}
