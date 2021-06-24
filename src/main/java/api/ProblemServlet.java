package api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import problem.Problem;
import problem.ProblemDAO;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/problem")
public class ProblemServlet extends HttpServlet {
    private Gson gson = new GsonBuilder().create();
//    用来实现读取题目列表读取指定题目详情

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        设置一下resp中需要的属性
        resp.setStatus(200);
        resp.setContentType("application/json; charset=utf-8");
//        从req对象中尝试读取id这个参数
        String id = req.getParameter("id");
        if(id == null || id.equals("")){
//            查找题目列表
            selectAll(resp);
        }else{
            selectOne(Integer.parseInt(id), resp);
        }
    }

    private void selectOne(int problemId, HttpServletResponse resp) {
//        1.创建ProblemDAO
        ProblemDAO problemDAO = new ProblemDAO();
//        2.指定查找的结果
        Problem problem = problemDAO.selectOne(problemId);
//        3.把结果包装成JSON格式
        String respString = gson.toJson(problem);
//        4.把结果写回客户端
        try {
            resp.getWriter().write(respString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void selectAll(HttpServletResponse resp) {
//        1.创建ProblemDAO对象
        ProblemDAO problemDAO = new ProblemDAO();
//        2.查找所有结果
        List<Problem> problems = problemDAO.selectAll();
//        3.把结果给包装成JSON格式
        String respString = gson.toJson(problems);
//        4.把结果写回前端
        try {
            resp.getWriter().write(respString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
