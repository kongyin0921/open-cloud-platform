package com.ocp.common.swagger2;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Lists;
import com.ocp.common.swagger2.properties.Swagger2Properties;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.*;
import java.util.stream.Collectors;

/**
 * swagger2 自动配置
 * @author kong
 * @date 2021/08/08 11:45
 * blog: http://blog.kongyin.ltd
 */
@Configuration
@EnableSwagger2
@EnableKnife4j
@EnableConfigurationProperties(Swagger2Properties.class)
@Import(BeanValidatorPluginsConfiguration.class)
public class Swagger2AutoConfiguration implements BeanFactoryAware {
    private static final String AUTH_KEY = "Authorization";

    private BeanFactory beanFactory;

    @Bean
    @ConditionalOnMissingBean
    public Swagger2Properties swagger2Properties() {
        return new Swagger2Properties();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(name = "ocp.swagger.enabled", matchIfMissing = true)
    public List<Docket> createRestApi(Swagger2Properties swagger2Properties) {
        ConfigurableBeanFactory configurableBeanFactory = (ConfigurableBeanFactory) beanFactory;
        List<Docket> docketList = new LinkedList<>();

        // 没有分组
        if (swagger2Properties.getDocket().size() == 0) {
            final Docket docket = createDocket(swagger2Properties);
            configurableBeanFactory.registerSingleton("defaultDocket", docket);
            docketList.add(docket);
            return docketList;
        }

        // 分组创建
        for (String groupName : swagger2Properties.getDocket().keySet()) {
            Swagger2Properties.DocketInfo docketInfo = swagger2Properties.getDocket().get(groupName);

            ApiInfo apiInfo = new ApiInfoBuilder()
                    .title(docketInfo.getTitle().isEmpty() ? swagger2Properties.getTitle() : docketInfo.getTitle())
                    .description(docketInfo.getDescription().isEmpty() ? swagger2Properties.getDescription() : docketInfo.getDescription())
                    .version(docketInfo.getVersion().isEmpty() ? swagger2Properties.getVersion() : docketInfo.getVersion())
                    .license(docketInfo.getLicense().isEmpty() ? swagger2Properties.getLicense() : docketInfo.getLicense())
                    .licenseUrl(docketInfo.getLicenseUrl().isEmpty() ? swagger2Properties.getLicenseUrl() : docketInfo.getLicenseUrl())
                    .contact(
                            new Contact(
                                    docketInfo.getContact().getName().isEmpty() ? swagger2Properties.getContact().getName() : docketInfo.getContact().getName(),
                                    docketInfo.getContact().getUrl().isEmpty() ? swagger2Properties.getContact().getUrl() : docketInfo.getContact().getUrl(),
                                    docketInfo.getContact().getEmail().isEmpty() ? swagger2Properties.getContact().getEmail() : docketInfo.getContact().getEmail()
                            )
                    )
                    .termsOfServiceUrl(docketInfo.getTermsOfServiceUrl().isEmpty() ? swagger2Properties.getTermsOfServiceUrl() : docketInfo.getTermsOfServiceUrl())
                    .build();

            // base-path处理
            // 当没有配置任何path的时候，解析/**
            if (docketInfo.getBasePath().isEmpty()) {
                docketInfo.getBasePath().add("/**");
            }
            List<Predicate<String>> basePath = new ArrayList<>(docketInfo.getBasePath().size());
            for (String path : docketInfo.getBasePath()) {
                basePath.add(PathSelectors.ant(path));
            }

            // exclude-path处理
            List<Predicate<String>> excludePath = new ArrayList<>(docketInfo.getExcludePath().size());
            for (String path : docketInfo.getExcludePath()) {
                excludePath.add(PathSelectors.ant(path));
            }

            Docket docket = new Docket(DocumentationType.SWAGGER_2)
                    .host(swagger2Properties.getHost())
                    .apiInfo(apiInfo)
                    .globalOperationParameters(assemblyGlobalOperationParameters(swagger2Properties.getGlobalOperationParameters(),
                            docketInfo.getGlobalOperationParameters()))
                    .groupName(groupName)
                    .select()
                    .apis(RequestHandlerSelectors.basePackage(docketInfo.getBasePackage()))
                    .paths(
                            Predicates.and(
                                    Predicates.not(Predicates.or(excludePath)),
                                    Predicates.or(basePath)
                            )
                    )
                    .build()
                    .securitySchemes(securitySchemes())
                    .securityContexts(securityContexts());

            configurableBeanFactory.registerSingleton(groupName, docket);
            docketList.add(docket);
        }
        return docketList;
    }

    /**
     * 创建 Docket对象
     *
     * @param swagger2Properties swagger配置
     * @return Docket
     */
    private Docket createDocket(final Swagger2Properties swagger2Properties) {
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title(swagger2Properties.getTitle())
                .description(swagger2Properties.getDescription())
                .version(swagger2Properties.getVersion())
                .license(swagger2Properties.getLicense())
                .licenseUrl(swagger2Properties.getLicenseUrl())
                .contact(new Contact(swagger2Properties.getContact().getName(),
                        swagger2Properties.getContact().getUrl(),
                        swagger2Properties.getContact().getEmail()))
                .termsOfServiceUrl(swagger2Properties.getTermsOfServiceUrl())
                .build();

        // base-path处理
        // 当没有配置任何path的时候，解析/**
        if (swagger2Properties.getBasePath().isEmpty()) {
            swagger2Properties.getBasePath().add("/**");
        }
        List<Predicate<String>> basePath = new ArrayList<>();
        for (String path : swagger2Properties.getBasePath()) {
            basePath.add(PathSelectors.ant(path));
        }

        // exclude-path处理
        List<Predicate<String>> excludePath = new ArrayList<>();
        for (String path : swagger2Properties.getExcludePath()) {
            excludePath.add(PathSelectors.ant(path));
        }

        return new Docket(DocumentationType.SWAGGER_2)
                .host(swagger2Properties.getHost())
                .apiInfo(apiInfo)
                .globalOperationParameters(buildGlobalOperationParametersFromSwaggerProperties(
                        swagger2Properties.getGlobalOperationParameters()))
                .select()
                .apis(RequestHandlerSelectors.basePackage(swagger2Properties.getBasePackage()))
                .paths(
                        Predicates.and(
                                Predicates.not(Predicates.or(excludePath)),
                                Predicates.or(basePath)
                        )
                )
                .build()
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    private List<SecurityContext> securityContexts() {
        List<SecurityContext> contexts = new ArrayList<>(1);
        SecurityContext securityContext = SecurityContext.builder()
                .securityReferences(defaultAuth())
                //.forPaths(PathSelectors.regex("^(?!auth).*$"))
                .build();
        contexts.add(securityContext);
        return contexts;
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        List<SecurityReference> references = new ArrayList<>(1);
        references.add(new SecurityReference(AUTH_KEY, authorizationScopes));
        return references;
    }

    private List<ApiKey> securitySchemes() {
        List<ApiKey> apiKeys = new ArrayList<>(1);
        ApiKey apiKey = new ApiKey(AUTH_KEY, AUTH_KEY, "header");
        apiKeys.add(apiKey);
        return apiKeys;
    }

    private List<Parameter> buildGlobalOperationParametersFromSwaggerProperties(
            List<Swagger2Properties.GlobalOperationParameter> globalOperationParameters) {
        List<Parameter> parameters = Lists.newArrayList();

        if (Objects.isNull(globalOperationParameters)) {
            return parameters;
        }
        for (Swagger2Properties.GlobalOperationParameter globalOperationParameter : globalOperationParameters) {
            parameters.add(new ParameterBuilder()
                    .name(globalOperationParameter.getName())
                    .description(globalOperationParameter.getDescription())
                    .modelRef(new ModelRef(globalOperationParameter.getModelRef()))
                    .parameterType(globalOperationParameter.getParameterType())
                    .required(Boolean.parseBoolean(globalOperationParameter.getRequired()))
                    .build());
        }
        return parameters;
    }

    /**
     * 局部参数按照name覆盖局部参数
     *
     * @param globalOperationParameters
     * @param docketOperationParameters
     * @return
     */
    private List<Parameter> assemblyGlobalOperationParameters(
            List<Swagger2Properties.GlobalOperationParameter> globalOperationParameters,
            List<Swagger2Properties.GlobalOperationParameter> docketOperationParameters) {

        if (Objects.isNull(docketOperationParameters) || docketOperationParameters.isEmpty()) {
            return buildGlobalOperationParametersFromSwaggerProperties(globalOperationParameters);
        }

        Set<String> docketNames = docketOperationParameters.stream()
                .map(Swagger2Properties.GlobalOperationParameter::getName)
                .collect(Collectors.toSet());

        List<Swagger2Properties.GlobalOperationParameter> resultOperationParameters = Lists.newArrayList();

        if (Objects.nonNull(globalOperationParameters)) {
            for (Swagger2Properties.GlobalOperationParameter parameter : globalOperationParameters) {
                if (!docketNames.contains(parameter.getName())) {
                    resultOperationParameters.add(parameter);
                }
            }
        }

        resultOperationParameters.addAll(docketOperationParameters);
        return buildGlobalOperationParametersFromSwaggerProperties(resultOperationParameters);
    }
}
