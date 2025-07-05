package com.achrafelaffas.mcpclient.agents;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.stereotype.Service;

@Service
public class GitlabAgent {
   private final ChatClient client;

    public GitlabAgent(ChatClient.Builder clientBuilder, ToolCallbackProvider toolCallbackProvider) {
        this.client = clientBuilder
                .defaultSystem("You are an agent that helps users managed their gitlab projects")
                .defaultToolCallbacks(toolCallbackProvider)
                .defaultAdvisors(MessageChatMemoryAdvisor
                        .builder(MessageWindowChatMemory.builder().build()).build()
                ).build();
    }

    public String prompt(String question) {
        return this.client.prompt().user(question).call().content();
    }
}
