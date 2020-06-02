package com.example.deneme.ui.rapor;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.deneme.ComplaintServiceGrpc;
import com.example.deneme.MainActivity;
import com.example.deneme.R;
import com.example.deneme.newComplaint;
import com.example.deneme.result;
import com.example.deneme.ui.map.MapsFragment;
//import com.google.firebase.storage.FirebaseStorage;
//import com.google.firebase.storage.StorageReference;

import org.json.JSONArray;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import static android.app.Activity.RESULT_OK;


public class RaporFragment extends Fragment {
    ImageView imgUpload;
    Button btnUpload, btnConfirm,btnGoMap;
    EditText etAd, etMail;
    TextView txtMailInvalid, txtNameInvalid, txtPhotoInvalid, txtAdres,txtAddressInvalid;
   // FirebaseStorage storage;
   // StorageReference storageReference;
    static final int SELECT_IMAGE = 12;
    static final int TAKE_IMAGE = 5;
    String isim;
    String mail;
    String adres;

    View root;
   Context context = null;

    @Override

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_rapor, container, false);

        init();
        setAddress();

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setAlertDialog(getActivity());

            }
        });
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adres=txtAdres.getText().toString();
                if (imgUpload.getDrawable() != null && !isim.isEmpty() && !mail.isEmpty() && !adres.isEmpty()) {
                    txtPhotoInvalid.setVisibility(View.GONE);
                    txtAddressInvalid.setVisibility(View.GONE);
                    //***************************



                } else {

                    txtMailInvalid.setVisibility(View.VISIBLE);
                    txtAddressInvalid.setVisibility(View.VISIBLE);
                    txtNameInvalid.setVisibility(View.VISIBLE);
                    txtPhotoInvalid.setVisibility(View.VISIBLE);

                }

                new GrpcTask(getActivity()).execute(
                        "10.0.2.2",
                        "",
                        "50052",
                        txtAdres.getText().toString(),
                        etMail.getText().toString(),
                        "//",
                        etAd.getText().toString()




                );
                setAlertDialogSuccess(getActivity());
            }
        });
        btnGoMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MapsFragment mapsFragment = new MapsFragment();
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment, mapsFragment)
                        .commit();
            }
        });
        return root;
    }

    private void init() {
        btnUpload = root.findViewById(R.id.btnUpload);
        btnConfirm = root.findViewById(R.id.btnConfirm);
        imgUpload = root.findViewById(R.id.imgUpload);
        txtMailInvalid = root.findViewById(R.id.txtMailInvalid);
        txtNameInvalid = root.findViewById(R.id.txtNameInvalid);
        txtPhotoInvalid = root.findViewById(R.id.txtPhotoInvalid);
        txtAddressInvalid = root.findViewById(R.id.txtAddressInvalid);
        txtAdres = root.findViewById(R.id.txtAdres);
        etAd = root.findViewById(R.id.etAd);
        etMail = root.findViewById(R.id.etMail);
        btnGoMap = root.findViewById(R.id.btnGoMap);

        etAd.addTextChangedListener(formTextWatcher);
        etMail.addTextChangedListener(formTextWatcher);
        txtMailInvalid.setVisibility(View.GONE);
        txtNameInvalid.setVisibility(View.GONE);
        txtAddressInvalid.setVisibility(View.GONE);
        txtPhotoInvalid.setVisibility(View.GONE);
        txtAdres.setVisibility(View.GONE);

       // storage = FirebaseStorage.getInstance();
        //storageReference = storage.getReference();


    }

    private TextWatcher formTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            isim = etAd.getText().toString();
            mail = etMail.getText().toString();
            if (isim.isEmpty()) etAd.setBackgroundResource(R.drawable.custom_border_error);
            else if (!isim.isEmpty()){
                txtNameInvalid.setVisibility(View.GONE);
                etAd.setBackgroundResource(R.drawable.custom_border);
            }
            if (mail.isEmpty()) etMail.setBackgroundResource(R.drawable.custom_border_error);
            else if (!mail.isEmpty()) {
                etMail.setBackgroundResource(R.drawable.custom_border);
            }
        }


        @Override
        public void afterTextChanged(Editable s) {
            if (!mail.isEmpty()) {
                boolean result = isEmailValid(mail);
                if (result == false) {
                    txtMailInvalid.setVisibility(View.VISIBLE);
                    etMail.setBackgroundResource(R.drawable.custom_border_error);
                } else if (result == true) {
                    txtMailInvalid.setVisibility(View.GONE);
                    etMail.setBackgroundResource(R.drawable.custom_border);
                }
            }
        }
    };

    private void setAlertDialog(Context context) {
        final CharSequence[] options = {"Galeriden Seç", "Fotoğraf Çek"};
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Fotoğraf Ekle");
        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item] == ("Galeriden Seç")) {
                    uploadImageFromGallery();

                } else if (options[item] == ("Fotoğraf Çek")) {
                    captureImageFromCamera();

                }
            }
        });
        builder.show();
    }

    boolean isEmailValid(CharSequence mail) {
        return Patterns.EMAIL_ADDRESS.matcher(mail).matches();
    }

    private void uploadImageFromGallery() {
        //dosya seçme
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        //eylem yapılmak zorunda
        startActivityForResult(intent, SELECT_IMAGE);
    }

    private void captureImageFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, TAKE_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getActivity().getContentResolver().openInputStream(imageUri);//?
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);

                imgUpload.setImageBitmap(selectedImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (requestCode == TAKE_IMAGE && resultCode == RESULT_OK) {
            //önizleme

            Bundle bundle = data.getExtras();
            Bitmap bitmap = (Bitmap) bundle.get("data");

            imgUpload.setImageBitmap(bitmap);


        }
    }


    public void setAddress() {
        Bundle bundle = this.getArguments();

        if(bundle != null) {
            String adress = bundle.getString("key");
            txtAdres.setText("ADRES"+adres);//deneme null--> adress
            txtAdres.setVisibility(View.VISIBLE);
        }
    }
    private void setAlertDialogSuccess(Context context) {
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(context);
        alertDialog
                .setMessage("Rapor başarıyla gönderildi.")
                .setCancelable(false)
                .setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent=new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                    }

                }).show();
    }

    private static class GrpcTask extends AsyncTask<String, Void, String> {
        private final WeakReference<Activity> activityReference;

        private ManagedChannel channel;
        private JSONArray jsonObject;
        private GrpcTask(Activity activity) {
            this.activityReference = new WeakReference<Activity>(activity);
        }


        @Override
        protected String doInBackground(String... params) {
            String host = params[0];
            String message = params[1];
            String portStr = params[2];
            String adress = params[3];
            String email = params[4];
            String imageUrl = params[5];
            String name = params[6];
            int port = TextUtils.isEmpty(portStr) ? 0 : Integer.valueOf(portStr);
            try {
                channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build();
                ComplaintServiceGrpc.ComplaintServiceBlockingStub stub = ComplaintServiceGrpc.newBlockingStub(channel);
                newComplaint request = newComplaint.newBuilder().setAdress(adress).setEmail(email)
                        .setImageUrl(imageUrl)
                        .setName(name)

                        .build();
                result reply = stub.createComplaint(request);
                return reply.getStatus();


            } catch (Exception e) {
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                e.printStackTrace(pw);
                pw.flush();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                channel.shutdown().awaitTermination(1, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            Activity activity = activityReference.get();
            if (activity == null) {
                return;
            }


            //TextView resultText = (TextView) activity.findViewById(R.id.grpc_response_text);
            //resultText.setText(result);


/*
      Gson gson = new Gson();
      String jsonProduct = gson.toJson(result);
      System.out.println("jsonProducts = " + jsonProduct);
      */





        }
    }

}

