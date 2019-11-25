package com.example.project1.database;

// This class contains a list of questions

import android.content.Context;

import com.example.project1.model.Question1;

import java.util.ArrayList;
import java.util.List;

public class QuestionBank {

    // declare list of Question objects
    List<Question1> list = new ArrayList<>();
    //    MyDataBaseHelper myDataBaseHelper;
    ExamN5Sql demo;

    // method returns number of questions in list
    public int getLength() {
        return list.size();
    }

    // method returns question from list based on list index
    public String getQuestion(int a) {
        return list.get(a).getQuestion();
    }

    // method return a single multiple choice item for question based on list index,
    // based on number of multiple choice item in the list - 1, 2, 3 or 4
    // as an argument
    public String getChoice(int index, int num) {
        return list.get(index).getChoice(num - 1);
    }
    //  method returns correct answer for the question based on list index
    public String getCorrectAnswer(int a) {
        return list.get(a).getAnswer();
    }


    public void initQuestions(Context context) {
        demo = new ExamN5Sql(context);
        demo.createDataBase();
//        list = demo.getAllQuestionsList();
        list = demo.getAllQuestionsList();


//        myDataBaseHelper = new MyDataBaseHelper(context);
//        list = myDataBaseHelper.getAllQuestionsList();//get questions/choices/answers from database
//        if (list.isEmpty()) {//if list is empty, populate database with default questions/choices/answers
//            myDataBaseHelper.addInitialQuestion(new Question1("1. bạn đến từ đâu ?",
//                    new String[]{"hà nội", "hưng yên", "lạng sơn", "hà nam"}, "hà nam","bạn ấy đẻ ra ở hà nam"));
//            myDataBaseHelper.addInitialQuestion(new Question1("2. vĩnh xấu hay đẹp.bạn nên nói thật",
//                    new String[]{"xấu như con gấu", "không những  xấu còn cà khịa", "đẹp nhưng mất cái mặt", "không còn gì để tả nổi"}, "không còn gì để tả nổi","thực tế"));
//            myDataBaseHelper.addInitialQuestion(new Question1("3. câu cửa miệng của bà tân vlog",
//                    new String[]{"cuộc đời bà gần 60 nồi bánh trưng", "cho nó chất", "siêu bự", "không có"}, "cuộc đời bà gần 60 nồi bánh trưng","bà ấy hay nói thế"));
//            myDataBaseHelper.addInitialQuestion(new Question1("4.bạn có đẹp trai",
//                    new String[]{"tất nhiên", "dĩ nhiên", "còn phải nói", "cái này ai cũng biết"}, "cái này ai cũng biết","vì tú đẹp trai lắm"));
//
//            list = myDataBaseHelper.getAllQuestionsList();//get list from database again

//        }
    }

    public void initQuestions1(Context context) {
        demo = new ExamN5Sql(context);
        demo.createDataBase();
        list = demo.getAllQuizKanji();

    }

    public void initQuestions2(Context context) {
        demo = new ExamN5Sql(context);
        demo.createDataBase();
        list = demo.getAllVocabulary();

    }
}