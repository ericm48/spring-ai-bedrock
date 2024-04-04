package dev.steve.dadjokes;

import org.springframework.ai.BedrockCohereChatClient;
import org.springframework.ai.chat.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


// @RestController
// public class ChatController {

//     private final BedrockCohereChatClient chatClient;

//     @Autowired
//     public ChatController(BedrockCohereChatClient chatClient) {
//         this.chatClient = chatClient;
//     }

//     @GetMapping("/dad-jokes")
//     public Map generate(@RequestParam(value = "message", defaultValue = "Tell me a dad joke") String message) {
//         return Map.of("generation", chatClient.call(message));
//     }

//     @GetMapping("/dad-jokes")
// 	public Flux<ChatResponse> generateStream(@RequestParam(value = "message", defaultValue = "Tell me a dad joke") String message) {
//         Prompt prompt = new Prompt(new UserMessage(message));
//         return chatClient.stream(prompt);
//     }
// }




public class ChatController {

    private final ChatClient chatClient;

    public ChatController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    
    @GetMapping("/dad-jokes")
    public String generate(@RequestParam(value = "message", defaultValue = "Tell me a dad joke") String message) {
        return chatClient.call(message);
    }
} 






