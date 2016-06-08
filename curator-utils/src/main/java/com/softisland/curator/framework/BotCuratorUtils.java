package com.softisland.curator.framework;

import com.softisland.curator.recipes.CuratorLocks;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 创建机器人相关的节点
 * Created by liwx on 2016/4/26.
 */
public class BotCuratorUtils {
    /**
     * self日志
     */
    private static final Logger log = LoggerFactory.getLogger(CuratorCrudUtils.class);
    /**
     * 在线普通机器人节点
     */
    public static final String COMMON_BOT_NODE_PATH = "/collect/bot/common";

    /**
     * 在线VI机器人节点
     */
    public static final String VIP_BOT_NODE_PATH = "/collect/bot/vip";
    /**
     * 在线自营机器人节点
     */
    public static final String SELF_BOT_NODE_PATH = "/collect/bot/self";
    /**
     * 在线批发机器人节点
     */
    public static final String WHOLE_SALE_BOT_NODE_PATH = "/collect/bot/wholesale";
    /**
     * 在线伪并发机器人节点
     */
    public static final String CONCURRENT_BOT_NODE_PATH = "/collect/bot/concurrent";
    /**
     * 在线真实的并发机器人节点
     */
    public static final String REAL_CONCURRENT_BOT_NODE_PATH = "/collect/bot/real/concurrent";

    /**
     * 在线并发机器人存储库存数量节点
     */
    public static final String CONCURRENT_BOT_STOCKNUM_NODE_PATH = "/collect/bot/stocknum/concurrent";
    /**
     * 离线机器人节点
     */
    public static final String OFF_LINE_BOT_NODE_PATH = "/collect/bot/off";
    /**
     * 在线在用机器人节点
     */
    public static final String USED_BOT_NODE_PATH = "/collect/bot/used";

    /**
     * 出错的在用机器人节点
     */
    public static final String ERROR_BOT_NODE_PATH = "/collect/bot/error";
    /**
     * 锁定的机器人库存查询节点
     */
    public static final String LOCKED_BOT_STOCK_NODE_PATH = "/collect/bot/stock/lock";

    /**
     * 所有在线机器人的session
     */
    public static final String BOT_SESSION_NODE_PATH = "/collect/bot/session";

    /**
     * 所有在线机器人的信息BotInfo
     */
    public static final String BOT_INFO_NODE_PATH = "/collect/bot/info";
    /**
     * 交易关闭flag的node地址
     */
    public static final String TRADE_OFF_FLAG_NODE_PATH = "/collect/bot/offflag";



    /**
     * 创建普通机器人节点并添加数据
     * @param client
     * @param botId
     * @param data
     * @throws Exception
     */
    public static boolean addCommonBot(CuratorFramework client, String botId,String data) throws Exception {
        // this will create the given ZNode with the given data
        if(!isExist(client,COMMON_BOT_NODE_PATH+"/"+botId)){
            log.debug("机器人{}不存在，添加到普通账户机器人中",botId);
            client.create().forPath(COMMON_BOT_NODE_PATH+"/"+botId, data.getBytes("UTF-8"));
            return true;
        }else{
            CuratorCrudUtils.setData(client,COMMON_BOT_NODE_PATH+"/"+botId,data.getBytes("UTF-8"));
            log.debug("机器人{}已存在，不添加到普通账户机器人中，更新数据",botId);
            return false;
        }
    }

    /**
     * 删除普通的在线机器人
     *
     * @param client
     * @param botId
     * @throws Exception
     */
    public static void deleCommonBot(CuratorFramework client, String botId)throws Exception{
        CuratorLocks locks = new CuratorLocks(client,COMMON_BOT_NODE_PATH);
        locks.getLock(10, TimeUnit.SECONDS);
        log.debug("线程（{}）删除普通机器人{}，已获得共享锁",getCurThread(),botId);
        try{
            if(isExist(client,COMMON_BOT_NODE_PATH+"/"+botId)){
                client.delete().forPath(COMMON_BOT_NODE_PATH+"/"+botId);
                log.debug("线程（{}）删除普通机器人{}，删除成功",getCurThread(),botId);
            }
        }finally {
            locks.releaseLock();
        }
    }

