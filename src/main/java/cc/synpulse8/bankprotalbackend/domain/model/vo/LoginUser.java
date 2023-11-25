package cc.synpulse8.bankprotalbackend.domain.model.vo;

import cc.synpulse8.bankprotalbackend.domain.model.entity.EndUserEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"enabled","accountNonExpired", "accountNonLocked", "credentialsNonExpired", "authorities", "username", "password"})
public class LoginUser implements UserDetails {

    private EndUserEntity user;

    public LoginUser(EndUserEntity user, List<String> permissions) {
        super();
        this.user = user;
        this.permissions = permissions;
    }

    List<String> permissions;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    List<SimpleGrantedAuthority> permissionList;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

//		List<SimpleGrantedAuthority> permissionList = permissions.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());

        if(permissionList != null) {
            return permissionList;
        }

        return permissions.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {

        return user.getPassword();
    }

    @Override
    public String getUsername() {

        return user.getInternalId();
    }

    @Override
    public boolean isAccountNonExpired() {

        return true;
    }

    @Override
    public boolean isAccountNonLocked() {

        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {

        return true;
    }

    @Override
    public boolean isEnabled() {

        return true;
    }
}
