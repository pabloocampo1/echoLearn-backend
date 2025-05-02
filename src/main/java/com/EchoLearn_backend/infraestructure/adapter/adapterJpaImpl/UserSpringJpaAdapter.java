package com.EchoLearn_backend.infraestructure.adapter.adapterJpaImpl;



import com.EchoLearn_backend.domain.model.User;
import com.EchoLearn_backend.domain.port.UserPersistencePort;

import com.EchoLearn_backend.infraestructure.adapter.entity.ProfileEntity;
import com.EchoLearn_backend.infraestructure.adapter.entity.RoleEntity;
import com.EchoLearn_backend.infraestructure.adapter.entity.UserEntity;
import com.EchoLearn_backend.infraestructure.adapter.mapper.UserDboMapper;
import com.EchoLearn_backend.infraestructure.adapter.repository.ProfileRepository;
import com.EchoLearn_backend.infraestructure.adapter.repository.RoleCrudRepository;
import com.EchoLearn_backend.infraestructure.adapter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserSpringJpaAdapter implements UserPersistencePort {
    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final UserDboMapper userDboMapper;

    @Autowired
    private final ProfileRepository profileRepository;

    @Autowired
    private final RoleCrudRepository roleCrudRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserSpringJpaAdapter(UserRepository userRepository, UserDboMapper userDboMapper, ProfileRepository profileRepository, RoleCrudRepository roleCrudRepository) {
        this.userRepository = userRepository;
        this.userDboMapper = userDboMapper;
        this.profileRepository = profileRepository;
        this.roleCrudRepository = roleCrudRepository;
    }

    public Optional<UserEntity> findByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }


    @Override
    public User save(User user) {
        // create instance
        UserEntity userEntity = this.userDboMapper.toDbo(user);
        ProfileEntity profile = new ProfileEntity();
        RoleEntity role = this.roleCrudRepository.findById(2)
                .orElseThrow(() -> new UsernameNotFoundException("ROLE NO FOUND"));

        // initial config - profile
        profile.setUser(userEntity);
        profile.setAvailable(true);

        // initial config - user
        userEntity.setRole(role);
        userEntity.setProfile(profile);
        userEntity.setPassword(this.passwordEncoder.encode(userEntity.getPassword()));

        return this.userDboMapper.toDomain(this.userRepository.save(userEntity));
    }

    @Override
    public Boolean existByEmail(String email) {
        return this.userRepository.existsByEmail(email);
    }

    @Override
    public User findByEmail(String email) {
        UserEntity user = this.userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("USER WITH THAT EMAIL, NO EXIST"));
        return this.userDboMapper.toDomain(user) ;
    }

    @Override
    public List<User> getAll() {
        List<UserEntity> userEntityList = (List<UserEntity>) this.userRepository.findAll();
        return userEntityList
                .stream()
                .map(this.userDboMapper::toDomain)
                .toList();
    }

    public void  updatePasswordUser(User user){
        UserEntity userEntity = this.userRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("User not exist"));
        userEntity.setPassword(user.getPassword());

       try{
           this.userRepository.save(userEntity);
       } catch (Exception e) {
           throw new RuntimeException(e);
       }
    }
}
