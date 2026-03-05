package com.rizzatto.makeachat.domain.user;

import com.rizzatto.makeachat.domain.base.BaseEntity;
import com.rizzatto.makeachat.domain.contact.Contact;
import com.rizzatto.makeachat.domain.chat.Message;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode(exclude = {"messages", "addedContacts"}, callSuper = true)
@ToString(exclude = {"messages", "addedContacts"})
@Table(name = "chat_user")
public class User extends BaseEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String username;
    private String email;

    private String pathPicture;

    @OneToMany(mappedBy = "owner")
    private List<Message> messages;

    @OneToMany(mappedBy = "userOrigin", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Contact> addedContacts = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}

