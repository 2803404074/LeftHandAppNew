package com.tencent.liteav.demo.player.superplayer;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import com.tencent.liteav.demo.player.R;
import com.zhutian.baselibrary.util.GlideImageLoader;

import java.util.ArrayList;

/**
 *
 */

public class TCVodPlayerListAdapter extends RecyclerView.Adapter<TCVodPlayerListAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<VideoModel> mSuperPlayerModelList;
    private OnItemClickLitener mOnItemClickLitener;

    public TCVodPlayerListAdapter(Context context) {
        mContext = context;
        mSuperPlayerModelList = new ArrayList<VideoModel>();
    }

    public void updateData(ArrayList<VideoModel> mSuperPlayerModelList){
        this.mSuperPlayerModelList = mSuperPlayerModelList;
        notifyDataSetChanged();
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_special_about_tow, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final VideoModel videoModel = mSuperPlayerModelList.get(position);
        new GlideImageLoader().displayImage(mContext,videoModel.placeholderImage,holder.thumb);
//        if (videoModel.duration > 0) {
//            holder.duration.setText(TCUtils.formattedTime(videoModel.duration));
//        } else {
//            holder.duration.setText("");
//        }
        if (videoModel.title != null) {
            holder.title.setText(videoModel.title);
        }
        holder.rlContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickLitener != null) {
                    mOnItemClickLitener.onItemClick(position, videoModel);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mSuperPlayerModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //private TextView duration;//时长
        private TextView title;
        private ImageView thumb;
        private LinearLayout rlContent;

        public ViewHolder(final View itemView) {
            super(itemView);
            thumb = (ImageView) itemView.findViewById(R.id.ivContent);
            title = (TextView) itemView.findViewById(R.id.tvTitle);
            rlContent = itemView.findViewById(R.id.rlContent);
            //duration = (TextView) itemView.findViewById(R.id.tv_duration);
        }
    }

    /**
     * 添加一个SuperPlayerModel
     *
     * @param superPlayerModel
     */
    public void addSuperPlayerModel(VideoModel superPlayerModel) {
        notifyItemInserted(mSuperPlayerModelList.size());
        mSuperPlayerModelList.add(superPlayerModel);
    }

    public void setOnItemClickLitener(OnItemClickLitener listener) {
        mOnItemClickLitener = listener;
    }

    public void clear() {
        mSuperPlayerModelList.clear();
    }

    public interface OnItemClickLitener {
        void onItemClick(int position, VideoModel superPlayerModel);
    }

}
