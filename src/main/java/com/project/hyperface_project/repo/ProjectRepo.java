package com.project.hyperface_project.repo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.project.hyperface_project.model.*;

@Repository
public interface ProjectRepo extends JpaRepository<Project,Long> {
}
