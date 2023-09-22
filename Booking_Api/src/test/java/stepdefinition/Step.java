package stepdefinition;

import Util.Booking;
import Util.Bookingdates;
import gherkin.deps.com.google.gson.Gson;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;

public class Step {

    private final String BASE_URL="https://restful-booker.herokuapp.com/booking";
    private Response response;
    private String token;


    @Given("el estado se encuentra activo {string}")
    public void elEstadoSeEncuentraActivo(String statusCode) {

        int actualResponseCode = response.then().extract().statusCode();
        Assert.assertEquals( statusCode, actualResponseCode+"");

    }


    @Given("comprobamos que el api se encuentre activa {string}")
    public void comprobamosqueelapiseencuentreactiva(String uri) throws URISyntaxException {
        RestAssured.baseURI="https://restful-booker.herokuapp.com";
        RequestSpecification requestSpecification = RestAssured.given();
        response=requestSpecification.when().get(new URI(uri));

    }


    @When("se muestra el response {string}{string}{string}{string}{string}{string}{string}")
    public void seMuestraElResponse(String arg0, String arg1, String arg2, String arg3, String arg4, String arg5, String arg6) {
        String firstname = response.then().extract().path("firstname");
        String lastname = response.then().extract().path("lastname");
        String totalprice = response.then().extract().path("totalprice");
        String depositpaid = response.then().extract().path("depositpaid");
        String checkin = response.then().extract().path("bookingdates.checkin");
        String checkout = response.then().extract().path("bookingdates.checkout");
        String additionalneeds = response.then().extract().path("additionalneeds");

        Assert.assertEquals(firstname,arg0);
        Assert.assertEquals(lastname,arg1);
        Assert.assertEquals(totalprice,arg2);
        Assert.assertEquals(depositpaid,arg3);
        Assert.assertEquals(checkin,arg4);
        Assert.assertEquals(checkout,arg5);
        Assert.assertEquals(additionalneeds,arg6);

    }

    @When("se envian los parametros name {string}{string}{string}{string}")
    public void seEnvianlosparametrosname(String paramsname, String paramslastname,String checkin, String checkout) {
        Map<String, String> params = new HashMap<>();
        if (!paramsname.isEmpty()){
            params.put("firstname",paramsname);
        }
        if (!paramslastname.isEmpty()){
            params.put("lastname",paramslastname);
        }
        if (!checkin.isEmpty()){
            params.put("checkin",checkin);
        }
        if (!checkout.isEmpty()){
            params.put("checkout",checkout);
        }

        response = given().queryParams(params).get(BASE_URL).thenReturn();

    }

    @Then("se visualiza el response")
    public void seVisualizaElResponse() {
        response.then().extract().body().asString();

        String name =response.then().extract().path("firstname");
        String lastname =response.then().extract().path("lastname");
        Integer totalprice =response.then().extract().path("totalprice");
        Boolean depositpaid =response.then().extract().path("depositpaid");
        String checkin =response.then().extract().path("bookingdates.checkin");
        String checkout =response.then().extract().path("bookingdates.checkout");
        String additionalneeds =response.then().extract().path("additionalneeds");

        System.out.println("|"+name+"|"+lastname+"|"+totalprice+"|"+depositpaid+"|"+checkin+"|"+checkout+"|"+additionalneeds+"|" +'\n');


    }

     @Then("se visualiza el bookingid del response")
    public void seVisualizaElDelResponse() {
        Integer bookingid =response.then().extract().path("bookingid");
        System.out.println("|"+bookingid+"|" );
    }


    @When("ingresamos los datos a actualizar {string}{string}{string}")
    public void ingresamosLosDatosAActualizar(String id, String firstname, String lastname) {

        Booking booking=new Booking();

        booking.setFirstname(firstname);
        booking.setLastname(lastname);

        Gson gson = new Gson();
        String jbooking = gson.toJson(booking);

        response=  given().pathParam("id",id).contentType(ContentType.JSON).accept("application/json").header("Cookie","token="+ token).body(jbooking).when().patch(BASE_URL+"/{id}").thenReturn();

    }


    @When("se ingresa el {string}")
    public void seIngresaEl(String id) {
    response = given().pathParam("id",id).when().get(BASE_URL+"/{id}").thenReturn();

    }

    @Given("ingresamos los datos en el body {string}{string}{string}{string}{string}{string}{string}")
    public void ingresamosLosDatosEnElBody(String firstname, String lastname, String totalprice, String depositpaid, String checkin, String checkout, String additionalneeds) {

        Booking booking=new Booking();
        Bookingdates bookingdates = new Bookingdates();

        booking.setFirstname(firstname);
        booking.setLastname(lastname);
        booking.setTotalprice(Double.valueOf(totalprice));
        booking.setDepositpaid(Boolean.valueOf(depositpaid));
        bookingdates.setCheckin(checkin);
        bookingdates.setCheckout(checkout);
        booking.setBookingdates(bookingdates);
        booking.setAdditionalneeds(additionalneeds);

        Gson gson = new Gson();
        String jbooking = gson.toJson(booking);

       response=  given().contentType(ContentType.JSON).body(jbooking).when().post(BASE_URL).thenReturn();
    }

    @When("ingresamos los datos en el body a actualizar {string}{string}{string}{string}{string}{string}{string}{string}")
    public void ingresamosLosDatosEnElBodyAActualizar(String id,String firstname, String lastname, String totalprice, String depositpaid, String checkin, String checkout, String additionalneeds) {


        Booking booking=new Booking();
        Bookingdates bookingdates = new Bookingdates();

        booking.setFirstname(firstname);
        booking.setLastname(lastname);
        booking.setTotalprice(Double.valueOf(totalprice));
        booking.setDepositpaid(Boolean.valueOf(depositpaid));
        bookingdates.setCheckin(checkin);
        bookingdates.setCheckout(checkout);
        booking.setBookingdates(bookingdates);
        booking.setAdditionalneeds(additionalneeds);

        Gson gson = new Gson();
        String jbooking = gson.toJson(booking);

        response=  given().pathParam("id",id).contentType(ContentType.JSON).accept("application/json").header("Cookie","token="+ token).body(jbooking).when().put(BASE_URL+"/{id}").thenReturn();

    }

    @And("generamos el token")
    public void generamosElToken() {
        String userData="{"+
                "\"username\" : \"admin\","+
                "\"password\" : \"password123\""+
                "}";

        String BASE_URI = "https://restful-booker.herokuapp.com";
        response=  given().contentType(ContentType.JSON).body(userData).when().post(BASE_URI + "/auth").thenReturn();
        token = response.then().extract().path("token");

    }

    @Given("se ingresa el {string} para eliminar la reserva")
    public void seIngresaElParaEliminarLaReserva(String id) {

        response=  given().contentType(ContentType.JSON).accept("application/json").header("Cookie","token="+ token).pathParam("id",id).when().delete(BASE_URL+"/{id}").thenReturn();

    }

    @Then("se visualiza el arreglo bookingid del response")
    public void seVisualizaElArregloBookingidDelResponse() {

        List<Integer> bookingid =new ArrayList<>();

         bookingid =response.then().extract().path("bookingid");
        System.out.println(bookingid );


    }
}
