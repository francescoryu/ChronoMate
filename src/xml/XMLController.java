package xml;

import ch.francescoryu.model.Events;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import util.PathHolder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class XMLController
{
    public static void marshal(Events events) throws JAXBException
    {
        JAXBContext context = JAXBContext.newInstance(Events.class);
        Marshaller mar = context.createMarshaller();
        mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        mar.marshal(events, new File(PathHolder.FILE_PATH));
    }

    public static Events unmarshal() throws JAXBException, FileNotFoundException
    {
        JAXBContext context = JAXBContext.newInstance(Events.class);
        return (Events) context.createUnmarshaller().unmarshal(new FileReader(PathHolder.FILE_PATH));
    }
}
