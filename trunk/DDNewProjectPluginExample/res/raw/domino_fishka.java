package com.assoft.GraphicsExample;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.Paint;
import android.app.Activity;
import android.content.res.Resources;
import android.widget.TextView;

public class Fishka extends TextView
{ 
   public static Resources res = null;
   private Activity parent;
   private Paint mPaint;
   private int w = 50;
   private int h = w;
   private int points1 = 0;
   private int points2 = 0;
   private Rect r1 = new Rect(0, 0, w - 1, h - 1);
   private Rect r2 = new Rect(0, h, w - 1, 2 * h - 1);
   
   public Fishka(Activity aParent, int aPoints1, int aPoints2, int aWidth)
   {
      super(aParent);
      w = aWidth;
      parent  = aParent;
      setWidth(w);
      setHeight(2 * h);
      points1 = aPoints1;
      points2 = aPoints2;
      setBackgroundColor(0xff000000);   
      mPaint = new Paint(); 
      mPaint.setStyle(Paint.Style.STROKE); 
      mPaint.setColor(0xFFFFFFFF);
      
      res = parent.getResources();
   }
   
   private void drawPoints(Canvas canvas, Rect rect, int points)
   {
      float cx = rect.centerX();
      float cy = rect.centerY();
      float dx = cx / 2;
      float d = w / 20;
      if (points == 1)
      {
        canvas.drawCircle(cx, cy, d, mPaint);
      } else if (points == 2)
      {
        canvas.drawCircle(cx + dx, cy - dx, d, mPaint);
        canvas.drawCircle(cx - dx, cy + dx, d, mPaint);
      } else if (points == 3)
      {
        canvas.drawCircle(cx, cy, d, mPaint);
        canvas.drawCircle(cx + dx, cy - dx, d, mPaint);
        canvas.drawCircle(cx - dx, cy + dx, d, mPaint);
       } else if (points == 4)
      {
        canvas.drawCircle(cx + dx, cy - dx, d, mPaint);
        canvas.drawCircle(cx - dx, cy + dx, d, mPaint);
        canvas.drawCircle(cx - dx, cy - dx, d, mPaint);
        canvas.drawCircle(cx + dx, cy + dx, d, mPaint);        
      }  else if (points == 5)
      {
        canvas.drawCircle(cx, cy, d, mPaint);
        canvas.drawCircle(cx + dx, cy - dx, d, mPaint);
        canvas.drawCircle(cx - dx, cy + dx, d, mPaint);
        canvas.drawCircle(cx - dx, cy - dx, d, mPaint);
        canvas.drawCircle(cx + dx, cy + dx, d, mPaint);        
      } else if (points == 6)
      {
        canvas.drawCircle(cx + dx, cy - dx, d, mPaint);
        canvas.drawCircle(cx - dx, cy + dx, d, mPaint);
        canvas.drawCircle(cx + dx, cy, d, mPaint);
        canvas.drawCircle(cx - dx, cy, d, mPaint);        
        canvas.drawCircle(cx - dx, cy - dx, d, mPaint);
        canvas.drawCircle(cx + dx, cy + dx, d, mPaint);        
      }
   }
   
   private void drawRect(Canvas canvas, Rect rect, int points)
   {
      canvas.drawRect(rect, mPaint);
      drawPoints(canvas, rect, points);
   }
   
   @Override
   protected void onDraw(Canvas canvas) 
   { 
      if (res == null)
        res = parent.getResources();
      drawRect(canvas, r1, points1);
      drawRect(canvas, r2, points2);
      super.onDraw(canvas); 
   }
}