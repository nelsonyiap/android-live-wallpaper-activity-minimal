package com.nksy.livewallpapertutorial;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.service.wallpaper.WallpaperService;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

public class TutorialWallpaperService extends WallpaperService {

    private static final long TOUCH_DURATION = 1000;
    private static final long FRAME_RATE = (long)(1/60f * 1000);
    private final Paint mPaint = new Paint();
    private final Handler handler = new Handler();
    private long touchStart;
    private float touchx;
    private float touchy;

    @Override
    public Engine onCreateEngine() {
        return new TutorialWallpaperEngine();
    }

    private class TutorialWallpaperEngine extends WallpaperService.Engine {

        private final Runnable mDraw = new Runnable() {
            @Override
            public void run() {
                drawFrame();
            }
        };

        public TutorialWallpaperEngine() {

        }

        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);
            setTouchEventsEnabled(true);
            mPaint.setColor(Color.WHITE);
            mPaint.setAntiAlias(true);
            mPaint.setAlpha(150);
            handler.post(mDraw);
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            handler.removeCallbacks(mDraw);
        }

        @Override
        public void onTouchEvent(MotionEvent event) {
            Log.d("TouchEvent", "Touched at " +event.getX() + ", " + event.getY());
            touchStart = System.currentTimeMillis();
            touchx = event.getX();
            touchy = event.getY();
        }

        private void drawFrame() {
            final SurfaceHolder surfaceHolder = getSurfaceHolder();
            Canvas canvas = null;
            try {
                canvas = surfaceHolder.lockCanvas();
                if (canvas != null) {
                    Log.d("drawFrame", "Drawing Something!");
                    canvas.drawColor(Color.BLUE);
                    drawCircle(canvas);
                }
            } finally {
                if (canvas != null) {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }

            handler.removeCallbacks(mDraw);
            handler.postDelayed(mDraw, FRAME_RATE);
        }

        private void drawCircle(final Canvas canvas) {
            Log.d("drawCircle", "Trying to draw circle");
            if (System.currentTimeMillis() > touchStart + TOUCH_DURATION) {
                Log.d("drawCircle", "NO NEED TO Circle drawn");
                return;
            }
            float t = (float)(System.currentTimeMillis() - touchStart) / TOUCH_DURATION;
            float interpolation = interpolation(t);
            Log.d("drawCircle", "Circle drawn w alpha interp " + interpolation);
            mPaint.setAlpha((int)(150 * interpolation));
            canvas.drawCircle(touchx, touchy, 50, mPaint);
        }

        private float interpolation(final float t) {
            if (t < 0) {
                return 0;
            } else if (t > 1) {
                return 1;
            }
            return 1-t*t*t;
        }
    }

}
