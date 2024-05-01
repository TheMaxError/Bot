package com.example.bot.BotController;


import com.example.bot.BotListener.MyEventHandlers;
import com.example.bot.ClientServer.ClientServerImpl.AiService;
import com.example.bot.ClientServer.ClientServerImpl.BotServerImpl;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.event.EventChannel;
import net.mamoe.mirai.event.events.BotEvent;
import net.mamoe.mirai.event.events.FriendMessageEvent;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.At;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BotController implements ApplicationRunner {
    @Autowired
    BotServerImpl botServer;
    @Autowired
    MyEventHandlers myEventHandlers;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        long qq=111111111L;//请输入自己的qq号,将该qq作为机器人进行部署
        Bot bot = botServer.InitBot((qq));
        //进行bot的登录
        bot.login();
        //注册消息监听器
        bot.getEventChannel().registerListenerHost(myEventHandlers);

    }
}
