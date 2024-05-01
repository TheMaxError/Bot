package com.example.bot.BotListener;


import com.example.bot.ClientServer.ClientServerImpl.AiService;
import kotlin.coroutines.CoroutineContext;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.stereotype.Component;


@Component
public class MyEventHandlers extends SimpleListenerHost {

    @Autowired
    private AiService aiService;
    @Override
    public void handleException(@NotNull CoroutineContext context, @NotNull Throwable exception){
        // TODO 处理事件处理时抛出的异常
    }
//    @EventHandler
//    public void onMessage(@NotNull MessageEvent event) throws Exception { // 可以抛出任何异常, 将在 handleException 处理
//        event.getSubject().sendMessage("received");
//        // 无返回值, 表示一直监听事件.
//    }
    @NotNull
    @EventHandler
    public ListeningStatus onMessage(@NotNull MessageEvent event) throws Exception { // 可以抛出任何异常, 将在 handleException 处理
        //1.接收信息并对信息进行分析处理
        MessageChain message = event.getMessage();
        //若消息来自于非群聊则可直接进行回复，否则不可以
        // TODO 对数据进行图像，音频等进行判断回复
        if (event.getSender().getId()==event.getSubject().getId()){//该消息并非发送到群聊中可直接进行消息处理与发送
            //2.交给ai进行数据处理后发送
            String call =aiService.Response(message.contentToString());
            event.getSubject().sendMessage(call);
        }else{//该消息是发送到群聊的，需要进行@后才可以进行消息处理发送
            //判断信息是否通过群聊进行@机器人,若是则此时机器人才进行回复，否则不回复
            if(("[mirai:at:"+event.getBot().getId()+"]").equals(message.get(At.Key).toString())){
                //2. TODO 进行前文引用

                //3.交给ai做message处理
                String call =aiService.Response(message.contentToString());
                //4.message发送
                event.getSubject().sendMessage(new At(event.getSender().getId()).plus(call));
            }
        }


        return ListeningStatus.LISTENING; // 表示继续监听事件
        // return ListeningStatus.STOPPED; // 表示停止监听事件
    }

}
// 注册：
// eventChannel.registerListenerHost(new MyEventHandlers())

