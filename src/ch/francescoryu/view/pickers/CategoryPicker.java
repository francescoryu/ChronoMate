package ch.francescoryu.view.pickers;

import javax.swing.*;
import java.awt.*;

public class CategoryPicker
{
    private Window parent;

    private JColorChooser colorChooser;
    private JDialog dialog;

    public CategoryPicker(Window parent)
    {
        this.parent = parent;
        init();
    }

    private void init()
    {
        initColorChooser();
        initFrame();
    }

    private void initColorChooser()
    {
        colorChooser = new JColorChooser();
        colorChooser.setPreviewPanel(new JPanel());
    }

    private void initFrame()
    {
        dialog = new JDialog(parent, "Select Color", Dialog.ModalityType.APPLICATION_MODAL);
        dialog.add(colorChooser);
        dialog.pack();
        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);
    }

}


