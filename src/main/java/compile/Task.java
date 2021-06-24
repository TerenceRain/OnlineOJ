package compile;

import util.FileUtil;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class Task{
    private String WORK_DIR;
    private String CLASS = "Solution";
    private String CODE;
    private String STDOUT;
    private String STDERR;
    private String COMPILE_ERROR ;
    public Task(){
        WORK_DIR = "./tmp/" + UUID.randomUUID().toString() + "/";

        CODE = WORK_DIR + CLASS + ".java";
        STDOUT = WORK_DIR + "stdout.txt";
       STDERR = WORK_DIR + "stderr.txt";
       COMPILE_ERROR = WORK_DIR + "compile_error";
    }

    public Answer compileAndRun(Question question) throws IOException, InterruptedException {
        Answer answer = new Answer();
        File file = new File(WORK_DIR);
        if(!file.exists()){
            file.mkdirs();
        }
        FileUtil.writeFile(CODE, question.getCode());
        String compileCmd = "javac -encoding utf-8 " + CODE + " -d " + WORK_DIR;
        System.out.println("编译命令" + compileCmd);
        CommandUtil.run(compileCmd, null, COMPILE_ERROR);
        String compileError = FileUtil.readFile(COMPILE_ERROR);
        if(!compileError.equals("") ){
            answer.setErrno(1);
            answer.setReason(compileError);
            return answer;
        }

        String runCmd = "java -classpath " + WORK_DIR + " " + CLASS;
        System.out.println("运行命令：" + runCmd);
        CommandUtil.run(runCmd, STDOUT, STDERR);
        String runError = FileUtil.readFile(STDERR);
        if(!runError.equals("")){
            answer.setErrno(2);
            answer.setReason(runError);
            return answer;
        }

        answer.setErrno(0);
        String runStdout = FileUtil.readFile(STDOUT);
        answer.setReason(runStdout);
        return answer;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Question question = new Question();
        Task task = new Task();
       question.setCode("public class Solution {\n" +
               "    public static void main(String[] args) {\n" +
               "        System.out.println(\"stdout\");\n" +
               "        String s = null;\n" +
               "        System.out.println(s.length());\n" +
               "    }\n" +
               "}\n");
       Answer answer = task.compileAndRun(question);
        System.out.println(answer);
    }

}