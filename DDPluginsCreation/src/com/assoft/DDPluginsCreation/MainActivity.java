package com.assoft.DDPluginsCreation;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.content.DialogInterface;
import android.net.Uri;
import android.app.AlertDialog;
import android.app.Dialog;

public class MainActivity extends Activity
{
  String[] names = {"Plugin Examples..."};
  
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
  
  String[] demoItems = {"Panel", "Two Panels", "Highlighting"};
  
  private void showPanel()
  {
      Intent intent = getIntent();
      intent.putExtra("android.intent.extra.CommandsCount", 3);
      intent.putExtra("android.intent.extra.CommandName0", "AddPanel");
      intent.putExtra("android.intent.extra.CommandData0", "Panel1");
      intent.putExtra("android.intent.extra.CommandName1", "AddLastPanelButton");
      intent.putExtra("android.intent.extra.CommandData1", demoItems[1]);
      intent.putExtra("android.intent.extra.CommandDataA1", "Hello World!!!");
      intent.putExtra("android.intent.extra.CommandName2", "ShowPanel");
      intent.putExtra("android.intent.extra.CommandData2", "Panel1");                          
      setResult(RESULT_OK, intent);
      finish();             
  }
  
  private void twoPanels()
  {
      Intent intent = getIntent();
      intent.putExtra("android.intent.extra.CommandsCount", 6);
      intent.putExtra("android.intent.extra.CommandName0", "AddPanel");
      intent.putExtra("android.intent.extra.CommandData0", "Panel1");
      intent.putExtra("android.intent.extra.CommandName1", "AddLastPanelButton");
      intent.putExtra("android.intent.extra.CommandData1", "close_panel");
      intent.putExtra("android.intent.extra.CommandDataA1", "Close Second Panel");
      intent.putExtra("android.intent.extra.CommandName2", "ShowPanel");
      intent.putExtra("android.intent.extra.CommandData2", "Panel1");            

      intent.putExtra("android.intent.extra.CommandName3", "AddPanel");
      intent.putExtra("android.intent.extra.CommandData3", "Panel2");
      intent.putExtra("android.intent.extra.CommandName4", "AddLastPanelText");
      intent.putExtra("android.intent.extra.CommandData4", "You can close it\nby first panel");
      intent.putExtra("android.intent.extra.CommandName5", "ShowPanel");
      intent.putExtra("android.intent.extra.CommandData5", "Panel2");                               
      setResult(RESULT_OK, intent);
      finish();               
  }
  
  private void showText()
  {
      Intent intent = getIntent();
      intent.putExtra("android.intent.extra.CommandsCount", 1);
      intent.putExtra("android.intent.extra.CommandName0", "ShowText");
      intent.putExtra("android.intent.extra.CommandData0", "Hello World!!!");                 
      setResult(RESULT_OK, intent);
      finish();             
  }  
  
  private void closePanel()
  {
      Intent intent = getIntent();
      intent.putExtra("android.intent.extra.CommandsCount", 1);
      intent.putExtra("android.intent.extra.CommandName0", "RemovePanel");
      intent.putExtra("android.intent.extra.CommandData0", "Panel2");                 
      setResult(RESULT_OK, intent);
      finish();               
  }
  
  private void highlightSelection()
  {
      Intent intent = getIntent();
      int start = intent.getIntExtra("android.intent.extra.SelectionStart", 0);
      int end = intent.getIntExtra("android.intent.extra.SelectionEnd", 0);           
      intent.putExtra("android.intent.extra.CommandsCount", 1);
      intent.putExtra("android.intent.extra.CommandName0", "HighlightText");
      intent.putExtra("android.intent.extra.CommandData0", Integer.toString(start));
      intent.putExtra("android.intent.extra.CommandDataA0", Integer.toString(end));          
      setResult(RESULT_OK, intent);
      finish();                               
  }
  
  private void clearHighlight()
  {
      Intent intent = getIntent();
      intent.putExtra("android.intent.extra.CommandsCount", 1);
      intent.putExtra("android.intent.extra.CommandName0", "ClearHighlight");  
      setResult(RESULT_OK, intent);
      finish();                    
  }
  
  private void highlightPanel()
  {
      Intent intent = getIntent();
      intent.putExtra("android.intent.extra.CommandsCount", 4);
      intent.putExtra("android.intent.extra.CommandName0", "AddPanel");
      intent.putExtra("android.intent.extra.CommandData0", "Panel1");
      intent.putExtra("android.intent.extra.CommandName1", "AddLastPanelButton");
      intent.putExtra("android.intent.extra.CommandData1", "highlight_selection");
      intent.putExtra("android.intent.extra.CommandDataA1", "Highlight Selection");
      intent.putExtra("android.intent.extra.CommandName2", "AddLastPanelButton");
      intent.putExtra("android.intent.extra.CommandData2", "clear_highlight");
      intent.putExtra("android.intent.extra.CommandDataA2", "Clear Highlight");      
      intent.putExtra("android.intent.extra.CommandName3", "ShowPanel");
      intent.putExtra("android.intent.extra.CommandData3", "Panel1");           

      setResult(RESULT_OK, intent);
      finish();                             
  }
  
  private void doProcessCommand()
  {
      AlertDialog.Builder builder = new AlertDialog.Builder(this);
      builder.setTitle("Choose Demo");  

      DialogInterface.OnClickListener buttonListener;
      buttonListener = new DialogInterface.OnClickListener() 
      {
        public void onClick(DialogInterface dialog, int which)
        {
           if (which == 0)
             showPanel();
           else if (which == 1)
             twoPanels();
           else if (which == 2)
             highlightPanel();             
        }
      };        
      builder.setItems(demoItems, buttonListener);      
      Dialog dlg = builder.create();
      dlg.show();    
  }
  
  private void processCommand()
  {
     String name = getIntent().getStringExtra("android.intent.extra.Name");        
     if (name.compareTo(names[0]) == 0)     
        doProcessCommand();
     else if (name.compareTo("hello_world") == 0)
        showText();
      else if (name.compareTo("close_panel") == 0)
        closePanel();    
      else if (name.compareTo("highlight_selection") == 0)
        highlightSelection();
      else if (name.compareTo("clear_highlight") == 0)
        clearHighlight();        
  }    
  
  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    Intent intent = getIntent();
    if (intent == null)
    {
       finishActivity(RESULT_CANCELED);
       return;
    }
    if (intent.getAction().contentEquals("com.assoft.DroidDevelop.getCommonPluginCommandsNames"))
    {
       intent.setData(Uri.parse(names2Query())); 
       setResult(RESULT_OK, intent);       
       finish();
    }
    if (intent.getAction().contentEquals("com.assoft.DroidDevelop.processCommonPluginCommand"))
       processCommand();
  }
} 
