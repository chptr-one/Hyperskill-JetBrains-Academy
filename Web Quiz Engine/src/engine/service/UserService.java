package engine.service;

import engine.exception.DuplicateEmailException;
import engine.model.User;
import engine.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format("No user %s found", email)));
    }

    public Long registerNewUser(String email, String password) {
        try {
            String encodedPassword = encoder.encode(password);
            User user = new User(email, encodedPassword);
            return userRepository.save(user).getId();
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateEmailException();
        }
    }
}

