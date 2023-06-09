package com.ll.weflea.boundedContext.member.entity;

import com.ll.weflea.base.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
@EntityListeners(AuditingEntityListener.class)
public class Member extends BaseEntity {

    private String username;

    @Column(unique = true)
    private String nickname;

    @Column(unique = true)
    private String email;

    private String providerTypeCode;

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private ProfileImage profileImage;

    @Builder.Default
    private Long point = 0L;

//    @OneToMany(mappedBy = "buyer")
//    private List<Goods> payedGoods = new ArrayList<>();

    public List<? extends GrantedAuthority> getGrantedAuthorities() {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        // 모든 멤버는 member 권한을 가진다.
        grantedAuthorities.add(new SimpleGrantedAuthority("member"));

        if (isAdmin()) {
            grantedAuthorities.add(new SimpleGrantedAuthority("admin"));
        }

        return grantedAuthorities;
    }

    public boolean isAdmin() {
        return username.startsWith("admin");
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setDefaultImage(boolean isDefault){
        if(isDefault){
            this.profileImage = null;
        }
    }

    public void updateProfileImage(ProfileImage profileImage) {

        // 새로운 프로필 이미지 설정
        this.profileImage = profileImage;
        if (profileImage != null) {
            profileImage.updateMember(this);
        }
    }

    public void updatePoint(Long point) {
        this.point = point;
    }

}
