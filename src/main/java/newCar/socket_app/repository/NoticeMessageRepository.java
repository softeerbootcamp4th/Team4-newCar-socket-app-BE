package newCar.socket_app.repository;

import newCar.socket_app.model.chat.NoticeMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeMessageRepository extends JpaRepository<NoticeMessage, Long> {
}