    /**
     * 创建在线机器人session节点并添加数据
     * @param client
     * @param botId
     * @param data
     * @throws Exception
     */
    public static boolean addBotSession(CuratorFramework client, String botId,String data) throws Exception {
        // this will create the given ZNode with the given data
        if(!isExist(client,BOT_SESSION_NODE_PATH+"/"+botId)){
            log.debug("机器人{}session不存在，添加session data",botId);
            client.create().forPath(BOT_SESSION_NODE_PATH+"/"+botId, data.getBytes("UTF-8"));
            return true;
        }else{
            CuratorCrudUtils.setData(client,BOT_SESSION_NODE_PATH+"/"+botId,data.getBytes("UTF-8"));
            log.debug("机器人{}session已存在，不添加session，更新数据",botId);
            return false;
        }
    }

    /**
     * 删除在线机器人的session
     * @param client
     * @param botId
     * @throws Exception
     */
    public static void deleBotSession(CuratorFramework client, String botId)throws Exception{
        CuratorLocks locks = new CuratorLocks(client,BOT_SESSION_NODE_PATH);
        locks.getLock(10, TimeUnit.SECONDS);
        log.debug("线程（{}）机器人{}删除在线session，已获得共享锁",getCurThread(),botId);
        try{
            if(isExist(client,BOT_SESSION_NODE_PATH+"/"+botId)){
                log.debug("线程（{}）删除普通机器人{}，删除成功",getCurThread(),botId);
                client.delete().forPath(BOT_SESSION_NODE_PATH+"/"+botId);
            }

        }finally {
            locks.releaseLock();
        }
    }

    /**
     * 创建在线机器人info节点并添加数据
     * @param client
     * @param botId
     * @param data
     * @throws Exception
     */
    public static boolean addBotInfo(CuratorFramework client, String botId,String data) throws Exception {
        // this will create the given ZNode with the given data
        if(!isExist(client,BOT_INFO_NODE_PATH+"/"+botId)){
            log.debug("机器人{}信息不存在，添加机器人info data",botId);
            client.create().forPath(BOT_INFO_NODE_PATH+"/"+botId, data.getBytes("UTF-8"));
            return true;
        }else{
            CuratorCrudUtils.setData(client,BOT_INFO_NODE_PATH+"/"+botId,data.getBytes("UTF-8"));
            log.debug("机器人{}info已存在，不添加info，更新数据",botId);
            return false;
        }
    }

    /**
     * 删除在线机器人的info
     * @param client
     * @param botId
     * @throws Exception
     */
    public static void deleBotInfo(CuratorFramework client, String botId)throws Exception{
        CuratorLocks locks = new CuratorLocks(client,BOT_INFO_NODE_PATH);
        locks.getLock(10, TimeUnit.SECONDS);
        log.debug("线程（{}）删除机器人info{}，已获得共享锁",getCurThread(),botId);
        try{
            if(isExist(client,BOT_INFO_NODE_PATH+"/"+botId)){
                client.delete().forPath(BOT_INFO_NODE_PATH+"/"+botId);
                log.debug("线程（{}）删除机器人info{}，删除成功",getCurThread(),botId);
            }
        }finally {
            locks.releaseLock();
        }
    }

    /**
     * 创建VIP机器人节点
     * @param client
     * @param botId
     * @param data
     * @throws Exception
     */
    public static boolean addVipBot(CuratorFramework client, String botId,String data) throws Exception {
        // this will create the given ZNode with the given data
        if(!isExist(client,VIP_BOT_NODE_PATH+"/"+botId)){
            log.debug("机器人{}不存在，添加到VIP账户机器人中",botId);
            client.create().forPath(VIP_BOT_NODE_PATH+"/"+botId, data.getBytes("UTF-8"));
            log.debug("机器人{}不存在，添加到VIP账户机器人，成功！",botId);
            return true;
        }else{
            log.debug("机器人{}已存在，不添加到VIP账户机器人中，更新数据",botId);
            CuratorCrudUtils.setData(client,VIP_BOT_NODE_PATH+"/"+botId,data.getBytes("UTF-8"));
            return false;
        }
    }

