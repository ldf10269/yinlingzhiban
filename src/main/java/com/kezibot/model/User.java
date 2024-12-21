package com.kezibot.model;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 用户实体类
 * 表示系统中的用户信息
 * 使用JPA注解进行数据库映射
 */
@Data
@Entity
@Table(name = "Users")
public class User {
    /**
     * 用户唯一标识ID
     * 自动生成，使用数据库自增策略
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户名
     * 不可为空，必须唯一
     */
    @Column(nullable = false, unique = true)
    private String username;

    /**
     * 用户密码
     * 不可为空，存储加密后的密码
     */
    @Column(nullable = false)
    private String password;

    /**
     * 用户邮箱
     * 不可为空，必须唯一
     */
    @Column(nullable = false, unique = true)
    private String email;

    /**
     * 用户创建时间
     * 不可更新，记录用户首次注册时间
     */
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    /**
     * 用户信息最后更新时间
     */
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // 显式添加setter方法
    /**
     * 设置用户名
     * @param username 新的用户名
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 设置用户邮箱
     * @param email 新的邮箱地址
     */
    public void setEmail(String email) {
        this.email = email;
    }

    // 显式添加getter方法
    /**
     * 获取用户ID
     * @return 用户唯一标识
     */
    public Long getId() {
        return this.id;
    }

    /**
     * 获取用户名
     * @return 用户名
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * 获取用户邮箱
     * @return 邮箱地址
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * 用户创建时自动设置创建和更新时间
     */
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    /**
     * 用户信息更新时自动更新更新时间
     */
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
