package com.nksy.livewallpapertutorial;

import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void set_static_wallpaper() {
        Log.d("Static Wallpaper", "Directly setting a static wallpaper");
        WallpaperManager wm = WallpaperManager.getInstance(MainActivity.this);
        try {
            wm.setResource(+R.mipmap.ic_launcher);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void set_live_wallpaper(View view) {
        Log.d("Live Wallpaper", "Attempting to set");
        final Intent intent = new Intent(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);
        intent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,
                new ComponentName(MainActivity.this, TutorialWallpaperService.class));
        startActivity(intent);
    }
}
