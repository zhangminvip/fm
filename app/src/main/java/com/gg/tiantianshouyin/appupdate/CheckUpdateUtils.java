package com.gg.tiantianshouyin.appupdate;

import android.util.Log;

import com.gg.tiantianshouyin.appupdate.bean.JsonsRootBean;
import com.google.gson.Gson;


import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * User: Losileeya (847457332@qq.com)
 * Date: 2016-09-27
 * Time: 15:29
 * 类描述：
 *
 * @version :
 */
public class CheckUpdateUtils {

    private static final String TAG = "CheckUpdateUtils";


    /**
     * 检查更新
     */
    @SuppressWarnings("unused")
    public static void checkUpdate(final String appName, final String curVersionCode, final CheckCallBack updateCallback) {
//     ApiService apiService=   ServiceFactory.createServiceFrom(ApiService.class);
//        apiService.getUpdateInfo()//测试使用
//                //   .apiService.getUpdateInfo(appCode, curVersion)//开发过程中可能使用的
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<UpdateAppInfo>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onNext(UpdateAppInfo updateAppInfo) {
//                        if (updateAppInfo.error_code == 0 || updateAppInfo.data == null ||
//                                updateAppInfo.data.updateurl == null) {
//                            updateCallback.onError(); // 失败
//                        } else {
//                            updateCallback.onSuccess(updateAppInfo);
//                        }
//                    }
//                });
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client = new OkHttpClient();
//                    MediaType mediaType = MediaType.parse("application/json");
//                    JsonObject jsonObject = new JsonObject();
//
//                        jsonObject.addProperty("appname",appName);
//                        jsonObject.addProperty("versioncode",curVersionCode);
//
//                    Log.d(TAG,jsonObject.toString());
//                    RequestBody requestBody = RequestBody.create(mediaType,jsonObject.toString());

                    Log.d(TAG," sljdfk");
                    RequestBody requestBody = new FormBody.Builder()
                            .add("appname",appName)
                            .add("versioncode",curVersionCode)
                            .build();

                    Log.d(TAG," s");

                    Request request = new Request.Builder().url("http://192.168.124.26:8080/znsb/appinfo/getAppinfoMsg.do").post(requestBody).build();
                    Log.d(TAG," 1");

                    Response response = client.newCall(request).execute();
                    Log.d(TAG,"response");
                    if(response.isSuccessful()){
                        Log.d(TAG,"response is successful");
                        String responseData = response.body().string();
                        Log.d(TAG,responseData);
                        Gson gson = new Gson();
                        JsonsRootBean updateAppInfo = gson.fromJson(responseData,JsonsRootBean.class);
                        Log.d(TAG,"getUpdate" + updateAppInfo.getData().getUpdate()+"getfilepath"+updateAppInfo.getData().getForceupdate());
                        Log.d(TAG,"updateInfo" + updateAppInfo);
                        if(updateAppInfo.getData().getUpdate().equals("no") ){

                            updateCallback.onError();

                        }else if(updateAppInfo.getData().getUpdate().equals("yes")){

                            updateCallback.onSuccess(updateAppInfo);

                        }

                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();

    }


    public interface CheckCallBack{
        void onSuccess(JsonsRootBean updateInfo);
        void onError();
    }


}
