package checker.repositories;

import checker.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {
//    @Query("SELECT * FROM task t INNER JOIN grouping_tasks gt ON t.task_id = gt.task_id INNER JOIN groupclass g ON g.grouping_id=gt.grouping_id")
//    List<Task> getTasksByGroupAndSubject();
}