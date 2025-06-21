package am.martirosyan.userbot.repository;

import am.martirosyan.userbot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByChatId(Long chatId);

    User findByChatId(Long chatId);
}
