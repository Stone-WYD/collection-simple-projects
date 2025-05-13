package com.jgdsun.ba.control.bean;

import com.google.gson.Gson;
import com.jgdsun.ba.control.BaAction;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName BaControlAction
 * @Author yinkang
 * @Date 2024/07/12
 */

public class BaControlAction extends HttpServlet {

    private BaControlListener baAction;

    public BaControlAction(BaControlListener baAction) {
        this.baAction = baAction;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        Map map = new HashMap();
        try {


            String id = req.getParameter("parameterId");
            String value = req.getParameter("value");

            if(StringUtils.isEmpty(id) || StringUtils.isEmpty(value))
            {
                map.put("code", "1");
                map.put("msg", "处理失败");
            }else {

                baAction.baWirte(id, value);
                map.put("code", "0");
                map.put("msg", "处理成功");
            }
        } catch (Exception e) {
            System.out.println("数据处理异常 ");
            e.printStackTrace();
            map.put("code", "1");
            map.put("msg", "处理失败");
        }
        resp.getWriter().write(new Gson().toJson(map));
        resp.flushBuffer();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        Map map = new HashMap();
        try {
            byte b[] = getRequestPostBytes(req);

            String str = new String(b, "UTF-8");

            System.out.println("数据 " + str);

            BaControlRequestBean requestBean = new Gson().fromJson(str, BaControlRequestBean.class);

            String id = requestBean.getParameterId();
            String value = requestBean.getValue();
            baAction.baWirte(id,value);
            map.put("code", "0");
            map.put("msg", "处理成功");
        } catch (Exception e) {
            System.out.println("数据处理异常 ");
            e.printStackTrace();
            map.put("code", "1");
            map.put("msg", "处理失败");
        }
        resp.getWriter().write(new Gson().toJson(map));
        resp.flushBuffer();
    }

    /**
     * 描述:获取 post 请求内容
     * <pre>
     * 举例：
     * </pre>
     *
     * @param request
     * @return
     * @throws IOException
     */
    public static String getRequestPostStr(HttpServletRequest request)
            throws IOException {
        byte buffer[] = getRequestPostBytes(request);
        String charEncoding = request.getCharacterEncoding();
        if (charEncoding == null) {
            charEncoding = "UTF-8";
        }
        return new String(buffer, charEncoding);
    }

    /**
     * 描述:获取 post 请求的 byte[] 数组
     * <pre>
     * 举例：
     * </pre>
     *
     * @param request
     * @return
     * @throws IOException
     */
    public static byte[] getRequestPostBytes(HttpServletRequest request) throws IOException {
        int contentLength = request.getContentLength();
        if (contentLength < 0) {
            return null;
        }
        byte buffer[] = new byte[contentLength];
        for (int i = 0; i < contentLength; ) {
            int readlen = request.getInputStream().read(buffer, i,
                    contentLength - i);
            if (readlen == -1) {
                break;
            }
            i += readlen;
        }
        return buffer;
    }

}
