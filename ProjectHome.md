# This is an example illustration DroidDevelop plugins creation. #

There are several type of plugins for DroidDevelop available.

### 1. Common plugins. ###

This is plugins which commands adding to the "Plugins..." submenu of main menu.


To returns a list of commands, you need process next intent:

"com.assoft.DroidDevelop.getCommonPluginCommandsNames".

To process command, you need process next intent:

"com.assoft.DroidDevelop.processCommonPluginCommand"



There are next variables available (only for process command intent):

> - "android.intent.extra.Name" (String in parameter);

> - "android.intent.extra.BuildFile" (String in parameter);

> - "android.intent.extra.ProjectPath" (String in parameter);

> - "android.intent.extra.CurrentEditFile" (String in parameter);

> - "android.intent.extra.CommandsCount" (int out parameter);



> - "android.intent.extra.CommandName0" (String out parameter);

> - "android.intent.extra.CommandData0" (String out parameter);

> - "android.intent.extra.CommandName1" (String out parameter);

> - "android.intent.extra.CommandData1" (String out parameter);

> - "android.intent.extra.CommandName2" (String out parameter);

> - "android.intent.extra.CommandData2" (String out parameter);


> ..........................................................................................
..........................

> - "android.intent.extra.CommandName" + Integer(CommandsCount -1).toString() (String out parameter);

> - "android.intent.extra.CommandData" + Integer(CommandsCount -1).toString() (String out parameter);


For some commands can be needed more that one "ComandData". For second command data use suffix "A", for thirdth suffix "B".

For examle  "android.intent.extra.CommandDataA0", "android.intent.extra.CommandDataB2" etc.

In command declaration I will use standart function declaration but you need transfer all parameters as string (int parameters confert from string on DroidDevelop code).



You can get those variables by

> String Intent.getStringExtra(String)

function

You can set those variables by

> void Intent.putExtra(String, String)

> void Intent.putExtra(String, int)

functions..



In post processing has ability to process some result commands.



Now next command names available:

> - void InsertText(String text) -  insert text from "CommandData" to current editor selection.

> - void HighlightText(int start, int end) - highlight editor text from "start" to "end" position.

> - void ClearHighlight() - clear all highlighting what made by "HighlightText" command.

> - void AddPanel(String name) - add plug-in panel with "name" name.

> - void ShowPanel(String name) - show panel with "name" name.

> - void RemovePanel(String name) - delete panel with "name" name.

> - void AddLastPanelButton(String buttonName, String buttonText) - add button to last added panel. When button pressed your appl will get "com.assoft.DroidDevelop.processNewProjectCommand" intent with buttonName as commandName.

> - void AddLastPanelText(String text) - add text to last added panel.

> - void ShowText(String text) - show text as alert dialog.

> - void OpenFile(String fileName) - open file with "fileName" in editor.

> - void CreateProject(String projectName) - create new project with "projectName" name on the assoft projects default path.



> You can see usage example in the "DDPluginsCreation" project. It's available on the "DDNewProjectPluginExample" plug-in or in google code.




### 2. New project plugins. ###

This is plugins which commands adding to the "New Project..." submenu of main menu.


To returns a list of commands, you need process next intent:

"com.assoft.DroidDevelop.getNewProjectCommandsNames".

To process command, you need process next intent:

"com.assoft.DroidDevelop.processNewProjectCommand"


There are next variables available (only for process command intent):

> - "android.intent.extra.ProjectPath" (out parameter);

> - "android.intent.extra.ProjectPackage" (out parameter);

> - "android.intent.extra.ProjectName" (out parameter).

You can set those variables by

> void Intent.putExtra(String, String)

function.


In post processing DroidDevelop will open your new project.

### 3. Refuctoring plugins. ###


This is plugins which commands adding to the submenu of the "Refactoring" button.


To returns a list of commands, you need process next intent:

"com.assoft.DroidDevelop.getRefactCommandsNames".

To process command, you need process next intent:

"com.assoft.DroidDevelop.processRefactCommand"


There are next variables available (only for process command intent):

> - "android.intent.extra.Name" (in parameter);

> - "android.intent.extra.Text" (in parameter);

> - "android.intent.extra.SelectionStart" (in/out parameter);

> - "android.intent.extra.SelectionEnd" (in/out parameter).

You can set those variables by

> void Intent.putExtra(String, String)

> void Intent.putExtra(String, int)

functions.

You can get those variables by

> String Intent.getStringExtra(String)

> int Intent.getIntExtra(String, int)

functions.

Result text you can set by

> void Intent.setData(Uri)

function.


In post processing DroidDevelop will set your result text to current and set selection according  your selection variables value.


## To create your self plugin you need makes next steps: ##

### 1. Add next strings in your activity declaration in the AndroidManifest.xml file: ###

```xml

<intent-filter>
<action android:name="com.assoft.DroidDevelop.getRefactCommandsNames" />
<category android:name="android.intent.category.DEFAULT" />


Unknown end tag for &lt;/intent-filter&gt;


<intent-filter>
<action android:name="com.assoft.DroidDevelop.processRefactCommand" />
<category android:name="android.intent.category.DEFAULT" />


Unknown end tag for &lt;/intent-filter&gt;


```

### 2. In your main activity your need to programming reaction to "com.assoft.DroidDevelop.getRefactCommandsNames" intent action. ###

For this you need set your command names separated by "|" as intent data.

For example:

```java

Intent I = getIntent();
if (I == null)
{
finishActivity(RESULT_CANCELED);
return;
}
if (I.getAction().contentEquals("com.assoft.DroidDevelop.getRefactCommandsNames"))
{
I.setData("Command1|Command2|Command3");
setResult(RESULT_OK, I);
}
```

### 3. At last your need to programming reaction to "com.assoft.DroidDevelop.processRefactCommand" intent action. ###

In this case you can access to next variables:

- command name

- current DroidDevelop editor content

- current DroidDevelop editor selection start

- current DroidDevelop editor selection end


You can access to this variables by next code:

```java

Intent intent = getIntent();
if (intent == null)
return;
String name = intent.getStringExtra("android.intent.extra.Name");
String text = intent.getStringExtra("android.intent.extra.Text");
int start = intent.getIntExtra("android.intent.extra.SelectionStart", 0);
int end = intent.getIntExtra("android.intent.extra.SelectionEnd", 0);
```

After process your command you need to set your result text and selection back to intent.

For example:

```java

if (name == "Clear Text")
{
intent.putExtra("android.intent.extra.SelectionStart", 0);
intent.putExtra("android.intent.extra.SelectionEnd", 0);
intent.setData(Uri.parse(""));
setResult(RESULT_OK, intent);
}
```

# There are also available "DDNewProjectPluginExample". #

You can use it similar to "DDRefactoringPluginDemo".


[![](http://www.en.assoft.ru/_/rsrc/1319896041100/config/customLogo.gif)](http://www.en.assoft.ru/)