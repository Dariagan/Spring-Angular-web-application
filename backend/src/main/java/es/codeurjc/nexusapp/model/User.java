package es.codeurjc.nexusapp.model;

import java.sql.Blob;
import java.time.LocalDateTime;
import java.util.*;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import es.codeurjc.nexusapp.service.EmailService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.lang.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

// 13-A
@NoArgsConstructor
@Entity(name = "UserTable")
public class User implements UserDetails
{
    public interface UsernameView {}
    public interface BasicView extends UsernameView{}
    public interface TweetsView {}
    public interface FollowView extends UsernameView{}
    public interface FullView extends User.BasicView, User.TweetsView, Tweet.BasicView {}
    
    @JsonView(UsernameView.class) @Id
    @Getter private String username;

    @JsonView(BasicView.class)
    @Getter @Setter private String name;

    @JsonView(BasicView.class)
    @Getter @Setter private String email;

    @JsonView(BasicView.class)
    @Getter @Setter private String description = "";

    @JsonView(BasicView.class)
    @Getter private LocalDateTime signUpDate = LocalDateTime.now();

    @JsonIgnore
    @Setter private String encodedPassword;

    @JsonView(BasicView.class) @Enumerated(EnumType.STRING)
    @Getter private Role role;

    @Nullable @Lob @JsonIgnore
    @Getter @Setter private Blob image;

    @JsonView(TweetsView.class)
    @OneToMany(mappedBy = "author")
    @Getter private List<Tweet> tweets = new ArrayList<>();

    @JoinTable(
        name = "user_followers",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "follower_id")
    )
    @JsonView(FollowView.class) @Nullable @ManyToMany @Getter
    private Set<User> following = new HashSet<>();

    @JsonView(FollowView.class) @Nullable @ManyToMany(mappedBy = "following") @Getter
    private Set<User> followers = new HashSet<>();

    @JsonIgnore @Nullable @ManyToMany @Getter 
    private Set<User> blocked = new HashSet<>();

    public static class Builder {
        private String username, email, encodedPassword, name = "", description = "";
        private boolean admin = false;
        private boolean banned = false;

        public Builder setName(String name){
            this.name = name;
            return this;
        }
        public Builder setUsername(String username){
            this.username = username;
            return this;
        }
        public Builder setEmail(String email){
            this.email = email;
            return this;
        }
        public Builder setEncodedPassword(String encodedPassword){
            this.encodedPassword = encodedPassword;
            return this;
        }
        public Builder setDescription(String description){
            assert description != null;
            this.description = description;
            return this;
        }
        public Builder setBasicUser(){
            this.admin = false;
            return this;
        }
        public Builder setAdmin(){
            this.admin = true;
            return this;
        }
        public Builder setBan(){
            this.banned = true;
            return this;
        }
        public void reset(){
            username = null; email = null; encodedPassword = null; 
            name = ""; description = ""; admin = false; banned = false;
        }
        public User build(){
            assert (
                username != null && !username.equals("") && username.length() <= 25 &&
                name != null && name.length() <= 25 &&
                email != null && EmailService.isEmail(email) &&
                encodedPassword != null && encodedPassword.length() >= 10
            );

            return new User(this);
        }
    }
    private User(User.Builder builder){
        this(
            builder.username, builder.email, builder.encodedPassword, builder.name,
            builder.description, builder.admin, builder.banned
        );
    }
    private User(
        String username, String email, String encodedPassword, String name,
        String description, boolean admin, boolean banned
    ) {
        this.username = username;
        this.email = email;
        this.encodedPassword = encodedPassword;
        this.name = name;
        this.description = description;
        this.image = null;

        if (admin) this.role = Role.ADMIN;
        else this.role = Role.USER;
    }

    public void ban() { role = Role.BANNED; }
    public void unban() { role = Role.USER; }
    public boolean isBanned() { return role == Role.BANNED; }

    public boolean hasImage() {return image != null;}

    public boolean isAdmin() { return role == Role.ADMIN; }
    public void setAdmin() { role = Role.ADMIN; }
    public void removeAdmin() { role = Role.USER; }

    public void toggleFollow(User user)
    {
        assert user != null && !user.equals(this);
        if (!following.contains(user)){
            following.add(user);
            user.followers.add(this);
        } else {
            following.remove(user);
            user.followers.remove(this);
        }
    }

    public void block(User user)
    {
        assert !user.equals(this);
        blocked.add(user);
    };

    public void unblock(User user)
    {
        assert !user.equals(this);
        blocked.remove(user);
    };

    public String toString() {return username;}

    // UserDetails interface methods
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }
    
    @Override public boolean isAccountNonExpired() {return true;}
    @Override public boolean isAccountNonLocked() {return true;}
    @Override public boolean isCredentialsNonExpired() { return true;}
    @Override public boolean isEnabled() {return !isBanned();}
    @Override public String getPassword() {return encodedPassword;}

    @Override
    public int hashCode() {return Objects.hash(username);}
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        User other = (User) obj;
        return Objects.equals(username, other.username);
    }
}
