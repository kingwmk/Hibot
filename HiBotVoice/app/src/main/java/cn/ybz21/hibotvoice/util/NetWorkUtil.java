package cn.ybz21.hibotvoice.util;

import android.os.Handler;
import android.os.Message;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import cn.ybz21.hibotvoice.bean.ResultCode;


/**
 * Created by smarf on 2016/12/24.
 */

public class NetWorkUtil {
    HttpUtils httpUtils;
    Handler mHandler;

    public NetWorkUtil(Handler handler) {
        httpUtils = new HttpUtils();
        this.mHandler = handler;
    }

    public void uploadMethod(final RequestParams params, final String uploadHost) {
        httpUtils.send(HttpRequest.HttpMethod.POST, uploadHost, params, new RequestCallBack<String>() {
            @Override
            public void onStart() {
                //上传开始
                Message msg = new Message();
                msg.what = 0;
                mHandler.sendMessage(msg);
            }

            @Override
            public void onLoading(long total, long current, boolean isUploading) {
                //上传中
                Message msg = new Message();
                msg.what = 1;
                msg.obj = (double) current / (double) total;
                mHandler.sendMessage(msg);
            }

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                //上传成功，这里面的返回值，就是服务器返回的数据
                //使用 String result = responseInfo.result 获取返回值
                Message msg = new Message();
                msg.what = 2;
                String jsonData = responseInfo.result;

                ResultCode rc = new Gson().fromJson(jsonData, ResultCode.class);
                msg.arg1 = rc.getResult();
                mHandler.sendMessage(msg);
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                //上传失败
                Message msg1 = new Message();
                msg1.what = 3;
                mHandler.sendMessage(msg1);
//                Log.d(StaticValues.TAG, "onFailure");
            }
        });
    }
}
