import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import java.util.Map;
import static io.restassured.RestAssured.given;

public class GoogleHeadersAndCookies {

    Response response;
    @Test
    void  getCookies(){
         response = given().get("https://www.google.com");

        Map<String, String> cookies = response.getCookies();

        // prints the keys and values of the cookies
        for (String c:cookies.keySet()) {

            System.out.println( c+" = "+response.getCookie(c));
        }
    }

    @Test

    void getHeaders(){

        Headers headers = response.getHeaders();

        //prints the header keys and values

      /*  for (int i = 0; i <headers.size() ; i++) {
            System.out.println(headers.asList().get(i).getName()+" ==> "+headers.asList().get(i).getValue());
        }*/

        for (Header hd:headers) {

            System.out.println(hd.getName()+" ==>"+hd.getValue());
        }


    }
}
