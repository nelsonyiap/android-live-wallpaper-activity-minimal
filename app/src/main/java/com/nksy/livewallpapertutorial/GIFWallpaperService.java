package com.nksy.livewallpapertutorial;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Movie;
import android.os.Handler;
import android.os.SystemClock;
import android.service.wallpaper.WallpaperService;
import android.util.Log;
import android.view.SurfaceHolder;

import java.io.IOException;
import java.io.InputStream;

public class GIFWallpaperService extends WallpaperService {

    private final long FRAME_RATE = (long)1000/60; // update at 24 Hz
    private final Handler mHandler = new Handler();

    @Override
    public Engine onCreateEngine() {
        try {
            return new GIFWallpaperEngine();
        } catch (IOException e) {
            Log.w("GIFWallpaperService", "Unable to create GIFWallpaperEngine");
            stopSelf();
            return null;
        }
    }

    private class GIFWallpaperEngine extends WallpaperService.Engine {

        private Movie gif;
        private Runnable runnable;
        private int gifSeeker;
        private int gifDuration;
        private long timeStart;
        private float xScale = 1;
        private float yScale = 1;
        private float xOffset = 0;

        public GIFWallpaperEngine() throws IOException {
            InputStream is = getResources().openRawResource(+R.drawable.example);
            if (is != null) {
                try {
                    gif = Movie.decodeStream(is);
                    gifDuration = gif.duration();
                } finally {
                    is.close();
                }
            }
            else {
                throw new IOException("Unable to read GIF InputStream");
            }
            gifSeeker = 0;
            runnable = new Runnable() {
                @Override
                public void run() {
                    updateClock();
                    render();
                    removeCallbacks();
                    scheduleDraw();
                }
            };
        }

        private void updateClock() {
            if (gifSeeker == -1) {
                gifSeeker = 0;
                timeStart = SystemClock.uptimeMillis();
                return;
            }
            long mDiff = (SystemClock.uptimeMillis() - timeStart) / 30 / 1000; // 30fps GIF
            gifSeeker = (int)(gifSeeker + mDiff) % gifDuration;
        }

        private void removeCallbacks() {
            mHandler.removeCallbacks(runnable);
        }

        private void scheduleDraw() {
            mHandler.postDelayed(runnable, FRAME_RATE);
        }

        private void render() {
            SurfaceHolder surfaceHolder = getSurfaceHolder();
            Canvas canvas = null;
            try {
                canvas = surfaceHolder.lockCanvas();
                if (canvas != null) {
                    drawGif(canvas);
                }
            }
            finally {
                if (canvas != null) {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }

        private void drawGif(Canvas canvas) {
            canvas.save();
            canvas.drawColor(Color.BLACK);
            canvas.scale(xScale, yScale);
            gif.setTime(gifSeeker);
            gif.draw(canvas, xOffset/xScale, 0);
            canvas.restore();
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            super.onVisibilityChanged(visible);
            if (visible) {
                render();
                removeCallbacks();
                scheduleDraw();
            }
            else {
                removeCallbacks();
            }
        }

        @Override
        public void onOffsetsChanged(float xOffset, float yOffset, float xOffsetStep, float yOffsetStep, int xPixelOffset, int yPixelOffset) {
            super.onOffsetsChanged(xOffset, yOffset, xOffsetStep, yOffsetStep, xPixelOffset, yPixelOffset);
            this.xOffset = (float)xPixelOffset;
        }

        @Override
        public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            super.onSurfaceChanged(holder, format, width, height);
            int w = holder.getSurfaceFrame().width();
            int h = holder.getSurfaceFrame().height();
            yScale = (float)h / gif.height();
            xScale = yScale;
        }

        private void scalePortrait(SurfaceHolder holder) {
        }

        private void scaleLandscape(SurfaceHolder holder) {
        }
    }
}
