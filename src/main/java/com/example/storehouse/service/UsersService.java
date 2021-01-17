package com.example.storehouse.service;

import static com.example.storehouse.util.UsersUtil.fromUserTo;
import static com.example.storehouse.util.UsersUtil.prepareToSave;
import static com.example.storehouse.util.ValidationUtil.addMessageDetails;
import static com.example.storehouse.util.ValidationUtil.assureIdConsistent;
import static com.example.storehouse.util.ValidationUtil.checkNotFound;
import static org.springframework.util.StringUtils.hasText;

import com.example.storehouse.dto.UserTo;
import com.example.storehouse.model.User;
import com.example.storehouse.repository.UsersRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UsersService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    public List<User> get(String email) {
        return hasText(email) ?
            List.of(checkNotFound(usersRepository.findByEmail(email),
                addMessageDetails(User.class.getSimpleName(), email)))
            : usersRepository.findAll();
    }

    public User getById(Integer id) {
        return checkNotFound(usersRepository.findById(id),
                             addMessageDetails(User.class.getSimpleName(), id));
    }

    @Transactional
    public User create(UserTo userTo) {
        User newUser = fromUserTo(userTo);
        return usersRepository.save(prepareToSave(newUser));
    }

    @Transactional
    public User update(UserTo userTo, Integer id) {
        User updatedUser = fromUserTo(userTo);
        //TODO переделать проверку через HasId
        assureIdConsistent(updatedUser, id);
        return usersRepository.save(prepareToSave(updatedUser));
    }

    @Transactional
    public void delete(Integer id) {
        usersRepository.deleteById(id);
    }

}
