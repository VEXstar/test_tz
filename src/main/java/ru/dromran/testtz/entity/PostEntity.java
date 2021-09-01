package ru.dromran.testtz.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@ToString
@RequiredArgsConstructor
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

    @JsonIgnore
    @OneToMany(mappedBy = "post")
    @ToString.Exclude
    private List<EmployeeEntity> employeeEntityList;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PostEntity post = (PostEntity) o;

        return Objects.equals(postId, post.postId);
    }
}
