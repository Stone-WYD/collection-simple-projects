package com.jgdsun.ba.brand.snmp;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;

import com.jgdsun.ba.mybatis.entity.TBa;
import com.jgdsun.ba.mybatis.entity.TBaParameter;
import com.jgdsun.ba.mybatis.entity.TBaParameterValue;
import com.jgdsun.ba.service.DataService;
import com.jgdsun.ba.utils.PropertiesUtil;
import lombok.extern.slf4j.Slf4j;
import org.snmp4j.mp.SnmpConstants;


import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author yk
 * @className SnmpService
 * @date 2025/4/14
 * @description snmp协议专用服务
 */
@Slf4j
public class SnmpService {
    private static SnmpUtil snmpUtil = null;

    private static String community = "public";

    private static String ipAddress = "";
    private static String userName = "";
    private static String password = "";
    private static String privPassword = "";
    private static Integer version = 0;
    private static Integer port = null;
    private static PropertiesUtil propertiesUtil;
    private final DataService dataService = new DataService();

    private boolean isDestory = false;

    public int sleepTime = 1000;//0.1秒


    /**
     * 请求token间隔
     */
    public static int getInfoJgTimes = 60;

    /**
     * 请求token当前计数
     */
    public static int getInfoNowTimes = getInfoJgTimes - 10;

    public SnmpService() {
        propertiesUtil = new PropertiesUtil(System.getProperty("user.dir") + File.separator + "snmpConfig.properties");
        initParam();

        snmpUtil = new SnmpUtil(ipAddress, port, userName, password, privPassword, version);

        t.start();

    }

    public void saveStringData(String name, String value) {
        propertiesUtil.writeProperty(name, value);
    }

    public String getStringData(String name) {
        String value = propertiesUtil.readProperty(name);
        if (value == null) {
            value = "";
        }
        return value;
    }


    Thread t = new Thread() {
        public void run() {

            try {
                sleep(10000);
            } catch (InterruptedException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }

            while (!isDestory) {
                try {
                    sleep(sleepTime);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    //e.printStackTrace();
                }

                getInfoNowTimes++;
                if (getInfoNowTimes > getInfoJgTimes) {
                    getInfoNowTimes = 0;
                    dataSync();
                }
            }
        }
    };


    private void initParam() {
        community = getStringData("community");
        ipAddress = getStringData("serverurl");
        userName = getStringData("userName");
        password = getStringData("password");
        privPassword = getStringData("privPassword");
        port = Integer.parseInt(getStringData("port"));
        String versionStr = getStringData("version");
        // 版本映射
        switch (versionStr) {
            case "v1":
                version = SnmpConstants.version1;
                break;
            case "v2":
                version = SnmpConstants.version2c;
                break;
            case "v3":
                version = SnmpConstants.version3;
                break;
            default:
                log.error("不支持的SNMP版本: {}", versionStr);
                throw new IllegalArgumentException("Unsupported SNMP version");
        }
    }

    /**
     * 同步所有Snmp设备数据
     */
    public void dataSync() {
        List<TBa> snmpList = dataService.getSnmpList();
        List<TBaParameterValue> parameterValueInsertList = new ArrayList<>();
        List<TBaParameter> parameterUpdateList = new ArrayList<>();
        if (CollectionUtils.isEmpty(snmpList)) {
            return;
        }
        Map<String, String> oidValueMap = new HashMap<>();
        List<String> snmpIdList = snmpList.stream().map(TBa::getId).collect(Collectors.toList());
        List<TBaParameter> parameterList = dataService.getBaParameterByIdList(snmpIdList);
        if (CollectionUtils.isEmpty(parameterList)) {
            return;
        }
        // Map<String, TSnmpParameter> snmpIdParameterListMap = parameterList.stream().collect(Collectors.toMap(TSnmpParameter::getEquipmentId, Function.identity(), (v1, v2) -> v1));
        for (TBa tBa : snmpList) {
            Map<String, String> tempMap = snmpUtil.snmpWalk(tBa.getAddress());
            oidValueMap.putAll(tempMap);
        }
        //  更新paramrter表
        System.out.println("成功读取snmp数据：" + oidValueMap.size() + "条");
        Date nowDate = new Date();
        for (TBaParameter tDeParameter : parameterList) {
            String lastValue = tDeParameter.getLastvalue();
            String nowValue = oidValueMap.get(tDeParameter.getDevid());
            if (StringUtils.isBlank(nowValue) || StringUtils.equals(nowValue, lastValue)) {
                continue;
            }
            tDeParameter.setLastvalue(nowValue);
            tDeParameter.setLasttime(nowDate);
            parameterUpdateList.add(tDeParameter);
            TBaParameterValue tDeParameterValue = new TBaParameterValue();
            tDeParameterValue.setId(UUID.randomUUID().toString().replaceAll("-", ""));
            tDeParameterValue.setParameterId(tDeParameter.getId());
            tDeParameterValue.setValue(nowValue);
            tDeParameterValue.setAddtime(nowDate);
            parameterValueInsertList.add(tDeParameterValue);
        }
        if (CollectionUtils.isNotEmpty(parameterUpdateList)) {
            dataService.updateParameterLastValue(parameterUpdateList);
        }
        if (CollectionUtils.isNotEmpty(parameterValueInsertList)) {
            dataService.insertParameterValueList(parameterValueInsertList);
        }
    }
}
