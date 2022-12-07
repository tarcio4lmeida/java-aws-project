package com.myorg;

import software.amazon.awscdk.App;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.ec2.Vpc;

public class AwsCdkApp {
    public static void main(final String[] args) {
        App app = new App();

        VpcStack vpcStack = new VpcStack(app, "Vpc");
        Vpc vpc = vpcStack.getVpc();

        ClusterStack clusterStack = new ClusterStack(app, "Cluster", vpc);
        clusterStack.addDependency(vpcStack);

        app.synth();
    }
}

