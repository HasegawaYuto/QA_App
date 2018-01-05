package jp.techacademy.yuuto.hasegawa.qa_app;

/**
 * Created by hasegawayuto on 2018/01/03.
 */
import java.io.Serializable;
import java.util.ArrayList;
import android.util.Log;
import java.util.HashMap;

public class Question implements Serializable {
    private String mTitle;
    private String mBody;
    private String mName;
    private String mUid;
    private String mQuestionUid;
    private int mGenre;
    private byte[] mBitmapArray;
    private ArrayList<Answer> mAnswerArrayList;
    private HashMap<String,Boolean> mFavorites;

    public String getTitle() {
        return mTitle;
    }

    public String getBody() {
        return mBody;
    }

    public String getName() {
        return mName;
    }

    public String getUid() {
        return mUid;
    }

    public String getQuestionUid() {
        return mQuestionUid;
    }

    public int getGenre() {
        return mGenre;
    }

    public byte[] getImageBytes() {
        return mBitmapArray;
    }

    public ArrayList<Answer> getAnswers() {
        return mAnswerArrayList;
    }

    public Boolean getIsFavored(String uid){
        Boolean flag = false;
        if (mFavorites.get(uid) != null ){
            flag = true;
        }
        return flag;
    }

    public HashMap<String,Boolean> getFavorites(){
        return mFavorites;
    }

    public Question(String title, String body, String name, String uid, String questionUid, int genre, byte[] bytes, ArrayList<Answer> answers, HashMap<String,Boolean> favorites) {
        mTitle = title;
        mBody = body;
        mName = name;
        mUid = uid;
        mQuestionUid = questionUid;
        mGenre = genre;
        mBitmapArray = bytes.clone();
        mAnswerArrayList = answers;
        mFavorites = favorites;
    }
}
