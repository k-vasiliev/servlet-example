package service;

import dto.ResumeDto;
import entity.ResumeEntity;

public class ResumeMapper {

  public ResumeDto map(ResumeEntity resumeEntity) {
    return new ResumeDto(resumeEntity.getId(), resumeEntity.getTitle(), resumeEntity.getDateArchived() != null);
  }
}
