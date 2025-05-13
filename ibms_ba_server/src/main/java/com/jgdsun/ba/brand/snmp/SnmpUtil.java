package com.jgdsun.ba.brand.snmp;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.jgdsun.ba.utils.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snmp4j.*;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.MPv1;
import org.snmp4j.mp.MPv2c;
import org.snmp4j.mp.MPv3;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.security.*;
import org.snmp4j.smi.*;
import org.snmp4j.transport.DefaultUdpTransportMapping;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * @author yk
 * @className SnmpUtil
 * @date 2024/5/16
 * @description snmp协议工具 v3版本
 */
public class SnmpUtil {

    private static Logger log = LoggerFactory.getLogger(SnmpUtil.class);
    public static Snmp snmp = null;
    private static String community = "public";
    private static String ipAddress = "udp:192.168.8.10/";
    private static String userName = "ibms";
    private static String password = "1234567a";
    private static String privPassword = "a1234567";
    private static Integer version = 0;
    private static Integer port = 161;

    private static PropertiesUtil propertiesUtil;

    public SnmpUtil() {
    }

    public SnmpUtil(String ipAddress, Integer port, String userName, String password, String privPassword,Integer version) {
        SnmpUtil.ipAddress = ipAddress;
        SnmpUtil.port = port;
        SnmpUtil.userName = userName;
        SnmpUtil.password = password;
        SnmpUtil.privPassword = privPassword;
        SnmpUtil.version = version;

        initSnmpConfig();
    }

    private void initSnmpConfig() {
        propertiesUtil = new PropertiesUtil(System.getProperty("user.dir") + File.separator + "snmpConfig.properties");

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

    /**
     * @throws IOException
     * @description 初始化snmp
     * @author YuanFY
     * @date 2017年12月16日 上午10:28:01
     * @version 1.0
     */
    public static void initSnmp() throws IOException {
        //1、初始化多线程消息转发类
        MessageDispatcher messageDispatcher = new MessageDispatcherImpl();
        //其中要增加三种处理模型。如果snmp初始化使用的是Snmp(TransportMapping<? extends Address> transportMapping) ,就不需要增加
        messageDispatcher.addMessageProcessingModel(new MPv1());
        messageDispatcher.addMessageProcessingModel(new MPv2c());
        //当要支持snmpV3版本时，需要配置user
        OctetString localEngineID = new OctetString(MPv3.createLocalEngineID());
        USM usm = new USM(SecurityProtocols.getInstance().addDefaultProtocols(), localEngineID, 0);
        UsmUser user = new UsmUser(new OctetString(userName), AuthMD5.ID, new OctetString(password),
                PrivDES.ID, new OctetString(privPassword));
        usm.addUser(user.getSecurityName(), user);
        messageDispatcher.addMessageProcessingModel(new MPv3(usm));
        //2、创建transportMapping
//        UdpAddress updAddr = (UdpAddress) GenericAddress.parse("udp:192.168.18.249/161");
        TransportMapping<?> transportMapping = new DefaultUdpTransportMapping();
        //3、正式创建snmp
        snmp = new Snmp(messageDispatcher, transportMapping);
        //开启监听
        snmp.listen();
    }

    private static Target createTarget(int version, int port) {
        Target target = null;
        if (!(version == SnmpConstants.version3 || version == SnmpConstants.version2c || version == SnmpConstants.version1)) {
            log.error("参数version异常");
            return target;
        }
        if (version == SnmpConstants.version3) {
            target = new UserTarget();
            //snmpV3需要设置安全级别和安全名称，其中安全名称是创建snmp指定user设置的new OctetString("SNMPV3")
            target.setSecurityLevel(SecurityLevel.AUTH_PRIV);
            target.setSecurityName(new OctetString(userName));
//            target.setSecurityName(new OctetString("SNMPV3"));
        } else {
            //snmpV1和snmpV2需要指定团体名名称
            target = new CommunityTarget();
            ((CommunityTarget) target).setCommunity(new OctetString(community));
            if (version == SnmpConstants.version2c) {
                target.setSecurityModel(SecurityModel.SECURITY_MODEL_SNMPv2c);
            }
        }
        target.setVersion(version);
        //必须指定，没有设置就会报错。
        target.setAddress(GenericAddress.parse(ipAddress + port));
        target.setRetries(5);
        target.setTimeout(3000);
        return target;
    }

    private static PDU createPDU(int version, int type, String oid) {
        PDU pdu = null;
        if (version == SnmpConstants.version3) {
            pdu = new ScopedPDU();
        } else {
            pdu = new PDUv1();
        }
        pdu.setType(type);
        //可以添加多个变量oid
        pdu.add(new VariableBinding(new OID(oid)));
        return pdu;
    }

    public Map<String, String> snmpGet(String oid) {
        Map<String, String> result = new HashMap<>();
        try {
            //1、初始化snmp,并开启监听
            initSnmp();
            //2、创建目标对象
            Target target = null;
            if (port != null && port != 0) {
                target = createTarget(version, port);
            } else {
                target = createTarget(version, SnmpConstants.DEFAULT_COMMAND_RESPONDER_PORT);
            }

            //3、创建报文
            PDU pdu = createPDU(version, PDU.GET, oid);
            System.out.println("-------> 发送PDU <-------");
            //4、发送报文，并获取返回结果
            ResponseEvent responseEvent = snmp.send(pdu, target);
            PDU response = responseEvent.getResponse();
            Vector<? extends VariableBinding> variableBindings = response.getVariableBindings();
            if (CollectionUtils.isNotEmpty(variableBindings)) {
                for (VariableBinding variableBinding : variableBindings) {
                    result.put(variableBinding.getOid().toString(), variableBinding.getVariable().toString());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Map<String, String> snmpWalk(String oid) {
        Map<String, String> result = new HashMap<>();
        try {
            //1、初始化snmp,并开启监听
            initSnmp();
            //2、创建目标对象
            Target target = createTarget(version, SnmpConstants.DEFAULT_COMMAND_RESPONDER_PORT);
            //3、创建报文
            PDU pdu = createPDU(version, PDU.GETNEXT, oid);
            System.out.println("-------> 发送PDU <-------");
            //4、发送报文，并获取返回结果
            boolean matched = true;
            while (matched) {
                ResponseEvent responseEvent = snmp.send(pdu, target);
                if (responseEvent == null || responseEvent.getResponse() == null) {
                    break;
                }
                PDU response = responseEvent.getResponse();
                String nextOid = null;
                Vector<? extends VariableBinding> variableBindings = response.getVariableBindings();
                for (int i = 0; i < variableBindings.size(); i++) {
                    VariableBinding variableBinding = variableBindings.elementAt(i);
                    Variable variable = variableBinding.getVariable();
                    result.put(variableBinding.getOid().toString(), variable.toString());
                    nextOid = variableBinding.getOid().toDottedString();
                    //如果不是这个节点下的oid则终止遍历，否则会输出很多，直到整个遍历完。
                    if (!nextOid.startsWith(oid)) {
                        matched = false;
                        break;
                    }
                    System.out.println(variable);
                }
                if (!matched) {
                    break;
                }
                pdu.clear();
                pdu.add(new VariableBinding(new OID(nextOid)));
                System.out.println("返回结果：" + response);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

}
