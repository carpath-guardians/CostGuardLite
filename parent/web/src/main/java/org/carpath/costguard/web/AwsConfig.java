package org.carpath.costguard.web;

import org.carpath.costgguard.aws.tenant.AwsTenant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.regions.Region;

@Configuration
public class AwsConfig {
    @Bean
    public AwsTenant awsTenant() {
        return new AwsTenant(EnvironmentVariableCredentialsProvider.create(), Region.EU_CENTRAL_1);
    }
}
