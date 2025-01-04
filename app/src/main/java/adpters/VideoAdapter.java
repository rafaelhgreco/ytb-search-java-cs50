package adpters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cs50final.R;

import java.util.ArrayList;
import java.util.List;

import models.YouTubeResponse;


public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {
    private List<YouTubeResponse.Item> videos = new ArrayList<>();
    private Context context;

    // Construtor
    public VideoAdapter(Context context) {
        this.context = context;
    }

    // Cria nova view
    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_video, parent, false);
        return new VideoViewHolder(view);
    }

    // Preenche os dados da view
    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        YouTubeResponse.Item video = videos.get(position);
        holder.titleTextView.setText(video.snippet.title);
        holder.descriptionTextView.setText(video.snippet.description);

        // Carrega a thumbnail usando Glide
        Glide.with(context)
                .load(video.snippet.thumbnails.medium.url)
                .into(holder.thumbnailImageView);
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    // Método para atualizar os vídeos
    public void setVideos(List<YouTubeResponse.Item> videos) {
        this.videos = videos;
        notifyDataSetChanged();
    }

    // ViewHolder para guardar as referências das views
    static class VideoViewHolder extends RecyclerView.ViewHolder {
        ImageView thumbnailImageView;
        TextView titleTextView;
        TextView descriptionTextView;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            thumbnailImageView = itemView.findViewById(R.id.thumbnailImageView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
        }
    }
}