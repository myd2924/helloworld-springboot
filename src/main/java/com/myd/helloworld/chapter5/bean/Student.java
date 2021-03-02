package com.myd.helloworld.chapter5.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.io.Serializable;


/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/1/26 18:08
 * @Description:
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_student")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class Student implements Serializable{

    private static final long serialVersionUID = -7087061131738302854L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "note")
    private String note;

    @Column(name = "age")
    private Integer age;
}
