package newCar.socket_app.repository;

import newCar.socket_app.model.entity.NoticeMessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeMessageRepository extends JpaRepository<NoticeMessageEntity, Long> {
    NoticeMessageEntity findTopByOrderByIdDesc();
}
