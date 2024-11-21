package pojos;

import java.io.Serializable;

public class Connected implements Serializable {
    private String message;

    public Connected() {
        this.message = "Klienten är ansluten!";
    }

    public String getMessage() {
        return message;
    }
}
