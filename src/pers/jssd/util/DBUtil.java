package pers.jssd.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DBUtil {
	private static String url;
	private static String user;
	private static String password;
	
	//读取属性文件properties并获取内容
	static{
		//准备一个空的map，没有key-value
		Properties prop = new Properties();
		
		//读取文件，并将文件键值对存入Properties对象
		InputStream is = DBUtil.class.getResourceAsStream("/db.properties"); //classpath
		try {
			prop.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		//从prop中根据key获取四个参数的值
		String driver = prop.getProperty("driver");
		//driver = prop.get("driver");
		url = prop.getProperty("url");
		user = prop.getProperty("username");
		password = prop.getProperty("password");
		
		//加载驱动
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取数据库连接
	 *
	 * @return 返回数据库连接对象
	 */
	public static  Connection getConnection(){
		Connection conn = null;
		try{			
			//建立数据库连接			
			conn = DriverManager.getConnection(url, user, password);
		}catch(SQLException e){
			e.printStackTrace();
		}
		return conn;
		
	}
	
	/**
	 * 关闭数据库资源
	 *
	 * @param rs 需要关闭的ResultSet资源
	 * @param stmt 需要关闭的Statement资源
	 * @param conn 需要关闭的数据库Connection资源
	 */
	public static void closeAll(ResultSet rs ,Statement stmt,Connection conn){
		//关闭数据库资源
		try {
			if(rs!=null){
				rs.close();
			}				
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			if(stmt != null){
				stmt.close();
			}				
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			if(conn != null){
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 执行DML操作
	 * DML:insert  update  delete
	 *
	 * @param sql    执行DML操作的语句
	 * @param params DML操作中需要的sql参数
	 * @return 返回更新数据的条数
	 */
	public static int executeUpdate(String  sql,Object ... params) {
		Connection conn =  null;
		PreparedStatement pstmt = null;
		int n = 0;
		try{
			//获取数据库连接
			conn = DBUtil.getConnection();

			conn.setAutoCommit(false);
			//使用手枪发送SQL命令并得到结果
			pstmt = conn.prepareStatement(sql);
			
			for(int i=0;i<params.length;i++){
				pstmt.setObject(i+1, params[i]);
			}
			n = pstmt.executeUpdate();
//System.out.println("n = " + n);

			conn.commit();
			conn.setAutoCommit(true);
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			//关闭数据库资源
			DBUtil.closeAll(null, pstmt, conn);
		}
		//返回数据
		return n;
	}

	// 测试代码
	public static void main(String[] args) {
		Connection conn = getConnection();
		System.out.println(conn);
	}
}
