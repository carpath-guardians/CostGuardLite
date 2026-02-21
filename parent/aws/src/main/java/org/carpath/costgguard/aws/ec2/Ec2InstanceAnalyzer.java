package org.carpath.costgguard.aws.ec2;

import org.carpath.costguard.core.api.ReportItem;
import software.amazon.awssdk.services.cloudwatch.CloudWatchClient;
import software.amazon.awssdk.services.cloudwatch.model.Dimension;
import software.amazon.awssdk.services.cloudwatch.model.GetMetricDataRequest;
import software.amazon.awssdk.services.cloudwatch.model.GetMetricDataResponse;
import software.amazon.awssdk.services.cloudwatch.model.MetricDataQuery;
import software.amazon.awssdk.services.cloudwatch.model.MetricDataResult;
import software.amazon.awssdk.services.cloudwatch.model.MetricStat;
import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.DescribeInstancesResponse;
import software.amazon.awssdk.services.ec2.model.Instance;
import software.amazon.awssdk.services.ec2.model.Reservation;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Ec2InstanceAnalyzer {

    public static final int DAY_IN_SECONDS = 864000;
    public static final int UPPER_THRESHOLD = 80;
    public static final int LOWER_THRESHOLD = 5;
    public static final String CPU_UTILIZATION = "CPUUtilization";

    private final Ec2Client ec2Client;
    private final CloudWatchClient cloudWatchClient;
    private final String accountId;

    public Ec2InstanceAnalyzer(Ec2Client ec2Client, CloudWatchClient cloudWatchClient, String accountId) {
        this.ec2Client = ec2Client;
        this.cloudWatchClient = cloudWatchClient;
        this.accountId = accountId;
    }

    public List<ReportItem> analyzeCPU() {
        String[] instanceIds = getInstanceIds();
        Map<String, MetricDataQuery> cpuUtilizationQueries = Arrays.stream(instanceIds)
                .collect(Collectors.toMap(Function.identity(), i -> getMetricDataQueryForInstance(i, CPU_UTILIZATION)));

        GetMetricDataResponse metricData = cloudWatchClient.getMetricData(GetMetricDataRequest.builder()
                .metricDataQueries(cpuUtilizationQueries.values())
                .startTime(Instant.now().minusSeconds(DAY_IN_SECONDS))
                .endTime(Instant.now())
                .build());

        return metricData.metricDataResults().stream()
                .map(m -> analyzeMetric(m, CPU_UTILIZATION))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private ReportItem analyzeMetric(MetricDataResult metricDataResult, String metricName) {
        if (!metricDataResult.hasValues()) {
            return null;
        }
        Double value = metricDataResult.values().get(0);
        String instanceId = metricDataResult.label();

        if (value > UPPER_THRESHOLD) {
            return new ReportItem("Instance" + instanceId + " has permanently overloaded " + metricName + "please scale up", 1);
        } else if (value < LOWER_THRESHOLD) {
            return new ReportItem("Instance" + instanceId + " is permanently underutilized for " + metricName + "please scale down", -1);
        }
        return null;
    }


    private MetricDataQuery getMetricDataQueryForInstance(String instanceId, String metricName) {
        return MetricDataQuery.builder()
                .accountId(accountId)
                .id(instanceId.replace("-", ""))
                .label(instanceId)
                .metricStat(MetricStat.builder()
                        .stat("p99")
                        .period(DAY_IN_SECONDS)
                        .metric(builder -> builder.metricName(metricName)
                                .namespace("AWS/EC2")
                                .dimensions(Dimension.builder()
                                        .name("InstanceId")
                                        .value(instanceId).build()))
                        .build())
                .returnData(true)
                .build();
    }

    private String[] getInstanceIds() {
        DescribeInstancesResponse describeInstancesResponse = ec2Client.describeInstances();
        return describeInstancesResponse.reservations().stream()
                .map(Reservation::instances)
                .flatMap(List::stream)
                .map(Instance::instanceId)
                .toArray(String[]::new);
    }

}
