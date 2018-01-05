package jp.techacademy.yuuto.hasegawa.qa_app;

/**
 * Created by hasegawayuto on 2018/01/03.
 */
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

public class QuestionDetailListAdapter extends BaseAdapter {
    private final static int TYPE_QUESTION = 0;
    private final static int TYPE_ANSWER = 1;

    private LayoutInflater mLayoutInflater = null;
    private Question mQustion;

    FirebaseAuth mAuth;
    Button favoriteButton;
    FirebaseUser user;
    Boolean isFavored;

    public QuestionDetailListAdapter(Context context, Question question) {
        mLayoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mQustion = question;
    }

    @Override
    public int getCount() {
        return 1 + mQustion.getAnswers().size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_QUESTION;
        } else {
            return TYPE_ANSWER;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public Object getItem(int position) {
        return mQustion;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (getItemViewType(position) == TYPE_QUESTION) {
            if (convertView == null) {
                convertView = mLayoutInflater.inflate(R.layout.list_question_detail, parent, false);
            }
            String body = mQustion.getBody();
            String name = mQustion.getName();

            //Log.d("DEBUG_PRINT" , String.valueOf(mQustion.getUid()));

            TextView bodyTextView = (TextView) convertView.findViewById(R.id.bodyTextView);
            bodyTextView.setText(body);

            TextView nameTextView = (TextView) convertView.findViewById(R.id.nameTextView);
            nameTextView.setText(name);

            favoriteButton = (Button) convertView.findViewById(R.id.favoriteButton);
            mAuth = FirebaseAuth.getInstance();
            user = mAuth.getCurrentUser();
            if ( user != null ){
                favoriteButton.setVisibility(View.VISIBLE);
                isFavored = mQustion.getIsFavored(user.getUid());
                Log.d("DEBUG_PRINT" , String.valueOf(isFavored));
                if ( isFavored ) {
                    favoriteButton.setText("UnFavotite");
                }else{
                    favoriteButton.setText("Favorite");
                }
                favoriteButton.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        Log.d("DEBUG_PRINT",String.valueOf(isFavored));
                        DatabaseReference dataBaseReference = FirebaseDatabase.getInstance().getReference();
                        DatabaseReference favoritesRef = dataBaseReference.child(Const.ContentsPATH).child(String.valueOf(mQustion.getQuestionUid())).child("favorites").child(String.valueOf(user.getUid()));
                        //ArrayList<String> favoriteList = new ArrayList<String>();
                        //Map<String, ArrayList<String>> data = new HashMap<String, ArrayList<String>>();
                        //favoriteList = mQustion.getFavorites();
                        //data.put("favorites",mQustion.getFavorites());
                        if ( isFavored ) {
                            favoriteButton.setText("Favorite");
                            isFavored = false;
                            favoritesRef.removeValue();
                            //favoriteList.remove(favoriteList.indexOf(user.getUid()));
                        }else{
                            favoriteButton.setText("UnFavorite");
                            isFavored = true;
                            favoritesRef.setValue(true);
                            //favoriteList.add(user.getUid());
                        }
                        //data.put("favorites",favoriteList);
                        //favoritesRef.setValue(true);
                        //Log.d("DEBUG_PRINT",String.valueOf(favoriteList));
                    }
                 });
            } else {
                favoriteButton.setVisibility(View.INVISIBLE);
            }

            byte[] bytes = mQustion.getImageBytes();
            if (bytes.length != 0) {
                Bitmap image = BitmapFactory.decodeByteArray(bytes, 0, bytes.length).copy(Bitmap.Config.ARGB_8888, true);
                ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView);
                imageView.setImageBitmap(image);
            }
        } else {
            if (convertView == null) {
                convertView = mLayoutInflater.inflate(R.layout.list_answer, parent, false);
            }

            Answer answer = mQustion.getAnswers().get(position - 1);
            String body = answer.getBody();
            String name = answer.getName();

            TextView bodyTextView = (TextView) convertView.findViewById(R.id.bodyTextView);
            bodyTextView.setText(body);

            TextView nameTextView = (TextView) convertView.findViewById(R.id.nameTextView);
            nameTextView.setText(name);

            //favoriteButton = (Button) convertView.findViewById(R.id.favoriteButton);
            //favoriteButton.setVisibility(View.VISIBLE);
        }

        return convertView;
    }
}
