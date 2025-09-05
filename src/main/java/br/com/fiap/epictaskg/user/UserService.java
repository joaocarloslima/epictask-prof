package br.com.fiap.epictaskg.user;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User register(OAuth2User principal) {
        var user = userRepository.findByEmail(principal.getAttributes().get("email").toString());
        return user.orElseGet(() -> userRepository.save(new User(principal)));
    }

    public List<User> getRanking() {
        return userRepository.findAllByOrderByScoreDesc();
    }

    public void addScore(User user, int score) {
        user.setScore(user.getScore() + score);
        userRepository.save(user);
    }
}
