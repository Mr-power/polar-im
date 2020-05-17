package com.aias.polar.jwt.config;

import com.aias.polar.jwt.inteceptor.JwtInteceptor;
import com.aias.polar.jwt.porperties.JwtProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @Classname SecurityAutoConfiguration
 * @Description TODO
 * @Date 2019/12/15 16:15
 * @Auther liuhy
 */
//@Configuration
@EnableConfigurationProperties(JwtProperties.class)
public class JwtAutoConfiguration implements WebMvcConfigurer {

    @Resource
    private JwtInteceptor jwtInteceptor;

    @Resource
    private JwtProperties jwtProperties;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInteceptor).addPathPatterns("/**")
                // .excludePathPatterns("/swagger*/**")
                .excludePathPatterns("/**/*.css", "/**/*.js", "/**/*.png",
                        "/**/*.jpg", "/**/*.jpeg", "/*.html", "/**/*.html",
                        "/*.jpg", "/*.ico", "/resources/**")
                .excludePathPatterns(jwtProperties.getExcludePathPatterns());
    }

    //    @Bean
//    CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(Collections.singletonList("127.0.0.1"));
//        configuration.setAllowedMethods(Arrays.asList("GET", "POST"));
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }

//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web.ignoring()
//                // .antMatchers("/swagger-ui.html").antMatchers("/swagger**").antMatchers("/swagger**/**")
//                // .antMatchers("/webjars/**").antMatchers("/v2/api-docs")
//                // .antMatchers("/**/login", "/login", "/login.*", "/login/*");
//                .antMatchers(securityProperties.getWeb().getIgnorePages());
//    }
//
//    @Override
//    public void configureMessageConverters(
//            List<HttpMessageConverter<?>> converters) {
//
//        FastJsonHttpMessageConverter4 oFastConverter = new FastJsonHttpMessageConverter4();
//
//        FastJsonConfig oFastJsonConfig = new FastJsonConfig();
//        oFastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
//        oFastConverter.setFastJsonConfig(oFastJsonConfig);
//        // 处理中文乱码问题
//        List<MediaType> oFastMediaTypeList = new ArrayList<MediaType>();
//        oFastMediaTypeList.add(MediaType.APPLICATION_JSON_UTF8);
//        oFastConverter.setSupportedMediaTypes(oFastMediaTypeList);
//
//        converters.add(oFastConverter);
//    }
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(jwtAuthInterceptor).addPathPatterns("/**")
//                // .excludePathPatterns("/swagger*/**")
//                .excludePathPatterns("/**/*.css", "/**/*.js", "/**/*.png",
//                        "/**/*.jpg", "/**/*.jpeg", "/*.html", "/**/*.html",
//                        "/*.jpg", "/*.ico", "/resources/**")
//                .excludePathPatterns(
//                        securityProperties.getJwt().getExcludePathPatterns());
//    }
}
