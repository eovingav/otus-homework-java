package hw15MessageSystem.frontend.MessageTypes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResult implements Serializable {
    private String name;
    private Boolean success;

}
