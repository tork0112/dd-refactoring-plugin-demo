package com.assoft.DDNewProjectPluginExample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.net.Uri;
import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.io.*;

public class MainActivity extends Activity
{  
  String[] names = {"DDNewProjectPluginExample", "DDRefactoringPluginExample", "GraphicsExample"};
  
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
  
  public static void forceDirectory(String path)
  {
     File f = new File(path);
     f.mkdirs();
  }
   
  public static Boolean fileExists(String filename)
  {
     File f = new File(filename);
     return f.exists();     
  }
  
   private void copyFileFromRaw(int resID, String resFile)
   {
     try 
     {
         InputStream s = getResources().openRawResource(resID);
         if ((s == null) || (s.available() <= 0))
            return;
         final byte[] bytes = new byte[s.available()];
         s.read(bytes);
         File f = new File(resFile);       
         OutputStream out = new BufferedOutputStream(new FileOutputStream(f)); 
         out.write(bytes);
         out.flush();
         out.close();
      }
      catch (Exception e) 
      {
      }
   }
  
   private void copyLogoFile(int resId, String outFile)
   {
      try 
      {
         Bitmap b = BitmapFactory.decodeResource(getResources(), resId);
         File f = new File(outFile);       
         OutputStream out = new BufferedOutputStream(new FileOutputStream(f));       
         b.compress(Bitmap.CompressFormat.PNG, 100, out);
         out.flush();
         out.close();
      }
      catch (Exception e) 
      {
      }
   }
  
  private String processName(String name, Intent intent)
  {
     String subPath = "/mnt";
     if (fileExists("/mnt/storage/sdcard"))
        subPath = "/mnt/storage/sdcard";
     else if  (fileExists("/mnt/sdcard"))
        subPath = "/mnt/sdcard";        
     String projectPath = subPath + "/assoft/examples/" + name; 
     
     intent.putExtra("android.intent.extra.ProjectPath", projectPath);
     intent.putExtra("android.intent.extra.ProjectPackage", "com.assoft");
     intent.putExtra("android.intent.extra.ProjectName", name);
     projectPath += "/";
     String pkgPath = "com/assoft/";
     
      forceDirectory(projectPath + "gen/" + pkgPath + name);
      forceDirectory(projectPath + "libs");
      forceDirectory(projectPath + "out/test/" + name);
      forceDirectory(projectPath + "res/drawable-hdpi"); 
      forceDirectory(projectPath + "res/drawable-ldpi"); 
      forceDirectory(projectPath + "res/drawable-mdpi"); 
      forceDirectory(projectPath + "res/layout"); 
      forceDirectory(projectPath + "res/menu"); 
      forceDirectory(projectPath + "res/values"); 
      forceDirectory(projectPath + "src/" + pkgPath + name);     

      if (name.contentEquals(names[0]))
      {
        copyFileFromRaw(R.raw.newprj_androidmanifest, projectPath + "AndroidManifest.xml");
        copyFileFromRaw(R.raw.newprj_mainactivity, projectPath + "src/" + pkgPath + name + "/MainActivity.java");    
        copyFileFromRaw(R.raw.newprj_buildapk, projectPath + "buildApk.bsh");       
        
        forceDirectory(projectPath + "res/raw");   
        copyFileFromRaw(R.raw.newprj_androidmanifest, projectPath + "res/raw/newprj_androidmanifest.xml");
        copyFileFromRaw(R.raw.newprj_mainactivity, projectPath + "res/raw/newprj_mainactivity.java");        
        copyFileFromRaw(R.raw.newprj_buildapk, projectPath + "res/raw/newprj_buildapk.bsh");         
                           
        copyFileFromRaw(R.raw.refact_androidmanifest, projectPath + "res/raw/refact_androidmanifest.xml");
        copyFileFromRaw(R.raw.refact_mainactivity, projectPath + "res/raw/refact_mainactivity.java");          
        copyFileFromRaw(R.raw.refact_buildapk, projectPath + "res/raw/refact_buildapk.bsh");              

        copyFileFromRaw(R.raw.domino_androidmanifest, projectPath + "res/raw/domino_androidmanifest.xml");
        copyFileFromRaw(R.raw.domino_mainactivity, projectPath + "res/raw/domino_mainactivity.java");  
        copyFileFromRaw(R.raw.domino_desk, projectPath + "res/raw/domino_desk.java");  
        copyFileFromRaw(R.raw.domino_fishka, projectPath + "res/raw/domino_fishka.java");      
        copyFileFromRaw(R.raw.domino_buildapk, projectPath + "res/raw/domino_buildapk.bsh");                
        copyFileFromRaw(R.raw.domino_main_layout, projectPath + "res/raw/domino_main_layout.xml");                        
      }
      else if (name.contentEquals(names[1]))
      {
        copyFileFromRaw(R.raw.refact_androidmanifest, projectPath + "AndroidManifest.xml");
        copyFileFromRaw(R.raw.refact_mainactivity, projectPath + "src/" + pkgPath + name + "/MainActivity.java");      
        copyFileFromRaw(R.raw.refact_buildapk, projectPath + "buildApk.bsh");       
      }  
      else if (name.contentEquals(names[2]))
      {
        copyFileFromRaw(R.raw.domino_androidmanifest, projectPath + "AndroidManifest.xml");
        copyFileFromRaw(R.raw.domino_mainactivity, projectPath + "src/" + pkgPath + name + "/MainActivity.java");  
        copyFileFromRaw(R.raw.domino_desk, projectPath + "src/" + pkgPath + name + "/Desk.java");  
        copyFileFromRaw(R.raw.domino_fishka, projectPath + "src/" + pkgPath + name + "/Fishka.java");      
        copyFileFromRaw(R.raw.domino_buildapk, projectPath + "buildApk.bsh");             
        copyFileFromRaw(R.raw.domino_main_layout, projectPath + "res/layout/main.xml");
      }         
     copyLogoFile(R.drawable.icon, projectPath + "res/drawable-hdpi/icon.png");     
     return "";
  }
  
  private void processRefactCommand(Intent intent)
  {     
     try 
     {
        String name = intent.getStringExtra("android.intent.extra.Name");        
        String res = processName(name, intent);
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
    if (I.getAction().contentEquals("com.assoft.DroidDevelop.getNewProjectCommandsNames"))
    {
       I.setData(Uri.parse(names2Query())); 
       setResult(RESULT_OK, I);
    }
    if (I.getAction().contentEquals("com.assoft.DroidDevelop.processNewProjectCommand"))
       processRefactCommand(I);
    finish();
  }
} 

