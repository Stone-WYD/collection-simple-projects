package com.jgdsun.ba.control;


import com.jgdsun.ba.control.bean.BaControlAction;
import com.jgdsun.ba.control.bean.BaControlListener;
import com.jgdsun.ba.utils.PropertiesUtil;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class BaAction implements BaControlListener {


    private BaService baService;

    private static Server server = null;

    private static PropertiesUtil propertiesUtil;

    /**
     * @param
     */
    public BaAction() {

        try {


            baService = new BaService();
            server = new Server(8078);

            propertiesUtil = new PropertiesUtil(System.getProperty("user.dir") + File.separator + "config.properties");

            ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
            // Or ServletContextHandler.NO_SESSIONS
            context.setContextPath("/api/v1/");
            server.setHandler(context);

            // 监听控制接口
            context.addServlet(new ServletHolder(new BaControlAction(this)), "/baControl");
            server.start();

            System.out.println("加载读取数据");

            initReadInfo();


            loopRead();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 初始化需要轮询的信息
     */
    public void initReadInfo() {


    }


    /**
     * 如果通讯异常 重复读取状态的间隔
     */
    private long sendErrorJg = 5000;
    /**
     * 通讯正常 重复读取 基础信息的间隔
     */
    private long readBaseInfoJg = 10000;

    /**
     * 是否有异常
     */
    private boolean isError = false;


    /**
     * 失败次数
     */
    private int errorTimes = 0;

    /**
     * 读取休眠频率
     */
    private int sleep = 100;




    private int readBaseJg = 3 * 60 * 1000 / sleep;
    private int readBaseNowCount = readBaseJg - 5;

    private int bvSaveNowCount = 0;
    private int bvSaveJg = 30 * 60 * 1000 / sleep;

    private boolean isDestory = false;

    private boolean bvSave = true;

    private void loopRead() {
        Thread t = new Thread() {
            public void run() {

                while (!isDestory) {
                    try {
                        Thread.sleep(sleep);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    /*
                    //需写入的内容
                    if(writeList.size()>0)
                    {
                        for(ModbusCommandBean bean:writeList)
                        {
                            writeList.remove(bean);

                            sendCommand(bean);
                        }
                    }

                    //需快速读取的内容
                    if(fastReadList.size()>0)
                    {
                        for(ModbusCommandBean bean:fastReadList)
                        {
                            fastReadList.remove(bean);

                            sendCommand(bean);
                        }
                    }

                     */
                    //检测是否有写入
                    baService.checkWrite();

                    //读取基本信息
                    readBaseNowCount++;
                    if (readBaseNowCount > readBaseJg) {
                        readBaseNowCount = 0;


                        baService.readOne(bvSave);

                        if (bvSave) {
                            bvSave = false;
                        }


                    }


                    if (!bvSave) {

                        bvSaveNowCount++;

                        if (bvSaveNowCount > bvSaveJg) {


                            bvSaveNowCount = 0;

                            bvSave = true;
                        }
                    }


                }

            }
        };

        t.start();
    }

    public BaService getBaService() {
        return baService;
    }

    @Override
    public void baWirte(String parameterId, String value) {
        baService.baWirte(parameterId, value);

    }

}
