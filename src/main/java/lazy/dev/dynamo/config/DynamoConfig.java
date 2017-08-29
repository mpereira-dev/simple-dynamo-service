package lazy.dev.dynamo.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import org.apache.commons.lang3.StringUtils;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableDynamoDBRepositories(basePackages = "lazy.dev")
@ComponentScan(basePackages = "lazy.dev")
public class DynamoConfig {

    @Value("${amazon.dynamodb.endpoint}")
    private String amazonDynamoDBEndpoint;

    @Value("${amazon.aws.accesskey}")
    private String amazonAWSAccessKey;

    @Value("${amazon.aws.secretkey}")
    private String amazonAWSSecretKey;

    @Value("${region}")
    private String region;

    @Bean
    public AWSCredentials amazonAWSCredentials() {
        // Or use an AWSCredentialsProvider/AWSCredentialsProviderChain
        return new BasicAWSCredentials(amazonAWSAccessKey, amazonAWSSecretKey);
    }

    @Bean
    public AWSStaticCredentialsProvider aWSStaticCredentialsProvider(AWSCredentials amazonAWSCredentials){
        return new AWSStaticCredentialsProvider(amazonAWSCredentials);
    }

    @Bean
    public AwsClientBuilder.EndpointConfiguration endpointConfiguration(){
        return new AwsClientBuilder.EndpointConfiguration(amazonDynamoDBEndpoint,region);
    }

    @Bean
    public AmazonDynamoDB amazonDynamoDB(AWSStaticCredentialsProvider awsStaticCredentialsProvider, AwsClientBuilder.EndpointConfiguration endpointConfiguration) {
        AmazonDynamoDBClientBuilder builder = AmazonDynamoDBClientBuilder.standard();

        if (StringUtils.isNotEmpty(amazonDynamoDBEndpoint)) {
            builder.setEndpointConfiguration(endpointConfiguration);
            builder.setCredentials(awsStaticCredentialsProvider);
        }

        return builder.build();
    }

}
