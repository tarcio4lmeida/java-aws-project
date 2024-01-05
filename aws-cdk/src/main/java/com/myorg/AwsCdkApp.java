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

        DdbStack ddbStack = new DdbStack(app, "Ddb", StackProps.builder()
                .env(envAmericaSul).build());

        Service02Stack service02Stack = new Service02Stack(app, "Service02",  StackProps.builder()
                .env(envAmericaSul).build(), clusterStack.getCluster(), snsStack.getProductEventsTopic(), ddbStack.getProductEventsDdb());
        service02Stack.addDependency(clusterStack);
        service02Stack.addDependency(snsStack);
        service02Stack.addDependency(ddbStack);


        app.synth();
    }

    static Environment makeEnv(String account, String region) {
        return Environment.builder()
                .account(account)
                .region(region)
                .build();
    }
}

