package com.example.bot.ClientServer.ClientServerImpl;
import com.example.bot.ClientServer.BotServer;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.auth.BotAuthorization;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.utils.BotConfiguration;
import org.springframework.stereotype.Service;

@Service
public class BotServerImpl implements BotServer {
    @Override
    public Bot InitBot(long qq) {
       Bot bot= BotFactory.INSTANCE.newBot(qq, BotAuthorization.byQRCode(),new BotConfiguration(){{
           setProtocol(MiraiProtocol.ANDROID_WATCH);
       }});
       return bot;
    }
}
