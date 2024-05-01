package com.example.bot.ClientServer;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Group;

public interface BotServer {
    //1.初始化Bot(登录和设置缓存文件位置)
    Bot InitBot(long  qq);

}
