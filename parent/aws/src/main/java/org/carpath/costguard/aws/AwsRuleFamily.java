package org.carpath.costguard.aws;

import com.google.auto.service.AutoService;
import org.carpath.costguard.aws.tenant.AwsTenant;
import org.carpath.costguard.core.spi.RuleFamily;

@AutoService(RuleFamily.class)
public class AwsRuleFamily extends RuleFamily<AwsTenant, AwsCostGuardChecklistItem> {
    public static final String NAME = "AWS";
    public AwsRuleFamily() {
        super(NAME, AwsCostGuardChecklistItem.class);
    }
}
