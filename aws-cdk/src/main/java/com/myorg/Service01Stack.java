package com.myorg;

import com.myorg.util.ConstantUtil;
import software.amazon.awscdk.*;
import software.amazon.awscdk.services.applicationautoscaling.EnableScalingProps;
import software.amazon.awscdk.services.ecs.*;
import software.amazon.awscdk.services.ecs.patterns.ApplicationLoadBalancedFargateService;
import software.amazon.awscdk.services.ecs.patterns.ApplicationLoadBalancedTaskImageOptions;
import software.amazon.awscdk.services.elasticloadbalancingv2.HealthCheck;
import software.amazon.awscdk.services.logs.LogGroup;
import software.constructs.Construct;

import java.util.HashMap;
import java.util.Map;
// import software.amazon.awscdk.Duration;
// import software.amazon.awscdk.services.sqs.Queue;

public class Service01Stack extends Stack {
    public Service01Stack(final Construct scope, final String id, Cluster cluster) {
        this(scope, id, null, cluster);
    }

    public Service01Stack(final Construct scope, final String id, final StackProps props, Cluster cluster) {
        super(scope, id, props);

        Map<String, String> envVariables = new HashMap<>();
        envVariables.put("SPRING_DATASOURCE_URL", "jdbc:mariadb://" + Fn.importValue(ConstantUtil.RDS_ENDPOINT)
                + ":3306/aws_project01?createDatabaseIfNotExist=true");
        envVariables.put("SPRING_DATASOURCE_USERNAME", ConstantUtil.RDS_USERNAME);
        envVariables.put("SPRING_DATASOURCE_PASSWORD", Fn.importValue(ConstantUtil.RDS_PASSWORD));



        ApplicationLoadBalancedFargateService service01 =
                ApplicationLoadBalancedFargateService.Builder.create(this, "ALB01")
                        .serviceName("service-01")
                        .cluster(cluster)
                        .cpu(512)
                        .memoryLimitMiB(1024)
                        .desiredCount(2)
                        .listenerPort(8080)
                        .assignPublicIp(true)
                        .taskImageOptions(
                                ApplicationLoadBalancedTaskImageOptions.builder()
                                        .containerName("aws_project01")
                                        .image(ContainerImage.fromRegistry("talima94/curso_aws_project01:1.0.2"))
                                        .containerPort(8080)
                                        .logDriver(LogDriver.awsLogs(AwsLogDriverProps.builder()
                                                .logGroup(LogGroup.Builder.create(this, "Service01LogGroup")
                                                        .logGroupName("Service01")
                                                        .removalPolicy(RemovalPolicy.DESTROY)
                                                        .build())
                                                .streamPrefix("Service01")
                                                .build()))
                                        .environment(envVariables)
                                        .build())
                        .publicLoadBalancer(true)
                        .build();

        //Configura????o para o monitoramento da sa??de da aplica????o
        service01.getTargetGroup().configureHealthCheck(HealthCheck.builder()
                .path("/actuator/health")
                .port("8080")
                .healthyHttpCodes("200")
                .build());

        //Configura????o do auto-scaling
        ScalableTaskCount scalableTaskCount = service01.getService()
                .autoScaleTaskCount(EnableScalingProps.builder()
                        .minCapacity(2)
                        .maxCapacity(4)
                        .build());

        //Controle do uso de CPU
        scalableTaskCount.scaleOnCpuUtilization("Service01AutoScaling", CpuUtilizationScalingProps.builder()
                .targetUtilizationPercent(50)
                .scaleInCooldown(Duration.seconds(60)) //Valores sensiveis
                .scaleOutCooldown(Duration.seconds(60))
                .build());

    }
}
