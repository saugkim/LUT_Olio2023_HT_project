App: Lutemon v1.0 

Tool: Android studio (Electric Eel 2022.1.1 Patch 1)

Test device: Virtual device, Android 10(Q) API 29


Dependency: (additionally) 
  - implementation 'androidx.room:room-runtime:2.5.1'
  - annotationProcessor 'androidx.room:room-compiler:2.5.1'


<br>

## Views: 6 activities (including Main)


|Main|Create|List|Transfer|Train|Battle|
|-|-|-|-|-|-|
|<kbd> <img src="https://user-images.githubusercontent.com/25344978/233714344-1e062f7f-5186-46a6-882a-f1947b7e99fc.png" width=65> </kbd>|<kbd> <img src="https://user-images.githubusercontent.com/25344978/233714388-0e88d0a6-a488-4372-8939-cdc81f0f938f.png" width=65> </kbd>|<kbd> <img src="https://user-images.githubusercontent.com/25344978/233714427-4ecbe079-ac9d-4573-b3b3-32ca8eba99c1.png" width=65> </kbd>|<kbd> <img src="https://user-images.githubusercontent.com/25344978/233714474-337f83e3-a14f-493c-9daf-014e5ba6f5c5.png" width=65> </kbd>|<kbd> <img src="https://user-images.githubusercontent.com/25344978/233714578-3587d739-6c30-4944-acca-92c8d4a71ead.png" width=65> </kbd>|<kbd> <img src="https://user-images.githubusercontent.com/25344978/233714519-1eb2884e-7959-4e0a-89d6-3c03b5d59743.png" width=65> </kbd>|



## Demo clip

https://user-images.githubusercontent.com/25344978/233713590-b9f00b38-1fc5-44ac-9de9-f36a19601579.mp4




## Implemented features:

### MainActivity 
   - 5 buttons to move other 5 acitivies


### CreateActivity

<kbd> <img src="https://user-images.githubusercontent.com/25344978/233714388-0e88d0a6-a488-4372-8939-cdc81f0f938f.png" width=200> </kbd>

   - user can/must select a team (color) of lutemon when creating new lutemon
   - user can give custom name for lutemon when creating new lutemon
   - there is default given name for each color of lutemon, naming is not mandatory 
   - user can change name in ListActivity any time later
   - press button creates a lutemon object and insert it into database
   - user can directly move to Home and List view using toolbar menu on top right corner


### ListActivity

|Captured Image||Gif clip|
|-|-|-|
|<kbd> <img src="https://user-images.githubusercontent.com/25344978/233714427-4ecbe079-ac9d-4573-b3b3-32ca8eba99c1.png" width=200> </kbd>| | <img src="https://user-images.githubusercontent.com/25344978/233719267-9fdf4561-a950-4757-9039-31fb4d3a944f.gif" width="180"> |

   - view shows all lutemons in recycler view (ListAdapter, ViewHolder, RecyclerView)
   - user can edit name of lutemon(only name is editable) clicking the lutemon in list 
   - user can remove current lutemon press item view about 2 seconds (or more)
   - there is warning and user can cancel deleting action in case of accident removing
   - user can sort list by team (color from white to black) or by XP (from high to low)
   - user can directly move to Home using toolbar menu on top right corner
   - there is remove-all(RESET DB) option in toolbar menu, it will remove all saved data without warning


### TransferActivity

|Home view|Train view|Battle view| 
|-|-|-|
|<kbd> <img src="https://user-images.githubusercontent.com/25344978/233722089-faa33b43-2858-4e64-a870-df40a64e2d08.png" width=200> </kbd>|<kbd> <img src="https://user-images.githubusercontent.com/25344978/233722098-d5c2560c-d279-4eb1-a3f1-f6a8d1d12fc9.png" width=200> </kbd> |<kbd> <img src="https://user-images.githubusercontent.com/25344978/233722133-6c3bbf91-7a6f-4cc2-b52d-47fb03309dd7.png" width="200"> </kbd>|

   - This activity implemented using fragment
   - TabLayout and ViewPager2 is used to implement fragmentation
   - 3 views using one fragment layout (they have exactly same struncture)
   - in each view, there is list of lutemons at current arena on top
   - there is list of arena (2 for each view) to transfer to 
   - button click event to transfer selected lutemons to selected arena
   - user must select at least one lutemon and one arena (otherwise nothing happens)
   - lutemons transfered to Home will recover their health to maximum level 
   - user can directly move to MainActivity and Train or BattleAcitivity to train or to battle
   - ViewModel is implemented to update data - like currenthealth transfered to Home arena 
      

### TrainAcitivy

|Before Train|Win|Lose| 
|-|-|-|
|<kbd> <img src="https://user-images.githubusercontent.com/25344978/233725981-89a39146-6685-47f3-854e-4774e935e849.png" width=200> </kbd>|<kbd> <img src="https://user-images.githubusercontent.com/25344978/233725989-bd9b8eee-e2d2-4018-8af9-1feff936e047.png" width=200> </kbd> |<kbd> <img src="https://user-images.githubusercontent.com/25344978/233725997-c756e8fa-c642-4972-914b-6883fe4e1a82.png" width=200> </kbd>|

   - user can select any one of lutemons in train arena (must select ONE to train)
   - user can select any one of opponent in the list (represent of color)
   - selected opponent will have same xp as selected lutemon
   - user's lutemon always attacks first then opponent's turn to attack
   - when one of party reached zero health, battle ends and shows brief result in bottom
   - case of user's lutemon wins, lutemon will get 1 xp (lose no xp gain)
   - to heal lutemon to maximum level of health, lutemon must be transfered to Home arena
   - opponent will have maximum health always, when battle starts
   - ViewModel is used to update lutemon stats before and after training (health and xp)
   - user can directly move to MainAcitivity and to TransferActivity 
   - user cannot transfer to other arena in this view



### BattleActivity    


|Battle1|Result|Battle2|result |
|-|-|-|-|
|<kbd> <img src="https://user-images.githubusercontent.com/25344978/233734598-28681c00-6582-4dbd-ae80-dc5340c1cf41.png" width=200> </kbd>|<kbd> <img src="https://user-images.githubusercontent.com/25344978/233734633-c707ba2c-6df0-4ab1-b051-da8c23f2981f.png" width=200> </kbd> |<kbd> <img src="https://user-images.githubusercontent.com/25344978/233734673-476f11c1-ab13-4097-89cf-f37eb87390b9.png" width=200> </kbd>|<kbd> <img src="https://user-images.githubusercontent.com/25344978/233734794-c1229982-61c1-499c-8b38-b965f55f8e77.png" width=200> </kbd>|

  - user selects two lutemons from the list 
  - it is random which lutemon attacks first against the other 
  - battle ends one of party lose health completely
  - winner will get 1 xp, loser will be removed immediately when battle ends (from list and from database)
  - battle logic is same as train battle
  - user must transfer lutemon to HOME arena to heal up
  - battle is based on the text (no graphical implementation)
  - user can move directly to MainActivity and TransferActivity
