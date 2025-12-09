package com.jgdsun.qms.test;

import com.jgdsun.qms.model.ims4.IMS4Result;
import com.jgdsun.qms.model.ims4.LoginData;
import org.noear.snack.ONode;
import org.noear.snack.core.Options;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Stone
 * @since 2025-10-17
 */
public class JsonTest {
    public static void main(String[] args) {
        String json = getJson();
        IMS4Result<LoginData> result = ONode.loadStr(json).toObject((new IMS4Result<LoginData>() {
        }).getClass());
        System.out.println(result);
    }


    public static String getJson() {
        return "{\n" +
                "    \"code\": 200,\n" +
                "    \"data\": {\n" +
                "        \"user_id\": 3,\n" +
                "        \"user_name\": \"admin\",\n" +
                "        \"csrf_token\": \"b1645110-5897-4e18-902c-6fed898108cc\",\n" +
                "        \"views\": [\n" +
                "            {\n" +
                "                \"id\": 60000,\n" +
                "                \"key\": \"dashboard\",\n" +
                "                \"name\": \"dashboard\",\n" +
                "                \"check\": true,\n" +
                "                \"children\": []\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": 10000,\n" +
                "                \"key\": \"release\",\n" +
                "                \"name\": \"publish\",\n" +
                "                \"check\": true,\n" +
                "                \"children\": [\n" +
                "                    {\n" +
                "                        \"id\": 10100,\n" +
                "                        \"key\": \"releaseNormal\",\n" +
                "                        \"name\": \"release normal\",\n" +
                "                        \"check\": true,\n" +
                "                        \"children\": []\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"id\": 10200,\n" +
                "                        \"key\": \"releaseEmergency\",\n" +
                "                        \"name\": \"release emergency\",\n" +
                "                        \"check\": true,\n" +
                "                        \"children\": []\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"id\": 10300,\n" +
                "                        \"key\": \"releaseCushion\",\n" +
                "                        \"name\": \"release cushion\",\n" +
                "                        \"check\": true,\n" +
                "                        \"children\": []\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"id\": 10400,\n" +
                "                        \"key\": \"releaseNotify\",\n" +
                "                        \"name\": \"release notify\",\n" +
                "                        \"check\": true,\n" +
                "                        \"children\": []\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"id\": 10500,\n" +
                "                        \"key\": \"releaseAppend\",\n" +
                "                        \"name\": \"release append\",\n" +
                "                        \"check\": true,\n" +
                "                        \"children\": []\n" +
                "                    }\n" +
                "                ]\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": 20000,\n" +
                "                \"key\": \"terminal\",\n" +
                "                \"name\": \"terminal\",\n" +
                "                \"check\": true,\n" +
                "                \"children\": []\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": 30000,\n" +
                "                \"key\": \"material\",\n" +
                "                \"name\": \"material\",\n" +
                "                \"check\": true,\n" +
                "                \"children\": []\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": 40000,\n" +
                "                \"key\": \"user\",\n" +
                "                \"name\": \"user\",\n" +
                "                \"check\": true,\n" +
                "                \"children\": [\n" +
                "                    {\n" +
                "                        \"id\": 40100,\n" +
                "                        \"key\": \"userManagement\",\n" +
                "                        \"name\": \"user management\",\n" +
                "                        \"check\": true,\n" +
                "                        \"children\": []\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"id\": 40200,\n" +
                "                        \"key\": \"roleSetting\",\n" +
                "                        \"name\": \"role setting\",\n" +
                "                        \"check\": true,\n" +
                "                        \"children\": []\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"id\": 40300,\n" +
                "                        \"key\": \"userOperationLog\",\n" +
                "                        \"name\": \"user operation log\",\n" +
                "                        \"check\": true,\n" +
                "                        \"children\": []\n" +
                "                    }\n" +
                "                ]\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": 50000,\n" +
                "                \"key\": \"audit\",\n" +
                "                \"name\": \"audit\",\n" +
                "                \"check\": true,\n" +
                "                \"children\": [\n" +
                "                    {\n" +
                "                        \"id\": 50100,\n" +
                "                        \"key\": \"auditSearch\",\n" +
                "                        \"name\": \"audit search\",\n" +
                "                        \"check\": true,\n" +
                "                        \"children\": []\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"id\": 50200,\n" +
                "                        \"key\": \"auditSetting\",\n" +
                "                        \"name\": \"audit setting\",\n" +
                "                        \"check\": true,\n" +
                "                        \"children\": []\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"id\": 50300,\n" +
                "                        \"key\": \"auditHistorySearch\",\n" +
                "                        \"name\": \"audit history search\",\n" +
                "                        \"check\": true,\n" +
                "                        \"children\": []\n" +
                "                    }\n" +
                "                ]\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": 70000,\n" +
                "                \"key\": \"theme\",\n" +
                "                \"name\": \"theme\",\n" +
                "                \"check\": true,\n" +
                "                \"children\": []\n" +
                "            }\n" +
                "        ]\n" +
                "    }\n" +
                "}\n";
    }
}
