package com.example.android.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    public static String flag="india";
    Call<ResponseModel> call;
    RecyclerView mainRecycler;
    LinearLayoutManager linearLayoutManager;
    APIInterface apiService;
    EditText search;
    String topic;

    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;

    public void search(View v){
        topic=search.getText().toString();
        search.clearFocus();

        flag="search";
        try {
            InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

        } catch (Exception e) {
            // TODO: handle exception
        }
        load("search");


    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(t.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


        search=findViewById(R.id.editText3);
        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    // do your stuff here
                    topic=search.getText().toString();
                    search.clearFocus();

                    flag="search";
                    try {
                        InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                    load("search");
                }
                return false;
            }
        });

        dl = (DrawerLayout)findViewById(R.id.abcd);
        t = new ActionBarDrawerToggle(this, dl,R.string.Open, R.string.Close);

        dl.addDrawerListener(t);
        t.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nv = (NavigationView)findViewById(R.id.nv);
        nv.setItemIconTintList(null);

        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id=menuItem.getItemId();

                if(id==R.id.indian)
                {flag="indian";
                    load(flag);
                    dl.closeDrawer(nv);
                     return true;}
                else if(id==R.id.sports){
                    flag="sports";
                    load(flag);
                    dl.closeDrawer(nv);
                    return true;
                }
                else if(id==R.id.sports){
                    flag="sports";
                    load(flag);
                    dl.closeDrawer(nv);
                    return true;}
                else if(id==R.id.entertainment){
                    flag="enter";
                    load(flag);
                    dl.closeDrawer(nv);
                    return true;}
                else if(id==R.id.science){
                    flag="science";
                    load(flag);
                    dl.closeDrawer(nv);
                    return true;}
                else if(id==R.id.tech){
                    flag="tech";
                    load(flag);
                    dl.closeDrawer(nv);
                    return true;}
                else{
                    flag="world";
                    load(flag);
                    dl.closeDrawer(nv);

                    return true;
                }


            }
        });




        Call<ResponseModel> call;
         mainRecycler = findViewById(R.id.activity_main_rv);
         linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mainRecycler.setLayoutManager(linearLayoutManager);

          apiService = ApiClient.getClient().create(APIInterface.class);
          load(flag);

    }

    public void load(String flag){
        if(flag=="world")
            call = apiService.derive2("bbc-news",                       "837c75bd575d41fdb6da9ea669d72df9");

        else if(flag=="sports")
            call=apiService.sports("https://gnews.io/api/v3/topics/sports?token=9aed7f287020de03946ca98c96681cf8&lang=en");
        else if(flag=="enter")
            call=apiService.sports("https://gnews.io/api/v3/topics/entertainment?token=9aed7f287020de03946ca98c96681cf8&lang=en");
        else if(flag=="science")
            call=apiService.sports("https://gnews.io/api/v3/topics/science?token=9aed7f287020de03946ca98c96681cf8&lang=en");
        else if(flag=="tech")
            call=apiService.sports("https://gnews.io/api/v3/topics/technology?token=9aed7f287020de03946ca98c96681cf8&lang=en");

        else if(flag=="search"){
            call=apiService.articles("https://newsapi.org/v2/everything?q="+topic+"&apiKey=837c75bd575d41fdb6da9ea669d72df9");
            Log.i("akul","https://newsapi.org/v2/everything?q="+topic+"&apiKey=837c75bd575d41fdb6da9ea669d72df9");

        }
        else
            call=  apiService.derive("in",                       "837c75bd575d41fdb6da9ea669d72df9");
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                final List<Article> articleList = response.body().getArticles();
                final MainArticleAdapter mainArticleAdapter = new MainArticleAdapter(articleList);
                mainRecycler.setAdapter(mainArticleAdapter);
                mainArticleAdapter.setOnItemClickListener(new MainArticleAdapter.onNoteListener() {
                    @Override
                    public void onClick(int pos) {
                        Article obj= articleList.get(pos);
                        String url=obj.getUrl();
                        Intent webActivity=new Intent(MainActivity.this,WebActivity.class);
                        webActivity.putExtra("url",url);
                        startActivity(webActivity);
                        Log.i("skul",url);

                    }
                });

            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Log.e("skul",t.getMessage());

            }
        });
    }


}
