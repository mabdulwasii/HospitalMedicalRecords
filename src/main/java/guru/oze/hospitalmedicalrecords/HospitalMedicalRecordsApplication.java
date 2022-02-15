package guru.oze.hospitalmedicalrecords;

import guru.oze.hospitalmedicalrecords.config.RSAKeyConfigProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({LiquibaseProperties.class, RSAKeyConfigProperties.class})
public class HospitalMedicalRecordsApplication {

    public static void main(String[] args) {
        SpringApplication.run(HospitalMedicalRecordsApplication.class, args);
    }

}
