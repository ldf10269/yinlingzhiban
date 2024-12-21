package com.kezibot.model;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 提醒事项实体类
 * 表示系统中的用户提醒信息
 * 使用JPA注解进行数据库映射
 */
@Data
@Entity
@Table(name = "Reminders")
public class Reminder {
    /**
     * 提醒事项唯一标识ID
     * 自动生成，使用数据库自增策略
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 关联的用户
     * 多对一关系，每个提醒事项属于一个用户
     */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * 提醒事项标题
     * 不可为空，长度限制为100个字符
     */
    @Column(nullable = false, length = 100)
    private String title;

    /**
     * 提醒事项描述
     * 可为空，使用TEXT类型存储
     */
    @Column(columnDefinition = "TEXT")
    private String description;

    /**
     * 提醒事项到期时间
     * 不可为空
     */
    @Column(name = "due_date", nullable = false)
    private LocalDateTime dueDate;

    /**
     * 提醒事项完成状态
     * 默认为false，表示未完成
     */
    @Column(name = "is_completed")
    private Boolean isCompleted = false;

    /**
     * 提醒事项创建时间
     * 不可更新，记录提醒事项首次创建时间
     */
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    /**
     * 提醒事项最后更新时间
     */
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // 显式添加setter方法
    /**
     * 设置关联用户
     * @param user 关联的用户
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * 设置提醒事项标题
     * @param title 新的标题
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 设置提醒事项描述
     * @param description 新的描述
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 设置提醒事项到期时间
     * @param dueDate 新的到期时间
     */
    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * 设置提醒事项完成状态
     * @param isCompleted 是否完成
     */
    public void setIsCompleted(Boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    /**
     * 创建提醒事项时自动设置创建和更新时间
     */
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    /**
     * 更新提醒事项时自动更新更新时间
     */
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
