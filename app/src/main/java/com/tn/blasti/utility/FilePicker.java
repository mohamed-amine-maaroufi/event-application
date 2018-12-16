package com.tn.blasti.utility;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by amine 15/12/2018.
 */

public class FilePicker {

    private static String pictureTempPath = "";
    private static final String CHOOSER_MSG = "Choose Option";
    private static final String IMAGE_PREFIX = "IMG_";
    private static final String IMAGE_EXTENSION = ".jpg";

    /**
     * Start picker intent
     *
     * @param context
     * @return
     */
    public static Intent getPickFileIntent(Context context) {
        Intent chooserIntent = null;

        List<Intent> intentList = new ArrayList<Intent>();
        addIntentsToList(context, intentList, getGalleryIntent());
        addIntentsToList(context, intentList, getCameraIntent(context));
        addIntentsToList(context, intentList, getFileIntent());

        if (intentList.size() > 0) {
            chooserIntent = Intent.createChooser(intentList.remove(intentList.size() - 1), CHOOSER_MSG);
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentList.toArray(new Parcelable[]{}));
        }

        return chooserIntent;
    }

    /**
     * Start camera picker intent
     *
     * @param context
     * @return
     */
    public static Intent getCameraPickerIntent(Context context) {
        Intent chooserIntent = null;

        List<Intent> intentList = new ArrayList<Intent>();
        addIntentsToList(context, intentList, getCameraIntent(context));

        if (intentList.size() > 0) {
            chooserIntent = Intent.createChooser(intentList.remove(intentList.size() - 1), CHOOSER_MSG);
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentList.toArray(new Parcelable[]{}));
        }

        return chooserIntent;
    }

    /**
     * Start gallery picker intent
     *
     * @param context
     * @return
     */
    public static Intent getGalleryPickerIntent(Context context) {
        Intent chooserIntent = null;

        List<Intent> intentList = new ArrayList<Intent>();
        addIntentsToList(context, intentList, getGalleryIntent());

        if (intentList.size() > 0) {
            chooserIntent = Intent.createChooser(intentList.remove(intentList.size() - 1), CHOOSER_MSG);
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentList.toArray(new Parcelable[]{}));
        }

        return chooserIntent;
    }

    /**
     * Get captured image path
     *
     * @param context
     * @param data
     * @return
     */
    public static String getPickedFilePath(Context context, Intent data) {
        String filePath = null;

        boolean isCameraIntent = (data == null || data.getData() == null);

        if (isCameraIntent) {
            filePath = pictureTempPath;
        } else {
            filePath = getImagePath(context, data.getData());
            /*if (filePath == null) {
                filePath = data.getData().getPath();
            }*/
        }

        return filePath;
    }

    private static String getImagePath(Context context, Uri imageUri) {
        String path = null;
        try {
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            // Get DB Cursor to get Image link
            Cursor cursor = context.getContentResolver().query(imageUri, filePathColumn, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
                // Get image path
                path = cursor.getString(columnIndex);
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return path;
    }

    /**
     * Creates a gallery intent
     *
     * @return
     */
    private static Intent getGalleryIntent() {
        // Define gallery intent
        return new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
    }

    private static Intent getFileIntent() {
        // Define file intent
        Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
        chooseFile.setType("*/*");
        return chooseFile;
    }


    /**
     * Creates a camera intent
     *
     * @return
     */
    private static Intent getCameraIntent(Context ctx) {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (cameraIntent.resolveActivity(ctx.getPackageManager()) != null) {

            cameraIntent.putExtra("return-data", true);

            File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
            String timeStamp = new SimpleDateFormat(DateUtils.DateTimeFormat.YYMMDD_HHMMSS.toString(), Locale.US).format(new Date());
            String imageFileName = IMAGE_PREFIX + timeStamp;
            File photoFile = new File(path, imageFileName + IMAGE_EXTENSION);

            pictureTempPath = photoFile.getAbsolutePath();

            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
        }

        return cameraIntent;
    }

    /**
     * Make a list of choices to display to user
     *
     * @param context
     * @param list
     * @param intent
     * @return
     */
    private static void addIntentsToList(Context context, List<Intent> list, Intent intent) {

        List<ResolveInfo> resInfo = context.getPackageManager().queryIntentActivities(intent, 0);
        for (ResolveInfo resolveInfo : resInfo) {
            String packageName = resolveInfo.activityInfo.packageName;
            Intent targetedIntent = new Intent(intent);
            targetedIntent.setPackage(packageName);
            list.add(targetedIntent);
        }
    }


}
