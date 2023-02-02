package ReusableMethods;

import POJOS.BookingBody.BookingBody;
import POJOS.BookingBody.bookingdates;
import com.github.javafaker.Faker;
import io.restassured.path.json.JsonPath;


import java.util.GregorianCalendar;
import java.util.Locale;


public class ReusableMethods {



    public static JsonPath rawToJson(String response){
        JsonPath js=new JsonPath(response);

        return js;
    }

    public static BookingBody jsonPayLoad(){
  Faker faker=new Faker(Locale.US);
     BookingBody bookingBody=new BookingBody(faker.name().firstName(),faker.name().lastName(),faker.number().numberBetween(100,1000),true
     ,new bookingdates(randomDate(),randomDate()),faker.food().fruit());

     return bookingBody;
    }

    private static String randomDate(){
        GregorianCalendar gc = new GregorianCalendar();

        int year = randBetween(2022, 2023);

        gc.set(gc.YEAR, year);

        int dayOfYear = randBetween(1, gc.getActualMaximum(gc.DAY_OF_YEAR));

        gc.set(gc.DAY_OF_YEAR, dayOfYear);

        return gc.get(gc.YEAR) + "-" + (gc.get(gc.MONTH) + 1) + "-" + gc.get(gc.DAY_OF_MONTH);
    }

    public static int randBetween(int start, int end) {
        return start + (int)Math.round(Math.random() * (end - start));
    }
}
