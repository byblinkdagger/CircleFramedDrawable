# CircleFramedDrawable
It can make any imageview convert into a circleimageview.

# use

```
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.workingdog);
        mImage.setImageDrawable(CircleFramedDrawable.getInstance(this,bitmap,800));
```
