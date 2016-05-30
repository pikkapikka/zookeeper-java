import com.softisland.curator.client.CuratorFrameworkUtils;
import com.softisland.curator.framework.BotCuratorUtils;
import org.apache.curator.framework.CuratorFramework;

import java.util.List;
import java.util.Random;

/**
 * Created by liwx on 2016/4/25.
 */
public class Test {

    public static void main(String[] args) throws Exception{
        //123.57.53.128
        //String str = "172.16.14.84:2181,172.16.14.84:2182,172.16.14.84:2183";
        String str = "123.57.53.128:2181,123.57.53.128:2182,123.57.53.128:2183";
        CuratorFramework client = CuratorFrameworkUtils.createSimple(str);
        client.start();
        /*CuratorCrudUtils.create(client,"/collect",null);
        CuratorCrudUtils.create(client,"/collect/bot",null);
        CuratorCrudUtils.create(client,"/collect/bot/common",null);*/
        //collect/bot/vip
        /*CuratorCrudUtils.create(client,"/collect/bot/vip",null);
        CuratorCrudUtils.create(client,"/collect/bot/self",null);
        CuratorCrudUtils.create(client,"/collect/bot/used",null);
        CuratorCrudUtils.create(client,"/collect/bot/off",null);
        CuratorCrudUtils.create(client,"/collect/bot/all",null);*/

        /*CuratorCrudUtils.create(client,"/collect/bot/session",null);
        CuratorCrudUtils.create(client,"/collect/bot/info",null);*/

        //CuratorCrudUtils.create(client,"/collect/bot/offflag","false".getBytes());

        //addCommonBot(client,"123");

       /* CuratorCrudUtils.create(client,"/collect/bot/stocknum",null);
        CuratorCrudUtils.create(client,"/collect/bot/stocknum/concurrent",null);*/

        /*CuratorCrudUtils.create(client,"/collect/bot/stock",null);
        CuratorCrudUtils.create(client,"/collect/bot/stock/lock",null);*/


        //String flag = CuratorCrudUtils.getData(client,"/collect/bot/offflag");
        //BotCuratorUtils.setTradeOffFlag(client,false);
        //System.out.println(flag);


        List<String> nodes =  BotCuratorUtils.getAllUsedBots(client);
        System.out.println(nodes.size());

        /*BotCuratorUtils.addOffLineBot(client,"76561198297106581","10.172.135.182;a");
        BotCuratorUtils.addOffLineBot(client,"76561198297108021","10.172.135.182;a");
        BotCuratorUtils.addOffLineBot(client,"76561198297678052","10.172.135.182;a");
        BotCuratorUtils.addOffLineBot(client,"76561198299187591","10.172.135.182;a");
        BotCuratorUtils.addOffLineBot(client,"76561198297806326","10.172.135.182;a");
        BotCuratorUtils.addOffLineBot(client,"76561198297806457","10.172.135.182;a");
        BotCuratorUtils.addOffLineBot(client,"76561198297302746","10.172.135.182;a");
        BotCuratorUtils.addOffLineBot(client,"76561198296564451","10.172.135.182;a");
        BotCuratorUtils.addOffLineBot(client,"76561198296427694","10.172.135.182;a");
        BotCuratorUtils.addOffLineBot(client,"76561198297171736","10.172.135.182;a");*/

       /* BotCuratorUtils.addOffLineBot(client,"76561198297106581","10.172.135.182;a");

        BotCuratorUtils.addOffLineBot(client,"76561198297108021","10.172.135.182;a");

        BotCuratorUtils.addOffLineBot(client,"76561198297678052","10.172.135.182;a");

        BotCuratorUtils.addOffLineBot(client,"76561198299187591","10.172.135.182;a");

        BotCuratorUtils.addOffLineBot(client,"76561198297806326","10.172.135.182;a");

        BotCuratorUtils.addOffLineBot(client,"76561198297806457","10.172.135.182;a");

        BotCuratorUtils.addOffLineBot(client,"76561198297302746","10.172.135.182;a");

        BotCuratorUtils.addOffLineBot(client,"76561198296564451","10.172.135.182;a");

        BotCuratorUtils.addOffLineBot(client,"76561198296427694","10.172.135.182;a");

        BotCuratorUtils.addOffLineBot(client,"76561198297171736","10.172.135.182;a");*/


        //BotCuratorUtils.addOffLineBot(client,"76561198297678052","10.172.135.182;a");

        //Map<String,String> botInfoMap = BotCuratorUtils.getAllOnlineBotsData(client);

        //BotCuratorUtils.deleUsedBot(client,"76561198297756834");
        nodes.forEach(v-> {
            System.out.println(v);
            try {
                //System.out.println(BotCuratorUtils.getOffLineBotData(client,v));
            } catch (Exception e) {
                e.printStackTrace();
            }
            /*try {
                BotCuratorUtils.getOffLineBotData(client,v);
            } catch (Exception e) {
                e.printStackTrace();
            }*/
            /*try {
                if(v.equals("76561198267157839")){
                    BotCuratorUtils.addOffLineBot(client,"76561198267157839","10.172.135.182;a");
                }
                String ss = BotCuratorUtils.getOffLineBotData(client,v);

                System.out.println(ss);
            } catch (Exception e) {
                e.printStackTrace();
            }*/
            /*try {
                String data = CuratorCrudUtils.getData(client,BotCuratorUtils.OFF_LINE_BOT_NODE_PATH+"/"+v)+";"+v;
                //BotCuratorUtils.addOffLineBot(client,"76561198267076076","10.44.47.131;a");
                System.out.println("data:"+data);
            } catch (Exception e) {
                e.printStackTrace();
            }*/
            /*try {
                BotCuratorUtils.deleUsedBot(client,v);
            } catch (Exception e) {
                e.printStackTrace();
            }*/
            /*try {
                BotCuratorUtils.deleCommonBot(client,v);
            } catch (Exception e) {
                e.printStackTrace();
            }*/
            /*try {
                byte[] bytes = CuratorCrudUtils.getData(client,VIP_BOT_NODE_PATH+"/"+v);
                System.out.println("+++++++++++++++++:"+new String(bytes));
            } catch (Exception e) {
                e.printStackTrace();
            }*/
        });
        /*botInfoMap.forEach((v,k)->{
            System.out.println(v);
            try {
                //BotCuratorUtils.deleBotInfo(client,v);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });*/
        //System.out.println(botInfoMap.containsKey("76561198267097286"));
        //System.out.println(nodes.indexOf("76561198289514405")>-1);
        client.close();
    }

    public static void addCommonBot(CuratorFramework client,String id)throws Exception{
        //BotCuratorUtils.addCommonBot(client,id,"172.16.14.65:8080");
        BotCuratorUtils.deleCommonBot(client,id);
    }

    public static void main1(String[] args) {
        Random random = new Random();
        System.out.println(random.nextInt(0));
    }

}
