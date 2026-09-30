//package com.wipro.security;
//
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Component;
//
//import com.wipro.entity.User;
//import com.wipro.repository.UserRepository;
//
//import lombok.RequiredArgsConstructor;
//
//
//@Component
//@RequiredArgsConstructor
//public class CustomerUserDetailsService implements UserDetailsService {
//
//    private final UserRepository userRepository ;
//
//    @Override
//    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
//        User user = userRepository.findByUsername(name)
//        		.orElseThrow(()-> new UsernameNotFoundException("User not found !"));
//        return user;
//    }
//
//
//}
