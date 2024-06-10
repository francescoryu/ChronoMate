import ch.francescoryu.view.MainView;

import javax.swing.*;

public class Application
{
    public static void main(String[] args)
    {
        initLaF();
        initGUI();
    }

    private static void initGUI()
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                new MainView();
            }
        });
    }

    private static void initLaF()
    {
        try
        {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        }
        catch (Exception e)
        {
            System.err.printf("LaF could not be initialized\n%s", e);
        }
    }
}