    /**
     * 删除VIP机器人节点
     * @param client
     * @param botId
     * @throws Exception
     */
    public static void deleVipBot(CuratorFramework client, String botId) throws Exception {
        // this will create the given ZNode with the given data
        CuratorLocks locks = new CuratorLocks(client,VIP_BOT_NODE_PATH);
        locks.getLock(10, TimeUnit.SECONDS);
        log.debug("线程（{}）删除VIP机器人{}，已获得共享锁",getCurThread(),botId);
        try{
            if(isExist(client,VIP_BOT_NODE_PATH+"/"+botId)){
                client.delete().forPath(VIP_BOT_NODE_PATH+"/"+botId);
                log.debug("线程（{}）删除VIP机器人{}，成功！",getCurThread(),botId);
            }
        }finally {
            locks.releaseLock();
        }
    }

    /**
     * 创建真正的并发机器人节点
     * @param client
     * @param botId
     * @param data
     * @throws Exception
     */
    public static boolean addRealConcurrentBot(CuratorFramework client, String botId,String data) throws Exception {
        // this will create the given ZNode with the given data
        if(!isExist(client,REAL_CONCURRENT_BOT_NODE_PATH+"/"+botId)){
            log.debug("机器人{}不存在，添加到真实的并发账户机器人中",botId);
            client.create().forPath(REAL_CONCURRENT_BOT_NODE_PATH+"/"+botId, data.getBytes("UTF-8"));
            log.debug("机器人{}不存在，添加到真实的并发账户机器人，成功！",botId);
            return true;
        }else{
            log.debug("机器人{}已存在，不添加到真实的并发账户机器人中，更新数据",botId);
            CuratorCrudUtils.setData(client,REAL_CONCURRENT_BOT_NODE_PATH+"/"+botId,data.getBytes("UTF-8"));
            return false;
        }
    }

    /**
     * 删除真正的并发机器人节点
     * @param client
     * @param botId
     * @throws Exception
     */
    public static void deleRealConcurrentBot(CuratorFramework client, String botId) throws Exception {
        // this will create the given ZNode with the given data
        CuratorLocks locks = new CuratorLocks(client,REAL_CONCURRENT_BOT_NODE_PATH);
        locks.getLock(10, TimeUnit.SECONDS);
        log.debug("线程（{}）删除真实的并发机器人{}，已获得共享锁",getCurThread(),botId);
        try{
            if(isExist(client,REAL_CONCURRENT_BOT_NODE_PATH+"/"+botId)){
                client.delete().forPath(REAL_CONCURRENT_BOT_NODE_PATH+"/"+botId);
                log.debug("线程（{}）删除真实的并发机器人{}，成功！",getCurThread(),botId);
            }
        }finally {
            locks.releaseLock();
        }
    }

    /**
     * 创建自营账户机器人节点
     * @param client
     * @param botId
     * @param data
     * @throws Exception
     */
    public static boolean addSelfBot(CuratorFramework client, String botId,String data) throws Exception {
        // this will create the given ZNode with the given data
        if(!isExist(client,SELF_BOT_NODE_PATH+"/"+botId)){
            log.debug("机器人{}不存在，添加到自营账户机器人中",botId);
            client.create().forPath(SELF_BOT_NODE_PATH+"/"+botId, data.getBytes("UTF-8"));
            log.debug("机器人{}不存在，添加到自营账户机器人中，成功！",botId);
            return true;
        }else{
            CuratorCrudUtils.setData(client,SELF_BOT_NODE_PATH+"/"+botId,data.getBytes("UTF-8"));
            log.debug("机器人{}已存在，不添加到自营账户机器人中，更新数据",botId);
            return false;
        }
    }

