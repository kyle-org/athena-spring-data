package com.bluecedar.service.events.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.simba.athena.amazonaws.auth.BasicAWSCredentials;
import com.simba.athena.amazonaws.auth.BasicSessionCredentials;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.AwsSessionCredentials;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;

/**
 * To support AWS SDK v2 SSO auth, this is used as a translations from v2 SDK to v1 SDK credentials, essentially using
 * the v2 SDK for authentication, and translating the creds to a v1 cred object for the Athena JDBC jar.
 */
public class DefaultV2AWSCredentialsProviderChain implements AWSCredentialsProvider {
    DefaultCredentialsProvider awsV2ProviderChain;

    public DefaultV2AWSCredentialsProviderChain() {
        awsV2ProviderChain = DefaultCredentialsProvider.create();
    }

    @Override
    public AWSCredentials getCredentials() {
        AwsCredentials v2Creds = awsV2ProviderChain.resolveCredentials();
        if (v2Creds instanceof AwsSessionCredentials sessionCreds) {
            return new BasicSessionCredentials(sessionCreds.accessKeyId(), sessionCreds.secretAccessKey(), sessionCreds.sessionToken());
        } else if (v2Creds instanceof AwsBasicCredentials basicCreds) {
            return new BasicAWSCredentials(basicCreds.accessKeyId(), basicCreds.secretAccessKey());
        } else {
            throw new UnsupportedOperationException("Unsupported credential type: " + v2Creds.getClass());
        }
    }

    @Override
    public void refresh() {
        throw new UnsupportedOperationException("BC aws cred provider refresh unsupported");
    }
}
