package com.inline.sub2.db.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "belong")
public class belongEntity {
    @Id
    @Column(name = "belong_id")
    private int belongId;
    @Column(name = "belong_name")
    private String belongName;
    @Column(name = "office_id")
    private int officeId;
}
