package com.example.viredapp.ui;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.viredapp.R;
import com.example.viredapp.model.PostFeed;
import com.example.viredapp.utilities.ApiClient;
import com.example.viredapp.utilities.UserClient;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShareFragment extends Fragment {
    private static final int REQUEST_CAMERA = 0, REQUEST_GALLERY = 1;
    String mCurrentPhotoPath;
    Button share;
    Button takePicture;
    EditText location;
    ImageView imageView;
    Bitmap imageBitmap;
    private static String globalPost = null;

    public ShareFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        selectImage();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_share, container, false);
        share = view.findViewById(R.id.share);
        takePicture = view.findViewById(R.id.takePicture);
        imageView = view.findViewById(R.id.image);
        location = view.findViewById(R.id.location);
        takePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharePost();
            }
        });
        return view;
    }


    private void selectImage() {
        final CharSequence[] items = {"Camera", "Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Select Image");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (items[which].equals("Camera")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if(intent.resolveActivity(getActivity().getPackageManager()) != null){
                        //Create file to store image
                        File photofile = null;
                        try{
                            photofile = createImageFile();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        if (photofile != null){
                            Uri photUri = FileProvider.getUriForFile(getContext(),"com.example.android.fileprovider",photofile);
                             intent.putExtra(MediaStore.EXTRA_OUTPUT,photUri);
                            startActivityForResult(intent, REQUEST_CAMERA);
                        }
                    }

                } else if (items[which].equals("Gallery")) {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(intent.createChooser(intent, "Select File"), REQUEST_GALLERY);

                } else if (items[which].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


    /**
     * @{Documentation} Boilerplate Code from google.androiddevelopers.com
     * Camera and Gallery Implementation
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CAMERA) {
                //Bundle bundle = data.getExtras();
              //  final Bitmap bmp = (Bitmap) data.getExtras().get("data");
                //imageView.setImageBitmap(bmp);
                Glide.with(this).load(imageFilePath).into(imageView);
                //imageBitmap = bmp;
                globalPost = imageFilePath;
                share.setVisibility(View.VISIBLE);
            } else if (requestCode == REQUEST_GALLERY) {

                Uri selectedImageUri = data.getData();
                imageView.setImageURI(selectedImageUri);
                try {
                    String[] filePathColumn = { MediaStore.Images.Media.DATA };

                    Cursor cursor = getActivity().getContentResolver().query(selectedImageUri,
                            filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    globalPost = picturePath;
                    cursor.close();

                    share.setVisibility(View.VISIBLE);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }

    private String imageToString(Bitmap imageBitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imgByte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgByte,Base64.DEFAULT);
    }


    private void sharePost(){
        File file = new File(globalPost);
        RequestBody filePart = RequestBody.create(MediaType.parse("image/*"),file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("m_url",file.getName(),filePart);
        Timber.i(file.toString());

        String loc = location.getText().toString();
        RequestBody loc_req = RequestBody.create(MultipartBody.FORM,loc);
        if(loc !=null && file != null){
            Call<ResponseBody> call = ApiClient.getApiClient().create(UserClient.class).postFeedItem(loc_req,body);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    Timber.i(String.valueOf(response.code()));
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Timber.e(t.getMessage()+"onFailure()");
                }
            });
        }
    }


    String imageFilePath;
    private File createImageFile() throws IOException {
        String timeStamp =
                new SimpleDateFormat("yyyyMMdd_HHmmss",
                        Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir =
                getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        imageFilePath = image.getAbsolutePath();
        return image;
    }
}
