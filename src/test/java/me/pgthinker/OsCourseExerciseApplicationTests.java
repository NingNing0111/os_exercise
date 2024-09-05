package me.pgthinker;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import me.pgthinker.model.entity.Question;
import me.pgthinker.service.QuestionService;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

@SpringBootTest
class OsCourseExerciseApplicationTests {

    private final String jsonResourcePath = "src/main/resources/static/json";

    @Autowired
    private QuestionService questionService;

    @Test
    void contextLoads() throws IOException {
        File root = new File(jsonResourcePath);
        if(!root.isDirectory()){
            return ;
        }
        File[] dirs = root.listFiles();
        for(File dir: dirs){
            if(dir.isDirectory()){
                File[] files = dir.listFiles();
                for(File jsonFile: files){
                    Question question = parseFileToObj(jsonFile);
                    questionService.save(question);
                }
            }
        }
    }

    private Question parseFileToObj(File file) throws IOException{
        String jsonStr = FileUtils.readFileToString(file, Charset.defaultCharset());
        JSONObject jsonObject = JSON.parseObject(jsonStr);
        String status = jsonObject.getString("status");
        List<String> knowledge = jsonObject.getList("knowledge", String.class);
        String question = jsonObject.getString("question");
        String explain = jsonObject.getString("explain");
        Integer degreeOfDifficulty = jsonObject.getInteger("degree_of_difficulty");
        String source = jsonObject.getString("source");
        String answer = jsonObject.getString("answer");
        String type = jsonObject.getString("type");
        List<String> options = jsonObject.getList("options", String.class);
        Integer qNumber = jsonObject.getInteger("q_number");

        Question storeQuestion = new Question();
        storeQuestion.setType(type);
        storeQuestion.setContent(question);
        storeQuestion.setAnswer(answer);
        storeQuestion.setExplain(explain);
        storeQuestion.setOptions(options);
        storeQuestion.setKnowledge(knowledge);
        storeQuestion.setDifficulty(degreeOfDifficulty);
        storeQuestion.setSource(source);

        return storeQuestion;

    }

    @Test
    public void exportData() throws IOException {
        List<Question> list = questionService.list();
        String jsonString = JSON.toJSONString(list);
        File parent = new File("/Users/pgthinker/StudyCode/JavaCode/OS_course_exercise");
        File file = File.createTempFile("os_course_exercise", ".json",parent);
        System.out.println(file.getAbsolutePath());
        FileUtils.write(file,jsonString, StandardCharsets.UTF_8);
    }

}
