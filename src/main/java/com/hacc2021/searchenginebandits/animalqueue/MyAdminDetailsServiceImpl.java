package com.hacc2021.searchenginebandits.animalqueue;

import com.hacc2021.searchenginebandits.animalqueue.model.Admin;
import com.hacc2021.searchenginebandits.animalqueue.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyAdminDetailsServiceImpl implements UserDetailsService {
    @Autowired
    AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(final String userName) throws UsernameNotFoundException {
        Optional<Admin> admin = adminRepository.findByUserName(userName);
        admin.orElseThrow(() -> new UsernameNotFoundException("Not found " + userName));
        return admin.map(MyAdminDetail::new).get();
    }
}
