package com.wlj.bihu.utils;

import android.os.Environment;
import android.util.Log;

import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;
import com.qiniu.util.Auth;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

public class UpLoadQiNiu {


//    Configuration config = new Configuration.Builder()
//            .chunkSize(512 * 1024)        // 分片上传时，每片的大小。 默认256K
//            .putThreshhold(1024 * 1024)   // 启用分片上传阀值。默认512K
//            .connectTimeout(10)           // 链接超时。默认10秒
//            .useHttps(true)               // 是否使用https上传域名
//            .responseTimeout(60)          // 服务器响应超时。默认60秒
//            .recorder(recorder)           // recorder分片上传时，已上传片记录器。默认null
//            .recorder(recorder, keyGen)   // keyGen 分片上传时，生成标识符，用于片记录器区分是那个文件的上传记录
//            .zone(FixedZone.zone0)        // 设置区域，指定不同区域的上传域名、备用域名、备用IP。
//            .build();
//    // 重用uploadManager。一般地，只需要创建一个uploadManager对象
//    UploadManager uploadManager = new UploadManager(config);

    public static String host = "http://pn5ls8rob.bkt.clouddn.com/";

    public static void put(File file, String key, com.qiniu.android.storage.UpCompletionHandler upCompletionHandler,
                           UploadOptions options) {

        final UploadManager uploadManager = new UploadManager();

        Auth auth = Auth.create("girA1smLWF4By06EVKtNe16GzpD-G966YylvM0x9",
                "c-MezGUupJzcN5HbfWCuKV7dsZhMmPii7kJqiTEG");
        String token = auth.uploadToken("wljyes");

        try {
            uploadManager.put(file, key, token, upCompletionHandler, options);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
