package com.inline.sub2.db.entity;


import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Data
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name="todo")
public class TodoEntity {

    @Id
    @Column(name = "todo_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long todoId;

    @Column(name = "title")
    private String title;

    @Column(name="content")
    private String content;

    @Column(name="todo_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date todoDate;

    @Column(name="done")
    private boolean done;

    @Column(name="user_id")
    private Long userId;

    @Column(name="office_id")
    private Long officeId;


}
