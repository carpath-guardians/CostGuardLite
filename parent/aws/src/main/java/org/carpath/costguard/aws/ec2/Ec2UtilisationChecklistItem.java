package org.carpath.costguard.aws.ec2;

import com.google.auto.service.AutoService;
import org.carpath.costguard.aws.AwsCostGuardChecklistItem;
import org.carpath.costguard.aws.tenant.AwsTenant;
import org.carpath.costguard.core.api.ReportItem;
import org.carpath.costguard.core.spi.RegisteredItem;
import software.amazon.awssdk.services.cloudwatch.CloudWatchClient;
import software.amazon.awssdk.services.ec2.Ec2Client;

import java.util.List;

@RegisteredItem("ec2-cpu")
@AutoService(AwsCostGuardChecklistItem.class)
public class Ec2UtilisationChecklistItem implements AwsCostGuardChecklistItem {

    @Override
    public List<ReportItem> check(AwsTenant tenant) {
        try (Ec2Client ec2Client = tenant.configureClient(Ec2Client.builder());
             CloudWatchClient cloudWatchClient = tenant.configureClient(CloudWatchClient.builder())) {
            return new Ec2InstanceAnalyzer(ec2Client, cloudWatchClient, tenant.getAccountId()).analyzeCPU();
        }
    }

}
