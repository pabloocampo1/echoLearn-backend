package com.EchoLearn_backend.infraestructure.externalServices;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
public class ClaudinaryConfig {

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dwutmzbdt",
                "api_key", "761883969333365",
                "api_secret", "WU-xFqh4vXg_V4LSsiMFqRvyQXM",
                "secure", true
        ));
    }
}
