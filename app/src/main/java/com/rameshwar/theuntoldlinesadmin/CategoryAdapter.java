package com.rameshwar.theuntoldlinesadmin;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private List<CategoryModel> categoryModelList;
    //private DeleteListner deleteListner;

    public CategoryAdapter(List<CategoryModel> categoryModelList) {
        this.categoryModelList = categoryModelList;
    //    this.deleteListner = deleteListner;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.categories_item,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.setData(categoryModelList.get(position).getUrl(),categoryModelList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return categoryModelList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private RoundedImageView imageView;
        private TextView textTitle;
        private ImageButton delete;
        public ViewHolder(@NonNull View itemView){
            super(itemView);

            imageView = itemView.findViewById(R.id.categoryImage);
            textTitle = itemView.findViewById(R.id.categoryName);
            delete = itemView.findViewById(R.id.delete);

        }

        private void setData(String url, String title){
            Glide.with(itemView.getContext()).load(url).into(imageView);
            this.textTitle.setText(title);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent setIntent = new Intent(itemView.getContext(), AddCategory.class);
                    setIntent.putExtra("title",title);
                    itemView.getContext().startActivity(setIntent);
                }
            });

//            delete.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    deleteListner.onDelete(key,position);
//                }
//            });
        }
    }

//    public interface DeleteListner{
//        public void onDelete(String key, int position);
//    }
}
