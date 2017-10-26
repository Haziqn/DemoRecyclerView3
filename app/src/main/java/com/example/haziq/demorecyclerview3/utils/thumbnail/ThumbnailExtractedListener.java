package com.example.haziq.demorecyclerview3.utils.thumbnail;

import android.content.Context;
import android.graphics.Bitmap;

import java.lang.ref.WeakReference;

/**
 * @author Khairuddin Bin Ali
 */
public interface ThumbnailExtractedListener {
    WeakReference<Context> getWeakContext();

    void onThumbnailExtracted(String filePath, Bitmap thumbnail);
}