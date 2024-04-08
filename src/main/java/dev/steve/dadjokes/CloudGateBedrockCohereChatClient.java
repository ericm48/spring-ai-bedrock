package dev.steve.dadjokes;

import java.security.InvalidParameterException;

import org.springframework.ai.bedrock.cohere.BedrockCohereChatClient;
import org.springframework.ai.bedrock.cohere.BedrockCohereChatOptions;
import org.springframework.ai.bedrock.cohere.api.CohereChatBedrockApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.AwsSessionCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;

@Component
public class CloudGateBedrockCohereChatClient {

    private BedrockCohereChatClient client = null;

    @Value("${modelid}")
    private String modelId;

    @Value("${spring.ai.bedrock.aws.region}")
    private String region;

    @Value("${spring.ai.bedrock.aws.access-key}")
    private String accessKey;

    @Value("${spring.ai.bedrock.aws.secret-key}")
    private String secretKey;

    @Value("${spring.ai.bedrock.aws.session-token}")
    private String sessionToken;

    @Value("${spring.ai.bedrock.cohere.chat.options.temperature}")
    private float temp;

    public BedrockCohereChatClient generate() 
    {
        ObjectMapper mapper = null;            
        AwsCredentials awsCreds = null;
        AwsCredentialsProvider awsCredentialProvider = null;
        BedrockCohereChatOptions chatOptions = null;

        if (client == null) 
        {
            awsCreds = AwsSessionCredentials.create(accessKey, secretKey, sessionToken);
            awsCredentialProvider = StaticCredentialsProvider.create(awsCreds); 
            chatOptions = new BedrockCohereChatOptions();

            chatOptions.setTemperature(temp);  //any additional config needed here?

            mapper = new ObjectMapper();            
            CohereChatBedrockApi chatApi = new CohereChatBedrockApi(modelId, awsCredentialProvider, region, mapper);

            client = new BedrockCohereChatClient(chatApi, chatOptions);    
        }

        return client;
    }

}

