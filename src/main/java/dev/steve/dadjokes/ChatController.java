package dev.steve.dadjokes;

import java.util.Map;

import reactor.core.publisher.Flux;

import org.springframework.ai.bedrock.cohere.BedrockCohereChatClient;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// *** below was copied from SpringAI documentation specifically for Cohere ***
@RestController
public class ChatController {

    private final BedrockCohereChatClient chatClient;
    private final BedrockCohereChatClient chatClientCG; //CG Version

    @Autowired
    public ChatController(BedrockCohereChatClient chatClient, CloudGateBedrockCohereChatClient cloudGateChatClient) {
        this.chatClient = chatClient;
        this.chatClientCG = cloudGateChatClient.generate();
    }

    @GetMapping("/dad-jokes")
    public Map generate(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        return Map.of("generation", chatClientCG.call(message));
    }

    @GetMapping("/dad-jokes/generateStream")
    public Flux<ChatResponse> generateStream(
            @RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        Prompt prompt = new Prompt(new UserMessage(message));
        return chatClientCG.stream(prompt);
    }
}