    /**
     * 删除自营账户机器人节点
     * @param client
     * @param botId
     * @throws Exception
     */
    public static void deleSelfBot(CuratorFramework client, String botId) throws Exception {
        // this will create the given ZNode with the given data
        CuratorLocks locks = new CuratorLocks(client,SELF_BOT_NODE_PATH);
        locks.getLock(10, TimeUnit.SECONDS);
        log.debug("线程（{}）删除自营机器人{}，已获得共享锁",getCurThread(),botId);
        try{
            if(isExist(client,SELF_BOT_NODE_PATH+"/"+botId)){
                client.delete().forPath(SELF_BOT_NODE_PATH+"/"+botId);
                log.debug("线程（{}）删除自营机器人{}，成功！",getCurThread(),botId);
            }
        }finally {
            locks.releaseLock();
        }
    }

    /**
     * 创建批发账户机器人节点
     * @param client
     * @param botId
     * @param data
     * @throws Exception
     */
    public static boolean addWholeSaleBot(CuratorFramework client, String botId,String data) throws Exception {
        // this will create the given ZNode with the given data
        if(!isExist(client,WHOLE_SALE_BOT_NODE_PATH+"/"+botId)){
            log.debug("机器人{}不存在，添加到批发账户机器人中",botId);
            client.create().forPath(WHOLE_SALE_BOT_NODE_PATH+"/"+botId, data.getBytes("UTF-8"));
            log.debug("机器人{}不存在，添加到批发账户机器人中，成功！",botId);
            return true;
        }else{
            CuratorCrudUtils.setData(client,WHOLE_SALE_BOT_NODE_PATH+"/"+botId,data.getBytes("UTF-8"));
            log.debug("机器人{}已存在，不添加到批发账户机器人中，更新数据",botId);
            return false;
        }
    }

    /**
     * 删除批发类型机器人节点
     * @param client
     * @param botId
     * @throws Exception
     */
    public static void deleWholeSaleBot(CuratorFramework client, String botId) throws Exception {
        // this will create the given ZNode with the given data
        CuratorLocks locks = new CuratorLocks(client,WHOLE_SALE_BOT_NODE_PATH);
        locks.getLock(10, TimeUnit.SECONDS);
        log.debug("线程（{}）删除批发机器人{}，已获得共享锁",getCurThread(),botId);
        try{
            if(isExist(client,WHOLE_SALE_BOT_NODE_PATH+"/"+botId)){
                client.delete().forPath(WHOLE_SALE_BOT_NODE_PATH+"/"+botId);
                log.debug("线程（{}）删除批发机器人{}，成功！",getCurThread(),botId);
            }
        }finally {
            locks.releaseLock();
        }
    }

    /**
     * 创建并发账户机器人节点
     * @param client
     * @param botId
     * @param data
     * @throws Exception
     */
    public static boolean addConcurrentSaleBot(CuratorFramework client, String botId,String data) throws Exception {
        // this will create the given ZNode with the given data
        if(!isExist(client,CONCURRENT_BOT_NODE_PATH+"/"+botId)){
            log.debug("机器人{}不存在，添加到并发账户机器人中",botId);
            client.create().forPath(CONCURRENT_BOT_NODE_PATH+"/"+botId, data.getBytes("UTF-8"));
            log.debug("机器人{}不存在，添加到并发账户机器人中，成功！",botId);
            return true;
        }else{
            CuratorCrudUtils.setData(client,CONCURRENT_BOT_NODE_PATH+"/"+botId,data.getBytes("UTF-8"));
            log.debug("机器人{}已存在，不添加到并发账户机器人中，更新数据",botId);
            return false;
        }
    }

