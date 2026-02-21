package org.carpath.costguard.aws.tenant;

import org.carpath.costguard.core.api.Tenant;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.awscore.AwsClient;
import software.amazon.awssdk.awscore.client.builder.AwsClientBuilder;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sts.StsClient;

public class AwsTenant implements Tenant {
    private final AwsCredentialsProvider credentialsProvider;
    private final String accountId;
    private final Region region;


    public AwsTenant(AwsCredentialsProvider credentialsProvider, Region region) {
        this.credentialsProvider = credentialsProvider;
        this.accountId = StsClient.builder().credentialsProvider(credentialsProvider).build()
                .getCallerIdentity().account();
        this.region = region;
    }

    @Override
    public String getReportName() {
        return accountId + "-" + region;
    }

    public String getAccountId() {
        return accountId;
    }

    public <T extends AwsClient, B extends AwsClientBuilder<B, T>> T configureClient(AwsClientBuilder<B , T> clientBuilder) {
        return clientBuilder.credentialsProvider(credentialsProvider).region(region).build();
    }
}
