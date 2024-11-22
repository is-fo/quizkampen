package panels;

import java.util.List;

public class CategoryPanel {

    List<String> categories;

    public CategoryPanel(List<String> categories) {
        this.categories = categories;
    }

    public void drawCategories() {

        for (String category : categories) {
            //TODO smäll upp knapparna
        }
    }
}
