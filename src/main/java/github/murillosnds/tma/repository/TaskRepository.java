package github.murillosnds.tma.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import github.murillosnds.tma.entity.Task;

public interface TaskRepository extends JpaRepository<Task, UUID> {}