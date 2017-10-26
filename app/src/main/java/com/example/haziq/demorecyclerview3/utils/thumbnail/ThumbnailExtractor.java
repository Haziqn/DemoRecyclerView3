package com.example.haziq.demorecyclerview3.utils.thumbnail;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.provider.BaseColumns;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Khairuddin Bin Ali
 */
public final class ThumbnailExtractor {
    private static final String TAG = "ThumbnailExtractor";
    private static final String[] idColumn = new String[]{BaseColumns._ID};
    private static final String selection = MediaStore.MediaColumns.DATA + " = ?";

    private static ThumbnailExtractor INSTANCE;
    private static Handler mainThreadHandler;

    public synchronized static ThumbnailExtractor getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ThumbnailExtractor();
        }

        if (mainThreadHandler == null) {
            mainThreadHandler = new Handler(Looper.getMainLooper());
        }

        return INSTANCE;
    }

    private ExecutorService imageLoadingTaskPool;

    private ThumbnailExtractor() { imageLoadingTaskPool = Executors.newFixedThreadPool(getNumThreads()); }

    public void asyncLoadThumbnail(@NonNull final String filePath,
                                   @NonNull final ThumbnailExtractedListener listener) {
        imageLoadingTaskPool.execute(new ImageLoadingTask(filePath, listener));
    }

    public void close() {
        try {
            imageLoadingTaskPool.awaitTermination(500, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private int getNumThreads() {
        return Math.min(Runtime.getRuntime().availableProcessors(), 6);
    }

    private static class ImageLoadingTask implements Runnable {
        private ThumbnailExtractedListener listener;
        private String filePath;

        ImageLoadingTask(String filePath, ThumbnailExtractedListener listener) {
            this.listener = listener;
            this.filePath = filePath;
        }

        @Override
        public void run() {
            Log.d(TAG, "run: Getting thumbnail for file " + filePath);

            Context context = listener.getWeakContext().get();
            if (context == null) {
                Log.d(TAG, "run: Context is not available. Returning...");
                return;
            }

            final Bitmap thumbnail = getThumbnail(context);
            Log.d(TAG, "run: haziq" + thumbnail.getAllocationByteCount());
            mainThreadHandler.post(new Runnable() {
                @Override
                public void run() { listener.onThumbnailExtracted(filePath, thumbnail); }
            });
        }

        private Bitmap getThumbnail(Context context) {
            ContentResolver contentResolver = context.getContentResolver();
            long id = getId(contentResolver, filePath);
            if (id == -1) {
                Log.d(TAG, "getThumbnail: Image [" + filePath + "] not found");
                return null;
            }

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            return MediaStore.Images.Thumbnails.getThumbnail(
                    contentResolver, id,
                    MediaStore.Images.Thumbnails.MINI_KIND,
                    options
            );
        }

        private long getId(ContentResolver contentResolver, String filePath) {
            Cursor cursor = contentResolver.query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, idColumn,
                    selection, new String[]{filePath}, null
            );

            long id = -1;
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    id = cursor.getLong(cursor.getColumnIndex(BaseColumns._ID));
                }

                cursor.close();
            }

            return id;
        }
    }
}