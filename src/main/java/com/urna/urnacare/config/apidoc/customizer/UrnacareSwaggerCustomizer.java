
package com.urna.urnacare.config.apidoc.customizer;

import com.google.common.collect.Lists;
import com.urna.urnacare.config.ApplicationProperties;
import com.urna.urnacare.security.jwt.JWTFilter;
import org.springframework.core.Ordered;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.service.*;
import springfox.documentation.spring.web.plugins.Docket;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static springfox.documentation.builders.PathSelectors.regex;

/**
 * A swagger customizer to setup {@link Docket}
 */
public class UrnacareSwaggerCustomizer implements SwaggerCustomizer, Ordered {

    /**
     * The default order for the customizer.
     */
    public static final int DEFAULT_ORDER = 0;

    private int order = DEFAULT_ORDER;

    private final ApplicationProperties.Swagger properties;

    public UrnacareSwaggerCustomizer(ApplicationProperties.Swagger properties) {
        this.properties = properties;
    }

    @Override
    public void customize(Docket docket) {
        Contact contact = new Contact(
            properties.getContactName(),
            properties.getContactUrl(),
            properties.getContactEmail()
        );

        ApiInfo apiInfo = new ApiInfo(
            properties.getTitle(),
            properties.getDescription(),
            properties.getVersion(),
            properties.getTermsOfServiceUrl(),
            contact,
            properties.getLicense(),
            properties.getLicenseUrl(),
            new ArrayList<>()
        );

        docket.host(properties.getHost())
            .protocols(new HashSet<>(Arrays.asList(properties.getProtocols())))
            .apiInfo(apiInfo)
            .useDefaultResponseMessages(properties.isUseDefaultResponseMessages())
            .forCodeGeneration(true)
            .directModelSubstitute(ByteBuffer.class, String.class)
            .genericModelSubstitutes(ResponseEntity.class)
            .ignoredParameterTypes(Pageable.class)
            //.select()
            //.paths(regex(properties.getDefaultIncludePattern()))
                .securityContexts(Lists.newArrayList(securityContext()))
                .securitySchemes(Lists.newArrayList(apiKey()))
                .select()
            .build();
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public int getOrder() {
        return order;
    }

    private ApiKey apiKey() {
        return new ApiKey("JWT", JWTFilter.AUTHORIZATION_HEADER, "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(regex(properties.getDefaultIncludePattern()))
                .build();
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope
                = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Lists.newArrayList(
                new SecurityReference("JWT", authorizationScopes));
    }
}
