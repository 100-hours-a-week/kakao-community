package com.ktb.lukas.dto;

import com.ktb.lukas.entity.User;
import lombok.Data;

public class Check {
    @Data
    public class UserCheck {
        private Long id;
        private String nickname;
        private String email;
        private String objectName;
        public UserCheck(User userCreate) {
            this.id = userCreate.getId();
            this.nickname = userCreate.getNickname();
            this.email = userCreate.getEmail();
            this.objectName = "userCreate";
        }
    }
}
