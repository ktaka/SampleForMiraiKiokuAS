/*
 * This code was derived from "Android Programming Nyumon 2nd edition".
 * http://www.amazon.co.jp/dp/4048860682/
 */

package org.tohokutechdojo.mirakikiokuapisample;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.AsyncTask;
import android.widget.ImageView;

public class SetImageTask extends AsyncTask<Void, Void, Bitmap> {
    protected String mUrl;
    protected ImageView mImageView;
    public SetImageTask(String url, ImageView iv) {
        mUrl = url;
        mImageView = iv;
        mImageView.setTag(mUrl);
    }

    /* (non-Javadoc)
     * @see android.os.AsyncTask#doInBackground(Params[])
     */
    @Override
    protected Bitmap doInBackground(Void... params) {
        String cacheName = mUrl;
        Bitmap bmp = ImageMap.getImage(cacheName);
        if (bmp == null) {
            try{
                URL url = new URL(mUrl);
                Options options = new Options();
                options.inPreferredConfig = Config.RGB_565;
                bmp = BitmapFactory.decodeStream(url.openStream(), null, options);
            } catch (MalformedURLException e) {
                return null;
            } catch (IOException e) {
                return null;
            }
            ImageMap.setImage(cacheName, bmp);
        }
        return bmp;
    }

    /* (non-Javadoc)
     * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
     */
    @Override
    protected void onPostExecute(Bitmap bmp) {
        if(mImageView != null && mImageView.getTag() != null && mImageView.getTag().equals(mUrl)) {
            mImageView.setImageBitmap(bmp);
        }
    }
}