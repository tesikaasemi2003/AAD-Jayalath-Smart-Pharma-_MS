package lk.ijse.Jayalath_Smart_Pharma.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommonResponse {
    private int status;
    private String message;
    private Object body;


    public CommonResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }


}