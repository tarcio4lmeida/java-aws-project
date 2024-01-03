package com.myorg;

import software.amazon.awscdk.App;
import software.amazon.awscdk.Environment;
import software.amazon.awscdk.StackProps;

public class AwsCdkApp {
    public static void main(final String[] args) {
        App app = new App();

        Environment envAmericaSul = makeEnv("014178825164", "sa-east-1");
        VpcStack vpcStack = new VpcStack(app, "Vpc", StackProps.builder()
                .env(envAmericaSul).build());

        ClusterStack clusterStack = new ClusterStack(app, "Cluster",  StackProps.builder()
                .env(envAmericaSul).build(), vpcStack.getVpc());
        clusterStack.addDependency(vpcStack);

        RdsStack rdsStack = new RdsStack(app, "Rds", StackProps.builder()
                .env(envAmericaSul).build(), vpcStack.getVpc());
        rdsStack.addDependency(vpcStack);

        SnsStack snsStack = new SnsStack(app, "Sns", StackProps.builder()
                .env(envAmericaSul).build());

        Service01Stack service01Stack = new Service01Stack(app, "Service01",  StackProps.builder()
                .env(envAmericaSul).build(), clusterStack.getCluster(), snsStack.getProductEventsTopic());
        service01Stack.addDependency(clusterStack);
        service01Stack.addDependency(rdsStack);
        service01Stack.addDependency(snsStack);

        app.synth();
    }

    static Environment makeEnv(String account, String region) {
        return Environment.builder()
                .account(account)
                .region(region)
                .build();
    }
}