    /**
     * 删除并发类型机器人节点
     * @param client
     * @param botId
     * @throws Exception
     */
    public static void deleConcurrentBot(CuratorFramework client, String botId) throws Exception {
        // this will create the given ZNode with the given data
        CuratorLocks locks = new CuratorLocks(client,CONCURRENT_BOT_NODE_PATH);
        locks.getLock(10, TimeUnit.SECONDS);
        log.debug("线程（{}）删除并发机器人{}，已获得共享锁",getCurThread(),botId);
        try{
            if(isExist(client,CONCURRENT_BOT_NODE_PATH+"/"+botId)){
                client.delete().forPath(CONCURRENT_BOT_NODE_PATH+"/"+botId);
                log.debug("线程（{}）删除并发机器人{}，成功！",getCurThread(),botId);
            }
        }finally {
            locks.releaseLock();
        }
    }

    /**
     * 删除并发类型机器人节点
     * @param client
     * @param botId
     * @throws Exception
     */
    public static void updateConcurrentBotStockNum(CuratorFramework client, String botId,int addNum) throws Exception {
        // this will create the given ZNode with the given data
        if(isExist(client,CONCURRENT_BOT_STOCKNUM_NODE_PATH+"/"+botId)){
            CuratorLocks locks = new CuratorLocks(client,CONCURRENT_BOT_STOCKNUM_NODE_PATH);
            try{
                locks.getLock(10, TimeUnit.SECONDS);
                log.debug("线程（{}）修改并发机器人库存数量{}，已获得共享锁",getCurThread(),botId);
                int curNum = getConcurrentBotStockNum(client,botId);
                int num = curNum + addNum;
                client.setData().forPath(CONCURRENT_BOT_STOCKNUM_NODE_PATH+"/"+botId,String.valueOf(num).getBytes("utf-8"));
                log.debug("线程（{}）修改并发机器人（{}）库存数量（{}），成功！",getCurThread(),botId,addNum);
            }finally {
                locks.releaseLock();
            }

        }else{
            if(addNum<0){
                addNum=0;
            }
            client.create().forPath(CONCURRENT_BOT_STOCKNUM_NODE_PATH+"/"+botId,String.valueOf(addNum).getBytes("utf-8"));
        }

    }

    /**
     * 获取并发机器人的本地库存数量
     * @param client
     * @param botId
     * @return
     * @throws Exception
     */
    public static int getConcurrentBotStockNum(CuratorFramework client, String botId)throws Exception{
        String numStr = CuratorCrudUtils.getData(client,CONCURRENT_BOT_STOCKNUM_NODE_PATH+"/"+botId);
        return Integer.valueOf(numStr);
    }

    /**
     * 创建离线机器人节点
     * @param client
     * @param botId
     * @throws Exception
     */
    public static boolean addOffLineBot(CuratorFramework client, String botId,String data) throws Exception {
        // this will create the given ZNode with the given data
        if(!isExist(client,OFF_LINE_BOT_NODE_PATH+"/"+botId)){
            log.debug("机器人{}不存在，添加到离线机器人中",botId);
            client.create().forPath(OFF_LINE_BOT_NODE_PATH+"/"+botId, (data + ";" + System.currentTimeMillis()).getBytes("UTF-8"));
            log.debug("机器人{}不存在，添加到离线机器人中，成功！",botId);
            return true;
        }else{
            log.debug("机器人{}已存在，不添加到离线机器人中，进行数据更新",botId);
            client.setData().forPath(OFF_LINE_BOT_NODE_PATH+"/"+botId, (data + ";" + System.currentTimeMillis()).getBytes("UTF-8"));
            return true;
        }
    }

    /**
     * 删除离线机器人节点
     * @param client
     * @param botId
     * @throws Exception
     */
    public static void deleOffLineBot(CuratorFramework client, String botId) throws Exception {
        // this will create the given ZNode with the given data
        CuratorLocks locks = new CuratorLocks(client,OFF_LINE_BOT_NODE_PATH);
        locks.getLock(10, TimeUnit.SECONDS);
        log.debug("线程（{}）删除离线机器人{}，已获得共享锁",getCurThread(),botId);
        try{
            if(isExist(client,OFF_LINE_BOT_NODE_PATH+"/"+botId)){
                client.delete().forPath(OFF_LINE_BOT_NODE_PATH+"/"+botId);
                log.debug("线程（{}）删除离线机器人{}，成功！",getCurThread(),botId);
            }
        }finally {
            locks.releaseLock();
        }
    }

