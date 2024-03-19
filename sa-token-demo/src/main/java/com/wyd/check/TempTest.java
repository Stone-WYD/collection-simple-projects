package com.wyd.check;

import java.util.*;

public class TempTest {


    public static void main(String[] args) {
        /*System.out.println(getMyNeededString());*/
        /* String x = "column=\"case_id\"";
        System.out.println(x.substring(8, x.length()-1));*/
        /*testStreamCount();*/

        System.out.println(String.format("%0" + 3 + "d", 12) );
    }

    private static void testStreamCount() {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("1");
        list.add("3");
        list.add("4");
        System.out.println(list.stream().filter(p -> p.equals("1")).count());
    }

    public static String getMyNeededString(){
        StringBuilder resultStringBuilder = new StringBuilder();
        int csize = 16;

        String x = getString();
        String[] firstSplit = x.split("\n");
        for (String f : firstSplit){
            String[] secondSplit = f.split(" ");
            for (String s:secondSplit){
                if (s.contains("column")){
                    csize--;
                    s = s.trim();
                    String neededColumn = s.substring(8, s.length() - 1);
                    if (csize > 0){
                        resultStringBuilder.append("c." + neededColumn + " as " + neededColumn + ", " );
                    }else {
                        resultStringBuilder.append("cl." + neededColumn + " as " + neededColumn + ", " );
                    }
                }
            }
        }

        return resultStringBuilder.toString();
    }

    public static String getString(){
        return "<result property=\"caseId\" column=\"case_id\" jdbcType=\"VARCHAR\"/>\n" +
                "        <result property=\"caseTitle\" column=\"case_title\" jdbcType=\"VARCHAR\"/>\n" +
                "        <result property=\"cbr\" column=\"cbr\" jdbcType=\"VARCHAR\"/>\n" +
                "        <result property=\"cbrMobile\" column=\"cbr_mobile\" jdbcType=\"VARCHAR\"/>\n" +
                "        <result property=\"courtCode\" column=\"court_code\" jdbcType=\"INTEGER\"/>\n" +
                "        <result property=\"courtName\" column=\"court_name\" jdbcType=\"VARCHAR\"/>\n" +
                "        <result property=\"createTime\" column=\"create_time\" jdbcType=\"INTEGER\"/>\n" +
                "        <result property=\"caseFillingTime\" column=\"case_filling_time\" jdbcType=\"INTEGER\"/>\n" +
                "        <result property=\"caseClosingTime\" column=\"case_closing_time\" jdbcType=\"INTEGER\"/>\n" +
                "        <result property=\"aymc\" column=\"aymc\" jdbcType=\"VARCHAR\"/>\n" +
                "        <result property=\"ssqq\" column=\"ssqq\" jdbcType=\"VARCHAR\"/>\n" +
                "        <result property=\"bdje\" column=\"bdje\" jdbcType=\"VARCHAR\"/>\n" +
                "        <result property=\"ah\" column=\"ah\" jdbcType=\"VARCHAR\"/>\n" +
                "        <result property=\"cbbm\" column=\"cbbm\" jdbcType=\"VARCHAR\"/>\n" +
                "        <result property=\"caseState\" column=\"case_state\" jdbcType=\"VARCHAR\"/>\n" +
                "        <result property=\"caseType\" column=\"case_type\" jdbcType=\"INTEGER\"/>\n" +
                "\n" +
                "        <collection property=\"rhcCaseLitigantList\" ofType=\"com.njxnet.entity.RhcCaseLitigant\" >\n" +
                "            <id column=\"case_id\" jdbcType=\"BIGINT\" property=\"caseId\" />\n" +
                "            <result property=\"id\" column=\"id\" jdbcType=\"INTEGER\"/>\n" +
                "            <result property=\"caseId\" column=\"case_id\" jdbcType=\"VARCHAR\"/>\n" +
                "            <result property=\"type\" column=\"type\" jdbcType=\"INTEGER\"/>\n" +
                "            <result property=\"characterType\" column=\"character_type\" jdbcType=\"INTEGER\"/>\n" +
                "            <result property=\"litigantName\" column=\"litigant_name\" jdbcType=\"VARCHAR\"/>\n" +
                "            <result property=\"nation\" column=\"nation\" jdbcType=\"VARCHAR\"/>\n" +
                "            <result property=\"mobile\" column=\"mobile\" jdbcType=\"VARCHAR\"/>\n" +
                "            <result property=\"address\" column=\"address\" jdbcType=\"VARCHAR\"/>\n" +
                "            <result property=\"identityNum\" column=\"identity_num\" jdbcType=\"VARCHAR\"/>\n" +
                "            <result property=\"country\" column=\"country\" jdbcType=\"VARCHAR\"/>\n" +
                "            <result property=\"companyName\" column=\"company_name\" jdbcType=\"VARCHAR\"/>\n" +
                "            <result property=\"institutionCode\" column=\"institution_code\" jdbcType=\"VARCHAR\"/>\n" +
                "            <result property=\"gender\" column=\"gender\" jdbcType=\"VARCHAR\"/>\n" +
                "            <result property=\"document_deliverAddress\" column=\"document_deliver_address\" jdbcType=\"VARCHAR\"/>\n" +
                "            <result property=\"areaCode\" column=\"area_code\" jdbcType=\"VARCHAR\"/>\n" +
                "        </collection>";
    }
}
