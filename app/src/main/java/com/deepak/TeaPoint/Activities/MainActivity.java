package com.deepak.TeaPoint.Activities;

import android.app.ProgressDialog;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.deepak.TeaPoint.Adapter.CategoryRecyclerViewAdapter;
import com.deepak.TeaPoint.Adapter.HomeRecyclerView;
import com.deepak.TeaPoint.Models.Hero;
import com.deepak.TeaPoint.R;
import com.deepak.TeaPoint.api.Client;
import com.deepak.TeaPoint.api.Service;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView homerecyclerview;
    List<Hero> heroList;
    CategoryRecyclerViewAdapter adapter;
    Hero hero;
    ProgressDialog pd;
    ImageView imgcategory;
    TextView catname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        pd = new ProgressDialog(this);
        pd.setMessage("Fetching Heros...");
        pd.setCancelable(false);
        pd.show();

        catname = findViewById(R.id.txt_category_name);
        imgcategory = findViewById(R.id.imgcategory);
        homerecyclerview = findViewById(R.id.category_recyclerview);
        heroList = new ArrayList<>();
        adapter = new CategoryRecyclerViewAdapter(this, heroList);

        implementView();
    }

    private void implementView() {

        String imgcat = getIntent().getStringExtra("thumbnail");
        String cname = getIntent().getStringExtra("name");
        Picasso.with(getBaseContext())
                .load(imgcat)
                .placeholder(R.drawable.load)
                .into(imgcategory);

        catname.setText(cname);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        homerecyclerview.setLayoutManager(mLayoutManager);
        homerecyclerview.addItemDecoration(new MainActivity.GridSpacingItemDecoration(2, dpToPx(10), true));
        homerecyclerview.setItemAnimator(new DefaultItemAnimator());
        homerecyclerview.setAdapter(adapter);

        loadJSON();
    }

    private void loadJSON(){
        try{

            Service service = Client.getRetrofit().create(Service.class);
            Call<List<Hero>> call = service.getHeros();

            call.enqueue(new Callback<List<Hero>>() {
                @Override
                public void onResponse(Call<List<Hero>> call, Response<List<Hero>> response) {
                    List<Hero> items = response.body();
                    homerecyclerview.setAdapter(new HomeRecyclerView(getApplicationContext(), items));
                    homerecyclerview.smoothScrollToPosition(0);
                    pd.hide();
                }

                @Override
                public void onFailure(Call<List<Hero>> call, Throwable t) {
                    Log.d("Error", t.getMessage());
                    Toast.makeText(MainActivity.this, "Error Fetching Data!", Toast.LENGTH_SHORT).show();
                    pd.hide();
                }
            });

        }catch (Exception e){
            Log.d("Error", e.getMessage());
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }


}
