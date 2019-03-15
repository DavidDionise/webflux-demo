

import com.webfluxdemo.WebfluxDemoApplication
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import java.net.URI

@SpringBootTest(classes = [WebfluxDemoApplication::class])
@ComponentScan(basePackages = ["com.webfluxdemo"])
class PersonTests {

  @Autowired
  private lateinit var routerFunction: RouterFunction<ServerResponse>
  private lateinit var webTestClient: WebTestClient

  @BeforeAll
  fun initialize() {
    webTestClient =
      WebTestClient.bindToRouterFunction(routerFunction).build()
  }

  @Test
  fun `Check that 'people' route returns 4 people`() {
    webTestClient.get()
      .uri(URI.create("/people"))
      .exchange()
      .expectBody()
      .json("[\n" +
        "    {\n" +
        "        \"id\": 1,\n" +
        "        \"name\": \"Lydia\",\n" +
        "        \"age\": 32\n" +
        "    },\n" +
        "    {\n" +
        "        \"id\": 2,\n" +
        "        \"name\": \"John\",\n" +
        "        \"age\": 33\n" +
        "    },\n" +
        "    {\n" +
        "        \"id\": 3,\n" +
        "        \"name\": \"Henri\",\n" +
        "        \"age\": 12\n" +
        "    },\n" +
        "    {\n" +
        "        \"id\": 4,\n" +
        "        \"name\": \"Amanda\",\n" +
        "        \"age\": 45\n" +
        "    }\n" +
        "]")
  }
}
