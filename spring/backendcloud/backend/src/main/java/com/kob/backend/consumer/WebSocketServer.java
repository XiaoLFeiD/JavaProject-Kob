package com.kob.backend.consumer;

import com.alibaba.fastjson.JSONObject;
import com.kob.backend.consumer.utils.Game;
import com.kob.backend.consumer.utils.JwtAuthentication;
import com.kob.backend.mapper.BotMapper;
import com.kob.backend.mapper.RecordMapper;
import com.kob.backend.mapper.UserMapper;
import com.kob.backend.pojo.Bot;
import com.kob.backend.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

//WebSocket前后端双向数据传递
//一个对应实例来维护一个用户的链接请求
//通常实际上是一个session来维护一个用户的链接请求
//实现后端向前端发送信息时候 需要用到websocket中的Session包
@Component
@ServerEndpoint("/websocket/{token}")  // 注意不要以'/'结尾
public class WebSocketServer {
    //前后端发送消息时候需要直到对应的链接是哪个用户 并且匹配成功后需要广播给匹配到的用户
    //所以需要有一个包含所有链接的数组和用户信息
    private Session session = null;
    private User user;
    //对于所有线程公有的数据用线程安全哈希表存储
    final public static ConcurrentHashMap<Integer,WebSocketServer> users = new ConcurrentHashMap<>();
    //匹配池
    //服务移动到system中
    //final private static CopyOnWriteArraySet<User> matchpool = new CopyOnWriteArraySet<>();
    public static UserMapper userMapper;
    public static RecordMapper recordMapper;
    public static BotMapper botMapper;
    public Game game = null;
    public static RestTemplate restTemplate;
    private final static String addPlayerUrl = "http://127.0.0.1:3001/player/add/";
    private final static String removePlayerurl = "http://127.0.0.1:3001/player/remove/";

    @Autowired
    public void setUserMapper(UserMapper userMapper){
        WebSocketServer.userMapper = userMapper;
    }
    @Autowired
    public void setRecordMapper(RecordMapper recordMapper) {
        WebSocketServer.recordMapper = recordMapper;
    }
    @Autowired
    public void setRestTemplate(RestTemplate restTemplate){
        WebSocketServer.restTemplate = restTemplate;
    }
    @Autowired
    public void setBotMapper(BotMapper botMapper){
        WebSocketServer.botMapper = botMapper;
    }
    @OnOpen
    public void onOpen(Session session, @PathParam("token") String token) throws IOException {
        // 建立连接
        this.session = session;
        int userid = JwtAuthentication.getUserId(token);
        this.user = userMapper.selectById(userid);

       if(this.user != null){
           users.put(userid,this);
       }else{
           this.session.close();
       }
        System.out.println(users);
    }

    @OnClose
    public void onClose() {
        System.out.println("disconnected");
        if(this.user != null){
            users.remove(this.user.getId());
        }
        // 关闭链接
    }
    private void move(int direction) {
        if (game.getPlayerA().getId().equals(user.getId())) {
            if (game.getPlayerA().getBotId().equals(-1))  // 亲自出马
                game.setNextStepA(direction);
        } else if (game.getPlayerB().getId().equals(user.getId())) {
            if (game.getPlayerB().getBotId().equals(-1))  // 亲自出马
                game.setNextStepB(direction);
        }
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("receive");
        // 从Client接收消息(后端接收前端发来的信息)
        JSONObject data = JSONObject.parseObject(message);
        String event = data.getString("event");
        if ("start-matching".equals(event)) {
            startMatching(data.getInteger("bot_id"));
        } else if ("stop-matching".equals(event)) {
            stopMatching();
        }else if ("move".equals(event)) {
            move(data.getInteger("direction"));
        }
    }
    public static void startGame(Integer aId,Integer aBotId,Integer bId,Integer bBotId) {
        User a = userMapper.selectById(aId), b = userMapper.selectById(bId);
        Bot botA = botMapper.selectById(aBotId),botB = botMapper.selectById(bBotId);
        Game game =
                new Game(
                        13,
                        14,
                        20,
                        a.getId(),
                        botA,
                        b.getId(),
                        botB);
        game.createMap();
        if (users.get(a.getId()) != null)
            users.get(a.getId()).game = game;
        if (users.get(b.getId()) != null)
            users.get(b.getId()).game = game;

        game.start();

        JSONObject respGame = new JSONObject();
        respGame.put("a_id", game.getPlayerA().getId());
        respGame.put("a_sx", game.getPlayerA().getSx());
        respGame.put("a_sy", game.getPlayerA().getSy());
        respGame.put("b_id", game.getPlayerB().getId());
        respGame.put("b_sx", game.getPlayerB().getSx());
        respGame.put("b_sy", game.getPlayerB().getSy());
        respGame.put("map", game.getG());

        JSONObject respA = new JSONObject();
        respA.put("event", "start-matching");
        respA.put("opponent_username", b.getUsername());
        respA.put("opponent_photo", b.getPhoto());
        respA.put("game", respGame);
        if (users.get(a.getId()) != null)
            users.get(a.getId()).sendMessage(respA.toJSONString());

        JSONObject respB = new JSONObject();
        respB.put("event", "start-matching");
        respB.put("opponent_username", a.getUsername());
        respB.put("opponent_photo", a.getPhoto());
        respB.put("game", respGame);
        if (users.get(b.getId()) != null)
            users.get(b.getId()).sendMessage(respB.toJSONString());
    }

    private void stopMatching() {
        System.out.println("stop matching");
        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("user_id", this.user.getId().toString());
        restTemplate.postForObject(removePlayerurl, data, String.class);
    }

    private void startMatching(Integer botId) {
        System.out.println("start matching");
        MultiValueMap<String,String> data = new LinkedMultiValueMap<>();
        data.add("user_id", this.user.getId().toString());
        data.add("rating", this.user.getRating().toString());
        data.add("bot_id", botId.toString());
        //后端发送请求到匹配端
        restTemplate.postForObject(addPlayerUrl, data, String.class);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

    public void sendMessage(String message){
        //当一个方法或代码块被 synchronized 修饰时，它被称为同步方法或同步代码块.
        //只有一个线程可以执行同步方法或同步代码块，其他线程需要等待当前线程执行完毕后才能执行。
        //指定了如下代码块成为锁
        synchronized (this.session){
            try{
                this.session.getBasicRemote().sendText(message);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}

