package com.pandeka.filmdb.Adapter;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pandeka.filmdb.Fragment.ByCategoryFragment;
import com.pandeka.filmdb.Model.CategoryData;
import com.pandeka.filmdb.R;
import com.pandeka.filmdb.Utility.ActivityUtil;

import java.util.List;

/**
 * Created by khris on 15/01/2018.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private Context context;
    private List<CategoryData> categoryData;

    public CategoryAdapter(Context context, List<CategoryData> list) {
        this.categoryData = list;
        this.context = context;
    }

    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.content_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CategoryAdapter.ViewHolder holder, int position) {
        final CategoryData category = categoryData.get(position);

        holder.category.setText(category.getCategory());

        holder.linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "" + category.getCategory(),
                        Toast.LENGTH_SHORT).show();
                FragmentManager fragmentManager = ((Activity) context).getFragmentManager();

                Bundle bundle = new Bundle();
                bundle.putString("Category", category.getCategory());
                bundle.putString("Id", category.getId());

                ByCategoryFragment byCategory_fragment = new ByCategoryFragment();
                byCategory_fragment.setArguments(bundle);
                ActivityUtil.addFragmentToActivity(fragmentManager, byCategory_fragment,
                        R.id.fragment_container);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView category;
        private LinearLayout linear;

        ViewHolder(View itemView) {
            super(itemView);

            linear = itemView.findViewById(R.id.category_linear);
            category = itemView.findViewById(R.id.category_textView);
        }
    }
}
