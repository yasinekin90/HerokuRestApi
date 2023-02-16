package ReusableMethods;

import POJOS.BookingBody.BookingBody;
import POJOS.BookingBody.bookingdates;
import POJOS.travelerpojo.Travelerinformation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.github.javafaker.Faker;

import io.restassured.response.Response;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.GregorianCalendar;
import java.util.Locale;


public class ReusableMethods {
    public static BookingBody jsonPayLoad() {
        Faker faker = new Faker(Locale.US);
        BookingBody bookingBody = new BookingBody(faker.name().firstName(), faker.name().lastName(), faker.number().numberBetween(100, 1000), true
                , new bookingdates(randomDate(), randomDate()), faker.food().fruit());

        return bookingBody;
    }

    private static String randomDate() {
        GregorianCalendar gc = new GregorianCalendar();

        int year = randBetween(2022, 2023);

        gc.set(gc.YEAR, year);

        int dayOfYear = randBetween(1, gc.getActualMaximum(gc.DAY_OF_YEAR));

        gc.set(gc.DAY_OF_YEAR, dayOfYear);

        return gc.get(gc.YEAR) + "-" + (gc.get(gc.MONTH) + 1) + "-" + gc.get(gc.DAY_OF_MONTH);
    }

    private static int randBetween(int start, int end) {
        return start + (int) Math.round(Math.random() * (end - start));
    }

    public static String xmlPayload() {
        Faker fk = new Faker(Locale.US);
        Travelerinformation tr = new Travelerinformation(1
                , fk.name().firstName()
                , fk.internet().emailAddress()
                , fk.address().streetAddress()
                , LocalDateTime.now().toString());
        XmlMapper xmlMapper = new XmlMapper();

        String xml = null;
        try {
            xml = xmlMapper.writeValueAsString(tr);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return xml;

    }

    public static String idFromXmlResponse(Response response) {
        return response.xmlPath().get("Travelerinformation.id");
    }

    public static void xmlSchemaValidation(Response response) throws SAXException, IOException {
        String xmlBody = response.asString();
        FileWriter fw = new FileWriter("src/main/resources/instance.xml");
        fw.write(xmlBody);
        fw.close();

        // create a SchemaFactory capable of understanding WXS schemas
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

// load a WXS schema, represented by a Schema instance
        Source schemaFile = new StreamSource(new File("src/main/resources/travelerXMLSchema.xsd"));
        Schema schema = factory.newSchema(schemaFile);
        // create a Validator instance, which can be used to validate an instance document
        Validator validator = schema.newValidator();

// validate the DOM tree
        try {
            validator.validate(new StreamSource(new File("src/main/resources/instance.xml")));
        } catch (SAXException e) {
            // instance document is invalid!
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


