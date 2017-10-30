package com.example.haziq.demorecyclerview3;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Created by Haziq on 10/19/2017.
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    public static final int GROUP_TYPE = 0;
    public static final int ITEM_TYPE = 1;
    public static String TAG = "CustomAdapter";

    public TextView textViewGroupname;
    public ImageView imageView1;

//    int totalCount = 0;
//
//    public static Cursor cursorGroupCount = null;
//    public static Cursor cursorAll = null;
//    private Context context = null;

    private int totalCount = 0;
    private GroupedCursor groupedCursor;


//    static class PositionDetail{
//        boolean group;
//        int realPosition;
//
//        public PositionDetail(boolean group, int realPosition) {
//            this.group = group;
//            this.realPosition = realPosition;
//        }
//
//        @Override
//        public String toString() {
//            return "PositionDetail{" +
//                    "group=" + group +
//                    ", realPosition=" + realPosition +
//                    '}';
//        }
//    }
//    static Map<Integer,PositionDetail> positionDetailMap = new HashMap<>();

    public CustomAdapter(Cursor cursorGroupCount, Cursor cursorAll, int totalCount) {
        this.totalCount = totalCount;
//        cursorGroupCount.moveToPosition(0);
//        Log.d(TAG, "CustomAdapter: ::cursorGroupCount " + cursorGroupCount.getString(cursorGroupCount.getColumnIndex(FileSQLiteHelper.COLUMN_GROUP)));
//        cursorGroupCount.moveToPosition(2);
//        Log.d(TAG, "CustomAdapter: ::cursorGroupCount " + cursorGroupCount.getString(cursorGroupCount.getColumnIndex(FileSQLiteHelper.COLUMN_GROUP)));
        groupedCursor = new GroupedCursor(cursorAll, cursorGroupCount, FileSQLiteHelper.COLUMN_FILE);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case GROUP_TYPE:
                Log.d(TAG, "onCreateViewHolder: ::cursorType : item count in groups");

                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_textview, parent, false);
                textViewGroupname = itemView.findViewById(R.id.textView);

                GroupItemViewH groupItemViewH = new GroupItemViewH(itemView);
                return groupItemViewH;

            case ITEM_TYPE:
                Log.d(TAG, "onCreateViewHolder: ::cursorType : all rows");
                View itemView1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_imageview, parent, false);
                imageView1 = itemView1.findViewById(R.id.imageView);

                Size thumbnailSize1 = ViewUtils.getSquareSizeForWidth(Resources.getSystem().getDisplayMetrics().widthPixels / Constants.NUM_MEDIA_COLUMN);

                ViewGroup.LayoutParams thumbnailLayoutParams1 = imageView1.getLayoutParams();
                thumbnailLayoutParams1.width = thumbnailSize1.getWidth();
                thumbnailLayoutParams1.height = thumbnailSize1.getHeight();

                ItemViewH itemViewH = new ItemViewH(itemView1);
                return itemViewH;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        if (groupedCursor.isGroup(position)) {

            Cursor cursor = groupedCursor.moveToPosition(position);
            Log.d(TAG, "onBindViewHolder: ::cursor : " + cursor);

            Log.d(TAG, "onBindViewHolder: ::position : " + position);

            Log.d(TAG, "onBindViewHolder: ::cursor count : " + cursor.getCount());

            int columnIndex = cursor.getColumnIndex(FileSQLiteHelper.COLUMN_GROUP);
            String groupName = cursor.getString(columnIndex);
            Log.d(TAG, "onBindViewHolder: ::groupName : " + groupName);
            holder.textViewGroupName.setText(groupName);

        }
        else {

            Cursor cursor = groupedCursor.moveToPosition(position);
            if (cursor != null) {
                int columnIndex = cursor.getColumnIndex(FileSQLiteHelper.COLUMN_FILE);
                Log.d(TAG, "onBindViewHolder: ::image columnIndex : " + columnIndex);

                final String imgURL = cursor.getString(columnIndex);
                Log.d(TAG, "onBindViewHolder: ::imgURL : " + imgURL);

                imageView1.setImageResource(R.drawable.photo1);
            }

        }

        return;

