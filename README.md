
<h1 align="center">SelectiveRecycleViewInBottomSheetDialog</h1>
<p align="center">Android native BottomsheetDialog in recycleview</p>
  
### Summary  

This library allows you to use the BottomsheetDialog in recycleview for item/s selection.

  
## Download  
  
> Step 1. Add the JitPack repository to your build file:
   
```gradle 
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```
	
> Step 2. Add the dependency:

```gradle

implementation 'com.github.sagar98:SelectiveRecycleViewInBottomSheetDialog:Tag'

```  
  
  
## Use  

It is recommended that you review the project to get a full understanding of library. 
> Step 1:-
To use bottom sheet in recycleview for selection of items, you need to implement ```CustomBottomSheetDialogInterface```
and override ```onCustomBottomSheetSelection()``` method.

Example:

```kotlin
class MainActivity : AppCompatActivity(), CustomBottomSheetDialogInterface {

  private var sampleList: ArrayList<SelectionListObject> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
     }

  override fun onCustomBottomSheetSelection(type: String) {
     when (type) {
      "Case A" -> {
           // your code here
      }
      "Case B" -> {
           // your code here
      }
   }
}
```

> Step 2:-
create arraylist which you have to provide to recycleview.
This arraylist will be of type SelectionListObject() which will contain<br>
val id: String, val value: String, var isSelected: Boolean<br>
If you have to display preselected item/s, set isSelected- true for respective item/s.

You can simply run for loop on your actual list and create ```SelectionListObject``` with id, value of each item and
then populate ```ArrayList<SelectionListObject>```.

>Step 3:-
Call ```CustomBottomSheetDialogClass()``` to display bottomsheet in recycleview for items selection.
 
Call ```CustomBottomSheetDialogClass()``` as shown below, where you want to open bottomsheet dialog recycleview with selectable items.<br>
Ex. On particular Button clicked action, bottomsheet dialog will open and user will select single or multiple items.

    val sampleBottomSheetDialog = CustomBottomSheetDialogClass(this,
            this, "Sample Title", sampleList, false)
    sampleBottomSheetDialog.show()
    sampleBottomSheetDialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.MATCH_PARENT)

Here, you need to pass five parameters to ```CustomBottomSheetDialogClass()```.
<br> 1st is ActivityContext,<br>
2nd is extended interface listener context,<br>
3rd is title string which will be displayed as bottomsheet title and we will use same string to differentiate between multiple BottomsheetDialog
 responses.<br>
4th is actual list of items- ArrayList<SelectionListObject><br>
5th is "isMultiSelectAllowed" boolean value.<br>
 true:- multiselection is allowed.<br>
 false:- Only single item selection is allowed.<br>
 
 Overrided below method from implementated interface will provide item/items selected by user.
3rd parameter in ```CustomBottomSheetDialogClass()``` is used in below overridden method to differentiate between multiple BottomsheetDialog
responses.
```

override fun onCustomBottomSheetSelection(type: String) {
     when (type) {
      "Sample Title" -> {
           // your code here
      }
      "Case B" -> {
           // your code here
      }
   }

```
