package com.booksystem.bookManagement.security;

import com.booksystem.bookManagement.entity.BookUsers;
import com.booksystem.bookManagement.repository.BookUsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final BookUsersRepository bookUsersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        BookUsers userEntity = bookUsersRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new User(
                userEntity.getEmail(),
                userEntity.getPassword(),
                rolesToAuthorities(userEntity)
        );
    }

    public Collection<GrantedAuthority> rolesToAuthorities(BookUsers userEntity){
        return Collections.singleton(new SimpleGrantedAuthority(String.valueOf(userEntity.getRoles())));
    }
}
