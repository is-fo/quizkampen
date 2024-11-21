package panels;

import java.awt.*;
import java.awt.event.ActionListener;

public interface IPanel {

    public void setPanel();

    public void setActionListener(ActionListener actionListener);

    public void setCustomColor(Color backgroundColor, Color logoColor, Color infoTextColor, Color nuance);
}
