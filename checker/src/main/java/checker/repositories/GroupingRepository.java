package checker.repositories;

import checker.entities.Grouping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import checker.enums.GroupIdentifier;

import java.util.Optional;

@Repository
public interface GroupingRepository extends JpaRepository<Grouping,Long> {

}
