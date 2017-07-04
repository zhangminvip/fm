package tingproject.testopensourceapplication.com.zmusic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

/**
 * Created by tom on 2017/7/4.
 */

public class CategoryActivity extends Activity implements View.OnClickListener{

    private String TAG = "CategoryActivity";

    private Button btn_category_music;
    private Button btn_category_children;
    private Button btn_category_humanity;
    private Button btn_category_english;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_category);
        initView();
        initEvent();



    }


    public void initView(){
        btn_category_music = (Button)findViewById(R.id.btn_category_music);
        btn_category_children = (Button)findViewById(R.id.btn_category_children);
        btn_category_humanity = (Button)findViewById(R.id.btn_category_humanity);
        btn_category_english = (Button)findViewById(R.id.btn_category_english);
    }

    public void initEvent(){
        btn_category_music.setOnClickListener(this);
        btn_category_children.setOnClickListener(this);
        btn_category_humanity.setOnClickListener(this);
        btn_category_english.setOnClickListener(this);
    }


    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn_category_music:
                String data_music = "music";
                Intent intent_music = new Intent(CategoryActivity.this,TagActivity.class);
                intent_music.putExtra("category",data_music);
                startActivity(intent_music);
                Log.d(TAG,""+data_music);
                break;
            case R.id.btn_category_children:
                String data_children = "children";
                Intent intent_children = new Intent(CategoryActivity.this,TagActivity.class);
                intent_children.putExtra("category",data_children);
                startActivity(intent_children);
                break;
            case R.id.btn_category_humanity:
                String data_humanity = "humanity";
                Intent intent_humanity = new Intent(CategoryActivity.this,TagActivity.class);
                intent_humanity.putExtra("category",data_humanity);
                startActivity(intent_humanity);
                break;

            case R.id.btn_category_english:
                String data_english = "english";
                Intent intent_english = new Intent(CategoryActivity.this,TagActivity.class);
                intent_english.putExtra("category",data_english);
                startActivity(intent_english);
                break;
            default:
                break;
        }
    }

}
