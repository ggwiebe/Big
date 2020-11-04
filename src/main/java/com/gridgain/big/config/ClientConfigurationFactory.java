package com.gridgain.big.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.cache.QueryEntity;
import org.apache.ignite.cache.QueryIndex;
import org.apache.ignite.cache.QueryIndexType;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.ClientConnectorConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.vm.TcpDiscoveryVmIpFinder;

/** This file was generated by Ignite Web Console (10/15/2020, 16:48) **/
public class ClientConfigurationFactory {
    /**
     * Configure grid.
     * 
     * @return Ignite configuration.
     * @throws Exception If failed to construct Ignite configuration instance.
     **/
    public static IgniteConfiguration createConfiguration() throws Exception {
        IgniteConfiguration cfg = new IgniteConfiguration();

        cfg.setClientMode(true);
        cfg.setIgniteInstanceName("Big");

        TcpDiscoverySpi discovery = new TcpDiscoverySpi();
        TcpDiscoveryVmIpFinder ipFinder = new TcpDiscoveryVmIpFinder();
        ipFinder.setAddresses(Arrays.asList("127.0.0.1:47500..47510"));
        discovery.setIpFinder(ipFinder);
        cfg.setDiscoverySpi(discovery);

        cfg.setClientConnectorConfiguration(new ClientConnectorConfiguration());

        cfg.setCacheConfiguration(cacheCustomerCache());

        return cfg;
    }

    /**
     * Create configuration for cache "CustomerCache".
     * 
     * @return Configured cache.
     **/
    public static CacheConfiguration cacheCustomerCache() {
        CacheConfiguration ccfg = new CacheConfiguration();

        ccfg.setName("CustomerCache");
        ccfg.setCacheMode(CacheMode.PARTITIONED);
        ccfg.setAtomicityMode(CacheAtomicityMode.ATOMIC);
        ccfg.setBackups(1);
        ccfg.setReadFromBackup(true);
        ccfg.setCopyOnRead(true);
        ccfg.setSqlSchema("BIG");
        ccfg.setQueryDetailMetricsSize(100);
        ccfg.setQueryParallelism(2);
        ccfg.setEagerTtl(true);
        ccfg.setStatisticsEnabled(true);
        ccfg.setManagementEnabled(true);

        ArrayList<QueryEntity> qryEntities = new ArrayList<>();

        QueryEntity qryEntity = new QueryEntity();
        qryEntity.setKeyType("java.lang.Integer");
        qryEntity.setValueType("com.gridgain.big.model.Customer");
        qryEntity.setTableName("Customer");

        HashSet<String> keyFields = new HashSet<>();
        keyFields.add("sequenceNum");
        qryEntity.setKeyFields(keyFields);

        LinkedHashMap<String, String> fields = new LinkedHashMap<>();
        fields.put("sequenceNum", "java.lang.Integer");
        fields.put("uuid", "java.lang.String");
        fields.put("updateDT", "java.sql.Timestamp");
        fields.put("name", "java.lang.String");
        fields.put("description", "java.lang.String");
        fields.put("customerType", "java.lang.String");
        fields.put("password", "java.lang.String");
        qryEntity.setFields(fields);

        HashSet<String> notNullFields = new HashSet<>();
        notNullFields.add("sequenceNum");
        notNullFields.add("updateDT");
        qryEntity.setNotNullFields(notNullFields);

        ArrayList<QueryIndex> indexes = new ArrayList<>();
        QueryIndex index = new QueryIndex();
        index.setName("Customer_CustomerType_IDX");
        index.setIndexType(QueryIndexType.SORTED);
        LinkedHashMap<String, Boolean> indFlds = new LinkedHashMap<>();
        indFlds.put("customerType", true);
        index.setFields(indFlds);
        indexes.add(index);

        index = new QueryIndex();
        index.setName("Customer_UpdateDT_IDX");
        index.setIndexType(QueryIndexType.SORTED);
        indFlds = new LinkedHashMap<>();
        indFlds.put("updateDT", true);
        index.setFields(indFlds);
        indexes.add(index);

        qryEntity.setIndexes(indexes);
        qryEntities.add(qryEntity);

        ccfg.setQueryEntities(qryEntities);

        return ccfg;
    }
}