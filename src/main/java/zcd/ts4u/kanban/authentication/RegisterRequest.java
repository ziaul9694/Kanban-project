package zcd.ts4u.kanban.authentication;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {
    private String firstname;
    private String lastname;
    private String email;
    private String password;
}
