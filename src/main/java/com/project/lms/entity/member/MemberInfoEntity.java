package com.project.lms.entity.member;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.project.lms.entity.member.enumfile.Role;
import com.project.lms.vo.member.MemberJoinVO;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="member_info")
@Inheritance(strategy = InheritanceType.JOINED) //매핑 전략을 Join으로 설정
@DiscriminatorColumn(name="mi_dtype") //구분정보를 mi_dtype칼럼에 저장하겠다는 의미
@DiscriminatorValue("mas") //MemberInfoEntity를 memberInfoRepository에 save하면 자동으로 mi_dtype에 mas로 저장됨
@SuperBuilder
public class MemberInfoEntity implements UserDetails{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="mi_seq") private Long miSeq;
    @Column(name="mi_id") private String miId;
    @Column(name="mi_pwd") private String miPwd;
    @Enumerated(value = EnumType.STRING) //설정안하면 enum이 저장된 위치로 DB에 저장됨. enum순서가 틀어지면 DB가 꼬일 가능성이 있어서 순서로 저장하는것은 권장하는 방법이 아님
    @Column(name="mi_role") private Role miRole;
    @Column(name="mi_name") private String miName;
    @Column(name="mi_birth") private LocalDate miBirth;
    @Column(name="mi_email") private String miEmail;
    @Column(name="mi_reg_dt") private LocalDate miRegDt;
    @Column(name="mi_status") private Boolean miStatus;
    // @Column(name="mi_dtype") private String miDtype;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> roles = new HashSet<>();
        roles.add(new SimpleGrantedAuthority(this.miRole.toString())); //Enum타입을 String으로 바꾸기 위해 toString사용함
        return roles;
    }
    @Override
    public String getPassword() {
        return miPwd; 
    }
    @Override
    public String getUsername() {
        return miId;
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
        return miStatus==true;
    }

    public MemberInfoEntity(MemberJoinVO data, Role role){ //entity안에 dto를 파라미터를 받는것은 권장되는 방법은 아니나 사용함(같은 패키지면 사용해도됨)
        this.miId = data.getId();
        this.miBirth = data.getBirth();
        this.miEmail = data.getEmail();
        this.miName = data.getName();
        this.miPwd = data.getPwd();
        this.miRegDt = data.getRegDt();
        this.miStatus = true;
        this.miRole = role;
    }

    public void updatePwd(String pwd){
        this.miPwd = pwd;
    }
}
