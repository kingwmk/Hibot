package cn.ybz21.hibotvoice;

import android.app.ProgressDialog;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.http.RequestParams;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.ybz21.hibotvoice.bean.UploadBean;
import cn.ybz21.hibotvoice.util.NetWorkUtil;
import cn.ybz21.hibotvoice.util.StaticValues;
import cn.ybz21.hibotvoice.widget.RecordButton;

public class MainActivity extends AppCompatActivity {

    Button btnUpload;

    RecordButton btn;
    ProgressDialog mProgress;
    List<String> paths;
    String uploadHost = "http://192.168.0.101:8080/HiBotServer/appUp.action";
    String path = StaticValues.path + "aa.amr";
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0://开始
                    mProgress.show();
                    break;
                case 1://进度
                    double pro = (double) msg.obj;
                    mProgress.setProgress((int) (100 * pro));
                    break;

                case 2://success
                    mProgress.dismiss();
                    if (msg.arg1 == 1)
                        Toast.makeText(MainActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(MainActivity.this, "上传失败", Toast.LENGTH_SHORT).show();
                    break;
                case 3://failed
                    mProgress.dismiss();
                    Toast.makeText(MainActivity.this, "上传失败", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
        initDialog();
        addListener();
    }

    void initData() {
        paths = new ArrayList<String>();
    }

    void initDialog() {

        mProgress = new ProgressDialog(MainActivity.this);
        mProgress.setMax(100);
        mProgress.setTitle("上传进度");
        mProgress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
    }


    void upload(UploadBean bean) {

        RequestParams params = new RequestParams();
        params.addBodyParameter("data", new Gson().toJson(bean));
        for (int i = 0; i < paths.size(); i++) {
            params.addBodyParameter("files[" + i + "]", new File(paths.get(i)), "multipart/form-data");
        }
        for (int i = 0; i < paths.size(); i++) {
            params.addBodyParameter("uploadPaths[" + i + "]", paths.get(i));
        }
        new NetWorkUtil(mHandler).uploadMethod(params, uploadHost);
    }


    void initView() {
        btnUpload = (Button) findViewById(R.id.btn_upload);
        btn = (RecordButton) findViewById(R.id.button);
        btn.setSavePath(path);
    }

    void addListener() {

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UploadBean bean = new UploadBean();
                paths.clear();
                paths.add(path);
                upload(bean);
            }
        });
        btn.setOnFinishedRecordListener(new RecordButton.OnFinishedRecordListener() {
            @Override
            public void onFinishedRecord(String audioPath) {
                Toast.makeText(MainActivity.this, "audioPath:" + audioPath, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