//        if (cursorGroupCount != null && cursorGroupCount.moveToFirst()) {
//            int total = 0;
//
//            do {
//                int columnIndex = cursorGroupCount.getColumnIndex("COUNT(" + FileSQLiteHelper.COLUMN_FILE + ")");
//                Log.d(TAG, "onBindViewHolder: ::itemInGroupCount : " + cursorGroupCount.getInt(columnIndex));
//
//
//                totalItemCount += cursorGroupCount.getInt(columnIndex) + 1;
//                Log.d(TAG, "onBindViewHolder: ::totalItemCount : " + totalItemCount);
//
//                if (position == 0 || position == totalItemCount) {
//                    if(cursorAll.moveToFirst()){
//                        Log.d(TAG, "onBindViewHolder: cursorAll.moveToFirst is true");
//                        int columnIndex1 = cursorGroupCount.getColumnIndex(FileSQLiteHelper.COLUMN_GROUP);
//                        String itemGroupName = cursorGroupCount.getString(columnIndex1);
//                        Log.d(TAG, "onBindViewHolder: ::itemGroupName " + itemGroupName);
//                        holder.textViewGroupName.setText(itemGroupName);
//                        Toast.makeText(context, itemGroupName, Toast.LENGTH_SHORT).show();
//                    }
//
//                }
//                else {
//
//                    for (int i = 0; i < cursorGroupCount.getCount(); i++) {
//                        Log.d(TAG, "onBindViewHolder: ::position i: " + i);
//
//                        cursorGroupCount.moveToPosition(i);
//
//                        int itemCount = cursorGroupCount.getInt(columnIndex);
//                        Log.d(TAG, "onBindViewHolder: itemCountInGroup i : " + itemCount);
//                        if (position < itemCount) {
//                            Log.d(TAG, "onBindViewHolder: ::position : " + position);
//                            Log.d(TAG, "onBindViewHolder: ::cursor size " + cursorAll.getCount());
//                            cursorAll.moveToPosition(position);
//
//                            int columnIndex2 = cursorAll.getColumnIndex(FileSQLiteHelper.COLUMN_FILE);
//
//                            final String itemImageView = cursorAll.getString(columnIndex2);
//
//                            imageView1.setTag(itemImageView);
//                            imageView1.setImageBitmap(null);
////                            Picasso.with(context).load(itemImageView).into(imageView1);
//
////                            ThreadFactory.post(itemImageView, new Runnable() {
////                                @Override
////                                public void run() {
////                                    final Bitmap bitmap = BitmapFactory.decodeFile(itemImageView);
////                                    if( itemImageView.equals(imageView1.getTag())){
////                                        Handler handler = new Handler(Looper.getMainLooper());
////                                        handler.post(new Runnable() {
////                                            @Override
////                                            public void run() {
////                                                if( imageView1.equals(imageView1.getTag())){
////                                                    imageView1.setImageBitmap(bitmap);
////                                                }else{
////                                                    bitmap.recycle();
////                                                }
////                                            }
////                                        });
////                                    }else{
////                                        bitmap.recycle();
////                                    }
////
////                                    ThreadFactory.quit(itemImageView);
////                                }
////                            });
//
////                            if (itemImageView != null) {
////                                java.io.File imgFile1 = new java.io.File(itemImageView);
////                                if (imgFile1 != null) {
////                                    if (imgFile1.exists()) {
////                                        ThumbnailExtractor.getInstance().asyncLoadThumbnail(itemImageView, new ThumbnailExtractedListener() {
////                                            @Override
////                                            public WeakReference<Context> getWeakContext() {
////                                                return new WeakReference<>(imageView1.getContext());
////                                            }
////
////                                            @Override
////                                            public void onThumbnailExtracted(String filePath, Bitmap thumbnail) {
////
////                                                Log.d(TAG, "onThumbnailExtracted:1 FP " + filePath);
////                                                Log.d(TAG, "onThumbnailExtracted:1 IV " + imageView1.getTag());
////
////
////                                                if (imageView1.getTag() == filePath) {
////                                                    imageView1.setImageBitmap(thumbnail);
////                                                    imageView1.setVisibility(View.VISIBLE);
////                                                    Log.d(TAG, "imageView1.getTag().equals(filePath)");
////                                                    filePath = null;
////                                                }
////                                            }
////                                        });
////                                    } else {
////                                        imageView1.setVisibility(View.INVISIBLE);
////                                        Log.d(TAG, "onBindViewHolder: ::imageFile does not exist");
////
////                                    }
////                                } else {
////                                    imageView1.setVisibility(View.INVISIBLE);
////                                    Log.d(TAG, "onBindViewHolder: ::imageFile is null");
////
////                                }
////                            } else {
////                                imageView1.setVisibility(View.INVISIBLE);
////                                Log.d(TAG, "onBindViewHolder: ::itemImageView is null");
////                            }
//                        }
//                    }
//                }
//
//                total += cursorGroupCount.getInt(columnIndex);
//            } while (cursorGroupCount.moveToNext());
//        }

    }

