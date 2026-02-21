package org.carpath.costgguard.aws.s3;

import com.google.auto.service.AutoService;
import org.carpath.costgguard.aws.AwsCostGuardChecklistItem;
import org.carpath.costgguard.aws.tenant.AwsTenant;
import org.carpath.costguard.core.api.ReportItem;
import org.carpath.costguard.core.spi.RegisteredItem;
import software.amazon.awssdk.services.s3.S3Client;

import java.util.ArrayList;
import java.util.List;

@RegisteredItem("unused-s3-storage")
@AutoService(AwsCostGuardChecklistItem.class)
public class UnusedS3StorageChecklistItem implements AwsCostGuardChecklistItem {
    @Override
    public List<ReportItem> check(AwsTenant tenant) {
        List<ReportItem> items = new ArrayList<>();
        try (S3Client s3Client = tenant.configureClient(S3Client.builder())) {
            s3Client.listBuckets();
            items.add(new ReportItem("test", 0));
        }
        return items;
    }
}