    /**
     * 创建正在使用的机器人节点
     * @param client
     * @param botId
     * @throws Exception
     */
    public static boolean addUsedBot(CuratorFramework client, String botId) throws Exception {
        // this will create the given ZNode with the given data
        CuratorLocks locks = new CuratorLocks(client,USED_BOT_NODE_PATH);
        locks.getLock(10, TimeUnit.SECONDS);
        log.debug("线程（{}）添加正在使用的机器人{}，已获得共享锁",getCurThread(),botId);
        try{
            if(!isExist(client,USED_BOT_NODE_PATH+"/"+botId)){
                client.create().forPath(USED_BOT_NODE_PATH+"/"+botId, botId.getBytes("UTF-8"));
                log.debug("线程（{}）添加正在使用的机器人{}，成功！",getCurThread(),botId);
                return true;
            }else{
                log.debug("机器人{}已存在，不添加到正在使用的机器人中",botId);
                return false;
            }
        }finally {
            locks.releaseLock();
        }
    }

    /**
     * 添加报错的机器人
     * @param client
     * @param botId
     * @return
     * @throws Exception
     */
    public static boolean addErrorBot(CuratorFramework client, String botId) throws Exception {
        // this will create the given ZNode with the given data
        CuratorLocks locks = new CuratorLocks(client,ERROR_BOT_NODE_PATH);
        locks.getLock(10, TimeUnit.SECONDS);
        log.debug("线程（{}）添加错误的机器人{}，已获得共享锁",getCurThread(),botId);
        try{
            if(!isExist(client,ERROR_BOT_NODE_PATH+"/"+botId)){
                client.create().forPath(ERROR_BOT_NODE_PATH+"/"+botId, botId.getBytes("UTF-8"));
                log.debug("线程（{}）添加错误的机器人{}，成功！",getCurThread(),botId);
                return true;
            }else{
                log.debug("错误的机器人{}已存在，不添加到正在使用的机器人中",botId);
                return false;
            }
        }finally {
            locks.releaseLock();
        }
    }

    /**
     * 删除错误的机器人
     * @param client
     * @param botId
     * @throws Exception
     */
    public static void deleErrorBot(CuratorFramework client, String botId) throws Exception {
        // this will create the given ZNode with the given data
        CuratorLocks locks = new CuratorLocks(client,ERROR_BOT_NODE_PATH);
        locks.getLock(10, TimeUnit.SECONDS);
        log.debug("线程（{}）删除错误的机器人{}，已获得共享锁",getCurThread(),botId);
        try{
            if(isExist(client,ERROR_BOT_NODE_PATH+"/"+botId)){
                client.delete().forPath(ERROR_BOT_NODE_PATH+"/"+botId);
                log.debug("线程（{}）删除错误的的机器人{}，成功！",getCurThread(),botId);
            }
        }finally {
            locks.releaseLock();
        }
    }

    /**
     * 删除正在使用的机器人节点
     * @param client
     * @param botId
     * @throws Exception
     */
    public static void deleUsedBot(CuratorFramework client, String botId) throws Exception {
        // this will create the given ZNode with the given data
        CuratorLocks locks = new CuratorLocks(client,USED_BOT_NODE_PATH);
        locks.getLock(10, TimeUnit.SECONDS);
        log.debug("线程（{}）删除正在使用的机器人{}，已获得共享锁",getCurThread(),botId);
        try{
            if(isExist(client,USED_BOT_NODE_PATH+"/"+botId)){
                client.delete().forPath(USED_BOT_NODE_PATH+"/"+botId);
                log.debug("线程（{}）删除正在使用的机器人{}，成功！",getCurThread(),botId);
            }
        }finally {
            locks.releaseLock();
        }
    }

