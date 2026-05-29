package view;

import javafx.scene.control.ListCell;
import model.Contribuinte;

public class FormatterListCell  extends ListCell<Contribuinte> {
    @Override
    protected void updateItem(Contribuinte item, boolean empty){

        super.updateItem(item, empty);
        setText(empty || item == null ? null : item.getCpfFormatted() + " || " + item.getNome() + " || " + item.getSituation()

        );

    }
}
