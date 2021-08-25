package ru.dromran.testtz.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "post")
public class PostEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "post_name", nullable = false)
    private String postName;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id", nullable = false)
    private Long postId;

    @OneToMany(mappedBy = "post")
    private List<EmployeeEntity> employeeEntityList;

}