    /**
     * 创建正在使用的机器人节点
     * @param client
     * @param botId
     * @throws Exception
     */
    public static boolean lockBotStockQuery(CuratorFramework client, String botId) throws Exception {
        // this will create the given ZNode with the given data
        CuratorLocks locks = new CuratorLocks(client,LOCKED_BOT_STOCK_NODE_PATH);
        locks.getLock(10, TimeUnit.SECONDS);
        log.debug("线程（{}）锁定库存查询机器人{}，已获得共享锁",getCurThread(),botId);
        try{
            if(!isExist(client,LOCKED_BOT_STOCK_NODE_PATH+"/"+botId)){
                client.create().forPath(LOCKED_BOT_STOCK_NODE_PATH+"/"+botId, botId.getBytes("UTF-8"));
                log.debug("线程（{}）锁定库存查询的机器人{}，成功！",getCurThread(),botId);
                return true;
            }else{
                log.debug("机器人{}已存在，不添加到锁定库存查询的机器人中",botId);
                return false;
            }
        }finally {
            locks.releaseLock();
        }
    }

    /**
     * 解锁机器人或是用户的库存查询
     * @param client
     * @param botId
     * @throws Exception
     */
    public static void releaseBotStockQuery(CuratorFramework client, String botId) throws Exception {
        // this will create the given ZNode with the given data
        CuratorLocks locks = new CuratorLocks(client,LOCKED_BOT_STOCK_NODE_PATH);
        locks.getLock(10, TimeUnit.SECONDS);
        log.debug("线程（{}）解锁正在锁定库存查询机器人{}，已获得共享锁",getCurThread(),botId);
        try{
            if(isExist(client,LOCKED_BOT_STOCK_NODE_PATH+"/"+botId)){
                client.delete().forPath(LOCKED_BOT_STOCK_NODE_PATH+"/"+botId);
                log.debug("线程（{}）解锁正在锁定的库存查询的机器人{}，成功！",getCurThread(),botId);
            }
        }finally {
            locks.releaseLock();
        }
    }

    /**
     * 获取当前线程
     * @return
     */
    private static Long getCurThread(){
        return Thread.currentThread().getId();
    }

    /**
     * 获取所有普通在线机器人节点
     * @return
     * @throws Exception
     */
    public static List<String> getAllCommonBots(CuratorFramework client)throws Exception{
        return client.getChildren().forPath(COMMON_BOT_NODE_PATH);
    }

    /**
     * 获取所有的被锁定的库存查询机器人
     * @param client
     * @return
     * @throws Exception
     */
    public static List<String> getAllLockedBotsStockQuery(CuratorFramework client)throws Exception{
        return client.getChildren().forPath(LOCKED_BOT_STOCK_NODE_PATH);
    }

    /**
     * 获取所有并发在线机器人节点
     * @return
     * @throws Exception
     */
    public static List<String> getAllConcurrentBots(CuratorFramework client)throws Exception{
        return client.getChildren().forPath(CONCURRENT_BOT_NODE_PATH);
    }

    /**
     * 获取所有真正的并发在线机器人节点
     * @return
     * @throws Exception
     */
    public static List<String> getAllRealConcurrentBots(CuratorFramework client)throws Exception{
        return client.getChildren().forPath(REAL_CONCURRENT_BOT_NODE_PATH);
    }

    /**
     * 获取所有VIP在线机器人节点
     * @return
     * @throws Exception
     */
    public static List<String> getAllVipBots(CuratorFramework client)throws Exception{
        return client.getChildren().forPath(VIP_BOT_NODE_PATH);
    }

    /**
     * 获取所有正在使用的机器人节点
     * @return
     * @throws Exception
     */
    public static List<String> getAllUsedBots(CuratorFramework client)throws Exception{
        return client.getChildren().forPath(USED_BOT_NODE_PATH);
    }

    /**
     * 获取所有离线机器人节点
     * @return
     * @throws Exception
     */
    public static List<String> getAllOffLineBots(CuratorFramework client)throws Exception{
        return client.getChildren().forPath(OFF_LINE_BOT_NODE_PATH);
    }

