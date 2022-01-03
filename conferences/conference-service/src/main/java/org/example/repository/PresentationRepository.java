package org.example.repository;

import org.example.entity.Presentation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PresentationRepository extends JpaRepository<Presentation, Long> {

    @Query("select 1 " +
            "from User u " +
            "left join u.presenterPresentations p " +
            "left join p.schedule s " +
            "where " +
            "    u.id = :presenterId " +
            "    and (:presentationId is null or p.id <> :presentationId) " +
            "    and s.startDateTime < :newEndDateTime " +
            "    and s.endDateTime > :newStartDateTime ")
    Optional<Boolean> checkPresenterOverlaps(@Param("presenterId") Long presenterId,
                                             @Param("presentationId") Long presentationId,
                                             @Param("newStartDateTime") LocalDateTime newStartDateTime,
                                             @Param("newEndDateTime") LocalDateTime newEndDateTime);

    @Query("select 1 " +
            "from Room r " +
            "left join r.schedule s " +
            "left join s.presentation p " +
            "where " +
            "    r.id = :roomId " +
            "    and (:presentationId is null or p.id <> :presentationId) " +
            "    and s.startDateTime < :newEndDateTime " +
            "    and s.endDateTime > :newStartDateTime ")
    Optional<Boolean> checkScheduleOverlaps(@Param("roomId") Long roomId,
                                            @Param("presentationId") Long presentationId,
                                            @Param("newStartDateTime") LocalDateTime newStartDateTime,
                                            @Param("newEndDateTime") LocalDateTime newEndDateTime);
}
