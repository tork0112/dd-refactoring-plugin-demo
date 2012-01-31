package com.assoft.DDRefactoringPluginExample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.net.Uri;
import android.app.AlertDialog;

public class MainActivity extends Activity
{  
  String[] names = {"New Class", "Comment", "Multiline comment"};
  
  private String names2Query()
  {
     String res = "";
     for (int i = 0; i < names.length; i++)
     {
       res += names[i];
       if (i < names.length - 1)
         res += "|";
     }
     return res;
  }
  
  private String addLine(String dest, String s, int curIndex)
  {
     int[] arr = {10};
     String newStr = new String(arr, 0, 1) + s;
     return addText(dest, newStr, curIndex);
  }
  
  private String addText(String dest, String s, int curIndex)
  {
     return dest.substring(0, curIndex) + s + dest.substring(curIndex, dest.length());
  }
  
  private String processName(String name, String text, int start, int end, Intent intent)
  {
     String res = text;
     if (name.contentEquals(names[0]))
     {
        res = addText(res, "public class ", start + res.length() - text.length());
        int newSelection = start + res.length() - text.length();
        res = addLine(res, "{", start + res.length() - text.length()); 
        res = addLine(res, "", start + res.length() - text.length());         
        res = addLine(res, "}", start + res.length() - text.length());                
        intent.putExtra("android.intent.extra.SelectionStart", newSelection);
        intent.putExtra("android.intent.extra.SelectionEnd", newSelection);                  
     };
     if (name.contentEquals(names[1]))
     {
        int cnt = 2;
        res = addText(res, "//", start + res.length() - text.length()); 
        int n = res.indexOf(10, start);
        while ((n > 0)&&(n < end))
        {
          res = addText(res, "//", n + 1); 
          cnt += 2;
          n = res.indexOf(10, n + 2);
        }    
        intent.putExtra("android.intent.extra.SelectionEnd", end + cnt);               
     };
     if (name.contentEquals(names[2]))
     {
        res = addText(res, "/*", start + res.length() - text.length()); 
        res = addText(res, "*/", end + res.length() - text.length()); 
        intent.putExtra("android.intent.extra.SelectionEnd", end + 4);              
     }
     return res;
  }
  
  private void processRefactCommand(Intent intent)
  {     
     try 
     {
        String name = intent.getStringExtra("android.intent.extra.Name");
        String text = intent.getStringExtra("android.intent.extra.Text");
        int start = intent.getIntExtra("android.intent.extra.SelectionStart", 0);
        int end = intent.getIntExtra("android.intent.extra.SelectionEnd", 0);
        
        String res = processName(name, text, start, end, intent);
        intent.setData(Uri.parse(res));         
        setResult(RESULT_OK, intent);
     }
     catch (Exception e) 
     {
     
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Error: " + e.getMessage());      
        builder.show();
     
        finishActivity(RESULT_CANCELED);
     }
  }

  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    Intent I = getIntent();
    if (I == null)
    {
       finishActivity(RESULT_CANCELED);
       return;
    }
    if (I.getAction().contentEquals("com.assoft.DroidDevelop.getRefactCommandsNames"))
    {
       I.setData(Uri.parse(names2Query())); 
       setResult(RESULT_OK, I);
    }
    if (I.getAction().contentEquals("com.assoft.DroidDevelop.processRefactCommand"))
       processRefactCommand(I);
    finish();
  }
} 
