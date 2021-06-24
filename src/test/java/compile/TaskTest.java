package compile;

import junit.framework.TestCase;

import java.io.IOException;

public class TaskTest extends TestCase {
    public static void main(String[] args) throws IOException, InterruptedException {
        Question question = new Question();
        Task task = new Task();
        question.setCode("public class Solution {\n" +
                "    public static void main(String[] args) {\n" +
                "        System.out.println(\"stdout\");\n" +
                "        String s = \"abc\";\n" +
                "        System.out.println(s.length());\n" +
                "    }\n" +
                "}\n");
        Answer answer = task.compileAndRun(question);
        System.out.println(answer);
    }

}