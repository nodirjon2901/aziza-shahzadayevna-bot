package uz.result.azizashahzadayevnabot.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.result.azizashahzadayevnabot.model.Counter;

@Repository
public interface CounterRepository extends JpaRepository<Counter, Long> {

    @Transactional
    @Modifying
    @Query(value = "update counter set count_application=:numberApp where id=:counterId", nativeQuery = true)
    void update(@Param("counterId") Long counterId, @Param("numberApp") Long numberApp);

}
