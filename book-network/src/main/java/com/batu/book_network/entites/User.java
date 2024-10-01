package com.batu.book_network.entites;

import java.io.Serial;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@Entity
@SuperBuilder
@Table(name = "\"user\"")
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class User implements UserDetails, Principal{
    @Serial
    private static final long serialVersionUID = -1209714198654368601L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String email;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private boolean isAccountLocked;
    private boolean enable;
    @Column(name = "password")
    private String passWord;


    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDate createdDate;

    @LastModifiedDate
    @Column(insertable = false)
    private LocalDate lastModifiedDate;


    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles;

    @OneToMany(mappedBy = "user")
    private List<Token> jwtTokens;



    @Override
    public String getName() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles
                .stream()
                .map(r -> new SimpleGrantedAuthority(r.getName()))
                .toList();
    }

    @Override
    public String getPassword() {return passWord;}

    @Override
    public String getUsername() {
        return email;
    }

    public String getFullName(){
        return firstName + " " + lastName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !isAccountLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enable;   
    }
    
}   
