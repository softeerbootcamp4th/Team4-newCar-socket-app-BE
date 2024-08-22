package newCar.socket_app.repository;

import newCar.socket_app.model.entity.ChatMessageEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessageEntity, Long> {
    public List<ChatMessageEntity> findAllByOrderByIdDesc(Pageable pageable);
}