//    private static void getItemOrGroup(int position) {
//        PositionDetail positionDetail = positionDetailMap.get(position);
//        if (positionDetail != null) {
//            if (positionDetail.group) {
//                System.out.println(position + "\t" + cursorGroupCount.moveToPosition(positionDetail.realPosition));
//            } else {
//                System.out.println(position + "\t" + cursorAll.moveToPosition(positionDetail.realPosition));
//            }
//
//            return;
//        }
//        getPositionDetail(position);
//
//        PositionDetail pd = positionDetailMap.get(position);
//        if (pd.group) {
//            System.out.println(position + " testing 1 \t" + cursorGroupCount.moveToPosition(positionDetail.realPosition));
//        } else {
//            System.out.println(position + "pulau ubin \t" + cursorAll.moveToPosition(positionDetail.realPosition));
//        }
//    }

//    private static void getPositionDetail(int position) {
//        int total = 0;
//        int groupIndex = 0;
//        boolean isGroup = false;
//        for (; groupIndex < cursorGroupCount.getCount(); groupIndex++) {
//            if (position == total) {
//                isGroup = true;
//                break;
//            }
//
//            cursorGroupCount.moveToPosition(groupIndex);
//            int columnIndex = cursorGroupCount.getColumnIndex("COUNT(" + FileSQLiteHelper.COLUMN_FILE + ")");
//
//            total += cursorGroupCount.getInt(columnIndex) + 1;
//            if (position < total) {
//                break;
//            }
//        }
//        if (isGroup) {
//            positionDetailMap.put(position, new PositionDetail(isGroup, groupIndex));
//        } else {
//            positionDetailMap.put(position, new PositionDetail(isGroup, position - groupIndex - 1));
//        }
//    }

//    private static void getType(int position) {
//        PositionDetail positionDetail = positionDetailMap.get(position);
//        if (positionDetail != null) {
//            System.out.println(position + "\t" + positionDetail);
//            return;
//        }
//
//        getPositionDetail(position);
//        System.out.println(position + "\t" + positionDetailMap.get(position));
//
//    }

    @Override
    public int getItemCount() {
        //retrieve item count in each group and group count

        return totalCount;
    }

    public int getItemViewType(int position) {

       if(groupedCursor.isGroup(position)){
           return GROUP_TYPE;
       } else {
           return ITEM_TYPE;
       }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewGroupName;
        public ImageView imageView1;

        public ViewHolder(View itemView) {
            super(itemView);

        }
    }

    public class ItemViewH extends ViewHolder {

        public ItemViewH(View itemView) {
            super(itemView);
            imageView1 = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }

    public class GroupItemViewH extends ViewHolder {

        public GroupItemViewH(View itemView) {
            super(itemView);
            textViewGroupName = (TextView) itemView.findViewById(R.id.textView);
        }
    }

}
