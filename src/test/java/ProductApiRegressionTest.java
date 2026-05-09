import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class ProductApiRegressionTest {

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "https://dummyjson.com";
    }

    @Test
    @DisplayName("GET /products/1 - Validasi produk beauty")
    void testGetProductById() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/products/1")
                .then()
                .assertThat()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("title", not(emptyString()))
                .body("category", equalTo("beauty"))
                .header("Content-Type", containsString("application/json"))
                .time(lessThan(2000L));
    }

    @Test
    @DisplayName("POST /products/add - Validasi penambahan produk")
    void testAddProductToCart() {
        String requestBody = """
        {
            "title": "Test Product",
            "description": "Produk uji regression test",
            "price": 123
        }
        """;

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/products/add")
                .then()
                .assertThat()
                .statusCode(201)
                .body("title", equalTo("Test Product"))
                .body("description", containsString("regression"))
                .body("price", equalTo(123))
                .body("id", greaterThan(0))
                .time(lessThan(3000L));
    }
}