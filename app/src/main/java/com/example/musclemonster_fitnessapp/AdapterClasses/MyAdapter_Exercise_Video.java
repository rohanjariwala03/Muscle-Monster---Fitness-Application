package com.example.musclemonster_fitnessapp.AdapterClasses;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musclemonster_fitnessapp.POJOClasses.Exercise_video_pojo;
import com.example.musclemonster_fitnessapp.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MyAdapter_Exercise_Video extends RecyclerView.Adapter<MyAdapter_Exercise_Video.MyViewHolder> {


    private Context context;
    private ArrayList<Exercise_video_pojo> list;

    public MyAdapter_Exercise_Video(Context context, ArrayList<Exercise_video_pojo> videoArrayList) {
        this.context = context;
        this.list = videoArrayList;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        //Inflate Layout
        View view= LayoutInflater.from(context).inflate(R.layout.activity_exercise_video_item,parent,false);
        return new MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {
        Exercise_video_pojo video_pojo=list.get(position);

        //String id=video_pojo.getId();
        String title=video_pojo.getTitle();
        String VideoUrl=video_pojo.getVideoUrl();

        //Set Data
        holder.titleTv.setText(title);
        setVideoUrl(video_pojo,holder);
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void setVideoUrl(Exercise_video_pojo video_pojo, MyViewHolder holder) {
        holder.progressBar.setVisibility(View.VISIBLE);

        //get Video Url
        String videoUrl=video_pojo.getVideoUrl();

        //Media Controller play,pause
        MediaController mediaController=new MediaController(context);
        mediaController.setAnchorView(holder.videoView);

        Uri videoUri=Uri.parse(videoUrl);
        holder.videoView.setMediaController(mediaController);
        holder.videoView.setVideoURI(videoUri);

        holder.videoView.requestFocus();
        holder.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                //Video is ready to play
                mp.start();
            }
        });

        holder.videoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                // check if video is loading

                switch (what){
                    case MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START:{
                        //rendering Started
                        holder.progressBar.setVisibility(View.GONE);
                        return true;
                    }
                    case MediaPlayer.MEDIA_INFO_BUFFERING_START:{
                        //buffering started
                        holder.progressBar.setVisibility(View.INVISIBLE);
                        return true;
                    }
                    case MediaPlayer.MEDIA_INFO_BUFFERING_END:{
                        //buffering end
                        holder.progressBar.setVisibility(View.INVISIBLE);
                        holder.progressBar.setVisibility(View.GONE);
                        return true;
                    }

                }
                return false;
            }
        });

        holder.videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.start();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        VideoView videoView;
        TextView titleTv;
        ProgressBar progressBar;

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            videoView=itemView.findViewById(R.id.videoView);
            titleTv=itemView.findViewById(R.id.titleTv);
            progressBar=itemView.findViewById(R.id.progressBarVideo);
        }
    }

    //View holder class holds inits the UI views

}
