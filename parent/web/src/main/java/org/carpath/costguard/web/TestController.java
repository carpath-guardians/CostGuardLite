package org.carpath.costguard.web;

import org.carpath.costguard.aws.tenant.AwsTenant;
import org.carpath.costguard.core.api.ChecklistItem;
import org.carpath.costguard.core.api.CostGuard;
import org.carpath.costguard.core.api.Tenant;
import org.carpath.costguard.core.spi.RuleFamily;
import org.carpath.costguard.core.spi.RuleFamilyRegistrar;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RequestMapping("/analyze")
@RestController
public class TestController {

    private final AwsTenant awsTenant;

    public TestController(AwsTenant awsTenant) {
        this.awsTenant = awsTenant;
    }

    @GetMapping
    public List<JsonReport> demo(@RequestParam(name = "rules") Set<String> rules) {
        RuleFamily<Tenant, ChecklistItem<Tenant>> awsRuleFamily = RuleFamilyRegistrar.getRuleFamilies().get("AWS");
        List<ChecklistItem<Tenant>> checklistItems = rules.stream().map(awsRuleFamily::getChecklistItem).collect(Collectors.toList());
        CostGuard<Tenant> demoCostGuard = new CostGuard<>(awsTenant, checklistItems);
        JsonReportVisitor report = new JsonReportVisitor();
        demoCostGuard.patrol(report);

        return report.getJsonReports();
    }

    @GetMapping("/options")
    public Set<String> returnRules() {
        return RuleFamilyRegistrar.getRuleFamilies().values().stream()
                .map(RuleFamily::getChecklistItems)
                .map(Map::keySet)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }
}
