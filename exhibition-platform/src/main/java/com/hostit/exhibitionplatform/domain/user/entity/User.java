package com.hostit.exhibitionplatform.domain.user.entity;

import com.hostit.exhibitionplatform.global.config.audit.BaseTimeEntity;
import com.hostit.exhibitionplatform.domain.auth.entity.ProviderType;
import com.hostit.exhibitionplatform.domain.auth.entity.RoleType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 64, nullable = false, unique = true)
    private String userId;

    @Column(length = 512, nullable = false)
    private String password;

    @Column(length = 100, nullable = false)
    private String username;

    @Column(length = 512, nullable = false, unique = true)
    private String email;

    @Column(length = 512)
    private String profileImageUrl;

    @Column(length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private ProviderType providerType;

    @Column(length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @Builder
    public User
            (String userId,
             String username,
             String email,
             String profileImageUrl,
             ProviderType providerType,
             RoleType roleType
            ) {
        this.userId = userId;
        this.password = "NO_PASS";
        this.username = username;
        this.email = email != null ? email : "NO_EMAIL";
        this.profileImageUrl = profileImageUrl != null ? profileImageUrl : "";
        this.providerType = providerType;
        this.roleType = roleType;
    }

    public void changeUsername(String username) {
        this.username = username;
    }

    public void changeProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }
}
