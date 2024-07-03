import ch.francescoryu.model.Events;
import ch.francescoryu.view.MainView;
import jakarta.xml.bind.JAXBException;
import ch.francescoryu.util.PathHolder;
import ch.francescoryu.xml.XMLController;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Application
{
    
    private static Events events;
    
    public static void main(String[] args)
    {
        createDirectoryIfNotExists();
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
                new MainView(events);
            }
        });
    }
    
    private static void initLaF()
    {
        try
        {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            UIManager.put("JFrame.activeTitleBackground", Color.red);
        }
        catch(Exception e)
        {
            System.err.printf("LaF could not be initialized\n%s", e);
        }
    }
    
    private static void createDirectoryIfNotExists()
    {
        File directory = new File(PathHolder.APP_PATH);
        if(!directory.exists())
        {
            boolean result = directory.mkdir();
            try
            {
                Files.createFile(Path.of(PathHolder.FILE_PATH));
                events = new Events();
                XMLController.marshal(events);
            }
            catch(IOException | JAXBException e)
            {
                throw new RuntimeException(e);
            }
        }
        
        else
        {
            try
            {
                events = XMLController.unmarshal();
            }
            catch(JAXBException | FileNotFoundException e)
            {
                throw new RuntimeException(e);
            }
        }
    }
}