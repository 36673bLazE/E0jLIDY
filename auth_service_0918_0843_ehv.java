// 代码生成时间: 2025-09-18 08:43:24
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ExceptionHandler;

@SpringBootApplication
# 增强安全性
@EnableWebSecurity
@RestController
@RequestMapping("/api")
public class AuthService extends WebSecurityConfigurerAdapter {

    @GetMapping("/authenticate")
    public ResponseEntity<String> authenticate() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).body("Unauthorized");
        }
        return ResponseEntity.ok("User authenticated");
# 添加错误处理
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(500).body("Internal Server Error: " + e.getMessage());
    }

    public static void main(String[] args) {
        SpringApplication.run(AuthService.class, args);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
# NOTE: 重要实现细节
            .authorizeRequests()
            .antMatchers("/api/authenticate").authenticated()
            .anyRequest().permitAll()
            .and()
            .httpBasic();
    }
}
# 增强安全性