    /**
     * 获取离线机器人的数据
     * @param client
     * @param botId
     * @return
     * @throws Exception
     */
    public static String getOffLineBotData(CuratorFramework client,String botId)throws Exception{
        return CuratorCrudUtils.getData(client,OFF_LINE_BOT_NODE_PATH+"/"+botId);
    }

    /**
     * 获取所有在线机器人节点
     * @return
     * @throws Exception
     */
    public static List<String> getAllOnLineBots(CuratorFramework client)throws Exception{
        List<String> list1 = getAllCommonBots(client);
        List<String> list2 = getAllVipBots(client);
        List<String> list3 = getAllSelfBots(client);
        List<String> list4 = getWholeSaleBots(client);
        List<String> list5 = getAllConcurrentBots(client);
        List<String> list6 = getAllRealConcurrentBots(client);
        List<String> allBot = new ArrayList<>(list1.size()+list2.size()+list3.size());
        allBot.addAll(list1);
        allBot.addAll(list2);
        allBot.addAll(list3);
        allBot.addAll(list4);
        allBot.addAll(list5);
        allBot.addAll(list6);
        return allBot;
    }

    /**
     * 获取所有自营在线机器人节点
     * @return
     * @throws Exception
     */
    public static List<String> getAllSelfBots(CuratorFramework client)throws Exception{
        return client.getChildren().forPath(SELF_BOT_NODE_PATH);
    }

    public static List<String> getAllErrorBots(CuratorFramework client)throws Exception{
        return client.getChildren().forPath(ERROR_BOT_NODE_PATH);
    }


    /**
     * 获取批发机器人
     * @param client
     * @return
     * @throws Exception
     */
    public static List<String> getWholeSaleBots(CuratorFramework client)throws Exception{
        return client.getChildren().forPath(WHOLE_SALE_BOT_NODE_PATH);
    }

    /**
     * 获取机器人的信息数据
     * @param client
     * @return
     * @throws Exception
     */
    public static Map<String,String> getAllBotInfo(CuratorFramework client)throws Exception{
        return getNodeChildrenData(client,BOT_INFO_NODE_PATH);
    }

    /**
     * 获取所有在线的机器人和数据
     * @param client
     * @return
     * @throws Exception
     */
    public static Map<String,String> getAllOnlineBotsData(CuratorFramework client)throws Exception{
        return getNodeChildrenData(client,BOT_SESSION_NODE_PATH);
    }

    /**
     * 获取指定节点下的所有子节点的数据
     * @param client
     * @param parent
     * @return
     * @throws Exception
     */
    private static Map<String,String> getNodeChildrenData(CuratorFramework client,String parent)throws Exception{
        List<String> botIds = client.getChildren().forPath(parent);
        if(null == botIds || botIds.isEmpty()){
            return new HashMap<>(0);
        }
        Map<String,String> mapInfo = new HashMap<>(botIds.size());
        botIds.forEach(v->{
            try {
                mapInfo.put(v,CuratorCrudUtils.getData(client,parent+"/"+v));
            } catch (Exception e) {
                log.error("从zookeeper中获取节点（{}）的数据报错",parent+"/"+v);
                e.printStackTrace();
            }
        });
        return mapInfo;
    }

    /**
     * 获取当前的交易开关
     * @param client
     * @return
     * @throws Exception
     */
    public static String getTradeOffFlag(CuratorFramework client)throws Exception{
        return CuratorCrudUtils.getData(client,TRADE_OFF_FLAG_NODE_PATH);
    }

    /**
     * 设置交易开关
     * @param client
     * @param flag
     * @throws Exception
     */
    public static void setTradeOffFlag(CuratorFramework client,boolean flag)throws Exception{
        CuratorCrudUtils.setData(client,TRADE_OFF_FLAG_NODE_PATH,String.valueOf(flag).getBytes());
    }


    /**
     * 判断指定的节点是否存在
     * @param client
     * @param path
     * @return
     * @throws Exception
     */
    public static boolean isExist(CuratorFramework client,String path)throws Exception{
        Stat stat = client.checkExists().forPath(path);
        if(null != stat){
            return true;
        }else{
            return false;
        }
    }
}

