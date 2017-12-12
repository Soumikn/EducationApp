package com.example.asus.educationapp;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.theartofdev.edmodo.cropper.CropImage;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class EmploiFragment extends Fragment {
    public int ccc;
    SharedPreferences sp;
    Uri selectedImage;
    boolean empExist; // Emploi mawjoud walla lee

    public EmploiFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        sp = getActivity().getSharedPreferences("emp",Context.MODE_PRIVATE);

        empExist = sp.getBoolean("empExist",false);

        if(!empExist){
            openGallery();


        }
        return inflater.inflate(R.layout.fragment_emploi, container, false);
    }
    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select File"),1);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //CropImage.ActivityResult result = CropImage.getActivityResult(data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {

                selectedImage = data.getData();
                ccc = 1;
                Log.d("IMAGE","FIRST !!");
                CropImage.activity(selectedImage).start(getContext(),this);

            }

            if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                Log.d("IMAGE","SECOND !!");
                Uri resultUri = result.getUri();
                sp.edit().putString(getString(ccc),resultUri.toString()).apply();
                ccc++;
                if(ccc < 8){
                    CropImage.activity(selectedImage).start(getContext(),this);
                    ccc++;
                }
            }

        }else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            Exception error = result.getError();
            Log.d("ERREUR",error.toString());
        }
    }

}
