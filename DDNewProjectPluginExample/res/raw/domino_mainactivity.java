package com.assoft.GraphicsExample;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout.LayoutParams;

public class MainActivity extends Activity
{
  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    
    ViewGroup scr = (ViewGroup)findViewById(R.id.vwView);
    scr.addView(new Desk(this), new LayoutParams(-1, -1));
  }
} 
