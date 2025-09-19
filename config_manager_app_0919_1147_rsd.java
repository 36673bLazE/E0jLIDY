// 代码生成时间: 2025-09-19 11:47:48
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.io.IOException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

@SpringBootApplication
@RestController
@RequestMapping("/config")
public class ConfigManagerApp {

    private final ResourceLoader resourceLoader;

    public ConfigManagerApp(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @GetMapping("/load")
    public ResponseEntity<String> loadConfigFile(String filename) {
        try {
            Resource resource = resourceLoader.getResource("classpath:config/" + filename);
            if (resource.exists()) {
                return ResponseEntity.ok(resource.getInputStream(), "utf-8").body(resource.getInputStream().readAllBytes());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Error loading file: " + filename);
        }
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.badRequest().body("An error occurred: " + e.getMessage());
    }

    public static void main(String[] args) {
        SpringApplication.run(ConfigManagerApp.class, args);
    }
}
