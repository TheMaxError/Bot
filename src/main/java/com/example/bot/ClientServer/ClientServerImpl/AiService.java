package com.example.bot.ClientServer.ClientServerImpl;

import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@PropertySource(value="classpath:/prompts/chat.yml",encoding ="UTF-8")
public class AiService {
    private final ChatClient chatClient;
    public List<Message> messages=new ArrayList<>();
    private final static Integer MAX_SIZE = 5;

    private String prompts;

    @Autowired
    public AiService(ChatClient chatClient, @Value("${prompts}")String prompts)
    {
        this.chatClient = chatClient;
        this.prompts = prompts;
        messages.add(new SystemMessage(this.prompts));
    }

    private void addUserMessage(String message)
    {
        Message userMessage = new UserMessage(message);
        messages.add(userMessage);
    }
    public String Response(String message){
        addUserMessage(message);
        Prompt prompt=new Prompt(messages);
        //将消息发送给ai
        ChatResponse call = chatClient.call(prompt);
        //获得ai回答G
        String result = call.getResult().getOutput().getContent();
        //实现上下文对话
        messages.add(call.getResult().getOutput());
        update();
        return result;
    }
    public void update(){
        if(messages.size() > MAX_SIZE)
        {
            messages = messages.subList(messages.size() - MAX_SIZE, messages.size());
            //使得prompts一直生效
            messages.add(0,new SystemMessage(prompts));
        }
    }



}

