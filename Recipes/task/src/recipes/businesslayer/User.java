package recipes.businesslayer;

import lombok.*;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table (name ="users")
public class User {

    @Id
    @NonNull
    //@Email annotation didn't seem to work well - using regexp pattern instead
    @Pattern(regexp = ".+@.+\\..+")
    private String email;

    @NotBlank
    @Size(min = 8)
    private String password;

    @OneToMany(mappedBy = "user")
    private Set<Recipe> recipes;
}