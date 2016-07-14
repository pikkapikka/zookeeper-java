import com.softisland.curator.client.CuratorFrameworkUtils;
import com.softisland.curator.framework.BotCuratorUtils;
import org.apache.curator.framework.CuratorFramework;

import java.util.List;

/**
 * Created by liwx on 2016/4/25.
 */
public class Test {

    public static void main(String[] args) throws Exception{
        //123.57.53.128
        //测试
        //String str = "172.16.14.84:2181,172.16.14.84:2182,172.16.14.84:2183";
        //交易
        String str = "123.57.53.128:2181,123.57.53.128:2182,123.57.53.128:2183";
        //轮盘
        //String str = "158.85.190.88:2181,158.85.190.88:2182,158.85.190.88:2183";
        //菠菜
        //String str = "120.25.67.161:2181,120.25.67.161:2182,120.25.67.161:2183";
        //国际
        //String str = "47.88.103.138:2181,47.88.103.138:2182,47.88.103.138:2183";
        CuratorFramework client = CuratorFrameworkUtils.createSimple(str);
        client.start();
        /*CuratorCrudUtils.create(client,"/collect",null);
        CuratorCrudUtils.create(client,"/collect/bot",null);
        CuratorCrudUtils.create(client,"/collect/bot/common",null);
        //collect/bot/vip
        CuratorCrudUtils.create(client,"/collect/bot/vip",null);
        CuratorCrudUtils.create(client,"/collect/bot/self",null);
        CuratorCrudUtils.create(client,"/collect/bot/used",null);
        CuratorCrudUtils.create(client,"/collect/bot/off",null);
        CuratorCrudUtils.create(client,"/collect/bot/all",null);

        CuratorCrudUtils.create(client,"/collect/bot/error",null);

        //BotCuratorUtils.setTradeOffFlag(client,false);

        CuratorCrudUtils.create(client,"/collect/bot/session",null);
        CuratorCrudUtils.create(client,"/collect/bot/info",null);

        CuratorCrudUtils.create(client,"/collect/bot/offflag","false".getBytes());

        CuratorCrudUtils.create(client,"/collect/bot/wholesale",null);


        //addCommonBot(client,"123");

        CuratorCrudUtils.create(client,"/collect/bot/real",null);
        CuratorCrudUtils.create(client,"/collect/bot/real/concurrent",null);

        CuratorCrudUtils.create(client,"/collect/bot/stock",null);
        CuratorCrudUtils.create(client,"/collect/bot/stock/lock",null);

        CuratorCrudUtils.create(client,"/collect/bot/concurrent",null);

        CuratorCrudUtils.create(client,"/collect/bot/stocknum",null);
        CuratorCrudUtils.create(client,"/collect/bot/stocknum/concurrent",null);*/

        //BotCuratorUtils.setTradeOffFlag(client,false);
        //System.out.println(flag);

        List<String> nodes =  BotCuratorUtils.getAllOffLineBots(client);

        //Map<String,String> botInfoMap = BotCuratorUtils.getAllOnlineBotsData(client);

        //BotCuratorUtils.deleUsedBot(client,"76561198297756834");
        nodes.forEach(v-> {
            try {
                System.out.println(v);
                //System.out.println(CuratorCrudUtils.getData(client,BotCuratorUtils.VIP_BOT_NODE_PATH+"/"+v));
            } catch (Exception e) {
                e.printStackTrace();
            }
            //76561198287312700
            /*try {
                System.out.println(BotCuratorUtils.getOffLineBotData(client,v));
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
        System.out.println(nodes.size());
        /*botInfoMap.forEach((v,k)->{
            try {
                System.out.println("online:"+v);
                BotCuratorUtils.deleBotSession(client,v);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });*/
        //System.out.println(botInfoMap.containsKey("76561198267097286"));
        //System.out.println(nodes.indexOf("76561198289514405")>-1);
        client.close();
    }


}
