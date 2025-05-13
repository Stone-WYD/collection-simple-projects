package com.jgdsun.ba.mybatis.factory;

import com.baomidou.mybatisplus.core.MybatisSqlSessionFactoryBuilder;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionException;
import org.apache.ibatis.session.SqlSessionFactory;


import java.io.InputStream;


public class MyBatisSessionFactory {

	/**
	 * Location of hibernate.cfg.xml file.
	 * Location should be on the classpath as Hibernate uses
	 * #resourceAsStream style lookup for its configuration file.
	 * The default classpath location of the hibernate config file is
	 * in the default package. Use #setConfigFile() to update
	 * the location of the configuration file for the current session.
	 */
	private static String CONFIG_FILE_LOCATION = "mybatis-config.xml";

	private static SqlSessionFactory sessionFactory;
	private static String configFile = CONFIG_FILE_LOCATION;

	static {
		try {
			InputStream resourceAsSteam = Resources.getResourceAsStream( CONFIG_FILE_LOCATION );
			//sessionFactory = new SqlSessionFactoryBuilder().build(resourceAsSteam);//.builder( resourceAsSteam );

			sessionFactory = new MybatisSqlSessionFactoryBuilder().build(resourceAsSteam,null,null);
		} catch (Exception e) {
			System.err
					.println("%%%% Error Creating SessionFactory %%%%");
			e.printStackTrace();
		}
	}


	/**
	 * 获取session
	 *  @return Session
	 *  @throws SqlSessionException
	 */
	public static SqlSession getSession() throws SqlSessionException {

		if (sessionFactory == null) {
			rebuildSessionFactory();
		}
		SqlSession	session = (sessionFactory != null) ? sessionFactory.openSession()
				: null;

		return session;
	}

	/**
	 *  Rebuild hibernate session factory
	 *
	 */
	public static void rebuildSessionFactory() {
		try {
			InputStream resourceAsSteam = Resources.getResourceAsStream( CONFIG_FILE_LOCATION );
			sessionFactory = new MybatisSqlSessionFactoryBuilder().build(resourceAsSteam,null,null);
		} catch (Exception e) {
			System.err
					.println("%%%% Error Creating SessionFactory %%%%");
			e.printStackTrace();
		}
	}

	/**
	 *  Close the single hibernate session instance.
	 *
	 *  @throws SqlSessionException
	 */

	public static void closeSession(SqlSession session) throws SqlSessionException {

		if (session != null) {

			session.close();
		}
	}

	/**
	 *  return session factory
	 *
	 */
	public static SqlSessionFactory getSessionFactory() {
		return sessionFactory;
	}


}