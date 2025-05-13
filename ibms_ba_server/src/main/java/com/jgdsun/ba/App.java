package com.jgdsun.ba;


import com.jgdsun.ba.control.BaAction;
import com.jgdsun.ba.modbusip.ModBusAction;
import com.jgdsun.ba.mqtt.MQTTService;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );


        BaAction baAction = new BaAction();

        new MQTTService(baAction.getBaService());

        // new ModBusAction();





        /*


        Modbus4jReadUtils rtuModbus4jReadUtils;
        Modbus4jWriteUtils rtuModbus4jWriteUtils;

        SerialPortWrapperImpl serialPortWrapper = new SerialPortWrapperImpl("COM1",9600);
        ModbusMaster rtuMaster;
        rtuMaster = RTUMaster.getMaster(serialPortWrapper);
        rtuMaster.setTimeout(250);
        rtuMaster.setDiscardDataDelay(100);

        rtuModbus4jReadUtils = new Modbus4jReadUtils(rtuMaster);



        rtuModbus4jWriteUtils = new Modbus4jWriteUtils(rtuMaster);

        while(true)
        {
            try {
                Thread.sleep(1000);

                short[] list = rtuModbus4jReadUtils.readHoldingRegister(2, 0,10);

                System.out.println(list[0]);

            }catch(Exception e)
            {

            }
        }

         */




        //new MQTTService("");
          //  new AcartonsService();
          //  GprsServer server = new GprsServer();
           // server.begService();

    }
}
