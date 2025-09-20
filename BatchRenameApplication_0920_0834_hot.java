// 代码生成时间: 2025-09-20 08:34:40
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class BatchRenameApplication {

    public static void main(String[] args) {
        SpringApplication.run(BatchRenameApplication.class, args);
    }
}

@RestController
@RequestMapping("/api/rename")
class RenameController {

    private final Path directory = Paths.get("./files");

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFiles(@RequestParam("files") List<MultipartFile> files) {
        try {
            if (!Files.exists(directory)) {
                Files.createDirectories(directory);
            }

            for (MultipartFile file : files) {
                if (file.isEmpty()) {
                    return ResponseEntity.unprocessableEntity().body("File is empty");
                }
                byte[] bytes = file.getBytes();
                String originalFileName = file.getOriginalFilename();
                String newFileName = generateNewFileName(originalFileName); // Generate new filename based on your logic
                Path path = directory.resolve(newFileName);
                Files.write(path, bytes);
            }

            return ResponseEntity.ok("Files uploaded and renamed successfully");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload files");
        }
    }

    private String generateNewFileName(String originalFileName) {
        // Implement your logic to generate a new filename
        return originalFileName;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleExceptions(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + ex.getMessage());
    }
}