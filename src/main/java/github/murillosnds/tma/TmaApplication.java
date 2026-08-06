package github.murillosnds.tma;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(
    info = @Info(
        title = "TMA | Tasks Management API",
        version = "1.0.0",
        description = "To-Do API"
    )
)
public class TmaApplication {

	public static void main(String[] args) {
		SpringApplication.run(TmaApplication.class, args);
	}

}
