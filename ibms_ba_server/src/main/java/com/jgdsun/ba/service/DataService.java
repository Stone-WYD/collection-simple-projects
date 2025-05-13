package com.jgdsun.ba.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jgdsun.ba.mybatis.entity.TBa;
import com.jgdsun.ba.mybatis.entity.TBaParameter;
import com.jgdsun.ba.mybatis.entity.TBaParameterDuration;
import com.jgdsun.ba.mybatis.entity.TBaParameterValue;
import com.jgdsun.ba.mybatis.factory.MyBatisSessionFactory;
import com.jgdsun.ba.mybatis.mapper.TBaMapper;
import com.jgdsun.ba.mybatis.mapper.TBaParameterDurationMapper;
import com.jgdsun.ba.mybatis.mapper.TBaParameterMapper;
import com.jgdsun.ba.mybatis.mapper.TBaParameterValueMapper;
import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataService {


    public DataService() {

    }

    /**
     * 保存车场信息
     *
     * @return
     */


    public List<TBaParameter> getAllParameters() {
        SqlSession session = MyBatisSessionFactory.getSession();
        try {

            TBaParameterMapper mapper = session.getMapper(TBaParameterMapper.class);


            QueryWrapper<TBaParameter> tBaParameterWrapper = new QueryWrapper<>();
            tBaParameterWrapper.eq("STAT", "1");
            tBaParameterWrapper.eq("PROTOCOL", "2");


            return mapper.selectList(tBaParameterWrapper);


        } catch (Exception e) {
            e.printStackTrace();


        } finally {
            MyBatisSessionFactory.closeSession(session);
        }

        return null;

    }

    /**
     * 根据id获取参数信息
     *
     * @return
     */
    public TBaParameter getAllParameterById(String id) {
        SqlSession session = MyBatisSessionFactory.getSession();
        try {

            TBaParameterMapper mapper = session.getMapper(TBaParameterMapper.class);

            return mapper.selectById(id);


        } catch (Exception e) {
            e.printStackTrace();


        } finally {
            MyBatisSessionFactory.closeSession(session);
        }

        return null;

    }


    /**
     * 保存BVAV值
     *
     * @return
     */


    public void saveBAvalue(TBaParameter baParameter) {
        SqlSession session = MyBatisSessionFactory.getSession();
        try {

            TBaParameterMapper mapper = session.getMapper(TBaParameterMapper.class);

            mapper.updateById(baParameter);


            TBaParameterValueMapper mapperParameterValue = session.getMapper(TBaParameterValueMapper.class);

            TBaParameterValue parameterValue = new TBaParameterValue();

            parameterValue.setParameterId(baParameter.getId());
            parameterValue.setValue(baParameter.getLastvalue());
            parameterValue.setAddtime(new Date());
            mapperParameterValue.insert(parameterValue);


            session.commit();


        } catch (Exception e) {
            e.printStackTrace();


        } finally {
            MyBatisSessionFactory.closeSession(session);
        }

    }


    /**
     * 保存 参数状态保持时长
     *
     * @return
     */
    public void saveBADuration(TBaParameterDuration duration) {
        SqlSession session = MyBatisSessionFactory.getSession();
        try {

            TBaParameterDurationMapper mapper = session.getMapper(TBaParameterDurationMapper.class);

            mapper.insert(duration);


            session.commit();


        } catch (Exception e) {
            e.printStackTrace();


        } finally {
            MyBatisSessionFactory.closeSession(session);
        }

    }


    //---modbus相关使用

    /**
     * 获取所有照明IP 或 串口COM 号
     *
     * @return
     */

    public List<String> getAllIp() {
        SqlSession session = MyBatisSessionFactory.getSession();
        try {

            TBaParameterMapper mapper = session.getMapper(TBaParameterMapper.class);


            QueryWrapper<TBaParameter> qw = new QueryWrapper<TBaParameter>();

            qw.select("DISTINCT ip");

            //qw.eq("enable","1");

            List listList = mapper.selectObjs(qw);

            return listList;

        } catch (Exception e) {
            e.printStackTrace();


        } finally {
            MyBatisSessionFactory.closeSession(session);
        }

        return null;
    }


    /**
     * 获取所有 参数 IP 或 串口COM 号
     *
     * @return
     */

    public List<String> getAllParamAddressByIp(String ip) {
        SqlSession session = MyBatisSessionFactory.getSession();
        try {

            TBaParameterMapper mapper = session.getMapper(TBaParameterMapper.class);


            QueryWrapper<TBaParameter> qw = new QueryWrapper<TBaParameter>();

            qw.select("DISTINCT DEVID");

            qw.eq("ip", ip);

            List listList = mapper.selectObjs(qw);

            return listList;

        } catch (Exception e) {
            e.printStackTrace();


        } finally {
            MyBatisSessionFactory.closeSession(session);
        }

        return null;
    }


    /**
     * 根据ip和站号获取所有 属性
     *
     * @return
     */

    public List<TBaParameter> getAllLightByIpAndSlaveId(String ip, String address) {
        SqlSession session = MyBatisSessionFactory.getSession();
        try {

            TBaParameterMapper mapper = session.getMapper(TBaParameterMapper.class);


            QueryWrapper<TBaParameter> qw = new QueryWrapper<TBaParameter>();

            qw.eq("ip", ip);
            qw.eq("DEVID", address);
            qw.orderByAsc("BA_INDEX");

            List<TBaParameter> listList = mapper.selectList(qw);

            return listList;

        } catch (Exception e) {
            e.printStackTrace();


        } finally {
            MyBatisSessionFactory.closeSession(session);
        }

        return null;
    }

    /**
     * 更新灯状态
     *
     * @return
     */

    public boolean updateLightInfo(List<TBaParameter> list) {
        SqlSession session = MyBatisSessionFactory.getSession();
        try {

            TBaParameterMapper mapper = session.getMapper(TBaParameterMapper.class);


            for (TBaParameter lighting : list) {
                mapper.updateParameter(lighting);
            }


            session.commit();

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            session.rollback();

        } finally {
            MyBatisSessionFactory.closeSession(session);
        }

        return false;
    }


    public List<TBa> getSnmpList() {
        SqlSession session = MyBatisSessionFactory.getSession();
        try {

            TBaMapper mapper = session.getMapper(TBaMapper.class);
            QueryWrapper<TBa> qw = new QueryWrapper<TBa>();
            qw.eq("PROTOCOL", "3");
            return mapper.selectList(qw);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            MyBatisSessionFactory.closeSession(session);
        }
        return new ArrayList<>();
    }

	public List<TBaParameter> getBaParameterByIdList(List<String> idList) {
		SqlSession session = MyBatisSessionFactory.getSession();
		try {

			TBaParameterMapper mapper = session.getMapper(TBaParameterMapper.class);
			QueryWrapper<TBaParameter> qw = new QueryWrapper<TBaParameter>();
			qw.in("EQUIPMENT_ID", idList);
			return mapper.selectList(qw);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisSessionFactory.closeSession(session);
		}
		return new ArrayList<>();

	}

    public void updateParameterLastValue(List<TBaParameter> updateList) {
        SqlSession session = MyBatisSessionFactory.getSession();
        try {

            TBaParameterMapper mapper = session.getMapper(TBaParameterMapper.class);
            for (TBaParameter tBaParameter : updateList) {
                UpdateWrapper<TBaParameter> wrapper = new UpdateWrapper<>();
                wrapper.eq("id", tBaParameter.getId());
                wrapper.set("LASTVALUE", tBaParameter.getLastvalue());
                wrapper.set("LASTTIME", tBaParameter.getLasttime());
                mapper.update(tBaParameter,wrapper);
            }
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.rollback();
        } finally {
            MyBatisSessionFactory.closeSession(session);
        }
    }

    public void insertParameterValueList(List<TBaParameterValue> valueList) {
        SqlSession session = MyBatisSessionFactory.getSession();
        try {
            TBaParameterValueMapper mapper = session.getMapper(TBaParameterValueMapper.class);
            for (TBaParameterValue tDeParameterValue : valueList) {
                mapper.insert(tDeParameterValue);
            }
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.rollback();
        } finally {
            MyBatisSessionFactory.closeSession(session);
        }
    }
}
