package com.softisland.curator.client;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * Created by liwx on 2016/4/25.
 */
public final class CuratorFrameworkUtils {
    /**
     * 创建client
     * @param connectionString
     * @return
     */
    public static CuratorFramework createSimple(String connectionString) {

        ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(1000, 3);

        return CuratorFrameworkFactory.newClient(connectionString, retryPolicy);
    }

    /**
     * 创建带参数的client
     * @param connectionString
     * @param retryPolicy
     * @param connectionTimeoutMs
     * @param sessionTimeoutMs
     * @return
     */
    public static CuratorFramework  createWithOptions(String connectionString, RetryPolicy retryPolicy, int connectionTimeoutMs, int sessionTimeoutMs) {
        // using the CuratorFrameworkFactory.builder() gives fine grained control
        // over creation options. See the CuratorFrameworkFactory.Builder javadoc
        // details
        return CuratorFrameworkFactory.builder()
                .connectString(connectionString)
                .retryPolicy(retryPolicy)
                .connectionTimeoutMs(connectionTimeoutMs)
                .sessionTimeoutMs(sessionTimeoutMs)
                .build();
    }
}
