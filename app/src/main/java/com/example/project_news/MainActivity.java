package com.example.project_news;

import  com.example.project_news.model.news;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.project_news.api.Apiclient;
import com.example.project_news.api.Apiinterface;
import com.example.project_news.model.Articles;

import java.security.Key;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public  static final String API_key="2ce10eb30f0e4be494723ef0131e7b9e";
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<Articles> articles=new ArrayList<>();///
    private  Adapter adapter;
    private String TAG = MainActivity.class.getSimpleName();
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//         swipeRefreshLayout= findViewById(R.id.SW_refesh_layput);
//         swipeRefreshLayout.setOnRefreshListener(this);
//         swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);

        recyclerView = findViewById(R.id.R_clearview);
        layoutManager= new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator( new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);
        loadjson();
//        onloading("");

    }
    public  void  loadjson(){

//        Toast.makeText(MainActivity.this,"not Result",Toast.LENGTH_LONG).show();

//        swipeRefreshLayout.setRefreshing(true);
        Apiinterface apiinterface= Apiclient.getApiClient().create(Apiinterface.class);
        String country=Utils.getCountry();//++
        String category = Utils.getcategory();


        Call<news> call;
//        if(Keyword.length() >0){
//             call = apiinterface.getnewsSearch(Keyword,country,"publishedAt",API_key);
//        }
//        else{
//            call = apiinterface.getNews(country,category,API_key);
//        }
        call = apiinterface.getNews(country,category,API_key);
        call.enqueue(new Callback<news>() {
            @Override
            public void onResponse(Call<news> call, Response<news> response) {
                if (response.isSuccessful() && response.body().getArticles() != null) {

                    if (!articles.isEmpty()) {
                        articles.clear();
                    }
                    articles = response.body().getArticles();
                    adapter = new Adapter(articles, MainActivity.this);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
//                    swipeRefreshLayout.setRefreshing(false);
                }
                else{
                    Toast.makeText(MainActivity.this,"not Result",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<news> call, Throwable t) {

            }
        });
    }

//    @Override
//    public void onRefresh() {
//        loadjson("");
//    }
//    private  void onloading(final  String keyword){
//        swipeRefreshLayout.post(
//                new Runnable() {
//                    @Override
//                    public void run() {
//                        loadjson(keyword);
//                    }
//                }
//        );
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menus,menu);
//        SearchManager searchManager =(SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        SearchView searchView= (SearchView)menu.findItem(R.id.action_search).getActionView();
//        MenuItem menuItem= menu.findItem(R.id.action_search);
//
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
//        searchView.setQueryHint("Search latest...");
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                if(query.length() >2){
//                   onloading(query);
//                }
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
////              onloading(newText));
//                return false;
//            }
//        });
//        menuItem.getIcon().setVisible(false,false);
//        return true;
//    }
}
