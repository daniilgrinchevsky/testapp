package com.madeline.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Duration;
import java.time.LocalDateTime;


@Table(name = "status")
public class UserStatus {

    public static final String ONLINE = "ONLINE";
    public static final String OFFLINE = "OFFLINE";
    public static final String AWAY = "AWAY";
    public static final String NULL = "NULL";
    @Id
    @Column(name = "id")
    private Long id;
    @Column(name = "status")
    private String status;
    private LocalDateTime changingTime;
    private Duration duration;

    public UserStatus(Long id, String status){
        this.id = id;
        this.status = status;
        this.changingTime = LocalDateTime.now();
    }
    public Long getDurationSeconds(){
        return duration.getSeconds();
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getChangingTime() {
        return changingTime;
    }

}
