package zcd.ts4u.kanban.exception;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectIdExceptionResponse {
    // we will just have one instance variable
    private String projectIdIdentifier;
    // this class will be used to encapsulate error information and send it back to the client.
}
