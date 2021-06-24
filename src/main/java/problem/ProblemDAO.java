package problem;

import util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProblemDAO {
    public  void insert(Problem problem){
//        1.建立数据库连接
        Connection connection = DBUtil.getConnection();
        PreparedStatement statement = null;
        try{
//         2.拼装SQL语句
            String sql = "insert into oj_table values(null, ?, ? ,?, ?, ?)";
            statement = connection.prepareStatement(sql);
            statement.setString(1, problem.getTitle());
            statement.setString(2, problem.getLevel());
            statement.setString(3, problem.getDescription());
            statement.setString(4, problem.getTemplateCode());
            statement.setString(5, problem.getTestCode());
//      3.执行SQL
            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            DBUtil.close(connection, statement, null);
        }
    }


    public void delete(int problemId){
//        1.建立数据库连接
        Connection connection = DBUtil.getConnection();
//        2.拼装SQL语句
        PreparedStatement statement = null;
        String sql = "delete from oj_table where id = ?";
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, problemId);
//            3.执行SQL
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtil.close(connection, statement, null);
        }
    }
    public List<Problem> selectAll(){
//        1.建立连接
        List<Problem> problems = new ArrayList<>();
        Connection connection = DBUtil.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
//        2.拼装SQL
        String sql = "select id, title, level from oj_table";
//        3.执行SQL
        try {
            statement = connection.prepareStatement(sql);
//            3.执行SQL
            resultSet = statement.executeQuery();
//            4.遍历结果集
            while (resultSet.next()){
                Problem problem = new Problem();
                problem.setId(resultSet.getInt("id"));
                problem.setTitle(resultSet.getString("title"));
                problem.setLevel(resultSet.getString("level"));
                problems.add(problem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtil.close(connection, statement, resultSet);
        }

        return problems;
    }

    public Problem selectOne(int problemId){
//        1.建立连接
        Connection connection = DBUtil.getConnection();
//        2.拼装SQL
        String sql = "select * from oj_table where id = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, problemId);
//            3.执行sql
            resultSet = statement.executeQuery();
//            4.遍历结果集，由于查询结果要么是true要么是false，所以直接使用if
            if (resultSet.next()){
                Problem problem = new Problem();
                problem.setId(resultSet.getInt("id"));
                problem.setTitle(resultSet.getString("title"));
                problem.setLevel(resultSet.getString("level"));
                problem.setDescription(resultSet.getString("description"));
                problem.setTemplateCode(resultSet.getString("templateCode"));
                problem.setTestCode(resultSet.getString("testCode"));
                return problem;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtil.close(connection, statement, resultSet);
        }
        return  null;


    }
}
