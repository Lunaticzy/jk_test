package org.example.jk_test1.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String name;
    private String password;
    private String email;
    private String phone;
    private String address;
    private String role;
    private String status;
    private String createTime;
    private String updateTime;
    private String deleteTime;
    private String deleteFlag;
    private String remark;
    private Boolean blocked;
    private String avatar;

}
